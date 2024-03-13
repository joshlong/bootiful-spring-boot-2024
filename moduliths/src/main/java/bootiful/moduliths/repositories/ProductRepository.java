package bootiful.moduliths.repositories;

import bootiful.moduliths.models.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository
    extends ListCrudRepository <Product, Integer> { }
