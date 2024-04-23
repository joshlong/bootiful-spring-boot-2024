package com.example.service.customers;

import org.springframework.data.annotation.Id;

record Customer(@Id Integer id, String name) {
}
