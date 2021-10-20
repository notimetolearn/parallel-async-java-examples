package com.notimetolearn.java.future;

import com.notimetolearn.java.service.WelcomeService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class FutureWelcomeComponentTest {

    FutureWelcomeComponent welcomeComponent = new FutureWelcomeComponent(new WelcomeService());

    @Test
    void sayHelloInFuture() {
        //when
        CompletableFuture<String> hello = welcomeComponent.sayHelloInFuture();

        //then
        hello.thenAccept(message -> assertEquals("HELLO", message)).join();
    }


    @Test
    void sayHelloAndGreetCombinesInFuture() {

        //when
        CompletableFuture<String> hello = welcomeComponent.sayHelloAndGreetCombinesInFuture();

        //then
        hello.thenAccept(message -> assertEquals("HELLO, HOW ARE YOU?", message)).join();
    }

    @Test
    void sayHelloAndGreetComposeInFuture() {

        //when
        CompletableFuture<String> hello = welcomeComponent.sayHelloAndGreetComposeInFuture();

        //then
        hello.thenAccept(message -> assertEquals("HELLO, HOW ARE YOU?", message)).join();
    }
}