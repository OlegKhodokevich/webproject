package by.khodokevich.web.model.dao.impl;

import static by.khodokevich.web.model.dao.impl.ContractColumnName.*;

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

import static by.khodokevich.web.model.entity.Contract.*;

public class ContractDaoImpl extends AbstractDao<Contract> implements ContractDao {
    private static final Logger logger = LogManager.getLogger(ContractDaoImpl.class);

    private static final String SQL_SELECT_ALL_CONTRACTS = "SELECT IdContract, IdOrder, IdUserExecutor, Conclude, Complete FROM contracts;";
    private static final String SQL_SELECT_ALL_CONTRACTS_BY_ID_CUSTOMER = "SELECT IdContract, orders.IdOrder, IdUserExecutor, Conclude, Complete FROM contracts JOIN orders ON orders.IdOrder = contracts.IdOrder WHERE orders.IdUserCustomer = ?;";
    private static final String SQL_SELECT_ALL_CONTRACTS_BY_ID_EXECUTOR = "SELECT IdContract, IdOrder, IdUserExecutor, Conclude, Complete FROM contracts WHERE IdUserExecutor = ?;";
    private static final String SQL_SELECT_ID_CONTRACTS_BY_ORDER_ID = "SELECT IdContract FROM contracts WHERE IdOrder = ?;";
    private static final String SQL_SELECT_ALL_OFFER_BY_ID_EXECUTOR = "SELECT IdContract, IdOrder, IdUserExecutor, Conclude, Complete FROM contracts WHERE IdUserExecutor = ?;";
    private static final String SQL_SELECT_DEFINED_CONTRACT = "SELECT IdOrder, IdUserExecutor, Conclude, Complete FROM contracts WHERE IdContract = ?;";
    private static final String SQL_DELETE_DEFINED_CONTRACT_BY_ID = "DELETE FROM contracts WHERE IdContract = ?;";
    private static final String SQL_DELETE_DEFINED_CONTRACT_BY_IDORDER_AND_IDEXECUTOR = "DELETE FROM contracts WHERE IdOrder = ? AND IdUserExecutor = ?;";
    private static final String SQL_INSERT_CONTRACT = "INSERT INTO contracts(IdOrder, IdUserExecutor, Conclude, Complete) VALUES (?, ?, ?, ?);";
    private static final String SQL_UPDATE_CONTRACT = "UPDATE contracts SET Conclude = ?, Complete = ?  WHERE IdContract = ?;";
    private static final String SQL_SET_CONCLUDED_STATUS = "UPDATE contracts SET Conclude = 'concluded'  WHERE IdContract = ?;";
    private static final String SQL_SET_NOT_CONCLUDED_STATUS_FOR_DEFINE_CONTRACT="UPDATE contracts SET Conclude = 'not_concluded'  WHERE IdContract = ?;";
    private static final String SQL_SET_COMPLETED_STATUS="UPDATE contracts SET Complete = 'completed'  WHERE IdContract = ?;";


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


    @Override
    public List<Contract> findContractByIdUserCustomer(long userCustomerId) throws DaoException {
        logger.info("findContractByIdUser()");
        List<Contract> contracts = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_CONTRACTS_BY_ID_CUSTOMER)) {
            statement.setLong(1,userCustomerId);
            try(ResultSet resultSet = statement.executeQuery()) {
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

    @Override
    public List<Contract> findContractByIdExecutor(long userExecutorId) throws DaoException {
        logger.info("Start findContractByIdExecutor(long userExecutorId). Id = " + userExecutorId);
        List<Contract> contracts = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_CONTRACTS_BY_ID_EXECUTOR)) {
            statement.setLong(1,userExecutorId);
            try(ResultSet resultSet = statement.executeQuery()) {
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


    @Override
    public List<Long> findAllContractByOrderId(long orderId) throws DaoException {
        logger.info("Start findAllContractByOrderId(long userCustomerId). Id = " + orderId);
        List<Long> contractIdList = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ID_CONTRACTS_BY_ORDER_ID)) {
            statement.setLong(1,orderId);
            try(ResultSet resultSet = statement.executeQuery()) {
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

    @Override
    public List<Contract> findOfferByIdExecutor(long executorId) throws DaoException {
        logger.info("Start findOfferByIdExecutor(long userCustomerId). Id = " + executorId);
        List<Contract> contracts = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_OFFER_BY_ID_EXECUTOR)) {
            statement.setLong(1,executorId);
            try(ResultSet resultSet = statement.executeQuery()) {
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
//        logger.info("Start delete(Contract entity). Contract = " + entity);
//        int numberUpdatedRows;
//        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_DEFINED_CONTRACT_BY_IDORDER_AND_IDEXECUTOR)) {
//            long orderId = entity.getIdOrder();
//            statement.setLong(1, orderId);
//            long executorId = entity.getIdExecutor();
//            statement.setLong(2, executorId);
//
//            numberUpdatedRows = statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Prepare statement can't be take from connection." + e.getMessage());
//            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
//        }
//        boolean result = numberUpdatedRows >= 1;
//        logger.info(() -> result ? "Operation was successful. Deleted " + numberUpdatedRows + " order." : " Contract hasn't been found");
        throw new UnsupportedOperationException();
//        return result;
    }

    @Override
    public boolean create(Contract entity) throws DaoException {
//        logger.info("Start create(Contract entity)." + entity);
//        if (entity.getIdContract() != 0) {
//            logger.warn("Warning: Contract's id is already define. Id = " + entity.getIdContract());
//        }
//        int numberUpdatedRows;
//        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CONTRACT)) {
//            statement.setLong(1, entity.getOrder().getOrderId());
//            statement.setLong(2, entity.getUser().getIdUser());
//            statement.setString(3, entity.getConcludedContractStatus().name().toLowerCase());
//            statement.setString(4, entity.getCompletionContractStatus().name().toLowerCase());
//            numberUpdatedRows = statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Prepare statement can't be take from connection." + e.getMessage());
//            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
//        }
//        boolean result = numberUpdatedRows == 1;
//        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
//        return result;
        throw new UnsupportedOperationException();
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

    @Override
    public boolean setNotConcludedStatusForCompetitor(List<Long> contractIdList) throws DaoException {
        logger.info("Start setNotConcludedStatusCompetitor(List<Long> contractIdList). contractIdList = " + contractIdList);
        int [] numberUpdatedRows;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SET_NOT_CONCLUDED_STATUS_FOR_DEFINE_CONTRACT)) {
            for (Long element :contractIdList) {
                statement.setLong(1, element);
                statement.addBatch();
            }
            numberUpdatedRows = statement.executeBatch();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = Arrays.stream(numberUpdatedRows).allMatch(s->s==1);
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

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
//        boolean result = numberUpdatedRows == 1;
//        numberUpdatedRows = 0;
//        try (PreparedStatement statement = connection.prepareStatement(SQL_SET_NOT_CONCLUDED_STATUS_FOR_DEFINE_CONTRACT)) {
//            statement.setLong(1, contractId);
//            numberUpdatedRows = statement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
//            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
//        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }


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
}
