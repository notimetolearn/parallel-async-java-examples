package com.notimetolearn.java.service;

import com.notimetolearn.java.domain.checkout.CartItem;
import com.notimetolearn.java.util.CommonUtil;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        CommonUtil.delay(500);
        if (cartId == 7 || cartId == 9 || cartId == 11) {
            return true;
        }
        return false;
    }
}
