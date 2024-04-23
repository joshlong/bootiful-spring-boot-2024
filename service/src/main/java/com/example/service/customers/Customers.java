package com.example.service.customers;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
class Customers {

    private final ApplicationEventPublisher applicationEventPublisher;

    record InitializedEvent(Instant instant) {
    }

    Customers(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    void ready(ApplicationReadyEvent applicationReadyEvent) {
        this.applicationEventPublisher.publishEvent(new InitializedEvent(Instant.now()));
    }

    @ApplicationModuleListener
    void ready (InitializedEvent initializedEvent) {
        System.out.println("got an application ready");
    }
}
