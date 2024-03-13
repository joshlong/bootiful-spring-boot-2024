package bootiful.moduliths.models;

import org.springframework.data.annotation.Id;

public record LineItem (@Id Long id, Long  product) {
}
