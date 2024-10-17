import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Product product1 = new Product("2","Gibson");
        Product product2 = new Product("3","Fender");

        ProductRepo productRepo1 = new ProductRepo();
        productRepo1.addProduct(product1);
        productRepo1.addProduct(product2);
        //System.out.println(productRepo1);

        List<String> productIds = List.of(product1.id(), product2.id());
        List<String> leer = List.of("412");
        List<Product> guitars1 = List.of(product1,product2);
        List<Product> guitars2 = List.of(product1);
        Order order1 = new Order("123", guitars1,LocalDateTime.now());
        Order order2 = new Order("234",guitars2,LocalDateTime.now().plusSeconds(1));

        OrderListRepo orderListRepo1 = new OrderListRepo();
        orderListRepo1.addOrder(order1);
        orderListRepo1.addOrder(order2);
        //System.out.println("Order List Repository: " + orderListRepo1);

        ShopService shopService1 = new ShopService(productRepo1, orderListRepo1);
        //shopService1.addOrder(productIds);

        shopService1.updateOrder("123",OrderStatus.COMPLETED);
        Order mod1 = shopService1.updateOrder("123",OrderStatus.COMPLETED);
        System.out.println("mode1: " + mod1);

        List<Order> completedOrder = shopService1.getOrdersByStatus(OrderStatus.COMPLETED);
        List<Order> processingOrder = shopService1.getOrdersByStatus(OrderStatus.PROCESSING);
        System.out.println("Processing Order: " + processingOrder);
        System.out.println("Completed Order: " + completedOrder);


        //System.out.println(productRepo1.getProductById("3"));

        try {
            shopService1.addOrder(leer);
        }
        catch (ProductDoesNotExist exception) {
            System.out.println(exception.getMessage());
        }


    }
}
