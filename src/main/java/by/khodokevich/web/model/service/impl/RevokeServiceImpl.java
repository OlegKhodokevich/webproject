package by.khodokevich.web.model.service.impl;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.dao.impl.ContractDaoImpl;
import by.khodokevich.web.model.dao.impl.ExecutorDaoImpl;
import by.khodokevich.web.model.dao.impl.RevokeDaoImpl;
import by.khodokevich.web.model.entity.*;
import by.khodokevich.web.model.service.RevokeService;
import by.khodokevich.web.validator.RevokeDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * Class implement Revoke Service and serve operations with revoke.
 *
 */
public class RevokeServiceImpl implements RevokeService {
    private static final Logger logger = LogManager.getLogger(RevokeServiceImpl.class);

    private static RevokeService instance;


    private RevokeServiceImpl() {
    }

    /**
     * @return RevokeServiceImpl instance as singleton
     */
    public static RevokeService getInstance() {
        if (instance == null) {
            instance = new RevokeServiceImpl();
        }
        return instance;
    }

    /**
     * Method search executor revoke by id
     *
     * @param userExecutorId of executor
     * @return List of revokes
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public List<Revoke> findAllExecutorRevoke(long userExecutorId) throws ServiceException {
        logger.info("Start findAllExecutorRevoke(long userExecutorId). IdUser = " + userExecutorId);
        List<Revoke> revokes;
        try (EntityTransaction transaction = new EntityTransaction()) {
            RevokeDaoImpl revokeDao = new RevokeDaoImpl();
            transaction.beginSingleQuery(revokeDao);
            revokes = revokeDao.findAllExecutorRevoke(userExecutorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findUsersOrders(long idUser). Orders = " + revokes);
        return revokes;
    }

    /**
     * Method create revoke in database
     *
     * @param contractIdString of contract
     * @param description      of revoke
     * @param markString       of revoke
     * @return true if revoke was created  and executor's information was changed.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public boolean createRevoke(String contractIdString, String description, String markString) throws ServiceException {
        logger.info("Start createRevoke(String contractIdString, String description, String markString). contractIdString = " + contractIdString + " , description = " + description + " , markString = " + markString);
        if (contractIdString == null || description == null || markString == null) {
            logger.error("Some enter params are null");
            throw new ServiceException("Some enter params are null");
        }
        boolean result = RevokeDataValidator.isDescriptionValid(description);
        if (result) {
            try (EntityTransaction transaction = new EntityTransaction()) {
                RevokeDaoImpl revokeDao = new RevokeDaoImpl();
                ExecutorDaoImpl executorDao = new ExecutorDaoImpl();
                ContractDaoImpl contractDao = new ContractDaoImpl();
                transaction.begin(revokeDao, executorDao, contractDao);
                long contractId = Long.parseLong(contractIdString);
                int mark = Integer.parseInt(markString);

                Date creationDate = new Date();
                Revoke revoke = new Revoke(contractId, description, mark, creationDate);

                logger.info("Prepare to create next revoke = " + revoke);
                result = revokeDao.create(revoke);
                if (result) {
                    result = false;
                    long executorId = contractDao.getIdExecutor(contractId);
                    if (executorId >= 0) {
                        Optional<Executor> executorOptional = executorDao.findEntityById(executorId);
                        if (executorOptional.isPresent()) {
                            ExecutorOption executorOption = executorOptional.get().getExecutorOption();
                            List<Revoke> revokes = revokeDao.findAllExecutorRevoke(executorId);
                            int numberRevokes = revokes.size();
                            double averageMark = executorOption.getAverageMark();
                            averageMark = (averageMark * numberRevokes + mark) / (numberRevokes + 1);
                            executorOption.setAverageMark(averageMark);
                            executorDao.update(executorOptional.get());
                            result = true;
                        }
                    }
                }
                transaction.commit();
            } catch (NumberFormatException e) {
                logger.error("UserId is incorrect. It isn't able to be parsed.", e);
                throw new ServiceException("UserId is incorrect. It isn't able to be parsed.", e);
            } catch (DaoException e) {
                logger.error("Can't create order.", e);
                throw new ServiceException("Can't create order.", e);
            }
        }
        return result;
    }
}
