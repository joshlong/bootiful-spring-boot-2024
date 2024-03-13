package bootiful.moduliths.repositories;

import bootiful.moduliths.models.Customer;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomerRepository
 extends ListCrudRepository <Customer, Integer>
{

}
