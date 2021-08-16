package by.khodokevich.web.model.dao;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.model.entity.Contract;

import java.util.List;
/**
 * This interface manage entity contract in database.
 * It is used for select create or update information connected with contract.
 */
public interface ContractDao {
    /**
     * Method searches all customer's contracts in database by customer id
     *
     * @param userCustomerId of customer which contracts  need to find
     * @return list of contracts.
     * @throws DaoException if can't execute query
     */
    List<Contract> findContractByIdUserCustomer(long userCustomerId) throws DaoException;

    /**
     * Method searches all executor's contract in database by executor id
     *
     * @param userExecutorId of executor which contracts  need to find
     * @return list of contracts.
     * @throws DaoException if can't execute query
     */
    List<Contract> findContractByIdExecutor(long userExecutorId) throws DaoException;

    /**
     * Method searches all contracts id in database by order id
     *
     * @param orderId of customer which contracts  need to find
     * @return list of contract's id.
     * @throws DaoException if can't execute query
     */
    List<Long> findAllContractIdByOrderId(long orderId) throws DaoException;

    /**
     * Method searches all not concluded contracts in database by executor id
     *
     * @param executorId of executor which contracts  need to find
     * @return list of contracts.
     * @throws DaoException if can't execute query
     */
    List<Contract> findOfferByIdExecutor(long executorId) throws DaoException;

    /**
     * Method set concluded status for define contract
     *
     * @param contractId of contract
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    boolean setConcludedStatus(long contractId) throws DaoException;

    /**
     * Method set not concluded status for define contract
     *
     * @param contractId of contract
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    boolean setNotConcludedStatusForDefineContract(long contractId) throws DaoException;

    /**
     * Method set not concluded status for define contracts list
     *
     * @param contractIdList is list of contracts id
     * @return true if all contracts are updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    boolean setNotConcludedStatusForCompetitor(List<Long> contractIdList) throws DaoException;

    /**
     * Method set completed status for define contract
     *
     * @param contractId of contract
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    boolean setCompletedStatus(long contractId) throws DaoException;

    /**
     * Method create contract(offer) for order with order id and executor id
     *
     * @param orderId of order for contact
     * @param executorId of executor who offer contract
     * @return true if it is created, in other way will return false.
     * @throws DaoException if can't execute query
     */
    boolean createOffer(long orderId, long executorId) throws DaoException;

    /**
     * Method search executorId by contractId
     *
     * @param contractId of contract
     * @return executor id
     * @throws DaoException if can't execute query
     */
    long getIdExecutor(long contractId) throws DaoException;
}
