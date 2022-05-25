package pao.persistence;

import pao.components.UserConsumer;
import pao.setup.AbstractRepository;
import pao.setup.PreparedStatementFactory;
import pao.setup.RepositoryException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
create table consumer (
id integer primary key auto_increment,
firstName varchar(30),
lastName varchar(50),
email varchar(80),
dateOfBirth varchar(50)
);
 */


/*
create table consumer_orders (
idConsumer integer not null,
idOrder integer not null
);
 */

/*
insert into consumer_orders values(1, 1);
insert into consumer_orders values(1, 2);
insert into consumer_orders values(1, 3);
insert into consumer_orders values(1, 3);

insert into consumer_orders values(2, 2);
insert into consumer_orders values(2, 2);
insert into consumer_orders values(2, 3);
insert into consumer_orders values(2, 1);

insert into consumer_orders values(3, 1);
insert into consumer_orders values(3, 1);
insert into consumer_orders values(3, 1);

insert into consumer_orders values(4, 4);
insert into consumer_orders values(4, 1);
insert into consumer_orders values(4, 3);
 */

public class ConsumerRepository  extends AbstractRepository<Long, UserConsumer> implements IConsumerRepository{
    public ConsumerRepository(Properties props) {
        super(props);
    }

    @Override
    public void save(UserConsumer elem) {
        logger.traceEntry("Saving consumer {}", elem);
        try {

            ensureConnection();
            String sql = "insert into consumer (firstName, lastName, email, dateOfBirth) values (?, ?, ?, ?)";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getFirstName(), elem.getLastName(), elem.getEmailAddress(), elem.getDateOfBirth().toString());
            statement.executeUpdate();

            for (var id : elem.getLastOrders()) {
                ensureConnection();
                String insertion = "insert into consumer_orders (idConsumer, idOrder) values (?, ?)";
                PreparedStatement s = PreparedStatementFactory.prepareStatement(connection, insertion, elem.getId(), id);
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
    public void delete(UserConsumer elem) {
        logger.traceEntry("Deleting consumer {}", elem);
        try {
            ensureConnection();
            String sql = "delete from consumer where id = ?";
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
    public void update(UserConsumer elem) {
        logger.traceEntry("Updating consumer {}", elem);
        try {
            ensureConnection();
            String sql = "update address set firstName = ?, lastName = ?, email = ?, dateOfBirth = ? where id = ?";
            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, elem.getFirstName(), elem.getLastName(), elem.getEmailAddress(), elem.getDateOfBirth().toString(),elem.getId());
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
    public UserConsumer findOne(Long id) {
        logger.traceEntry("Finding consumer with id {}", id);
        try {
            List<Integer> ord = new ArrayList<>();

            ensureConnection();

            String sql = "select * from consumer where id = ?";

            PreparedStatement statement = PreparedStatementFactory.prepareStatement(connection, sql, id);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                logger.trace("No address instance found");
                throw new RepositoryException("Element not found!");
            }

            String query = "select * from consumer_orders where idConsumer = ?";
            PreparedStatement s = PreparedStatementFactory.prepareStatement(connection, query, id);
            ResultSet res = s.executeQuery();

            while (res.next()) {
                ord.add(res.getInt(1));
            }
            //aici nu am tinut minte si adresa userului pentru ca ma complicam. Ori puneam foreign key, ori trebuia sa am inca 4 campuri in database

            UserConsumer userConsumer = new UserConsumer(result.getString("firstName"), result.getString("lastName"), result.getString("email"), LocalDate.parse(result.getString("dateOfBirth")), null, ord);

            destroyConnection();

            logger.trace("Found 1 instance", userConsumer);

            return userConsumer;
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
            return null;
        } finally {
            logger.traceExit();
        }
    }

    @Override
    public List<UserConsumer> findAll() {
        logger.traceEntry("Finding all consumers");
        List<UserConsumer> consumerList = new ArrayList<>();
        try {
            ensureConnection();
            String sql = "select * from consumer";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {

                List<Integer> ord = new ArrayList<>();

                String query = "select * from consumer_orders where idConsumer = ?";
                PreparedStatement s = PreparedStatementFactory.prepareStatement(connection, query, result.getLong("id"));
                ResultSet res = s.executeQuery();

                while (res.next()) {
                    ord.add(res.getInt(1));
                }
                //aici nu am tinut minte si adresa userului pentru ca ma complicam. Ori puneam foreign key, ori trebuia sa am inca 4 campuri in database

                UserConsumer userConsumer = new UserConsumer(result.getString("firstName"), result.getString("lastName"), result.getString("email"), LocalDate.parse(result.getString("dateOfBirth")), null, ord);

                consumerList.add(userConsumer);
            }
            destroyConnection();
            logger.trace("Found all instances");
        } catch (SQLException throwables) {
            logger.error(throwables);
            throwables.printStackTrace();
        } finally {
            logger.traceExit();
        }
        return consumerList;
    }

    @Override
    public void returnLastOrder() {

    }
}
