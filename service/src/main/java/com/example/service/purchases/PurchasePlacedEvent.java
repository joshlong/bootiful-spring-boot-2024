package com.example.service.purchases;

public record PurchasePlacedEvent( Integer purchaseId,String username, Integer productId, int quantity) {
}
