package bootiful.moduliths.models;

import org.springframework.data.annotation.Id;

public record Product(@Id Long id, String sku, int inventory) {
}
