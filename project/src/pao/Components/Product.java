package pao.Components;

import java.util.*;

public class Product extends Entity<Long, Product>{
    static long serialProductsNumber = 0;

    private String nameOfProduct, unityOfMeasurement;
    private SortedSet<String> ingredients = new TreeSet<>();
    private double price, quantity;

    public Product(){
        super(serialProductsNumber);
        serialProductsNumber += 1;
    }

    public Product(String nameOfProduct, String unityOfMeasurement, SortedSet<String> ingredients, double price, double quantity) {
        super(serialProductsNumber);
        serialProductsNumber += 1;

        this.nameOfProduct = nameOfProduct;
        this.unityOfMeasurement = unityOfMeasurement;
        this.ingredients = ingredients;
        this.price = price;
        this.quantity = quantity;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }

    public String getUnityOfMeasurement() {
        return unityOfMeasurement;
    }

    public void setUnityOfMeasurement(String unityOfMeasurement) {
        this.unityOfMeasurement = unityOfMeasurement;
    }

    public SortedSet<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(SortedSet<String> ingredients) {
        this.ingredients = ingredients;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "nameOfProduct='" + nameOfProduct + '\'' +
                ", unityOfMeasurement='" + unityOfMeasurement + '\'' +
                ", ingredients=" + Arrays.toString(ingredients.toArray()) +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public String convertEntityToCsvString() {
        StringBuilder ingred = new StringBuilder();
        for (var ingredient : ingredients) {
            ingred.append(",").append(ingredient);
        }
        return nameOfProduct + "," + unityOfMeasurement + "," + price + "," + quantity + ingred;
    }

    @Override
    public void convertCsvStringToEntity(String CsvString) {
        String []tempArr = CsvString.split(CsvDelimiter);

        try {
            this.nameOfProduct = tempArr[0];
            this.unityOfMeasurement = tempArr[1];
            this.price = Double.parseDouble(tempArr[2]);
            this.quantity = Double.parseDouble(tempArr[3]);
            this.ingredients.addAll(Arrays.asList(tempArr).subList(4, tempArr.length));
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea produselor din CSV : " + exception);
        }
    }
}
