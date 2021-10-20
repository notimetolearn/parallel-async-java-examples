package com.notimetolearn.java.service;

import com.notimetolearn.java.util.CommonUtil;

import java.util.concurrent.CompletableFuture;

public class WelcomeService {

    private static final String HELLO = "Hello";
    private static final String HOW_ARE_YOU = "How are you?";

    public String sayHello() {
        CommonUtil.delay(1000);
        return HELLO;
    }

    public String askHowAreYou() {
        CommonUtil.delay(1000);
        return HOW_ARE_YOU;
    }

    public CompletableFuture<String> sayHelloFuture(String message) {
        return CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return message + ", " + HOW_ARE_YOU;
        });
    }
}
