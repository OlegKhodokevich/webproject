package by.khodokevich.web.dao.impl;

import static by.khodokevich.web.dao.impl.ContractColumnName.*;

import by.khodokevich.web.dao.AbstractDao;
import by.khodokevich.web.entity.*;
import by.khodokevich.web.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContractDaoImpl extends AbstractDao<Contract> {
    private static final Logger logger = LogManager.getLogger(ContractDaoImpl.class);

    private static final String SQL_SELECT_ALL_CONTRACTS = "SELECT IdContract, IdOrder, IdUserExecutor, Conclude, Complete FROM contracts;";
    private static final String SQL_SELECT_DEFINED_CONTRACT = "SELECT IdOrder, IdUserExecutor, Conclude, Complete FROM contracts WHERE IdContract = ?;";
    private static final String SQL_DELETE_DEFINED_CONTRACT_BY_ID = "DELETE FROM contracts WHERE IdContract = ?;";
    private static final String SQL_DELETE_DEFINED_CONTRACT_BY_IDORDER_AND_IDEXECUTOR = "DELETE FROM contracts WHERE IdOrder = ? AND IdUserExecutor = ?;";
    private static final String SQL_INSERT_CONTRACT = "INSERT INTO contracts(IdOrder, IdUserExecutor, Conclude, Complete) VALUES (?, ?, ?, ?);";
    private static final String SQL_UPDATE_CONTRACT = "UPDATE contracts SET Conclude = ?, Complete = ?  WHERE IdContract = ?;";


    @Override
    public List<Contract> findAll() throws DaoException {
        logger.info("Start findAll().");
        List<Contract> contracts = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_CONTRACTS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                long contractId = resultSet.getLong(ID_CONTRACT);
                long orderId = resultSet.getLong(ID_ORDER);
                long executorId = resultSet.getLong(ID_EXECUTOR);
                String concludeStatusString = resultSet.getString(CONCLUDE);
                ConcludedContractStatus concludedContractStatus = ConcludedContractStatus.valueOf(concludeStatusString.toUpperCase());
                String completionStatusString = resultSet.getString(COMPLETE);
                CompletionContractStatus completionContractStatus = CompletionContractStatus.valueOf(completionStatusString.toUpperCase());

                Contract contract = new Contract(contractId, orderId, executorId, concludedContractStatus, completionContractStatus);
                logger.info("Has found next contract = " + contract);
                contracts.add(contract);
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next contracts : " + contracts);
        return contracts;
    }

    @Override
    public Optional<Contract> findEntityById(long contractId) throws DaoException {
        logger.info("Start findEntityById(long contractId). Id = " + contractId);
        Contract contract = null;

        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_DEFINED_CONTRACT)) {
            ;
            statement.setLong(1, contractId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long orderId = resultSet.getLong(ID_ORDER);
                    long executorId = resultSet.getLong(ID_EXECUTOR);
                    String concludeStatusString = resultSet.getString(CONCLUDE);
                    ConcludedContractStatus concludedContractStatus = ConcludedContractStatus.valueOf(concludeStatusString.toUpperCase());
                    String completionStatusString = resultSet.getString(COMPLETE);
                    CompletionContractStatus completionContractStatus = CompletionContractStatus.valueOf(completionStatusString.toUpperCase());

                    contract = new Contract(contractId, orderId, executorId, concludedContractStatus, completionContractStatus);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next contact : " + contract);
        return Optional.ofNullable(contract);
    }

    @Override
    public boolean delete(long id) throws DaoException {
        logger.info("Start delete(long id). Contract's id = " + id);
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_DELETE_DEFINED_CONTRACT_BY_ID)) {
            statement.setLong(1, id);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Contract hasn't been found");
        return result;
    }

    @Override
    public boolean delete(Contract entity) throws DaoException {
        logger.info("Start delete(Contract entity). Contract = " + entity);
        int numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_DEFINED_CONTRACT_BY_IDORDER_AND_IDEXECUTOR)) {
            long orderId = entity.getIdOrder();
            statement.setLong(1, orderId);
            long executorId = entity.getIdExecutor();
            statement.setLong(2, executorId);

            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows >= 1;
        logger.info(() -> result ? "Operation was successful. Deleted " + numberUpdatedRows + " order." : " Contract hasn't been found");
        return result;
    }

    @Override
    public boolean create(Contract entity) throws DaoException {
        logger.info("Start create(Contract entity)." + entity);
        if (entity.getIdContract() != 0) {
            logger.warn("Warning: Contract's id is already define. Id = " + entity.getIdContract());
        }
        int numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CONTRACT)) {
            statement.setLong(1, entity.getIdOrder());
            statement.setLong(2, entity.getIdExecutor());
            statement.setString(3, entity.getConcludedContractStatus().name().toLowerCase());
            statement.setString(4, entity.getCompletionContractStatus().name().toLowerCase());
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
    public boolean update(Contract entity) throws DaoException {
        logger.info("Start update(Contract entity)." + entity);
        if (entity.getIdContract() == 0) {
            throw new DaoException("Contract's id = 0. Contract can't be updated.");
        }
        int numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CONTRACT)) {
            statement.setString(1, entity.getConcludedContractStatus().name().toLowerCase());
            statement.setString(2, entity.getCompletionContractStatus().name().toLowerCase());
            statement.setLong(3, entity.getIdContract());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }
}
