package bootiful.moduliths.controllers;

import bootiful.moduliths.models.Product;
import bootiful.moduliths.repositories.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping ("/products")
    Collection<Product> products (){
        return this.repository.findAll();
    }
}
