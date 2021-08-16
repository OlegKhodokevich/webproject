package by.khodokevich.web.model.dao.impl;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.model.dao.AbstractDao;
import by.khodokevich.web.model.dao.RevokeDao;
import by.khodokevich.web.model.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static by.khodokevich.web.model.dao.impl.OrderColumnName.CREATION_DATE;
import static by.khodokevich.web.model.dao.impl.RevokeColumnName.*;
/**
 * This class implement RevokeDao
 * This class manage entity revoke in database.
 * It is used for select create or update information connected with revoke.
 */
public class RevokeDaoImpl extends AbstractDao<Revoke> implements RevokeDao {
    private static final Logger logger = LogManager.getLogger(ContractDaoImpl.class);

    private static final String SQL_SELECT_ALL_REVOKE_BY_ID_EXECUTOR = "SELECT IdRevoke, revokes.IdContract, Description, Mark, CreationDate FROM revokes JOIN contracts ON revokes.IdContract = contracts.IdContract WHERE contracts.IdUserExecutor = ?;";
    private static final String SQL_SELECT_ALL_REVOKE_BY_ID_CONTRACT = "SELECT IdRevoke, IdContract, Description, Mark, CreationDate FROM revokes WHERE IdContract = ?;";
    private static final String SQL_INSERT_REVOKE = "INSERT INTO revokes(IdContract, Description, Mark, CreationDate) VALUES (?,?,?,?);";
    private static final String DATE_PATTERN = "yyyy-MM-dd";


    @Override
    public List<Revoke> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Revoke> findEntityById(long ContractId) throws DaoException {
        throw new UnsupportedOperationException();
    }
    /**
     * Method search revoke by contractId
     *
     * @param ContractId of contract
     * @return optional revoke
     * @throws DaoException if can't execute query
     */
    @Override
    public Optional<Revoke> findEntityByContractId(long ContractId) throws DaoException {
        logger.info("Start findEntityByContractId(long ContractId). Id = " + ContractId);
        Revoke revoke = null;
        SimpleDateFormat parser = new SimpleDateFormat(DATE_PATTERN);
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_REVOKE_BY_ID_CONTRACT)) {
            statement.setLong(1, ContractId);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    long revokeId = resultSet.getLong(ID_REVOKE);
                    long contractId = resultSet.getLong(ID_CONTRACT);
                    String description = resultSet.getString(DESCRIPTION);
                    int mark = resultSet.getInt(MARK);
                    Date creationDate = parser.parse(resultSet.getString(CREATION_DATE));

                    revoke = new Revoke(revokeId, contractId, description, mark, creationDate);
                    logger.info("Has found next revoke = " + revoke);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        } catch (ParseException e) {
            logger.error("Can't parse date completion.", e);
            throw new DaoException("Can't parse date completion.", e);
        }
        return Optional.ofNullable(revoke);
    }

    @Override
    public boolean delete(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Revoke entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Method create entity revoke in database.
     *
     * @param revoke entity
     * @return true if it is created, in other way will return false.
     * @throws DaoException if can't execute query
     */
    @Override
    public boolean create(Revoke revoke) throws DaoException {
        logger.info("Start create(Order entity)." + revoke);
        if (revoke.getRevokeId() != 0) {
            logger.warn("Warning: Revoke's id is already define. Id = " + revoke.getContractId());
        }
        int numberUpdatedRows;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_INSERT_REVOKE)) {
            statement.setLong(1, revoke.getContractId());
            statement.setString(2, revoke.getDescription());
            statement.setInt(3, revoke.getMark());
            Date creationDate = revoke.getCreationDate();
            statement.setString(4, formatter.format(creationDate));

            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    @Override
    public boolean update(Revoke entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Method search executor's revokes by executor id.
     *
     * @param userExecutorId of executor
     * @return  List of revokes
     * @throws DaoException if can't execute query
     */
    @Override
    public List<Revoke> findAllExecutorRevoke(long userExecutorId) throws DaoException {
        logger.info("Start findContractByIdExecutor(long userExecutorId). Id = " + userExecutorId);
        List<Revoke> revokes = new ArrayList<>();
        SimpleDateFormat parser = new SimpleDateFormat(DATE_PATTERN);
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_REVOKE_BY_ID_EXECUTOR)) {
            statement.setLong(1, userExecutorId);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    long revokeId = resultSet.getLong(ID_REVOKE);
                    long contractId = resultSet.getLong(ID_CONTRACT);
                    String description = resultSet.getString(DESCRIPTION);
                    int mark = resultSet.getInt(MARK);
                    Date creationDate = parser.parse(resultSet.getString(CREATION_DATE));

                    Revoke revoke = new Revoke(revokeId, contractId, description, mark, creationDate);
                    logger.info("Has found next revoke = " + revoke);
                    revokes.add(revoke);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        } catch (ParseException e) {
            logger.error("Can't parse date completion.", e);
            throw new DaoException("Can't parse date completion.", e);
        }
        logger.info("Has found next revokes : " + revokes);
        return revokes;
    }
}
