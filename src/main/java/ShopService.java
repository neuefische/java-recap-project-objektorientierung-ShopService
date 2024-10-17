import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();


    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }


    //changed to Optional
    //Modify the 'addOrder' method in the ShopService so that an exception
    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrderOpt = productRepo.getProductById(productId);
            if (productToOrderOpt.isEmpty()) {
                throw new ProductDoesNotExist("Product mit der Id: " + productId + " not exist");
//                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
//                return null;
            }
            Product productToOrder = productToOrderOpt.get();
            products.add(productToOrder);
        }
        Order newOrder = new Order(UUID.randomUUID().toString(), products, LocalDateTime.now());
        return orderRepo.addOrder(newOrder);
    }

    public Order updateOrder(String id, OrderStatus status){
        for(Order order : orderRepo.getOrders()){
            if (order.id().equals(id)){
                Order orderUpdate = order.withStatus(status);
                orderRepo.removeOrder(order.id());
                orderRepo.addOrder(orderUpdate);
                return orderUpdate;
            }
        }
        return null;
    }


    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.status() == status)
                .collect(Collectors.toList());
    }

}
