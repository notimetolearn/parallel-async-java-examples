package com.notimetolearn.java.service;

import com.notimetolearn.java.domain.Product;
import com.notimetolearn.java.util.CommonUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    ProductService productService = new ProductService(new ProductInfoService(), new ReviewService(),
            new InventoryService());

    @Test
    void fetchProduct() {

        //given
        String productId = "ABC123";

        //when
        Product product = productService.fetchProduct(productId);

        //then
        assertNotNull(product);
        assertEquals(productId, product.getProductId());
    }

    @Test
    void fetchProductAsync() {

        //given
        String productId = "ABC123";

        //when
        Product product = productService.fetchProductAsync(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void fetchProductFutureAsync() {
        CommonUtil.startTimer();
        //given
        String productId = "ABC123";
        //when
        CompletableFuture<Product> productFuture = productService.fetchProductFutureAsync(productId);
        //then
        productFuture.thenAccept(p -> {
            assertNotNull(p);
            assertTrue(p.getProductInfo().getProductOptions().size() > 0);
            assertNotNull(p.getReview());
        }).join();

        CommonUtil.timeTaken();
    }

    @Test
    void fetchProductAsyncWithInventory() {
        //given
        String productId = "ABC123";

        //when
        Product product = productService.fetchProductAsyncWithInventory(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertTrue(product.getProductInfo().getProductOptions().get(0).getInventory().getCount() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void fetchProductAsyncWithInventoryAsync() {
        //given
        String productId = "ABC123";

        //when
        Product product = productService.fetchProductAsyncWithInventoryAsync(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertTrue(product.getProductInfo().getProductOptions().get(0).getInventory().getCount() > 0);
        assertNotNull(product.getReview());
    }
}