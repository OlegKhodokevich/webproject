package by.khodokevich.web.model.dao.impl;


import by.khodokevich.web.model.dao.AbstractDao;
import by.khodokevich.web.model.dao.ContractDao;
import by.khodokevich.web.model.entity.*;
import by.khodokevich.web.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static by.khodokevich.web.model.dao.impl.ContractColumnName.*;
import static by.khodokevich.web.model.entity.Contract.*;

/**
 * This class manage entity contact in database.
 * It is used for select create or update information connected with contract.
 */
public class ContractDaoImpl extends AbstractDao<Contract> implements ContractDao {
    private static final Logger logger = LogManager.getLogger(ContractDaoImpl.class);

    private static final String SQL_SELECT_ALL_CONTRACTS = "SELECT IdContract, IdOrder, IdUserExecutor, Conclude, Complete FROM contracts;";
    private static final String SQL_SELECT_ALL_CONTRACTS_BY_ID_CUSTOMER = "SELECT IdContract, orders.IdOrder, IdUserExecutor, Conclude, Complete FROM contracts JOIN orders ON orders.IdOrder = contracts.IdOrder WHERE orders.IdUserCustomer = ?;";
    private static final String SQL_SELECT_ALL_CONTRACTS_BY_ID_EXECUTOR = "SELECT IdContract, IdOrder, IdUserExecutor, Conclude, Complete FROM contracts WHERE IdUserExecutor = ?;";
    private static final String SQL_SELECT_ID_CONTRACTS_BY_ORDER_ID = "SELECT IdContract FROM contracts WHERE IdOrder = ?;";
    private static final String SQL_SELECT_ALL_OFFER_BY_ID_EXECUTOR = "SELECT IdContract, IdOrder, IdUserExecutor, Conclude, Complete FROM contracts WHERE Conclude = 'not_concluded' AND IdUserExecutor = ? ORDER BY IdOrder;";
    private static final String SQL_SELECT_DEFINED_CONTRACT = "SELECT IdOrder, IdUserExecutor, Conclude, Complete FROM contracts WHERE IdContract = ?;";
    private static final String SQL_DELETE_DEFINED_CONTRACT_BY_ID = "DELETE FROM contracts WHERE IdContract = ?;";
    private static final String SQL_INSERT_CONTRACT = "INSERT INTO contracts(IdOrder, IdUserExecutor, Conclude, Complete) VALUES (?, ?, ?, ?);";
    private static final String SQL_UPDATE_CONTRACT = "UPDATE contracts SET Conclude = ?, Complete = ?  WHERE IdContract = ?;";
    private static final String SQL_SET_CONCLUDED_STATUS = "UPDATE contracts SET Conclude = 'concluded'  WHERE IdContract = ?;";
    private static final String SQL_SET_NOT_CONCLUDED_STATUS_FOR_DEFINE_CONTRACT = "UPDATE contracts SET Conclude = 'not_concluded'  WHERE IdContract = ?;";
    private static final String SQL_SET_COMPLETED_STATUS = "UPDATE contracts SET Complete = 'completed'  WHERE IdContract = ?;";
    private static final String SQL_SELECT_SELECT_ID_EXECUTOR_BY_CONTRACT_ID = "SELECT IdUserExecutor FROM contracts WHERE IdContract = ?;";

    /**
     * Method searches all contract's entity in database
     *
     * @return List of contracts have been found in database
     * @throws DaoException if can't execute query
     */
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

                Contract contract = new Contract(contractId, new Order(orderId), new User(executorId), concludedContractStatus, completionContractStatus);
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

    /**
     * Method searches define contract in database by id
     *
     * @param contractId of contract
     * @return optional contract.
     * @throws DaoException if can't execute query
     */
    @Override
    public Optional<Contract> findEntityById(long contractId) throws DaoException {
        logger.info("Start findEntityById(long contractId). Id = " + contractId);
        Contract contract = null;

        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_DEFINED_CONTRACT)) {
            statement.setLong(1, contractId);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    long orderId = resultSet.getLong(ID_ORDER);
                    long executorId = resultSet.getLong(ID_EXECUTOR);
                    String concludeStatusString = resultSet.getString(CONCLUDE);
                    ConcludedContractStatus concludedContractStatus = ConcludedContractStatus.valueOf(concludeStatusString.toUpperCase());
                    String completionStatusString = resultSet.getString(COMPLETE);
                    CompletionContractStatus completionContractStatus = CompletionContractStatus.valueOf(completionStatusString.toUpperCase());

                    contract = new Contract(contractId, new Order(orderId), new User(executorId), concludedContractStatus, completionContractStatus);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next contact : " + contract);
        return Optional.ofNullable(contract);
    }

    /**
     * Method searches all customer's contracts in database by customer id
     *
     * @param userCustomerId of customer which contracts  need to find
     * @return list of contracts.
     * @throws DaoException if can't execute query
     */
    @Override
    public List<Contract> findContractByIdUserCustomer(long userCustomerId) throws DaoException {
        logger.info("Start findContractByIdUserCustomer(long userCustomerId). Id = " + userCustomerId);
        List<Contract> contracts = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_CONTRACTS_BY_ID_CUSTOMER)) {
            statement.setLong(1, userCustomerId);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    long contractId = resultSet.getLong(ID_CONTRACT);
                    long orderId = resultSet.getLong(ID_ORDER);
                    long executorId = resultSet.getLong(ID_EXECUTOR);
                    String concludeStatusString = resultSet.getString(CONCLUDE);
                    ConcludedContractStatus concludedContractStatus = ConcludedContractStatus.valueOf(concludeStatusString.toUpperCase());
                    String completionStatusString = resultSet.getString(COMPLETE);
                    CompletionContractStatus completionContractStatus = CompletionContractStatus.valueOf(completionStatusString.toUpperCase());

                    Contract contract = new Contract(contractId, new Order(orderId), new User(executorId), concludedContractStatus, completionContractStatus);
                    logger.info("Has found next contract = " + contract);
                    contracts.add(contract);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next contracts : " + contracts);
        return contracts;
    }

    /**
     * Method searches all executor's contract in database by executor id
     *
     * @param userExecutorId of executor which contracts  need to find
     * @return list of contracts.
     * @throws DaoException if can't execute query
     */
    @Override
    public List<Contract> findContractByIdExecutor(long userExecutorId) throws DaoException {
        logger.info("Start findContractByIdExecutor(long userExecutorId). Id = " + userExecutorId);
        List<Contract> contracts = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_CONTRACTS_BY_ID_EXECUTOR)) {
            statement.setLong(1, userExecutorId);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    long contractId = resultSet.getLong(ID_CONTRACT);
                    long orderId = resultSet.getLong(ID_ORDER);
                    long executorId = resultSet.getLong(ID_EXECUTOR);
                    String concludeStatusString = resultSet.getString(CONCLUDE);
                    ConcludedContractStatus concludedContractStatus = ConcludedContractStatus.valueOf(concludeStatusString.toUpperCase());
                    String completionStatusString = resultSet.getString(COMPLETE);
                    CompletionContractStatus completionContractStatus = CompletionContractStatus.valueOf(completionStatusString.toUpperCase());

                    Contract contract = new Contract(contractId, new Order(orderId), new User(executorId), concludedContractStatus, completionContractStatus);
                    logger.info("Has found next contract = " + contract);
                    contracts.add(contract);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next contracts : " + contracts);
        return contracts;
    }

    /**
     * Method searches all contracts id in database by order id
     *
     * @param orderId of customer which contracts  need to find
     * @return list of contract's id.
     * @throws DaoException if can't execute query
     */
    @Override
    public List<Long> findAllContractIdByOrderId(long orderId) throws DaoException {
        logger.info("Start findAllContractIdByOrderId(long userCustomerId). Id = " + orderId);
        List<Long> contractIdList = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ID_CONTRACTS_BY_ORDER_ID)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    long contractId = resultSet.getLong(ID_CONTRACT);
                    logger.info("Has found next contract = " + contractId);
                    contractIdList.add(contractId);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next contracts : " + contractIdList);
        return contractIdList;
    }

    /**
     * Method searches all not concluded contracts in database by executor id
     *
     * @param executorId of executor which contracts  need to find
     * @return list of contracts.
     * @throws DaoException if can't execute query
     */
    @Override
    public List<Contract> findOfferByIdExecutor(long executorId) throws DaoException {
        logger.info("Start findOfferByIdExecutor(long userCustomerId). Id = " + executorId);
        List<Contract> contracts = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_OFFER_BY_ID_EXECUTOR)) {
            statement.setLong(1, executorId);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    long contractId = resultSet.getLong(ID_CONTRACT);
                    long orderId = resultSet.getLong(ID_ORDER);
                    String concludeStatusString = resultSet.getString(CONCLUDE);
                    ConcludedContractStatus concludedContractStatus = ConcludedContractStatus.valueOf(concludeStatusString.toUpperCase());
                    String completionStatusString = resultSet.getString(COMPLETE);
                    CompletionContractStatus completionContractStatus = CompletionContractStatus.valueOf(completionStatusString.toUpperCase());

                    Contract contract = new Contract(contractId, new Order(orderId), new User(executorId), concludedContractStatus, completionContractStatus);
                    logger.info("Has found next contract = " + contract);
                    contracts.add(contract);
                }
            }

        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next contracts : " + contracts);
        return contracts;
    }

    /**
     * Method delete information about contract by id
     *
     * @param id of contract
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query
     */
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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(Contract entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Method create contract(offer) for order with order id and executor id
     *
     * @param orderId of order for contact
     * @param executorId of executor who offer contract
     * @return true if it is created, in other way will return false.
     * @throws DaoException if can't execute query
     */
    @Override
    public boolean createOffer(long orderId, long executorId) throws DaoException {
        logger.info("Start createOffer(long orderId, long executorId)  Id order= " + orderId + " , executor id = " + executorId);
        int numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CONTRACT)) {
            statement.setLong(1, orderId);
            statement.setLong(2, executorId);
            statement.setString(3, ConcludedContractStatus.UNDER_CONSIDERATION.name().toLowerCase());
            statement.setString(4, CompletionContractStatus.NOT_COMPLETED.name().toLowerCase());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }
    /**
     * Method search executorId by contractId
     *
     * @param contractId of contract
     * @return executor id
     * @throws DaoException if can't execute query
     */
    @Override
    public long getIdExecutor(long contractId) throws DaoException {
        logger.info("Start getIdExecutor(long contractId). ContractId = " + contractId);
        long executorId = -1;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SELECT_ID_EXECUTOR_BY_CONTRACT_ID)) {
            statement.setLong(1, contractId);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    executorId = resultSet.getLong(ID_EXECUTOR);
                    logger.info("Has found next executor id: " + executorId);
                }
            }

        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        return executorId;
    }

    /**
     * Method update information about contract by id
     *
     * @param entity of contact
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
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

    /**
     * Method set not concluded status for define contract
     *
     * @param contractId of contract
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    @Override
    public boolean setConcludedStatus(long contractId) throws DaoException {
        logger.info("Start setConcludedStatus(long contractId). ContractId = " + contractId);

        int numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SET_CONCLUDED_STATUS)) {
            statement.setLong(1, contractId);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    /**
     * Method set not concluded status for define contract
     *
     * @param contractId of contract
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    @Override
    public boolean setNotConcludedStatusForDefineContract(long contractId) throws DaoException {
        logger.info("Start setNotConcludedStatusForDefineContract(long contractId). ContractId = " + contractId);
        int numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SET_NOT_CONCLUDED_STATUS_FOR_DEFINE_CONTRACT)) {
            statement.setLong(1, contractId);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    /**
     * Method set not concluded status for define contracts list
     *
     * @param contractIdList is list of contracts id
     * @return true if all contracts are updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    @Override
    public boolean setNotConcludedStatusForCompetitor(List<Long> contractIdList) throws DaoException {
        logger.info("Start setNotConcludedStatusCompetitor(List<Long> contractIdList). contractIdList = " + contractIdList);
        int[] numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SET_NOT_CONCLUDED_STATUS_FOR_DEFINE_CONTRACT)) {
            for (Long element : contractIdList) {
                statement.setLong(1, element);
                statement.addBatch();
            }
            numberUpdatedRows = statement.executeBatch();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = Arrays.stream(numberUpdatedRows).allMatch(s -> s == 1);
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    /**
     * Method set completed status for define contract
     *
     * @param contractId of contract
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    @Override
    public boolean setCompletedStatus(long contractId) throws DaoException {
        logger.info("Start setCompletedStatus(long contractId). ContractId = " + contractId);
        int numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SET_COMPLETED_STATUS)) {
            statement.setLong(1, contractId);
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
