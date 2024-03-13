package bootiful.moduliths.repositories;

import bootiful.moduliths.models.LineItem;
import org.springframework.data.repository.ListCrudRepository;

public interface LineItemRepository
        extends ListCrudRepository<LineItem, Long> {
}
