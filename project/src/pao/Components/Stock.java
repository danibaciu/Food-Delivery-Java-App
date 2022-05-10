package pao.Components;

import java.time.LocalDate;
import java.util.Map;

public class Stock extends IOStream<Stock> {
    private Map<Long, Integer> products;
    private LocalDate dateLastSupply;

    public Stock(){}

    public Stock(Map<Long, Integer> products) {
        this.products = products;
        this.dateLastSupply = LocalDate.now();
    }

    public LocalDate getDateLastSupply() {
        return dateLastSupply;
    }

    public void updateStock(Map<Long, Integer> productsOrdered) {
        for (var produs : productsOrdered.keySet()) {

            if (products.containsKey(produs)) {

                products.put(produs, products.get(produs) - productsOrdered.get(produs)); // I decreased the stock

                if (products.get(produs) < 0) { // if we don't have enough stocks
                    dateLastSupply = LocalDate.now();
                    products.put(produs, products.get(produs) + 100); // add 100 products
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder("{");

        for (var key : products.keySet()) {
            mapAsString.append(key + " x " + products.get(key) + ", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");

        return "Stock{" +
                "products=" + mapAsString +
                ", dateLastSupply=" + dateLastSupply +
                '}';
    }

    @Override
    public String convertEntityToCsvString() {
        StringBuilder stockProducts = new StringBuilder();
        for (var key : products.keySet()) {
            stockProducts.append(",(").append(key).append("*").append(products.get(key)).append(")");
        }
        return dateLastSupply.toString() + stockProducts;
    }

    @Override
    public void convertCsvStringToEntity(String CsvString) {
        String []tempArr = CsvString.split(CsvDelimiter);

        try {
           this.dateLastSupply = LocalDate.parse(tempArr[0]);
            for (int i = 1; i < tempArr.length; i++) {
                String []prod = tempArr[i].split("/(/)/*");
                this.products.put(Long.valueOf(prod[0]), Integer.valueOf(prod[1]));
            }
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea stocurilor din CSV : " + exception);
        }
    }
}
