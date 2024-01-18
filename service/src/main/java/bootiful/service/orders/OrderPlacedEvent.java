package bootiful.service.orders;

import org.springframework.modulith.events.Externalized;

@Externalized
public record OrderPlacedEvent(int id, int product, int customer) {
}
