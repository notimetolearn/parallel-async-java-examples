package com.notimetolearn.java.service;

import com.notimetolearn.java.domain.Review;
import com.notimetolearn.java.util.CommonUtil;

public class ReviewService {

    public Review fetchReviews(String productId) {
        CommonUtil.delay(1000);
        return new Review(200, 4.5);
    }
}
