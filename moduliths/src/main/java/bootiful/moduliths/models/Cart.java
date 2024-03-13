package bootiful.moduliths.models;


import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

public record Cart(@Id Long id, List<LineItem> lineItems) {
}
