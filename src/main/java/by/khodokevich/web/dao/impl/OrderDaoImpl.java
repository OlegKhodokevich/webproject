package by.khodokevich.web.dao.impl;

import static by.khodokevich.web.dao.impl.OrderColumnName.*;

import by.khodokevich.web.dao.AbstractDao;
import by.khodokevich.web.dao.OrderDao;
import by.khodokevich.web.entity.*;
import by.khodokevich.web.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_ALL_ORDER = "SELECT IdOrder, IdUserCustomer, Title, JobDescription, Address, CreationDate, CompletionDate, Specialization, OrderStatus FROM orders JOIN specializations ON orders.IdSpecialization = specializations.IdSpecialization;";
    private static final String SQL_SELECT_DEFINED_ORDER = "SELECT IdOrder, IdUserCustomer, Title, JobDescription, Address, CreationDate, CompletionDate, Specialization, OrderStatus FROM orders JOIN specializations ON orders.IdSpecialization = specializations.IdSpecialization WHERE IdOrder = ?;";
    private static final String SQL_SELECT_USERS_ORDERS = "SELECT IdOrder, IdUserCustomer, Title, JobDescription, Address, CreationDate, CompletionDate, Specialization, OrderStatus FROM orders JOIN specializations ON orders.IdSpecialization = specializations.IdSpecialization WHERE IdUserCustomer = ?;";
    private static final String SQL_SELECT_ORDERS_BY_SPECIALIZATIONS = "SELECT IdOrder, IdUserCustomer, Title, JobDescription, Address, CreationDate, CompletionDate, Specialization, OrderStatus FROM orders JOIN specializations ON orders.IdSpecialization = specializations.IdSpecialization WHERE Specialization = ?;";
    private static final String SQL_DELETE_DEFINED_ORDER_BY_ID = "DELETE FROM orders WHERE IdOrder = ?;";
    private static final String SQL_DELETE_DEFINED_ORDER_BY_IDUSER_TITLE_AND_ADDRESS = "DELETE FROM orders WHERE IdUserCustomer = ? AND Title = ? AND Address = ?;";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(IdUserCustomer, Title, JobDescription, Address, CreationDate, CompletionDate, IdSpecialization, OrderStatus ) VALUES (?, ?, ?, ?, ?, ?, (SELECT IdSpecialization FROM specializations WHERE Specialization = ?), ?);";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET IdUserCustomer = ?, Title = ?, JobDescription = ?, Address = ?, CreationDate = ?, CompletionDate = ?, IdSpecialization = (SELECT IdSpecialization FROM specializations WHERE Specialization = ?), OrderStatus = ?  WHERE IdOrder = ?;";
    private static final String SQL_SET_ORDER_STATUS = "UPDATE orders SET OrderStatus = ?  WHERE IdOrder = ?;";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public List<Order> findAll() throws DaoException {
        logger.info("Start findAll().");
        List<Order> orders = new ArrayList<>();
        SimpleDateFormat parser = new SimpleDateFormat(DATE_PATTERN);
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_ORDER);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                long orderId = resultSet.getLong(ID_ORDER);
                long userId = resultSet.getLong(ID_CUSTOMER);
                String title = resultSet.getString(TITLE);
                String description = resultSet.getString(JOB_DESCRIPTION);
                String address = resultSet.getString(ADDRESS);
                Date creationDate = parser.parse(resultSet.getString(CREATION_DATE));
                Date completionDate = parser.parse(resultSet.getString(COMPLETION_DATE));
                Specialization specialization = Specialization.valueOf(resultSet.getString(SPECIALIZATION).toUpperCase());
                OrderStatus status = OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase());

                Order order = new Order(orderId, userId, title, description, address, creationDate, completionDate, specialization, status);
                logger.info("Has found next order = " + order);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        } catch (ParseException e) {
            logger.error("Can't parse date completion.", e);
            throw new DaoException("Can't parse date completion.", e);
        }
        logger.info("Has found next orders : " + orders);
        return orders;
    }

    @Override
    public Optional<Order> findEntityById(long id) throws DaoException {
        logger.info("Start findEntityById(long id). Order id = " + id);
        Order order = null;

        SimpleDateFormat parser = new SimpleDateFormat(DATE_PATTERN);
        String creationDateString = null;
        String completionDateString = null;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_DEFINED_ORDER)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long orderId = resultSet.getLong(ID_ORDER);
                    long userId = resultSet.getLong(ID_CUSTOMER);
                    String title = resultSet.getString(TITLE);
                    String description = resultSet.getString(JOB_DESCRIPTION);
                    String address = resultSet.getString(ADDRESS);
                    creationDateString = resultSet.getString(COMPLETION_DATE);
                    Date creationDate = parser.parse(creationDateString);
                    completionDateString = resultSet.getString(COMPLETION_DATE);
                    Date completionDate = parser.parse(completionDateString);
                    Specialization specialization = Specialization.valueOf(resultSet.getString(SPECIALIZATION).toUpperCase());
                    OrderStatus status = OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase());

                    order = new Order(orderId, userId, title, description, address, creationDate, completionDate, specialization, status);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        } catch (ParseException e) {
            logger.error("Can't parse date completion. creationDateString = " + creationDateString + ", completionDateString = " + completionDateString, e);
            throw new DaoException("Can't parse date completion. creationDateString = " + creationDateString + ", completionDateString = " + completionDateString, e);
        }
        logger.info("Has found next order : " + order);
        return Optional.ofNullable(order);
    }


    @Override
    public boolean delete(long id) throws DaoException {
        logger.info("Start delete(long id). Order's ID = " + id);
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_DELETE_DEFINED_ORDER_BY_ID)) {
            statement.setLong(1, id);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed.");
        return result;
    }

    @Override
    public boolean delete(Order entity) throws DaoException {
        logger.info("Start delete(Order entity)." + entity);
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_DELETE_DEFINED_ORDER_BY_IDUSER_TITLE_AND_ADDRESS)) {
            statement.setLong(1, entity.getUserId());
            statement.setString(2, entity.getTitle());
            statement.setString(3, entity.getAddress());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows >= 1;
        logger.info(() -> result ? "Operation was successful. Deleted " + numberUpdatedRows + " order." : " Order hasn't been found");
        return result;
    }

    @Override
    public boolean create(Order entity) throws DaoException {
        logger.info("Start create(Order entity)." + entity);
        if (entity.getOrderId() != 0) {
            logger.warn("Warning: Order's id is already define. Id = " + entity.getOrderId());
        }
        int numberUpdatedRows;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_INSERT_ORDER)) {
            statement.setLong(1, entity.getUserId());
            statement.setString(2, entity.getTitle());
            statement.setString(3, entity.getDescription());
            statement.setString(4, entity.getAddress());
            Date creationDate = entity.getCreationDate();
            statement.setString(5, formatter.format(creationDate));
            Date completionDate = entity.getCompletionDate();
            statement.setString(6, formatter.format(completionDate));
            statement.setString(7, entity.getSpecialization().name().toLowerCase());
            statement.setString(8, entity.getStatus().name().toLowerCase());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed"); // TODO Do I have to push Exception if false?
        return result;
    }

    @Override
    public boolean update(Order entity) throws DaoException {
        if (entity.getOrderId() == 0) {
            throw new DaoException("Order's id = 0. Order can't be updated.");
        }
        logger.info("Start update(Order entity)." + entity);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_UPDATE_ORDER)) {
            statement.setLong(1, entity.getUserId());
            statement.setString(2, entity.getTitle());
            statement.setString(3, entity.getDescription());
            statement.setString(4, entity.getAddress());
            Date creationDate = entity.getCreationDate();
            statement.setString(5, formatter.format(creationDate));
            Date completionDate = entity.getCompletionDate();
            statement.setString(6, formatter.format(completionDate));
            statement.setString(7, entity.getSpecialization().name().toLowerCase());
            statement.setString(8, entity.getStatus().name().toLowerCase());
            statement.setLong(9, entity.getOrderId());

            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    @Override
    public boolean setOrderStatus(long idOrder, OrderStatus orderStatus) throws DaoException {
        logger.info("Start setOrderStatus(long idOrder, OrderStatus orderStatus). idOrder = " + idOrder + " , status = " + orderStatus);
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SET_ORDER_STATUS)) {
            statement.setString(1, orderStatus.name().toLowerCase());

            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    @Override
    public List<Order> findUserOrders(long idUser) throws DaoException {
        logger.info("Start ffindUserOrders(long idUser) . Id = " + idUser );
        List<Order> orders = new ArrayList<>();
        SimpleDateFormat parser = new SimpleDateFormat(DATE_PATTERN);
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_USERS_ORDERS)) {
            statement.setLong(1, idUser);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    long orderId = resultSet.getLong(ID_ORDER);
                    long userId = resultSet.getLong(ID_CUSTOMER);
                    String title = resultSet.getString(TITLE);
                    String description = resultSet.getString(JOB_DESCRIPTION);
                    String address = resultSet.getString(ADDRESS);
                    Date creationDate = parser.parse(resultSet.getString(CREATION_DATE));
                    Date completionDate = parser.parse(resultSet.getString(COMPLETION_DATE));
                    Specialization specialization = Specialization.valueOf(resultSet.getString(SPECIALIZATION).toUpperCase());
                    OrderStatus status = OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase());

                    Order order = new Order(orderId, userId, title, description, address, creationDate, completionDate, specialization, status);
                    logger.info("Has found next order = " + order);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        } catch (ParseException e) {
            logger.error("Can't parse date completion.", e);
            throw new DaoException("Can't parse date completion.", e);
        }
        logger.info("Has found next orders : " + orders);
        return orders;
    }

    @Override
    public List<Order> findOrdersBySpecialization(Specialization specialization) throws DaoException {

        logger.info("Start findOrdersBySpecializations(List<Specialization> specializations). Specialization = " + specialization);
        List<Order> orders = new ArrayList<>();
        SimpleDateFormat parser = new SimpleDateFormat(DATE_PATTERN);
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ORDERS_BY_SPECIALIZATIONS)) {
            statement.setString(1, specialization.name().toLowerCase());
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    long orderId = resultSet.getLong(ID_ORDER);
                    long userId = resultSet.getLong(ID_CUSTOMER);
                    String title = resultSet.getString(TITLE);
                    String description = resultSet.getString(JOB_DESCRIPTION);
                    String address = resultSet.getString(ADDRESS);
                    Date creationDate = parser.parse(resultSet.getString(CREATION_DATE));
                    Date completionDate = parser.parse(resultSet.getString(COMPLETION_DATE));
                    OrderStatus status = OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase());

                    Order order = new Order(orderId, userId, title, description, address, creationDate, completionDate, specialization, status);
                    logger.info("Has found next order = " + order);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        } catch (ParseException e) {
            logger.error("Can't parse date completion.", e);
            throw new DaoException("Can't parse date completion.", e);
        }
        logger.info("Has found next orders : " + orders);
        return orders;
    }
}
