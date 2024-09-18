import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.shop.exception.ProductNotFoundException;

import java.time.ZonedDateTime;
import java.util.*;

@RequiredArgsConstructor
public class ShopService {
    @NonNull
    private final ProductRepo productRepo;
    @NonNull
    private final OrderRepo orderRepo;
    @NonNull
    private final IdService idService;

    public ShopService() {
        idService = new UUIDService();
        productRepo = new ProductRepo();
        orderRepo = new OrderMapRepo();
    }

    public Order addOrder(List<String> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new ProductNotFoundException("product not found");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(idService.generateId(), products, OrderStatus.PROCESSING, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> orderStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream().filter(order -> order.orderStatus().equals(orderStatus)).toList();
    }

    public Order updateOrder(String orderId, OrderStatus newOrderStatus) {
        Order foundOrder = orderRepo.getOrderById(orderId);
        if (foundOrder == null) {
            return null;
        }
        Order updatedOrder = foundOrder.withOrderStatus(newOrderStatus);
        orderRepo.addOrder(updatedOrder);
        return updatedOrder;
    }

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        List<Order> processingOrders = orderRepo.getOrders().stream().filter(order -> order.orderStatus().equals(OrderStatus.PROCESSING)).sorted(Comparator.comparing(Order::timeStamp)).toList();
        List<Order> inDeliveryOrders = orderRepo.getOrders().stream().filter(order -> order.orderStatus().equals(OrderStatus.IN_DELIVERY)).sorted(Comparator.comparing(Order::timeStamp)).toList();
        List<Order> completedOrders = orderRepo.getOrders().stream().filter(order -> order.orderStatus().equals(OrderStatus.COMPLETED)).sorted(Comparator.comparing(Order::timeStamp)).toList();
        Map<OrderStatus, Order> result = new HashMap<>();
        result.put(OrderStatus.PROCESSING, processingOrders.getFirst());
        result.put(OrderStatus.IN_DELIVERY, inDeliveryOrders.getFirst());
        result.put(OrderStatus.COMPLETED, completedOrders.getFirst());
        return result;
    }
}
