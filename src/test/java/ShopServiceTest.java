import org.junit.jupiter.api.Test;
import org.shop.exception.ProductNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() throws ProductNotFoundException {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING, ZonedDateTime.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectException() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN & //THEN
        assertThrows(ProductNotFoundException.class, ()->shopService.addOrder(productsIds));
    }

    @Test
    void updateOrderTest_WhenValidOrderId_updatesOrderByStatus() throws ProductNotFoundException {
//    GIVEN
        ShopService shopService = new ShopService();
        String productId = "1";
        Order actual = shopService.addOrder(List.of(productId));
//    WHEN
        Order updatedOrder = shopService.updateOrder(actual.id(), OrderStatus.COMPLETED);
//    THEN
        assertEquals(updatedOrder.orderStatus(), OrderStatus.COMPLETED);
    }

    @Test
    void updateOrderTest_WhenInvalidOrderId(){
//        GIVEN
        ShopService shopService = new ShopService();
//        WHEN
        Order updateOrder = shopService.updateOrder("1", OrderStatus.COMPLETED);
//        THEN
        assertNull(updateOrder);
    }
}
