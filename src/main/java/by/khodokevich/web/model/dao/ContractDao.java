package by.khodokevich.web.model.dao;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.model.entity.Contract;

import java.util.List;

public interface ContractDao { //TODO if it been needed
    List<Contract> findContractByIdUserCustomer(long userCustomerId) throws DaoException;

    List<Contract> findOfferByIdExecutor(long executorId) throws DaoException;

    boolean setConcludedStatus(long contractId) throws DaoException;

    boolean setNotConcludedStatusForCompetitor(List<Long> contractIdList) throws DaoException;

    boolean setNotConcludedStatusForDefineContract(long contractId) throws DaoException;

    List<Long> findAllContractByOrderId(long userCustomerId) throws DaoException;
}
