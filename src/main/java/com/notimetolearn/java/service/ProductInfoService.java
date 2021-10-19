package com.notimetolearn.java.service;

import com.notimetolearn.java.domain.ProductInfo;
import com.notimetolearn.java.domain.ProductOption;
import com.notimetolearn.java.util.CommonUtil;

import java.util.Arrays;
import java.util.List;

public class ProductInfoService {

    public ProductInfo fetchProductInfo(String productId) {
        CommonUtil.delay(1000);

        List<ProductOption> productOptions = Arrays.asList(
                new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99)
        );

        return ProductInfo.builder().productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
