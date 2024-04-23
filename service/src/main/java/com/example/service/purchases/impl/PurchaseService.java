package com.example.service.purchases.impl;

import com.example.service.purchases.PurchasePlacedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class PurchaseService {

    private final PurchaseRepository repository;

    private final ApplicationEventPublisher publisher;

    PurchaseService(PurchaseRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

     Integer place(String currentCustomerUsername, Integer productId, int quantity) {
        var purchase = this.repository.save(new Purchase(null, currentCustomerUsername, productId, quantity));
        this.publisher.publishEvent(new PurchasePlacedEvent(
                purchase.id(),
                purchase.username(),
                purchase.productId(),
                purchase.quantity()
        ));
        return purchase.id();
    }
}

