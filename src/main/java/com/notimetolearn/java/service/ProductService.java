package com.notimetolearn.java.service;

import com.notimetolearn.java.domain.*;

import static com.notimetolearn.java.util.CommonUtil.noOfCores;
import static com.notimetolearn.java.util.CommonUtil.watch;
import static com.notimetolearn.java.util.LoggerUtil.log;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProductService {

    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private final InventoryService inventoryService;

    public Product fetchProduct(String productId) {
        watch.start();

        ProductInfo productInfo = productInfoService.fetchProductInfo(productId);
        Review review = reviewService.fetchReviews(productId);

        watch.stop();
        log("Total Time Taken : "+ watch.getTime());
        return new Product(productId, productInfo, review);
    }

    public Product fetchProductAsync(String productId) {
        watch.start();
        CompletableFuture<ProductInfo> productInfoFuture = getProductInfoFuture(productId);
        CompletableFuture<Review> reviewFuture = getReviewFuture(productId);

        Product product = productInfoFuture.thenCombine(reviewFuture,
                (productInfo, review) -> new Product(productId, productInfo, review))
                .join();

        watch.stop();
        log("Total Time Taken : "+ watch.getTime());
        return product;
    }

    public CompletableFuture<Product> fetchProductFutureAsync(String productId) {

        CompletableFuture<ProductInfo> productInfoFuture = getProductInfoFuture(productId);
        CompletableFuture<Review> reviewFuture = getReviewFuture(productId);

        return productInfoFuture.thenCombine(reviewFuture,
                (productInfo, review) -> new Product(productId, productInfo, review));

    }

    public Product fetchProductAsyncWithInventory(String productId) {
        watch.start();
        CompletableFuture<ProductInfo> productInfoFuture = getProductInfoFuture(productId)
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventory(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewFuture = getReviewFuture(productId);

        Product product = productInfoFuture.thenCombine(reviewFuture,
                (productInfo, review) -> new Product(productId, productInfo, review))
                .join();

        watch.stop();
        log("Total Time Taken : "+ watch.getTime());
        return product;
    }

    public Product fetchProductAsyncWithInventoryAsync(String productId) {
        watch.start();
        CompletableFuture<ProductInfo> productInfoFuture = getProductInfoFuture(productId)
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventoryAsync(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewFuture = getReviewFuture(productId)
                .exceptionally(e -> {
                    log("Exception Occurred while fetching review: "+ e.getMessage());
                    return Review.builder().noOfReviews(0).overallRating(0.00).build();
                });

        Product product = productInfoFuture.thenCombine(reviewFuture,
                (productInfo, review) -> new Product(productId, productInfo, review))
                .whenComplete((r, e) -> log("Error Occurred: "+ e.getMessage()))
                .join();

        watch.stop();
        log("Total Time Taken : "+ watch.getTime());
        return product;
    }

    private CompletableFuture<ProductInfo> getProductInfoFuture(String productId) {
        return CompletableFuture.supplyAsync(() ->
                productInfoService.fetchProductInfo(productId));
    }

    private CompletableFuture<Review> getReviewFuture(String productId) {
        return CompletableFuture.supplyAsync(() ->
                reviewService.fetchReviews(productId));
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        List<ProductOption> productOptions = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    Inventory i = inventoryService.fetchInventory();
                    productOption.setInventory(i);
                    return productOption;
                }).collect(Collectors.toList());

        return productOptions;
    }

    private List<ProductOption> updateInventoryAsync(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> futureOptionsList = productInfo.getProductOptions()
                .stream()
                .map(productOption -> CompletableFuture.supplyAsync(() -> inventoryService.fetchInventory())
                        .thenApply(i -> {
                            productOption.setInventory(i);
                            return productOption;
                        }))
                .collect(Collectors.toList());

       return futureOptionsList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
