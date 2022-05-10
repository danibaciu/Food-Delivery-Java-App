package pao.Components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order extends IOStream<Order>{

    private Address addressOfDelivery;
    private Map<Long, Integer> productsOrdered;
    private Entity<Long, Integer> restaurantId, driverID;
    private LocalDateTime timeOfOrder;

    public Order(){}

    public Order(Address addressOfDelivery, Map<Long, Integer> productsOrdered, long restaurantId) {
        this.addressOfDelivery = addressOfDelivery;
        this.productsOrdered = productsOrdered;
        this.restaurantId.id = restaurantId;
        this.timeOfOrder = LocalDateTime.now();
        this.driverID.id = -1L;
    }

    public Address getAddressOfDelivery() {
        return addressOfDelivery;
    }

    public Long getRestaurantId() {
        return restaurantId.getId();
    }

    public Map<Long, Integer> getProductsOrdered() {
        return this.productsOrdered;
    }

    public LocalDateTime getDateTime(){
        return this.timeOfOrder;
    }

    public void setDriverID(Long id) {
        this.driverID.id = id;
    }

    public Long getDriverID() {
        return driverID.getId();
    }

    public double calculateTotalFee(List<Product> restaurantProducts) {
        double sum = 0.0;
        for (var id : productsOrdered.keySet()) {
            sum += restaurantProducts.get(Math.toIntExact(id)).getPrice() * productsOrdered.get(id) ; // sum(price * quantity)
        }
        return sum;
    }

    @Override
    public String toString() {

        StringBuilder mapAsString = new StringBuilder("{");

        for (var key : productsOrdered.keySet()) {
            mapAsString.append(key + "=" + productsOrdered.get(key) + ", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");

        return "Order{" +
                "addressOfDelivery=" + addressOfDelivery.toString() +
                ", productsOrdered=" + mapAsString +
                ", restaurantId=" + restaurantId.id +
                ", timeOfOrder=" + timeOfOrder.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
        '}';
    }

    @Override
    public String convertEntityToCsvString() {
        StringBuilder mapAsString = new StringBuilder("");

        for (var key : productsOrdered.keySet()) {
            mapAsString.append(",(").append(key).append("*").append(productsOrdered.get(key)).append(")");
        }
        return "Order," + timeOfOrder + "," + restaurantId.id + "," + driverID.id + "," + addressOfDelivery.convertEntityToCsvString() + mapAsString;
    }

    @Override
    public void convertCsvStringToEntity(String CsvString) {

        String []tempArr = CsvString.split(CsvDelimiter);

        try {
            this.timeOfOrder = LocalDateTime.parse(tempArr[0]);
            this.restaurantId.id = Long.valueOf(tempArr[1]);
            this.driverID.id = Long.valueOf(tempArr[2]);

            (this.addressOfDelivery = new Address()).convertCsvStringToEntity(tempArr[3] + "," + tempArr[4] + "," + tempArr[5] + "," + tempArr[6]);

            for (int i = 7; i < tempArr.length; i++) {
                String []prod = tempArr[i].split("/(/)/*");
                this.productsOrdered.put(Long.valueOf(prod[0]), Integer.valueOf(prod[1]));
            }
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea comenzilor din CSV : " + exception);
        }
    }
}
