package pao.components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserConsumer extends AbstractUser {

    private LocalDate dateOfSignUp;
    private List<Integer> lastOrders;

    public UserConsumer(){}

    public UserConsumer(String firstName, String lastName, String emailAddress, LocalDate timeOfBirth, Address addressOfLiving) {
        super(firstName, lastName, emailAddress, timeOfBirth, addressOfLiving);
        dateOfSignUp = LocalDate.now();
        lastOrders = new ArrayList<>();
    }

    public UserConsumer(String firstName, String lastName, String emailAddress, LocalDate timeOfBirth, Address addressOfLiving, List<Integer> lst) {
        super(firstName, lastName, emailAddress, timeOfBirth, addressOfLiving);
        dateOfSignUp = LocalDate.now();
        lastOrders = lst;
    }

    public void addOrder(Integer order) {
        lastOrders.add(order);
    }

    public List<Integer> getLastOrders() {
        return lastOrders;
    }

    public Order getLastOrder(List<Order> orders) {
        return orders.get(this.lastOrders.get(lastOrders.size() - 1));
    }

    public Integer getNumOfOrders() {
        return this.lastOrders.size();
    }

    public Long getNoOfOrdersForADriver(UserEmployee driver, List<Order> orders) {
        Long counter = 0L;
        for (var order : lastOrders) {
            if (Objects.equals(driver.getId(), orders.get(order).getDriverID())) {
                counter += 1;
            }
        }
        return counter;
    }

    public LocalDateTime returnTimeOfLastOrder(List<Order> orders) {
        if (lastOrders.size() >= 1)
            return orders.get(lastOrders.get(lastOrders.size() - 1)).getDateTime();
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
            orders.append(",").append(k);
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

            this.lastOrders = new ArrayList<>();

            for (int i = 10; i < tempArr.length; i++) {
                this.lastOrders.add(Integer.valueOf(tempArr[i]));
            }
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea utilizatorului din CSV : " + exception);
        }
    }
}
