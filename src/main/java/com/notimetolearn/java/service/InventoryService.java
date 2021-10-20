package com.notimetolearn.java.service;

import com.notimetolearn.java.domain.Inventory;
import com.notimetolearn.java.util.CommonUtil;

import java.util.concurrent.CompletableFuture;

public class InventoryService {

    public Inventory fetchInventory() {
        CommonUtil.delay(500);
        return Inventory.builder()
                .count(2).build();
    }

    public CompletableFuture<Inventory> fetchInventoryAsync() {
       return CompletableFuture.supplyAsync(() -> {
               CommonUtil.delay(500);
                return Inventory.builder()
                        .count(2).build();
       });
    }
}
