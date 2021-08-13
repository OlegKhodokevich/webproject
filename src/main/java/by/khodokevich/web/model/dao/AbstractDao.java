package by.khodokevich.web.model.dao;

import by.khodokevich.web.model.entity.Entity;
import by.khodokevich.web.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> {
    private static final Logger logger = LogManager.getLogger();

    protected Connection connection;

    public abstract List<T> findAll() throws DaoException;

    public abstract Optional<T> findEntityById(long id) throws DaoException;

    public abstract boolean delete(long id) throws DaoException;

    public abstract boolean delete(T entity) throws DaoException;

    public abstract boolean create(T entity) throws DaoException;

    public abstract boolean update(T entity) throws DaoException;

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            } else {
                logger.warn("Statement is null.");
            }

        } catch (SQLException e) {
            logger.error("Statement can't be closed.", e);
        }
    }

    void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            logger.error("Connection can't be moved back in pool.", e);
        }
    }
}
