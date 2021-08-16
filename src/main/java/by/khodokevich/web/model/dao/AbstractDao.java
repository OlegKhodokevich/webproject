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

    /**
     * Method searches all entity in database.
     *
     * @return List of entity
     * @throws DaoException if can't execute query
     */
    public abstract List<T> findAll() throws DaoException;
    /**
     * Method searches define entity in database by id
     *
     * @param id of entity
     * @return optional entity.
     * @throws DaoException if can't execute query
     */
    public abstract Optional<T> findEntityById(long id) throws DaoException;
    /**
     * Method delete information about entity by id
     *
     * @param id of entity
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query
     */
    public abstract boolean delete(long id) throws DaoException;
    /**
     * Method delete information about user by entity
     *
     * @param entity of entity
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query
     */
    public abstract boolean delete(T entity) throws DaoException;
    /**
     * Method create entity in database.
     *
     * @return true if it is created, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    public abstract boolean create(T entity) throws DaoException;
    /**
     * Method update entity in database.
     *
     * @param entity of entity
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    public abstract boolean update(T entity) throws DaoException;
    /**
     * Method set connection for dao.
     *
     * @param connection which set
     */
    void setConnection(Connection connection) {
        this.connection = connection;
    }
    /**
     * Method statement connection for dao.
     *
     * @param statement which is closing
     */
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
    /**
     * Method close connection for dao.
     *
     */
    void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            logger.error("Connection can't be moved back in pool.", e);
        }
    }
}
