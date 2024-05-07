package com.example.service.orders;

import com.example.service.inventory.InventoryUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;


//
// curl -H"content-type:application/json" -d '{"lineItems":[{"product":2,"quantity":5}]}' -XPOST http://localhost:8080/orders
//
@Transactional
@RestController
@RequestMapping("/orders")
class OrdersController {

    private final OrderRepository orders;

    private final ApplicationEventPublisher publisher;

    OrdersController(OrderRepository orders, ApplicationEventPublisher publisher) {
        this.orders = orders;
        this.publisher = publisher;
    }

    @GetMapping
    Collection<Order> readOrders() {
        return this.orders.findAll();
    }

    @PostMapping
    void placeOrder(@RequestBody Order order) {
        var saved = this.orders.save(order);
        var lineItems = saved.lineItems();
        lineItems
            .forEach(li -> this.publisher.publishEvent(new InventoryUpdatedEvent(saved.id(), li.product(), li.quantity())));
    }
}

interface OrderRepository extends ListCrudRepository<Order, Integer> {
}

@Table("orders_line_items")
record LineItem(@Id Integer id, int product, int quantity) {
}

@Table("orders")
record Order(@Id Integer id, Set<LineItem> lineItems) {

    void addLineItem(LineItem lineItem) {
        this.lineItems().add(lineItem);
    }
}
