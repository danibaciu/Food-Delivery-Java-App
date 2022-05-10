package pao.Components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserConsumer extends AbstractUser {

    private LocalDate dateOfSignUp;
    private List<Order> lastOrders;

    public UserConsumer(String firstName, String lastName, String emailAddress, LocalDate timeOfBirth, Address addressOfLiving) {
        super(firstName, lastName, emailAddress, timeOfBirth, addressOfLiving);
        dateOfSignUp = LocalDate.now();
        lastOrders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        lastOrders.add(order);
    }

    public Order getLastOrder() {
        return this.lastOrders.get(lastOrders.size() - 1);
    }

    public Integer getNumOfOrders() {
        return this.lastOrders.size();
    }

    public Long getNoOfOrdersForADriver(UserEmployee driver) {
        Long counter = 0L;
        for (Order order : lastOrders) {
            if (Objects.equals(driver.getId(), order.getDriverID())) {
                counter += 1;
            }
        }
        return counter;
    }

    public double returnSumLastOrder(List<Product> restaurantProducts) {
        if (lastOrders.size() >= 1)
            return lastOrders.get(lastOrders.size() - 1).calculateTotalFee(restaurantProducts);
        return 0.0;
    }

    public LocalDateTime returnTimeOfLastOrder() {
        if (lastOrders.size() >= 1)
            return lastOrders.get(lastOrders.size() - 1).getDateTime();
        return LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "UserConsumer {" +
                super.toString() +
                ", dateOfSignUp=" + dateOfSignUp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                '}';
    }

    @Override
    public String convertEntityToCsvString() {
        StringBuilder orders = new StringBuilder();
        for (var k : lastOrders) {
            orders.append(",").append(k.convertEntityToCsvString());
        }
        return super.convertEntityToCsvString() + "," + dateOfSignUp.toString() + "," + orders;
    }

    @Override
    public void convertCsvStringToEntity(String CsvString) {
        String []tempArr = CsvString.split(CsvDelimiter);

        try {
            StringBuilder abstractConstructor = new StringBuilder(tempArr[0]);
            for (int i = 1; i <= 8; i++) {
                abstractConstructor.append(",").append(tempArr[i]);
            }
            super.convertCsvStringToEntity(abstractConstructor.toString());
            this.dateOfSignUp = LocalDate.parse(tempArr[9]);
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea utilizatorului din CSV : " + exception);
        }
    }
}
