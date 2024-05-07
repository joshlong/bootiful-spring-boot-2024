package com.example.service.customers;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Configuration
class Customers {

    private final Set<String> sessions = new ConcurrentSkipListSet<>();

    @EventListener
    void authenticationSuccess(AuthenticationSuccessEvent ae) {
        var name = ae.getAuthentication().getName();
        if (!this.sessions.contains(name)) {
            this.sessions.add(name);
            System.out.println("going to update an existing record " +
                    "or write a new record in the customers table for [" +
                    name + "]");
        }

    }
}


@Controller
@ResponseBody
class CustomersController {

    @GetMapping("/me")
    Map<String, String> me() {
        return Map.of("name", SecurityContextHolder
                .getContextHolderStrategy()
                .getContext()
                .getAuthentication()
                .getName());
    }
}