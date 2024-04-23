package com.example.service.purchases.impl;

import org.springframework.data.annotation.Id;

public record Purchase(@Id Integer id, String username, Integer productId, int quantity) {
}
