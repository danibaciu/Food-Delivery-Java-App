package pao.setup;

import org.apache.logging.log4j.LogManager;
import pao.components.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.Logger;

public abstract class AbstractRepository<T, E extends Entity<T>> implements IRepository<T, E> {

    private final JDBCUtils dbUtils;

    protected Connection connection;

    protected static final Logger logger = LogManager.getLogger();

    public AbstractRepository(Properties props) {
        dbUtils = new JDBCUtils(props);
    }

    protected void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dbUtils.getConnection();
        }
    }

    protected void destroyConnection() throws SQLException {
        if (connection != null || !connection.isClosed()) {
            connection.close();
        }
    }
}