package by.khodokevich.web.dao;

import by.khodokevich.web.connection.CustomConnectionPool;
import by.khodokevich.web.exception.PoolConnectionException;
import by.khodokevich.web.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private static final Logger logger = LogManager.getLogger();
    private Connection connection;

    public void begin(AbstractDao... daos) throws DaoException {
        if (connection == null) {
            try {
                connection = CustomConnectionPool.getInstance().getConnection();
            } catch (PoolConnectionException e) {
                logger.error("Can't get connection", e);
                throw new DaoException("Can't get connection", e);
            }
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Can't set auto commit false", e);
            throw new DaoException("Can't set commit false", e);
        }
        for (AbstractDao daoElelment : daos) {
            daoElelment.setConnection(connection);
        }
    }

    public void beginSingleQuery(AbstractDao dao) throws DaoException {
        if (connection == null) {
            try {
                connection = CustomConnectionPool.getInstance().getConnection();
            } catch (PoolConnectionException e) {
                logger.error("Can't get connection", e);
                throw new DaoException("Can't get connection", e);
            }
        }
        dao.setConnection(connection);
    }


    public void end() throws DaoException {
        if (connection == null) {
            throw new DaoException("Connection has been lost.");
        }
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Can't set auto commit true", e);
            throw new DaoException("Can't set auto commit true", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Can't release connection.", e);
                throw new DaoException("Can't release connection.", e);
            }
            connection = null;
        }
    }


    public void endSingleQuery() throws DaoException {
        if (connection == null) {
            throw new DaoException("Connection has been lost.");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        connection = null;
    }
}