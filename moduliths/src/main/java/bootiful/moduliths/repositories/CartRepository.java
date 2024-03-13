package bootiful.moduliths.repositories;

import bootiful.moduliths.models.Cart;
import org.springframework.data.repository.ListCrudRepository;

public interface CartRepository
 extends ListCrudRepository <Cart, Long>
{
}
