package com.example.service.purchases.impl;

import org.springframework.data.repository.ListCrudRepository;

interface PurchaseRepository extends ListCrudRepository<Purchase, Integer> {
}
