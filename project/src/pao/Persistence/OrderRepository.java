package pao.persistence;

import pao.components.Address;
import pao.components.Order;
import pao.setup.AbstractRepository;
import pao.setup.PreparedStatementFactory;
import pao.setup.RepositoryException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/*
create table orders (
id integer primary key auto_increment,
timestamp varchar(100),
restaurantId integer not null,
driverId integer not null,
addressCountry varchar(100),
addressCity varchar(100),
addressStreetName varchar(100),
addressNumber integer
);
 */

/*
create table order_products (
idOrder integer not null,
idProduct integer not null,
numberOfProducts integer not null
);
 */

/*
insert into order_products values(1, 1, 1);
insert into order_products values(1, 2, 1);
insert into order_products values(2, 1, 2);
insert into order_products values(2, 3, 1);
insert into order_products values(3, 1, 2);
insert into order_products values(3, 3, 1);
insert into order_products values(4, 2, 2);
insert into order_products values(4, 3, 1);
insert into order_products values(5, 1, 1);
insert into order_products values(5, 4, 2);
 */

public class OrderRepository extends AbstractRepository<Long, Order> implements IOrderRepository{
    public OrderRepository(Properties props) {
        super(props);
    }

    @Override
    public void save(Order elem) {
        logger.traceEntry("Saving order {}", elem);
        try {
            ensureConnection();

            String sql = "insert into orders values (null, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getDateTime().toString(), elem.getRestaurantId(), elem.getDriverID(), elem.getAddressOfDelivery().getCountry(), elem.getAddressOfDelivery().getCity(), elem.getAddressOfDelivery().getStreet(), elem.getAddressOfDelivery().getStreetNumber());

            statement.executeUpdate();

            for (Long id : elem.getProductsOrdered().keySet()) {
                ensureConnection();
                String insertion = "insert into order_products (idOrder, idProduct, numberOfProducts) values (?)";
                PreparedStatement s = PreparedStatementFactory.prepareStatement(connection, insertion, elem.getId(), id, elem.getProductsOrdered().get(id));
                s.executeUpdate();
            }

            destroyConnection();
            logger.trace("Saved {} instances", elem);
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
        } finally {
            logger.traceExit();
        }
    }

    @Override
    public void delete(Order elem) {
        logger.traceEntry("Deleting order {}", elem);
        try {
            ensureConnection();
            String sql = "delete from orders where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getId());
            statement.executeUpdate();
            destroyConnection();
            logger.trace("Deleted {} instances", elem);
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
        } finally {
            logger.traceExit();
        }
    }

    @Override
    public void update(Order elem) {
        logger.traceEntry("Updating order {}", elem);
        try {
            ensureConnection();
            String sql = "update orders set restaurantId = ?, driverId = ?, addressCountry = ?, addressCity = ?, addressStreetName = ?, addressNumber = ? where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getRestaurantId(), elem.getDriverID(), elem.getAddressOfDelivery().getCountry(), elem.getAddressOfDelivery().getCity(), elem.getAddressOfDelivery().getStreet(), elem.getAddressOfDelivery().getStreetNumber(), elem.getId());
            statement.executeUpdate();
            destroyConnection();
            logger.trace("Updated {} instances", elem);
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
        } finally {
            logger.traceExit();
        }
    }

    @Override
    public Order findOne(Long id) {
        logger.traceEntry("Finding order with id {}", id);
        try {
            Map<Long, Integer> m = new TreeMap<>();

            ensureConnection();

            String sql = "select * from orders where id = ?";

            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, id);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                logger.trace("No stock instance found");
                throw new RepositoryException("Element not found!");
            }

            String query = "select * from order_products where idOrder = ?";

            PreparedStatement s = PreparedStatementFactory.prepareStatement(connection, query, id);

            ResultSet res = s.executeQuery();

            while (res.next()) {
                m.put(res.getLong(1), res.getInt(2));
            }

            Order order = new Order(new Address(result.getString("addressCountry"), result.getString("addressCity"), result.getString("addressStreetName"), result.getInt("addressStreetNumber")), m, result.getInt("restaurantId"), result.getInt("driverId"));

            destroyConnection();

            logger.trace("Found 1 instance", order);

            return order;
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
            return null;
        } finally {
            logger.traceExit();
        }
    }

    @Override
    public List<Order> findAll() {
        logger.traceEntry("Finding all orders");
        List<Order> orderList = new ArrayList<>();
        try {
            ensureConnection();
            String sql = "select * from orders";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                Map<Long, Integer> m = new TreeMap<>();

                String query = "select * from order_products where idOrder = ?";

                PreparedStatement s = PreparedStatementFactory.prepareStatement(connection, query, result.getLong("id"));

                ResultSet res = s.executeQuery();

                while (res.next()) {
                    m.put(res.getLong(1), res.getInt(2));
                }

                Order order = new Order(new Address(result.getString("addressCountry"), result.getString("addressCity"), result.getString("addressStreetName"), result.getInt("addressStreetNumber")), m, result.getInt("restaurantId"), result.getInt("driverId"));

                orderList.add(order);
            }
            destroyConnection();
            logger.trace("Found all instances");
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
        } finally {
            logger.traceExit();
        }
        return orderList;
    }
}
