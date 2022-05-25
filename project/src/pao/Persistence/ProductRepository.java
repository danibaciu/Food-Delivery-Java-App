package pao.persistence;

import pao.components.Product;
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
create table product(
id integer primary key auto_increment,
name varchar(100),
unityOfMeasurement varchar(20),
price double,
quantity double
);
 */

/*
insert into product values (null, 'Pizza', 'KG', 45, 0.5);
insert into product values (null, 'Tiramisu', 'KG', 29, 0.2);
insert into product values (null, 'Tarta cu visine', 'KG', 120, 1.0);
insert into product values (null, 'Tomahawk', 'KG', 2799, 1.0);
insert into product values (null, 'Cafea', 'ML', 10, 100.0);
insert into product values (null, 'Bere', 'ML', 15, 500.0);
 */

public class ProductRepository extends AbstractRepository<Long, Product> implements IProductRepository{
    public ProductRepository(Properties props) {
        super(props);
    }

    @Override
    public void save(Product elem) {
        logger.traceEntry("Saving product {}", elem);
        try {
            ensureConnection();
            String sql = "insert into product values (null, ?, ?, ?, ?)";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getNameOfProduct(), elem.getUnityOfMeasurement(), elem.getPrice(), elem.getQuantity());
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
    public void delete(Product elem) {
        logger.traceEntry("Deleting product {}", elem);
        try {
            ensureConnection();
            String sql = "delete from product where id = ?";
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
    public void update(Product elem) {
        logger.traceEntry("Updating product {}", elem);
        try {
            ensureConnection();
            String sql = "update address set name = ?, unityOfMeasurement = ?, price = ?, quantity = ? where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getNameOfProduct(), elem.getUnityOfMeasurement(), elem.getPrice(), elem.getQuantity(), elem.getId());
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
    public Product findOne(Long id) {
        logger.traceEntry("Finding product with id {}", id);
        try {
            ensureConnection();

            String sql = "select * from product where id = ?";

            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, id);
            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                logger.trace("No address instance found");
                throw new RepositoryException("Element not found!");
            }

            Product product = new Product(result.getString("name"), result.getString("unityOfMeasurement"), result.getDouble("price"), result.getDouble("quantity"));

            destroyConnection();

            logger.trace("Found 1 instance", product);

            return product;
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
            return null;
        } finally {
            logger.traceExit();
        }
    }

    @Override
    public List<Product> findAll() {
        logger.traceEntry("Finding all products");
        List<Product> productList = new ArrayList<>();
        try {
            ensureConnection();
            String sql = "select * from product";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = new Product(result.getString("name"), result.getString("unityOfMeasurement"), result.getDouble("price"), result.getDouble("quantity"));
                productList.add(product);
            }
            destroyConnection();
            logger.trace("Found all instances");
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
        } finally {
            logger.traceExit();
        }
        return productList;
    }
}
