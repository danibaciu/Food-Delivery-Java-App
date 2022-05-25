package pao.persistence;

import pao.components.Restaurant;
import pao.setup.AbstractRepository;
import pao.setup.PreparedStatementFactory;
import pao.setup.RepositoryException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RestaurantRepository extends AbstractRepository<Long, Restaurant> implements IRestaurantRepository {

    public RestaurantRepository(Properties props) {
        super(props);
    }

    @Override
    public void save(Restaurant elem) {
        logger.traceEntry("saving restaurant {}", elem);
        try {
            ensureConnection();
            String sql = "insert into restaurant (name, commision_of_delivery, address_id, stock_id) values (?, ?, ?, ?)";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getName(), elem.getCommissionOfDelivery(), elem.getAddressID(), elem.getStockID());
            statement.executeUpdate();
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
    public void delete(Restaurant elem) {
        logger.traceEntry("deleting restaurant {}", elem);
        try {
            ensureConnection();
            String sql = "delete from restaurant where id = ?";
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
    public void update(Restaurant elem) {
        logger.traceEntry("Updating restaurant {}", elem);
        try {
            ensureConnection();
            String sql = "update restaurant set name = ?, commision_of_delivery = ?, address_id = ?, stock_id = ? where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getName(),
                    elem.getCommissionOfDelivery(), elem.getAddressID(), elem.getStockID(), elem.getId());
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
    public Restaurant findOne(Long id) {
        logger.traceEntry("Finding restaurant with id {}", id);
        try {
            ensureConnection();
            String sql = "select * from restaurent where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, id);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                logger.trace("No restaurant instance found");
                throw new RepositoryException("Element not found!");
            }

            Restaurant restaurant = new Restaurant(result.getLong("addressID"), result.getString("name"), result.getLong("stockID"),
                    result.getDouble("commision_of_delivery"));
            destroyConnection();
            logger.trace("Found 1 instance", restaurant);
            return restaurant;

        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
            return null;
        } finally {
            logger.traceExit();
        }
    }

    @Override
    public List<Restaurant> findAll() {
        logger.traceEntry("Finding all restaurants");
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            ensureConnection();
            String sql = "select * from restaurant";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Restaurant restaurant = new Restaurant(result.getLong("addressID"), result.getString("name"), result.getLong("stockID"),
                        result.getDouble("commision_of_delivery"));
                restaurantList.add(restaurant);
            }
            destroyConnection();
            logger.trace("Found all instances");
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
        } finally {
            logger.traceExit();
        }
        return restaurantList;
    }
}
