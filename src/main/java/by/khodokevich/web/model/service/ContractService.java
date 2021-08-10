package by.khodokevich.web.model.service;

import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractService {
    List<Contract> findConcludedContractByUserCustomerId(long userCustomerId)  throws ServiceException;

    List<Contract> findUnderConsiderationContractByUserCustomerId(long userCustomerId) throws ServiceException;


    List<Contract> findOfferByUserExecutorId(long userCustomerId) throws ServiceException;


    Optional<Contract> findContractInformationById(long contractId) throws ServiceException;

    boolean setConcludedStatus(long contractId, long orderId) throws ServiceException;

    boolean setNotConcludedStatus(long contractId) throws ServiceException;
}
