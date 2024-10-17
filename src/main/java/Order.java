import java.util.List;

public record Order(
        String id,
        List<Product> products,
        OrderStatus status
) {
    public Order(String id, List<Product> products) {
        this(id, products, OrderStatus.PROCESSING);
    }
}
