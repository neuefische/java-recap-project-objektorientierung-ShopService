import java.time.LocalDateTime;
import java.util.List;
import lombok.With;

@With
public record Order(
        String id,
        List<Product> products,
        OrderStatus status,
        LocalDateTime timestamp
) {
    public Order(String id, List<Product> products, LocalDateTime localdatetime) {
        this(id, products, OrderStatus.PROCESSING,localdatetime);
    }
}
