package pao.Components;

import java.util.Arrays;
import java.util.Map;

public class Restaurant extends Entity<Long, Restaurant> {
    static long serialRestaurantsNumber = 0;

    private Address address;
    private String name;
    private Stock restaurantStock;
    private double commissionOfDelivery;

    public Restaurant() {
        super(serialRestaurantsNumber);
        serialRestaurantsNumber += 1;
    }

    public Restaurant(Address address, String name, Stock restaurantStock, double commissionOfDelivery) {
        super(serialRestaurantsNumber);
        serialRestaurantsNumber += 1;

        this.address = address;
        this.name = name;
        this.restaurantStock = restaurantStock;
        this.commissionOfDelivery = commissionOfDelivery;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCommissionOfDelivery() {
        return commissionOfDelivery;
    }

    public void setCommissionOfDelivery(double commissionOfDelivery) {
        this.commissionOfDelivery = commissionOfDelivery;
    }

    public void updateStock(Map<Long, Integer> productsOrdered) {
        restaurantStock.updateStock(productsOrdered);
    }

    public Stock getRestaurantStock() {
        return restaurantStock;
    }

    public void setRestaurantStock(Stock restaurantStock) {
        this.restaurantStock = restaurantStock;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + getId()+
                ", address=" + address.toString() +
                ", name='" + name + '\'' +
                ", commissionOfDelivery=" + commissionOfDelivery +
                Stock.class.toString() +
                '}';
    }

    @Override
    public String convertEntityToCsvString() {
        return name + "," + commissionOfDelivery + "," + address.convertEntityToCsvString() + "," + restaurantStock.convertEntityToCsvString();
    }

    @Override
    public void convertCsvStringToEntity(String CsvString) {
        String []tempArr = CsvString.split(CsvDelimiter);
        StringBuilder stockString = null;

        try {
            this.name = tempArr[0];
            this.commissionOfDelivery = Double.parseDouble(tempArr[1]);
            (this.address = new Address()).convertCsvStringToEntity(tempArr[2] + "," + tempArr[3] + "," + tempArr[4] + "," + tempArr[5]);

            for (int i = 6; i < tempArr.length; i++) {
                stockString.append(",").append(tempArr[i]);
            }

            (this.restaurantStock = new Stock()).convertCsvStringToEntity(stockString.toString());
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea restaurantelor din CSV : " + exception);
        }
    }
}
