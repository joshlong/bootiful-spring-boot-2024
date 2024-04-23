package com.example.service.inventory;

import com.example.service.purchases.PurchasePlacedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
class InventoryService {

//    @Async
//    @Transactional
    @EventListener
    @ApplicationModuleListener
    void purchasePlacedEvent(PurchasePlacedEvent purchasePlacedEvent) throws Exception {
        System.out.println("got an event " + purchasePlacedEvent);
        Thread.sleep(20_000);
    }

}

