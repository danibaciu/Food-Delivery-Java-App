package pao.app;

import pao.components.*;
import pao.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

public class Services {
    private static Services servicesInstance = null;

    private final ReadServices readServices = ReadServices.getInstance();

    private final IRestaurantRepository restaurantRepository;
    private final IAddressRepository addressRepository;
    private final IStockRepository stockRepository;
    private final IOrderRepository orderRepository;
    private final IConsumerRepository consumerRepository;

//    private List<UserConsumer> appUser = new ArrayList<>();
    private List<UserEmployee> appDrivers = new ArrayList<>();
    private List<LocalDateTime> driversAvailability = new ArrayList<>();
//    private List<Restaurant> appRestaurants = new ArrayList<>();
    private List<Product> restaurantProducts = new ArrayList<>();
//    private List<Order> appOrders = new ArrayList<>();

   /* public static Services getInstance() {
        if (servicesInstance == null)
            servicesInstance = new Services();
        return servicesInstance;
    }*/

    public Services(IRestaurantRepository restaurantRepository, IAddressRepository addressRepository, IStockRepository stockRepository, IOrderRepository orderRepository, IConsumerRepository consumerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
        this.consumerRepository = consumerRepository;
    }

//    public void uploadFromMemory() {
//        this.appDrivers = readServices.readFromCsvUserEmployee("C:\\Users\\danib\\OneDrive\\Documente\\GitHub\\Food-Delivery-Java-App\\project\\src\\pao\\CsvFiles\\Drivers.csv");
//        this.appUser = readServices.readFromCsvUserConsumer("C:\\Users\\danib\\OneDrive\\Documente\\GitHub\\Food-Delivery-Java-App\\project\\src\\pao\\CsvFiles\\Users.csv");
//        this.appOrders = readServices.readFromCsvOrder("C:\\Users\\danib\\OneDrive\\Documente\\GitHub\\Food-Delivery-Java-App\\project\\src\\pao\\CsvFiles\\Orders.csv");
        //this.appRestaurants = readServices.readFromCsvRestaurant("C:\\Users\\danib\\OneDrive\\Documente\\GitHub\\Food-Delivery-Java-App\\project\\src\\pao\\CsvFiles\\Restaurants.csv");
//        this.restaurantProducts = readServices.readFromCsvProduct("C:\\Users\\danib\\OneDrive\\Documente\\GitHub\\Food-Delivery-Java-App\\project\\src\\pao\\CsvFiles\\Products.csv");
//    }



    public void addClient() {
        UserConsumer client = readServices.readClient();
        consumerRepository.save(client);
//        sortUsersByFullName();
    }

    public void addClient(UserConsumer client) {
//        appUser.add(client);
        consumerRepository.save(client);
//        sortUsersByFullName();
    }

    public void viewClients() {
//        for (var user : appUser) {
//            System.out.println(user.toString());
//        }
        consumerRepository.findAll().forEach(System.out::println);
    }

    public void addProduct() {
        restaurantProducts.add(readServices.readProduct());
    }

    public void viewProducts() {
        for (Product product : restaurantProducts) {
            System.out.println(product.toString());
        }
    }

    public void addDriver() {
        UserEmployee driver = readServices.readDriver();
        appDrivers.add(driver);
    }

    public void addDriver(UserEmployee driver) {
        appDrivers.add(driver);
        driversAvailability.add(LocalDateTime.now());
    }

//    public void sortUsersByFullName() {
//        appUser.sort(new Comparator<UserConsumer>() {
//            public int compare(UserConsumer o1, UserConsumer o2) {
//                return Integer.compare(0, o1.getFirstName().compareTo(o2.getFirstName()));
//            }
//        });
//    }

    public void viewDrivers() {
        for (var driver : appDrivers) {
            System.out.println(driver.toString());
        }
    }

    public void addRestaurant() {
        Restaurant restaurant = readServices.readRestaurant();
        //appRestaurants.add(restaurant);
        restaurantRepository.save(restaurant);
    }

    public void addRestaurant(Restaurant restaurant) {
        //appRestaurants.add(restaurant);
        restaurantRepository.save(restaurant);
    }

    public void viewRestaurants() {
        /*for (var restaurant : appRestaurants) {
            System.out.println(restaurant.toString());
        }*/
        restaurantRepository.findAll().forEach(System.out::println);
    }

    public void orderFood(){
        Scanner in = new Scanner(System.in);

        System.out.println("Which id have the user that ordered the food ? Enter the user id : ");
        Long userId = in.nextLong();

        System.out.println("Enter the order info :");
        Order order = readServices.readOrder();

//        appOrders.add(order);
        orderRepository.save(order);

//        for (UserConsumer user : appUser) {
//            if (user.getId().equals(userId)) {
//                user.addOrder(appOrders.size() - 1);
//                break;
//            }
//        }

        //update stock
        Long restaurantId = order.getRestaurantId();

        /*for (Restaurant restaurant : appRestaurants) {
            if (restaurant.getId().equals(restaurantId)) {
                restaurant.updateStock(order.getProductsOrdered());
                break;
            }
        }*/
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
//        restaurant.updateStock(order.getProductsOrdered());
        restaurantRepository.update(restaurant);

        Integer driverIndex;

        while ((driverIndex = getDriverIndex()) != -1) {
            try {
                wait(10); // we will wait until a driver is available
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Comanda a fost preluata si va fi livrata in " + order.getProductsOrdered().size() + " minutes and the name of the driver is " + appDrivers.get(driverIndex).getFullName());
        driversAvailability.set(driverIndex, LocalDateTime.now().plusMinutes(order.getProductsOrdered().size()));
        // here I added num of products minutes to the now local date time
    }

    public void printUserThatOrderedToday() {

    }

    public void printFirst3UsersWithMaxNumOrders() {

        Integer poz1 = 0, poz2 = 0, poz3 = 0, noOfOrders;

        List<UserConsumer> appUser = consumerRepository.findAll();

        for (Integer i = 0; i < appUser.size(); i++) {
            noOfOrders = appUser.get(i).getNumOfOrders();
            if (noOfOrders > appUser.get(poz1).getNumOfOrders()) {
                poz3 = poz2;
                poz2 = poz1;
                poz1 = i;
            } else {
                if (noOfOrders > appUser.get(poz2).getNumOfOrders()) {
                    poz3 = poz2;
                    poz2 = noOfOrders;
                } else {
                    if (noOfOrders > appUser.get(poz3).getNumOfOrders()) {
                        poz3 = noOfOrders;
                    }
                }
            }
        }
        System.out.println("The user with the maximum number of the orders is : " + appUser.get(poz1).toString());
        System.out.println("The user with the second maximum number of the orders is : " + appUser.get(poz2).toString());
        System.out.println("The user with the third maximum number of the orders is : " + appUser.get(poz3).toString());
    }

    private Integer getDriverIndex() {
        Integer position = 0;
        for (Integer i = 0; i < appDrivers.size(); i++) {
            if (driversAvailability.get(i).isAfter(LocalDateTime.now())) {
                return i;
            }
        }
        return -1; // we don't have disponible drivers and we have to wait
    }

    private Long getNoOfOrders(UserEmployee driver) {
        Long counter = 0L;
        for (UserConsumer user : consumerRepository.findAll()) {
            counter += user.getNoOfOrdersForADriver(driver, orderRepository.findAll());
        }
        return counter;
    }

    public void increaseSalaryForTop2MostActiveDrivers() {
        appDrivers.stream().sorted(Comparator.comparingLong(this::getNoOfOrders).reversed()).limit(2).toList().forEach((UserEmployee driver) -> driver.increaseSalaryByXPercent(10));
    }

//    public void printSumLastOrderForEachUser() {
//        for (var user : appUser) {
//            System.out.println("User " + user.getFullName() + " was ordered in value of " + calculateTotalFee(user.getLastOrder(appOrders)) + " in the date of " + user.returnTimeOfLastOrder(appOrders));
//        }
//    }

    public double calculateTotalFee(Order order) {
        double sum = 0.0;
        var productsOrdered = order.getProductsOrdered();
        for (var id : productsOrdered.keySet()) {
            sum += restaurantProducts.get(Math.toIntExact(id)).getPrice() * productsOrdered.get(id); // sum(price * quantity)
        }
        return sum;
    }
}
