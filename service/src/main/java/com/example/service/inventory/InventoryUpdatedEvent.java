package com.example.service.inventory;

public record InventoryUpdatedEvent(int  order  ,
                                    int product ,
                                    int quantity  ) {
}
