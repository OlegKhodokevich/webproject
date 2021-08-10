package by.khodokevich.web.model.service.Impl;

import by.khodokevich.web.model.dao.AbstractDao;
import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.dao.impl.ExecutorDaoImpl;
import by.khodokevich.web.model.entity.Executor;
import by.khodokevich.web.model.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.ExecutorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ExecutorServiceImpl implements ExecutorService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private static ExecutorService instance;

    private ExecutorServiceImpl() {
    }

    public static ExecutorService getInstance() {
        if (instance == null) {
            instance = new ExecutorServiceImpl();
        }
        return instance;
    }


    @Override
    public List<Executor> findAllExecutors() throws ServiceException {
        logger.info("Start findAllExecutors().");
        List<Executor> executors;
        try (EntityTransaction transaction = new EntityTransaction()){
            ExecutorDaoImpl executorDao = new ExecutorDaoImpl();
            transaction.beginSingleQuery(executorDao);
            List<Executor> foundedExecutors = executorDao.findAll();
            logger.info("Has found next executors : " + foundedExecutors);
            executors = foundedExecutors.stream()
                    .filter(s->s.getStatus() == UserStatus.CONFIRMED)
                    .toList();
            logger.info("Has found next confirmed executors : " + executors);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return executors;
    }

    @Override
    public Optional<Executor> findDefineExecutor(long executorId) throws ServiceException {
        logger.info("Start findDefineExecutor(long executorId). ExecutorId = " + executorId);
        Optional<Executor> optionalExecutor;
        try (EntityTransaction transaction = new EntityTransaction()){
            ExecutorDaoImpl executorDao = new ExecutorDaoImpl();
            transaction.beginSingleQuery(executorDao);
            optionalExecutor = executorDao.findEntityById(executorId);
            if (optionalExecutor.isPresent() && optionalExecutor.get().getStatus() != UserStatus.CONFIRMED) {
                optionalExecutor = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalExecutor;
    }


}
