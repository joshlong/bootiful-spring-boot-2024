package com.example.service.inventory;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
class Inventory {

    @ApplicationModuleListener
    void inventoryUpdated(InventoryUpdatedEvent inventoryUpdatedEvent) throws Exception {
        System.out.println("started: got an inventory updated event [" + inventoryUpdatedEvent + "]");
        Thread.sleep(10_000);
        System.out.println("got an inventory updated event [" + inventoryUpdatedEvent + "]");
        Thread.sleep(10_000);
        System.out.println("finished: got an inventory updated event [" + inventoryUpdatedEvent + "]");

    }
}
