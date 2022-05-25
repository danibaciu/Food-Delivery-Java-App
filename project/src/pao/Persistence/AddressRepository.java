package pao.persistence;
import pao.components.Address;
import pao.setup.AbstractRepository;
import pao.setup.PreparedStatementFactory;
import pao.setup.RepositoryException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
create table address (
id integer primary key auto_increment,
country varchar(50),
city varchar(100),
streetName varchar(100),
streetNumber integer
);
 */

public class AddressRepository extends AbstractRepository<Long, Address> implements IAddressRepository {
    public AddressRepository(Properties props) {
        super(props);
    }

    @Override
    public void save(Address elem) {
        logger.traceEntry("Saving address {}", elem);
        try {
            ensureConnection();
            String sql = "insert into address (country, city, streetName, streetNumber) values (?, ?, ?, ?)";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getCountry(), elem.getCity(), elem.getStreet(), elem.getStreetNumber());
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
    public void delete(Address elem) {
        logger.traceEntry("Deleting address {}", elem);
        try {
            ensureConnection();
            String sql = "delete from address where id = ?";
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
    public void update(Address elem) {
        logger.traceEntry("Updating restaurant {}", elem);
        try {
            ensureConnection();
            String sql = "update address set country = ?, city = ?, streetName = ?, streetNumber = ? where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getCountry(), elem.getCity(), elem.getStreet(), elem.getStreetNumber(), elem.getId());
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
    public Address findOne(Long id) {
        logger.traceEntry("Finding address with id {}", id);
        try {
            ensureConnection();
            String sql = "select * from address where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, id);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                logger.trace("No address instance found");
                throw new RepositoryException("Element not found!");
            }

            Address address = new Address(result.getString("country"), result.getString("city"), result.getString("streetName"),
                    result.getInt("streetNumber"));
            destroyConnection();
            logger.trace("Found 1 instance", address);
            return address;
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
            return null;
        } finally {
            logger.traceExit();
        }
    }

    @Override
    public List<Address> findAll() {
        logger.traceEntry("Finding all addresses");
        List<Address> addressList = new ArrayList<>();
        try {
            ensureConnection();
            String sql = "select * from address";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Address address = new Address(result.getString("country"), result.getString("city"), result.getString("streetName"),
                        result.getInt("streetNumber"));
                addressList.add(address);
            }
            destroyConnection();
            logger.trace("Found all instances");
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
        } finally {
            logger.traceExit();
        }
        return addressList;
    }
}
