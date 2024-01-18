package bootiful.service.products;

import bootiful.service.orders.OrderPlacedEvent;
import org.springframework.ai.client.AiClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collection;

record Product(@Id Integer id, String name, String description, int inventory) {
}

interface ProductRepository extends ListCrudRepository<Product, Integer> {
}

@Configuration
class ProductConfiguration {

    private final ProductRepository productRepository;

    ProductConfiguration(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @TransactionalEventListener
    void inventoryInvented(OrderPlacedEvent ope) {
        System.out.println("placing order and" +
                " updating inventory for [" +ope +
                "]");
        this.productRepository
                .findById(ope.product())
                .ifPresent(product -> {
                    var p = new Product(product.id(),
                            product.name(),
                            product.description(),
                            product.inventory() - 1);
                    this.productRepository.save(p);
                });

    }
}

@Controller
class ProductsController {

    private final ProductRepository repository;
    private final AiClient singularity;

    ProductsController(ProductRepository repository, AiClient singularity) {
        this.repository = repository;
        this.singularity = singularity;
    }


    @MutationMapping
    Product createProduct(@Argument String name) {
        return this.repository.save(new Product(null, name,
                this.singularity.generate("please write a description for a fictional new product called '" + name +
                        "'"), 0
        ));
    }

    @QueryMapping
    Collection<Product> products() {
        return this.repository.findAll();
    }
}


