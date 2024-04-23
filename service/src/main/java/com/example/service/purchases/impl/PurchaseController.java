package com.example.service.purchases.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@ResponseBody
class PurchaseController {

    private final PurchaseService purchaseService;

    PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchases")
    Integer place(@RequestBody Purchase purchase, Principal principal) {
        System.out.println(purchase + " for user " + principal.getName());
        return this.purchaseService.place(principal.getName(), purchase.productId(), purchase.quantity());
    }

}
