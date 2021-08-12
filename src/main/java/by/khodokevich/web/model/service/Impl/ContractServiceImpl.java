package by.khodokevich.web.model.service.Impl;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.dao.AbstractDao;
import by.khodokevich.web.model.dao.ContractDao;
import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.dao.impl.ContractDaoImpl;
import by.khodokevich.web.model.dao.impl.OrderDaoImpl;
import by.khodokevich.web.model.dao.impl.UserDaoImpl;
import by.khodokevich.web.model.entity.Contract;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.OrderStatus;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.service.ContractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ContractServiceImpl implements ContractService {
    private static final Logger logger = LogManager.getLogger(ContractServiceImpl.class);
    private static ContractServiceImpl instance;


    private ContractServiceImpl() {
    }

    public static ContractServiceImpl getInstance() {
        if (instance == null) {
            instance = new ContractServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Contract> findConcludedContractByUserCustomerId(long userCustomerId) throws ServiceException {
        logger.info("Start findConcludedContractByUserCustomerId(long userCustomerId). Id = " + userCustomerId);
        List<Contract> contracts;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.begin(contractDao, orderDao, userDao);
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
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findContractByUserCustomerId(long userCustomerId). Contracts = " + contracts);
        return contracts;
    }


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

    @Override
    public List<Contract> findOfferByUserExecutorId(long executorId) throws ServiceException {
        logger.info("Start findOfferByUserExecutorId(long executorId). Id = " + executorId);
        List<Contract> contracts;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.begin(contractDao, orderDao, userDao);
            List<Contract> allUserContracts = contractDao.findOfferByIdExecutor(executorId);
            contracts = allUserContracts.stream().filter(s -> s.getConcludedContractStatus() != Contract.ConcludedContractStatus.CONCLUDED).sorted((s1, s2) -> (int) (s2.getOrder().getOrderId() - s1.getOrder().getOrderId())).toList();
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

    @Override
    public List<Contract> findUnderConsiderationContractByUserCustomerId(long userCustomerId) throws ServiceException {
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


    @Override
    public List<Contract> findUnderConsiderationContractByUserExecutorId(long userExecutorId) throws ServiceException {
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

    @Override
    public boolean setConcludedStatus(long contractId, long orderId) throws ServiceException {
        logger.info("Start setConcludedStatus(long contractId). Id = " + contractId);
        boolean result = false;
        ContractDaoImpl contractDao = new ContractDaoImpl();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.begin(contractDao, orderDao);
            List<Long> contractIdList = contractDao.findAllContractByOrderId(orderId);
            contractIdList.remove(contractId);
            if (contractDao.setConcludedStatus(contractId)) {
                result = contractDao.setNotConcludedStatusForCompetitor(contractIdList);
            }
            orderDao.setOrderStatus(orderId, OrderStatus.IN_WORK);
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

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


    @Override
    public boolean setCompletedStatus(long contractId, long orderId) throws ServiceException {
        logger.info("Start setCompletedStatus(long contractId). Id = " + contractId);
        boolean result = false;
        try (EntityTransaction transaction = new EntityTransaction()) {
            ContractDaoImpl contractDao = new ContractDaoImpl();
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.begin(contractDao, orderDao);
            if (contractDao.setCompletedStatus(contractId)) {
                result = orderDao.setOrderStatus(orderId, OrderStatus.CLOSE);
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean createOffer(long orderId, long executorId) throws ServiceException {
        logger.info("Start createOffer(long orderId, long executorId). Id order= " + orderId + " , executor id = " + executorId);
        boolean result;
        try (EntityTransaction transaction = new EntityTransaction()){
            ContractDaoImpl contractDao = new ContractDaoImpl();
            transaction.beginSingleQuery(contractDao);
            result = contractDao.createOffer(orderId, executorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
