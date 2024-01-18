package bootiful.service.orders;


import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;


@Configuration
class OrdersConfiguration {

    @Bean
    ApplicationRunner ordersRunner(OrderRepository repository) {
        return args -> repository.findAll().forEach(System.out::println);
    }
}

@Table(name = "product_orders")
record Order(@Id Integer id, int customer, int product) {
}

@Controller
@Transactional
class OrdersController {

    private final OrderRepository repository;

    private final ApplicationEventPublisher publisher;

    OrdersController(OrderRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @MutationMapping
    void placeOrder(@Argument int customer, @Argument int product) {
        var saved = this.repository.save(new Order(null, customer, product));
        this.publisher.publishEvent(new OrderPlacedEvent(
                saved.id(), saved.product(), saved.customer()
        ));
    }
}

interface OrderRepository extends ListCrudRepository<Order, Integer> {
}
