package com.notimetolearn.java.util;

import com.notimetolearn.java.domain.checkout.Cart;
import com.notimetolearn.java.domain.checkout.CartItem;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataSet {

    public static List<String> lowerCaseAlphabetList = Arrays.asList("a", "b", "c", "a", "d", "e", "f", "e", "g", "h", "i");


    public static Cart createCart(int noOfItems) {
        Cart cart = new Cart();
        List<CartItem> cartItemList = new ArrayList<>();

        IntStream.rangeClosed(1, noOfItems)
                .forEach(i -> {
                    String cartItemName = "CartItem -".concat(i + "");
                    CartItem cartItem = new CartItem(i, cartItemName, generateRandomPrice(), noOfItems, false);
                    cartItemList.add(cartItem);
                });

        cart.setCartItemList(cartItemList);
        return cart;
    }

    public static List<String> namesList() {
        return Arrays.asList("Bob", "Jamie", "Jill", "Rick");
    }

    public static List<Integer> generateIntegerList(int maxNumber) {
        return IntStream.rangeClosed(1, maxNumber)
                .boxed().collect(Collectors.toList());
    }



    public static double generateRandomPrice() {
        int min = 50;
        int max = 100;

        return Math.random() * (max - min +1) + min;
    }
}
