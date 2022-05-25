package pao.components;

import java.util.Arrays;
import java.util.Map;

public class Restaurant extends Entity<Long> {
    static long serialRestaurantsNumber = 0;

    private Long addressID;
    private String name;
    private Long stockID;
    private Double commissionOfDelivery;

    public Restaurant() {
        super(serialRestaurantsNumber);
        serialRestaurantsNumber += 1;
    }

    public Restaurant(Long address, String name, Long restaurantStock, double commissionOfDelivery) {
        super(serialRestaurantsNumber);
        serialRestaurantsNumber += 1;

        this.addressID = address;
        this.name = name;
        this.stockID = restaurantStock;
        this.commissionOfDelivery = commissionOfDelivery;
    }


    public String getName() {
        return name;
    }

    public Long getAddressID() {
        return addressID;
    }

    public Long getStockID() {
        return stockID;
    }

    public double getCommissionOfDelivery() {
        return commissionOfDelivery;
    }

    public void setCommissionOfDelivery(double commissionOfDelivery) {
        this.commissionOfDelivery = commissionOfDelivery;
    }

//    public void updateStock(Map<Long, Integer> productsOrdered) {
//        restaurantStock.updateStock(productsOrdered);
//    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + getId()+
                ", addressID=" + addressID +
                ", name='" + name + '\'' +
                ", commissionOfDelivery=" + commissionOfDelivery +
                ", stockID=" + stockID +
                '}';
    }

    @Override
    public String convertEntityToCsvString() {
        return name + "," + commissionOfDelivery + "," + addressID + "," + stockID;
    }

    @Override
    public void convertCsvStringToEntity(String CsvString) {
        String []tempArr = CsvString.split(CsvDelimiter);

        try {
            this.name = tempArr[0];
            this.commissionOfDelivery = Double.parseDouble(tempArr[1]);
            this.addressID = Long.valueOf(tempArr[2]);
            this.stockID = Long.valueOf(tempArr[3]);
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea restaurantelor din CSV : " + exception);
        }
    }
}
