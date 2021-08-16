package by.khodokevich.web.model.service;

import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.Contract;

import java.util.List;
import java.util.Optional;

/**
 * Interface serve operations with contracts.
 */
public interface ContractService {
    /**
     * Method searches all concluded contracts by customer id.
     *
     * @param userCustomerId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Contract> findConcludedContractByUserCustomerId(long userCustomerId) throws ServiceException;

    /**
     * Method searches all concluded contracts by executor id.
     *
     * @param executorId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Contract> findConcludedContractByUserExecutorId(long executorId) throws ServiceException;

    /**
     * Method searches all contracts (offer) except concluded by executor id.
     *
     * @param executorId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Contract> findOfferByUserExecutorId(long executorId) throws ServiceException;

    /**
     * Method searches all contracts under consideration (offer) by customer id.
     *
     * @param userCustomerId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Contract> findUnderConsiderationContractByUserCustomerId(long userCustomerId) throws ServiceException;

    /**
     * Method searches all contracts under consideration (offer) by executor id.
     * Method sort list by order id.
     *
     * @param userExecutorId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Contract> findUnderConsiderationContractByUserExecutorId(long userExecutorId) throws ServiceException;

    /**
     * Method searches contract by id.
     *
     * @param contractId of contract
     * @return optional contract
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    Optional<Contract> findContractInformationById(long contractId) throws ServiceException;

    /**
     * Method set concluded status for contract and close status for order which was base for contract.
     * And update option for executor whose contract was completed.
     * Number of concluded contracts increase fo that executor.
     *
     * @param contractId of contract
     * @param orderId    of order
     * @return true if it is updated, in other way will return false.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    boolean setConcludedStatus(long contractId, long orderId) throws ServiceException;

    /**
     * Method set not concluded status for contract by id.
     *
     * @param contractId of contract
     * @return true if it is updated, in other way will return false.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    boolean setNotConcludedStatus(long contractId) throws ServiceException;

    /**
     * Method set completed status for contract by id.
     * And update option for executor whose contract was completed.
     * Number of completed contracts increase fo that executor.
     *
     * @param contractId of contract
     * @param orderId    of order
     * @return true if it is updated, in other way will return false.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    boolean setCompletedStatus(long contractId, long orderId) throws ServiceException;

    /**
     * Method create contract(offer) by order id and executor id.
     * Contract has under consideration status and not completed status
     *
     * @param executorId of executor
     * @param orderId    of order
     * @return true if it is updated, in other way will return false.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    boolean createOffer(long orderId, long executorId) throws ServiceException;


}
