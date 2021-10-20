package com.notimetolearn.java.service;

import com.notimetolearn.java.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceExceptionTest {

    @Mock
    ProductInfoService productInfoService;

    @Mock
    ReviewService reviewService;

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    ProductService productService;


    @Test
    void fetchProductAsyncWithInventoryAsync_withReviewException() {

        //given
        String productId = "ABC123";
        when(productInfoService.fetchProductInfo(any())).thenCallRealMethod();
        when(reviewService.fetchReviews(any())).thenThrow(new RuntimeException("Review service error"));
        when(inventoryService.fetchInventory()).thenCallRealMethod();

        //when
        Product product = productService.fetchProductAsyncWithInventoryAsync(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().stream().forEach(
                pi -> assertNotNull(pi.getInventory())
        );
        assertEquals(0, product.getReview().getNoOfReviews());
    }

    @Test
    void fetchProductAsyncWithInventoryAsync_withProductInfoException() {

        //given
        String productId = "ABC123";
        when(productInfoService.fetchProductInfo(any())).thenThrow(new RuntimeException("Product Info error"));
        when(reviewService.fetchReviews(any())).thenCallRealMethod();

        //then
        assertThrows(RuntimeException.class, () -> productService.fetchProductAsyncWithInventoryAsync(productId));
    }
}