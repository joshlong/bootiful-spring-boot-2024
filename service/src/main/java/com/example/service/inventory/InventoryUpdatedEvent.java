package com.example.service.inventory;

import java.time.Instant;

public record InventoryUpdatedEvent (Instant instant ,
                                     int product, int quantity) {
}
