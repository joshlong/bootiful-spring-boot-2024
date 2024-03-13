package bootiful.moduliths;

import bootiful.moduliths.models.LineItem;
import bootiful.moduliths.models.Cart;
import bootiful.moduliths.models.Product;
import bootiful.moduliths.repositories.CustomerRepository;
import bootiful.moduliths.repositories.LineItemRepository;
import bootiful.moduliths.repositories.CartRepository;
import bootiful.moduliths.repositories.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;


@SpringBootApplication
public class ModulithsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModulithsApplication.class, args);
    }

    @Bean
    ApplicationRunner demo(
            LineItemRepository lineItemRepository,
            CartRepository cartRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository
    ) {
        return args -> {

            // products
            var products = java.util.List.of(
                    productRepository.save(new Product(null, "123", 30)),
                    productRepository.save(new Product(null, "345", 20))
            );
            System.out.println(products);

            // orders
            var order = cartRepository.save(new Cart(null, new ArrayList<>()));

//                var li1 = lineItemRepository.save(new LineItem(null, products.getFirst().id()));
//                var li2 = lineItemRepository.save(new LineItem(null, products.getFirst().id()));

            order.lineItems().add(new LineItem(null, products.getFirst().id()));
            order.lineItems().add(new LineItem(null, products.getFirst().id()));
            cartRepository.save(order);

             (cartRepository.findById(order.id())).ifPresent(cart -> {
                 System.out.println(cart.toString());
             });


        };
    }

}
