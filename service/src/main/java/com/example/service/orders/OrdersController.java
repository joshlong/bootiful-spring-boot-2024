package com.example.service.orders;

import com.example.service.inventory.InventoryUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

@Transactional
@Controller
@ResponseBody
@RequestMapping("/orders")
class OrdersController {

    private final OrderRepository repository;

    private final ApplicationEventPublisher eventPublisher;

    OrdersController(OrderRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    void create(@RequestBody Order order) {
        var saved = this.repository.save(order);
        System.out.println("saved [" +
                saved + "]");
        saved.lineItems()
                .forEach(l -> eventPublisher
                        .publishEvent(new InventoryUpdatedEvent(Instant.now(), l.product(), l.quantity())));

    }

    @GetMapping
    Collection<Order> orders() {
        return this.repository.findAll();
    }

}

@Table("orders")
record Order(@Id Integer id, Set<LineItem> lineItems) {
}

@Table("orders_line_items")
record LineItem(@Id Integer id, int quantity, int product) {
}

interface OrderRepository extends ListCrudRepository<Order, Integer> {
}
