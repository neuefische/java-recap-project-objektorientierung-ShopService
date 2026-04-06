import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) throws ProductNotFound {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId).orElse(null);
            if (productToOrder == null) {
                throw new ProductNotFound("Product mit der Id: \"" + productId + "\" konnte nicht bestellt werden!");
                //System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                //return null;
            }
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products,Orderstatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }
    public List<Order> getByStatus(Orderstatus  orderstatus) {
        List<Order> orders = orderRepo.getOrders();
        List<Order> matched = orders.stream()
            .filter(s->s.orderstatus()==orderstatus)
            .toList();
        return matched;
    }
}
