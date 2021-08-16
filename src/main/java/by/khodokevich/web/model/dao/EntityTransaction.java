package by.khodokevich.web.model.dao;

import by.khodokevich.web.model.connection.CustomConnectionPool;
import by.khodokevich.web.exception.PoolConnectionException;
import by.khodokevich.web.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class create transaction for dao and set connection.
 * It is used for managing transaction(commit, rollback)
 */
public class EntityTransaction implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger();
    private Connection connection;
    private boolean singleTransaction;
    private boolean isCommit;

    /**
     * Method set connection for each dao included in param
     * Method set autocommit false
     *
     * @param daos array of DAO
     * @throws DaoException when connection can't be taken
     */
    public void begin(AbstractDao... daos) throws DaoException {
        singleTransaction = false;
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
            isCommit = false;
        } catch (SQLException e) {
            logger.error("Can't set auto commit false", e);
            throw new DaoException("Can't set commit false", e);
        }
        for (AbstractDao daoElelment : daos) {
            daoElelment.setConnection(connection);
        }
    }

    /**
     * Method set connection for dao in param
     *
     * @param dao which will be used
     * @throws DaoException when connection can't be taken
     */
    public void beginSingleQuery(AbstractDao dao) throws DaoException {
        singleTransaction = true;
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

    /**
     * Method do rollback
     *
     * @throws DaoException when connection is null
     */
    public void rollback() throws DaoException {
        if (connection == null) {
            throw new DaoException("Connection has been lost.");
        }
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("Can't rollback transaction", e);
            throw new DaoException("Can't rollback transaction", e);
        }
    }

    /**
     * Method do commit
     *
     * @throws DaoException when connection is null
     */
    public void commit() throws DaoException {
        if (connection == null) {
            throw new DaoException("Connection has been lost.");
        }
        try {
            connection.commit();
            isCommit = true;
        } catch (SQLException e) {
            logger.error("Can't commit transaction", e);
            throw new DaoException("Can't commit transaction", e);
        }
    }

    /**
     * Method close transaction and do rollback if transaction was failed.
     *
     * @throws DaoException when connection is null
     */
    @Override
    public void close() throws DaoException {
        if (connection == null) {
            throw new DaoException("Connection has been lost.");
        }
        try {
            if (singleTransaction) {
                endSingleQuery();
            } else {
                if (!isCommit) {
                    rollback();
                }
                end();
            }
        } catch (SQLException e) {
            logger.error("Transaction isn't able to be closed.", e);
            throw new DaoException("Transaction isn't able to be closed.", e);
        }
    }

    private void endSingleQuery() throws SQLException {
        connection.close();
        connection = null;
    }

    private void end() throws SQLException {
        try {
            connection.setAutoCommit(true);
        } finally {
            connection.close();
            connection = null;
        }
    }
}
