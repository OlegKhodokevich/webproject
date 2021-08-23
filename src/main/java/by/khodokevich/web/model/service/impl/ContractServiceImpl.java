package by.khodokevich.web.model.service.impl;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.dao.impl.*;
import by.khodokevich.web.model.entity.*;
import by.khodokevich.web.model.service.ContractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;


/**
 * Class implement Contract Service and serve operations with contracts.
 */
public class ContractServiceImpl implements ContractService {
    private static final Logger logger = LogManager.getLogger(ContractServiceImpl.class);
    private static ContractServiceImpl instance;


    private ContractServiceImpl() {
    }
    /**
     * @return ContractServiceImpl instance as singleton
     */
    public static ContractServiceImpl getInstance() {
        if (instance == null) {
            instance = new ContractServiceImpl();
        }
        return instance;
    }

    /**
     * Method searches all concluded contracts by customer id.
     *
     * @param userCustomerId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public List<Contract> findConcludedContractByUserCustomerId(long userCustomerId) throws ServiceException {
        logger.info("Start findConcludedContractByUserCustomerId(long userCustomerId). Id = " + userCustomerId);
        List<Contract> contracts;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            RevokeDaoImpl revokeDao = new RevokeDaoImpl();
            transaction.begin(contractDao, orderDao, userDao, revokeDao);
            List<Contract> allUserContracts = contractDao.findContractByIdUserCustomer(userCustomerId);
            contracts = allUserContracts.stream().filter(s -> s.getConcludedContractStatus() == Contract.ConcludedContractStatus.CONCLUDED).sorted((s1, s2) -> s2.getCompletionContractStatus().ordinal() - s1.getCompletionContractStatus().ordinal()).toList();

            for (Contract contract : contracts) {
                Optional<Order> optionalOrder = orderDao.findEntityById(contract.getOrder().getOrderId());
                if (optionalOrder.isPresent()) {
                    contract.setOrder(optionalOrder.get());
                } else {
                    logger.error("Can't find order. Order id = " + contract.getOrder().getOrderId());
                    throw new DaoException();
                }

                Optional<User> optionalUser = userDao.findEntityById(contract.getUser().getIdUser());
                if (optionalUser.isPresent()) {
                    contract.setUser(optionalUser.get());
                } else {
                    logger.error("Can't find user. User id = " + contract.getUser().getIdUser());
                    throw new DaoException();
                }

                Optional<Revoke> optionalRevoke = revokeDao.findEntityByContractId(contract.getIdContract());
                optionalRevoke.ifPresent(contract::setRevoke);
            }

            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findContractByUserCustomerId(long userCustomerId). Contracts = " + contracts);
        return contracts;
    }

    /**
     * Method searches all concluded contracts by executor id.
     *
     * @param executorId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public List<Contract> findConcludedContractByUserExecutorId(long executorId) throws ServiceException {
        logger.info("Start findConcludedContractByUserExecutorId(long executorId). Id = " + executorId);
        List<Contract> contracts;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.begin(contractDao, orderDao, userDao);

            List<Contract> allUserContracts = contractDao.findContractByIdExecutor(executorId);
            contracts = allUserContracts.stream().filter(s -> s.getConcludedContractStatus() == Contract.ConcludedContractStatus.CONCLUDED).sorted((s1, s2) -> s2.getCompletionContractStatus().ordinal() - s1.getCompletionContractStatus().ordinal()).toList();
            for (Contract contract : contracts) {
                Optional<Order> optionalOrder = orderDao.findEntityById(contract.getOrder().getOrderId());
                if (optionalOrder.isPresent()) {
                    contract.setOrder(optionalOrder.get());
                } else {
                    logger.error("Can't find order. Order id = " + contract.getOrder().getOrderId());
                    throw new DaoException();
                }

                Optional<User> optionalUser = userDao.findEntityById(contract.getUser().getIdUser());
                if (optionalUser.isPresent()) {
                    contract.setUser(optionalUser.get());
                } else {
                    logger.error("Can't find user. User id = " + contract.getUser().getIdUser());
                    throw new DaoException();
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findContractByUserCustomerId(long userCustomerId). Contracts = " + contracts);
        return contracts;
    }

    /**
     * Method searches all contracts (offer) except concluded by executor id.
     *
     * @param executorId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public List<Contract> findOfferByUserExecutorId(long executorId) throws ServiceException {
        logger.info("Start findOfferByUserExecutorId(long executorId). Id = " + executorId);
        List<Contract> contracts;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.begin(contractDao, orderDao, userDao);
            contracts = contractDao.findOfferByIdExecutor(executorId);
            for (Contract contract : contracts) {
                Optional<Order> optionalOrder = orderDao.findEntityById(contract.getOrder().getOrderId());
                if (optionalOrder.isPresent()) {
                    contract.setOrder(optionalOrder.get());
                } else {
                    logger.error("Can't find order. Order id = " + contract.getOrder().getOrderId());
                    throw new DaoException();
                }

                Optional<User> optionalUser = userDao.findEntityById(contract.getUser().getIdUser());
                if (optionalUser.isPresent()) {
                    contract.setUser(optionalUser.get());
                } else {
                    logger.error("Can't find user. User id = " + contract.getUser().getIdUser());
                    throw new DaoException();
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findOfferByUserExecutorId(long userCustomerId). Contracts = " + contracts);
        return contracts;
    }

    /**
     * Method searches all contracts under consideration (offer) by customer id.
     *
     * @param userCustomerId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public List<Contract> findUnderConsiderationContractByUserCustomerId(long userCustomerId) throws
            ServiceException {
        logger.info("Start findUnderConsiderationContractByUserCustomerId(long userCustomerId). Id = " + userCustomerId);
        List<Contract> contracts;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.begin(contractDao, orderDao, userDao);
            List<Contract> allUserContracts = contractDao.findContractByIdUserCustomer(userCustomerId);
            contracts = allUserContracts.stream().filter(s -> s.getConcludedContractStatus() == Contract.ConcludedContractStatus.UNDER_CONSIDERATION).sorted((s1, s2) -> (int) (s2.getOrder().getOrderId() - s1.getOrder().getOrderId())).toList();
            for (Contract contract : contracts) {
                Optional<Order> optionalOrder = orderDao.findEntityById(contract.getOrder().getOrderId());
                if (optionalOrder.isPresent()) {
                    contract.setOrder(optionalOrder.get());
                } else {
                    logger.error("Can't find order. Order id = " + contract.getOrder().getOrderId());
                    throw new DaoException();
                }

                Optional<User> optionalUser = userDao.findEntityById(contract.getUser().getIdUser());
                if (optionalUser.isPresent()) {
                    contract.setUser(optionalUser.get());
                } else {
                    logger.error("Can't find user. User id = " + contract.getUser().getIdUser());
                    throw new DaoException();
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findUnderConsiderationContractByUserCustomerId(long userCustomerId). Contracts = " + contracts);
        return contracts;
    }


    /**
     * Method searches all contracts under consideration (offer) by executor id.
     * Method sort list by order id.
     *
     * @param userExecutorId of contract
     * @return List of contracts
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public List<Contract> findUnderConsiderationContractByUserExecutorId(long userExecutorId) throws
            ServiceException {
        logger.info("Start findUnderConsiderationContractByUserExecutorId(long userCustomerId). Id = " + userExecutorId);
        List<Contract> contracts;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.begin(contractDao, orderDao, userDao);
            List<Contract> allUserContracts = contractDao.findContractByIdExecutor(userExecutorId);
            contracts = allUserContracts.stream().filter(s -> s.getConcludedContractStatus() == Contract.ConcludedContractStatus.UNDER_CONSIDERATION).sorted((s1, s2) -> (int) (s2.getOrder().getOrderId() - s1.getOrder().getOrderId())).toList();
            for (Contract contract : contracts) {
                Optional<Order> optionalOrder = orderDao.findEntityById(contract.getOrder().getOrderId());
                if (optionalOrder.isPresent()) {
                    contract.setOrder(optionalOrder.get());
                } else {
                    logger.error("Can't find order. Order id = " + contract.getOrder().getOrderId());
                    throw new DaoException();
                }

                Optional<User> optionalUser = userDao.findEntityById(contract.getUser().getIdUser());
                if (optionalUser.isPresent()) {
                    contract.setUser(optionalUser.get());
                } else {
                    logger.error("Can't find user. User id = " + contract.getUser().getIdUser());
                    throw new DaoException();
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findUnderConsiderationContractByUserCustomerId(long userCustomerId). Contracts = " + contracts);
        return contracts;
    }

    /**
     * Method searches contract by id.
     *
     * @param contractId of contract
     * @return optional contract
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public Optional<Contract> findContractInformationById(long contractId) throws ServiceException {
        logger.info("Start findContractInformationById(long contractId). Id = " + contractId);
        Optional<Contract> optionalContract;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.begin(contractDao, orderDao, userDao);
            optionalContract = contractDao.findEntityById(contractId);
            if (optionalContract.isPresent()) {
                Contract contract = optionalContract.get();
                Optional<Order> optionalOrder = orderDao.findEntityById(contract.getOrder().getOrderId());
                if (optionalOrder.isPresent()) {
                    contract.setOrder(optionalOrder.get());
                } else {
                    logger.error("Can't find order. Order id = " + contract.getOrder().getOrderId());
                    throw new DaoException();
                }

                Optional<User> optionalUser = userDao.findEntityById(contract.getUser().getIdUser());
                if (optionalUser.isPresent()) {
                    contract.setUser(optionalUser.get());
                } else {
                    logger.error("Can't find user. User id = " + contract.getUser().getIdUser());
                    throw new DaoException();
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findContractInformationById(long contractId). Contract = " + optionalContract);
        return optionalContract;
    }

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
    @Override
    public boolean setConcludedStatus(long contractId, long orderId) throws ServiceException {
        logger.info("Start setConcludedStatus(long contractId). Id = " + contractId);
        boolean result = false;
        ContractDaoImpl contractDao = new ContractDaoImpl();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        ExecutorDaoImpl executorDao = new ExecutorDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.begin(contractDao, orderDao, executorDao);
            Optional<Contract> contractOptional = findContractInformationById(contractId);
            if (contractOptional.isPresent()) {
                contractDao.setConcludedStatusDefineContractNotConcludedOtherOrderContract(contractId);
                orderDao.setOrderStatus(orderId, OrderStatus.IN_WORK);
                Optional<Executor> optionalExecutor = executorDao.findEntityById(contractOptional.get().getUser().getIdUser());
                if (optionalExecutor.isPresent()) {
                    ExecutorOption executorOption = optionalExecutor.get().getExecutorOption();
                    int numberContractInProgress = executorOption.getNumberContractsInProgress();
                    executorOption.setNumberContractsInProgress(numberContractInProgress + 1);
                    executorDao.updateExecutorOption(optionalExecutor.get());
                    result =true;
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    /**
     * Method set not concluded status for contract by id.
     *
     * @param contractId of contract
     * @return true if it is updated, in other way will return false.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public boolean setNotConcludedStatus(long contractId) throws ServiceException {
        logger.info("Start setNotConcludedStatus(long contractId). Id = " + contractId);
        boolean result;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            transaction.beginSingleQuery(contractDao);
            result = contractDao.setNotConcludedStatusForDefineContract(contractId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

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
    @Override
    public boolean setCompletedStatus(long contractId, long orderId) throws ServiceException {
        logger.info("Start setCompletedStatus(long contractId). Id = " + contractId);
        boolean result = false;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            ExecutorDaoImpl executorDao = new ExecutorDaoImpl();
            transaction.begin(contractDao, orderDao, executorDao);
            Optional<Contract> contractOptional = findContractInformationById(contractId);
            if (contractOptional.isPresent() && contractDao.setCompletedStatus(contractId)) {
                Optional<Executor> optionalExecutor = executorDao.findEntityById(contractOptional.get().getUser().getIdUser());
                if (optionalExecutor.isPresent()) {
                    ExecutorOption executorOption = optionalExecutor.get().getExecutorOption();
                    logger.debug("executor options before change= " + executorOption);
                    int numberContractInProgress = executorOption.getNumberContractsInProgress();
                    int numberCompletedContracts = executorOption.getNumberCompletionContracts();
                    executorOption.setNumberContractsInProgress(numberContractInProgress - 1);
                    executorOption.setNumberCompletionContracts(numberCompletedContracts + 1);
                    logger.debug("executor options after change= " + executorOption);
                    executorDao.updateExecutorOption(optionalExecutor.get());
                }
                result = orderDao.setOrderStatus(orderId, OrderStatus.CLOSE);
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    /**
     * Method create contract(offer) by order id and executor id.
     * Contract has under consideration status and not completed status
     *
     * @param executorId of executor
     * @param orderId    of order
     * @return true if it is updated, in other way will return false.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public boolean createOffer(long orderId, long executorId) throws ServiceException {
        logger.info("Start createOffer(long orderId, long executorId). Id order= " + orderId + " , executor id = " + executorId);
        boolean result;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            transaction.beginSingleQuery(contractDao);
            result = contractDao.createOffer(orderId, executorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
