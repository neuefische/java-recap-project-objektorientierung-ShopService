import org.shop.exception.ProductNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        UUIDService idService = new UUIDService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        Product product = new Product("11", "T Shirt");
        Product product2 = new Product("21", "Jeans");
        Product product3 = new Product("31", "T Shirt");
        Product product4 = new Product("41", "Jeans");
        productRepo.addProduct(product);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);
        productRepo.addProduct(product4);

//        try {
//            shopService.addOrder(List.of("1", "2"));
//            shopService.addOrder(List.of("3"));
//            shopService.addOrder(List.of("4"));
//        } catch (ProductNotFoundException e) {
//            System.out.println("Product now found");
//        }
//
//        System.out.println("List of orders: " + orderRepo.getOrders());

        Path path = Paths.get("transactions.txt");
        Map<String, String> orderalies = new HashMap<>();
        Files.lines(path).forEach(line -> {
            String[] splitArray = line.split(" ");
            if (splitArray[0].equals("addOrder")) {
                List<String> productIds = Arrays.stream(splitArray).skip(2).toList();

                try {
                    Order createdOrder = shopService.addOrder(productIds);
                    orderalies.put(splitArray[1], createdOrder.id());
                } catch (ProductNotFoundException e) {
                    System.out.println("product not found");
                }
            }
            if (splitArray[0].equals("setStatus")) {
                Order updatedOrder = shopService.updateOrder(orderalies.get(splitArray[1]), OrderStatus.valueOf(splitArray[2]));
                System.out.println(updatedOrder);
            }
            if (splitArray[0].equals("printOrders")) {
                System.out.println(orderRepo.getOrders());
            }
        });
    }
}
