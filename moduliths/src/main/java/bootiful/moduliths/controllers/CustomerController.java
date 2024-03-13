package bootiful.moduliths.controllers;

import bootiful.moduliths.models.Customer;
import bootiful.moduliths.repositories.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@ResponseBody
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    Collection<Customer> customers() {
        return this.repository.findAll();
    }

}
