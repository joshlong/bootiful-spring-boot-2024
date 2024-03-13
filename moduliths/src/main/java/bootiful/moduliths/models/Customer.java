package bootiful.moduliths.models;

import org.springframework.data.annotation.Id;

public record Customer(@Id Long id, String name)   {
}
