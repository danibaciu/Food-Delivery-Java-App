package pao.Components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order extends IOStream<Order> {

    private Address addressOfDelivery;
    private Map<Long, Integer> productsOrdered;
    private Entity<Long, Integer> restaurantId, driverID;
    private LocalDateTime timeOfOrder;

    public Order() {
    }

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

    public LocalDateTime getDateTime() {
        return this.timeOfOrder;
    }

    public void setDriverID(Long id) {
        this.driverID.id = id;
    }

    public Long getDriverID() {
        return driverID.getId();
    }



    @Override
    public String toString() {

        StringBuilder mapAsString = new StringBuilder("{");

        for (var key : productsOrdered.keySet()) {
            mapAsString.append(key).append("=").append(productsOrdered.get(key)).append(", ");
        }
        mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");

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
            mapAsString.append(",").append(key).append(",").append(productsOrdered.get(key));
        }
        return timeOfOrder + "," + restaurantId.id + "," + driverID.id + "," + addressOfDelivery.convertEntityToCsvString() + mapAsString;
    }

    @Override
    public void convertCsvStringToEntity(String CsvString) {

        String[] tempArr = CsvString.split(CsvDelimiter);

        try {
            this.timeOfOrder = LocalDateTime.parse(tempArr[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            this.restaurantId.id = Long.valueOf(tempArr[1]);
            this.driverID.id = Long.valueOf(tempArr[2]);

            (this.addressOfDelivery = new Address()).convertCsvStringToEntity(tempArr[3] + "," + tempArr[4] + "," + tempArr[5] + "," + tempArr[6]);

            for (int i = 7; i < tempArr.length - 1; i += 2) {
                this.productsOrdered.put(Long.valueOf(tempArr[i]), Integer.valueOf(tempArr[i + 1]));
            }
        } catch (Exception exception) {
            System.out.println("Exceptie la citirea comenzilor din CSV : " + exception);
        }
    }
}
