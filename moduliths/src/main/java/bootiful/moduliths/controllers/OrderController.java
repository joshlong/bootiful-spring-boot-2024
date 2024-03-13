package bootiful.moduliths.controllers;

import bootiful.moduliths.models.Cart;
import bootiful.moduliths.repositories.CartRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class OrderController {

    private final CartRepository cartRepository;

    OrderController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @GetMapping("/orders")
    Collection<Cart> orders() {
        return this.cartRepository.findAll();
    }
}
