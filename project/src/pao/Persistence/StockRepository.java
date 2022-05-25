package pao.persistence;

import pao.components.Stock;
import pao.setup.AbstractRepository;
import pao.setup.PreparedStatementFactory;
import pao.setup.RepositoryException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


public class StockRepository extends AbstractRepository<Long, Stock> implements IStockRepository {
    public StockRepository(Properties props) {
        super(props);
    }

    @Override
    public void save(Stock elem) {
        logger.traceEntry("Saving stock {}", elem);
        try {
            ensureConnection();
            String sql = "insert into stock (timestamp) values (?)";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getDateLastSupply().toString());
            statement.executeUpdate();

            for (Long id : elem.getProducts().keySet()) {
                ensureConnection();
                String insertion = "insert into stock_products (idStock, idProduct, numberOfProducts) values (?, ?, ?)";
                PreparedStatement s = PreparedStatementFactory.prepareStatement(connection, insertion, elem.getId(), id, elem.getProducts().get(id));
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
    public void delete(Stock elem) {
        logger.traceEntry("Deleting stock {}", elem);
        try {
            ensureConnection();
            String sql = "delete from stock where id = ?";
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
    public void update(Stock elem) {
        logger.traceEntry("Updating stock {}", elem);
        try {
            ensureConnection();
            String sql = "update stock set timestamp = ? where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getDateLastSupply(), elem.getId());
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
    public Stock findOne(Long id) {
        logger.traceEntry("Finding stock with id {}", id);
        try {
            Map<Long, Integer> m = new TreeMap<>();
            ensureConnection();
            String sql = "select * from stock where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, id);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                logger.trace("No stock instance found");
                throw new RepositoryException("Element not found!");
            }

            String query = "select * from stock_products where idStock = ?";
            PreparedStatement s = PreparedStatementFactory.prepareStatement(connection, query, id);
            ResultSet res = s.executeQuery();

            while (res.next()) {
                m.put(res.getLong(1), res.getInt(2));
            }

            Stock stock = new Stock(LocalDate.parse(result.getString("timestamp")), m);

            destroyConnection();

            logger.trace("Found 1 instance", stock);

            return stock;
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
            return null;
        } finally {
            logger.traceExit();
        }
    }

    @Override
    public List<Stock> findAll() {
        logger.traceEntry("Finding all stocks");
        List<Stock> stockList = new ArrayList<>();
        try {
            ensureConnection();
            String sql = "select * from stocks";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {

                Map<Long, Integer> m = new TreeMap<>();

                String query = "select * from stock_products where idStock = ?";

                PreparedStatement s = PreparedStatementFactory.prepareStatement(connection, query, result.getLong("id"));

                ResultSet res = s.executeQuery();

                while (res.next()) {
                    m.put(res.getLong(1), res.getInt(2));
                }

                Stock stock = new Stock(LocalDate.parse(result.getString("timestamp")), m);
                stockList.add(stock);
            }
            destroyConnection();
            logger.trace("Found all instances");
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
        } finally {
            logger.traceExit();
        }
        return stockList;
    }
}
