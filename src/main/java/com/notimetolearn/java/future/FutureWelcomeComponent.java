package com.notimetolearn.java.future;

import com.notimetolearn.java.service.WelcomeService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class FutureWelcomeComponent {

    private final WelcomeService welcomeService;

    /**
     * Returns a Completable future
     * @return CompletableFuture<String>
     */
    public CompletableFuture<String> sayHelloInFuture() {
        return CompletableFuture
                .supplyAsync(() -> welcomeService.sayHello())
                .thenApply(String::toUpperCase);
    }

    /**
     * Both futures executes in parallel
     * @return CompletableFuture<String>
     */
    public CompletableFuture<String> sayHelloAndGreetCombinesInFuture()  {
       CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> welcomeService.sayHello());
       CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> welcomeService.askHowAreYou());

        return task1
                .thenCombine(task2, (r1,r2) -> r1 + ", " + r2)
                .thenApply(String::toUpperCase);
    }

    /**
     * Second future call depends on first future call.
     * @return CompletableFuture<String>
     */
    public CompletableFuture<String> sayHelloAndGreetComposeInFuture()  {

        return CompletableFuture.supplyAsync(() -> welcomeService.sayHello())
                .thenCompose(prevFutureResult -> welcomeService.sayHelloFuture(prevFutureResult))
                .thenApply(String::toUpperCase);
    }
}
