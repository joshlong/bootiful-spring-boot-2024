package com.example.service.inventory;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
class Inventory {

    @ApplicationModuleListener
    void handle(InventoryUpdatedEvent inventoryUpdatedEvent) throws InterruptedException {
        System.out.println("starting to wait...");
        Thread.sleep(10_000);
        System.out.println("the inventory [" + inventoryUpdatedEvent +
                "] was updated!");
    }
}
