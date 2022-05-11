package pao.App;

import pao.Components.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ReadServices {

    private static ReadServices readServicesInstance = null;

    protected Address readAddress(Scanner in) {
        String country, city, street;
        Integer streetNumber;

        System.out.println("Enter the country :");
        country = in.nextLine();
        System.out.println("Enter the city name :");
        city = in.nextLine();
        System.out.println("Enter the street name :");
        street = in.nextLine();

        System.out.println("Enter the street number :");
        streetNumber = in.nextInt();

        return new Address(country, city, street, streetNumber);
    }

    protected UserConsumer readClient() {
        Scanner in = new Scanner(System.in);

        String firstName, lastName, emailAddress, birthDate;

        System.out.println("Enter the first name :");
        firstName = in.nextLine();
        System.out.println("Enter the last name :");
        lastName = in.nextLine();
        System.out.println("Enter the email address :");
        emailAddress = in.nextLine();
        System.out.println("Enter the birthday date as the following format yyyy-MM-dd (ex : 2005-05-23) :");
        birthDate = in.nextLine();

        return new UserConsumer(firstName, lastName, emailAddress, LocalDate.parse(birthDate), readAddress(in));
    }

    protected UserEmployee readDriver() {
        Scanner in = new Scanner(System.in);
        String firstName, lastName, emailAddress, birthDate, jobName;
        Integer salary;

        System.out.println("Enter the first name :");
        firstName = in.nextLine();
        System.out.println("Enter the last name :");
        lastName = in.nextLine();
        System.out.println("Enter the email address :");
        emailAddress = in.nextLine();
        System.out.println("Enter the birthday date as the following format yyyy-MM-dd (ex : 2005-05-23) :");
        birthDate = in.nextLine();
        System.out.println("Enter the salary (RON currency) :");
        salary = in.nextInt();
        in.nextLine();

        System.out.println("Enter the job name(ex : CEO/Manager/Call_Center_Operator/Driver :");
        jobName = in.nextLine();

        return new UserEmployee(firstName, lastName, emailAddress, LocalDate.parse(birthDate), readAddress(in), salary, PossibleJobs.valueOf(jobName));
    }

    protected Product readProduct() {
        Scanner in = new Scanner(System.in);

        String nameOfProduct, unityOfMeasurement;
        List<String> ingredients = new ArrayList<>();
        Integer noOfIngredients;
        double price, quantity;

        System.out.println("Enter the product name :");
        nameOfProduct = in.nextLine();
        System.out.println("Enter the unity of measurement of the product (ex: buc/gr/etc) :");
        unityOfMeasurement = in.nextLine();

        System.out.println("Enter the price of the product (double) :");
        price = in.nextDouble();
        System.out.println("Enter the quantity of the product (ex : 100 gr) (double):");
        quantity = in.nextDouble();

        System.out.println("Enter the number of the product ingredients :");
        noOfIngredients = in.nextInt();
        in.nextLine(); // consume the \n

        for (int i = 0; i < noOfIngredients; i++) {
            System.out.println("Enter the name of " + i + " ingredient :");
            ingredients.add(in.nextLine());
        }

        return new Product(nameOfProduct, unityOfMeasurement, ingredients, price, quantity);
    }

    protected Stock readStock() {
        Scanner in = new Scanner(System.in);

        Map<Long, Integer> products = new HashMap<>();
        Integer noOfProducts, productNo;
        Long x;

        System.out.println("Enter the number of the product ingredients :");
        noOfProducts = in.nextInt();
        in.nextLine(); // consume the \n

        for (int i = 0; i < noOfProducts; i++) {
            System.out.println("Enter the index of " + i + " product :");
            x = in.nextLong();
            System.out.println("Enter the quantity of this product :");
            productNo = in.nextInt();

            products.put(x, productNo);
        }

        return new Stock(products);
    }

    protected Restaurant readRestaurant() {
        Scanner in = new Scanner(System.in);

        String name;
        double commissionOfDelivery;

        System.out.println("Enter the restaurant name :");
        name = in.nextLine();
        System.out.println("Enter the commission Of Delivery :");
        commissionOfDelivery = in.nextDouble();
        in.nextLine(); //consume the \n

        System.out.println("\t****  The next steps is to enter the restaurant address");

        Address restaurantAddress = readAddress(in);

        System.out.println("\t****  The next steps is to enter the restaurant stock of products");

        Stock restaurantStock = readStock();


        return new Restaurant(restaurantAddress, name, restaurantStock, commissionOfDelivery);
    }

    protected Order readOrder() {
        Scanner in = new Scanner(System.in);

        Map<Long, Integer> productsOrdered = new HashMap<>();
        Long restaurantId, x;
        Integer noOfProducts, productNo;

        System.out.println("\t****  The next steps is to enter the delivery address");

        Address addressOfDelivery = readAddress(in);

        System.out.println("Enter the restaurant id for this order :");
        restaurantId = in.nextLong();
        System.out.println("Enter the number of the product that you want to order :");
        noOfProducts = in.nextInt();

        for (int i = 0; i < noOfProducts; i++) {
            System.out.println("Enter the id of " + i + " product :");
            x = in.nextLong();
            System.out.println("Enter the quantity of this product :");
            productNo = in.nextInt();

            productsOrdered.put(x, productNo);
        }
        return new Order(addressOfDelivery, productsOrdered, restaurantId);
    }

    public static ReadServices getInstance() {
        if (readServicesInstance == null)
            readServicesInstance = new ReadServices();
        return readServicesInstance;
    }

    public List<UserConsumer> readFromCsvUserConsumer(String filePath) {
        List<UserConsumer> userConsumerList = new ArrayList<>();

        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            while ((line = br.readLine()) != null) {
                UserConsumer aux = new UserConsumer();
                aux.convertCsvStringToEntity(line);
                userConsumerList.add(aux);
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return userConsumerList;
    }

    public List<UserEmployee> readFromCsvUserEmployee(String filePath) {
        List<UserEmployee> userEmployeeList = new ArrayList<>();

        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            while ((line = br.readLine()) != null) {
                UserEmployee aux = new UserEmployee();
                aux.convertCsvStringToEntity(line);
                userEmployeeList.add(aux);
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return userEmployeeList;
    }

    public List<Product> readFromCsvProduct(String filePath) {
        List<Product> productList = new ArrayList<>();

        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            while ((line = br.readLine()) != null) {
                Product aux = new Product();
                aux.convertCsvStringToEntity(line);
                productList.add(aux);
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return productList;
    }

    public List<Restaurant> readFromCsvRestaurant(String filePath) {
        List<Restaurant> restaurantList = new ArrayList<>();

        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            while ((line = br.readLine()) != null) {
                Restaurant aux = new Restaurant();
                aux.convertCsvStringToEntity(line);
                restaurantList.add(aux);
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return restaurantList;
    }

    public List<Order> readFromCsvOrder(String filePath) {
        List<Order> orderList = new ArrayList<>();

        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            while ((line = br.readLine()) != null) {
                Order aux = new Order();
                aux.convertCsvStringToEntity(line);
                orderList.add(aux);
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return orderList;
    }

}
