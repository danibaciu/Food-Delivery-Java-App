package pao.app;

import pao.persistence.*;

import java.io.IOException;
import java.util.Properties;

public class MainApp {

    public static void main(String[] args) {

        Properties properties = new Properties();

        try {
            properties.load(MainApp.class.getResourceAsStream("/bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRestaurantRepository restaurantRepository = new RestaurantRepository(properties);
        IAddressRepository addressRepository = new AddressRepository(properties);
        IStockRepository stockRepository = new StockRepository(properties);
        IOrderRepository orderRepository = new OrderRepository(properties);
        IConsumerRepository consumerRepository = new ConsumerRepository(properties);
        IProductRepository productRepository = new ProductRepository(properties);


        Services services = new Services(restaurantRepository, addressRepository, stockRepository, orderRepository, consumerRepository, productRepository);

        UI ui = new UI(services);

        ui.run();
    }
}
