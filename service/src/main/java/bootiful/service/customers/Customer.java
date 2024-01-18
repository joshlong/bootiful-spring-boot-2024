package bootiful.service.customers;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

record Customer(@Id Integer id, String name) {
}

interface CustomerRepository extends ListCrudRepository<Customer, Integer> {
}

@Controller
class CustomersController {

    private final CustomerRepository repository;

    CustomersController(CustomerRepository repository) {
        this.repository = repository;
    }

    @QueryMapping
    Collection<Customer> customers() {
        return this.repository.findAll();
    }
}


@Configuration
class CustomersConfiguration {

    @Bean
    ApplicationRunner customerInitialization(CustomerRepository repo) {
        return args -> repo.findAll().forEach(System.out::println);
    }
}