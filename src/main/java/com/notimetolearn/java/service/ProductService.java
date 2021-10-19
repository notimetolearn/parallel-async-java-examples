package com.notimetolearn.java.service;

import com.notimetolearn.java.domain.Product;
import static com.notimetolearn.java.util.CommonUtil.watch;
import static com.notimetolearn.java.util.LoggerUtil.log;
import com.notimetolearn.java.domain.ProductInfo;
import com.notimetolearn.java.domain.Review;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService {

    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public Product fetchProduct(String productId) {
        watch.start();

        ProductInfo productInfo = productInfoService.fetchProductInfo(productId);
        Review review = reviewService.fetchReviews(productId);

        watch.stop();
        log("Total Time Taken : "+ watch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductService productService = new ProductService(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.fetchProduct(productId);
        log("Product is " + product);

    }
}
