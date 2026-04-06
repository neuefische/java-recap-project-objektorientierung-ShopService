import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        ShopService shopService = new ShopService();
        ProductRepo productRepo = new ProductRepo();

        List<String> order1 = new ArrayList<>();
        order1.add("1");

        try{
            shopService.addOrder(order1);
        }catch(ProductNotFound e){
            System.out.println(e);
        }

        List<String> order2 = new ArrayList<>();
        order2.add("2");
        try{
            shopService.addOrder(order2);
        }catch(ProductNotFound e){
            System.out.println(e);
        }


        shopService.getByStatus(Orderstatus.PROCESSING);
        System.out.println(productRepo.getProductById("1"));
        System.out.println(productRepo.getProductById("1").get());
    }
}
