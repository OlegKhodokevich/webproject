package by.khodokevich.web.model.service;

import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.Revoke;

import java.util.List;
/**
 * Interface serve operations with revokes.
 */
public interface RevokeService {
    /**
     * Method search executor revoke by id
     *
     * @param userExecutorId of executor
     * @return List of revokes
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Revoke> findAllExecutorRevoke(long userExecutorId) throws ServiceException;

    /**
     * Method create revoke in database
     *
     * @param contractIdString of contract
     * @param description      of revoke
     * @param markString       of revoke
     * @return true if revoke was created  and executor's information was changed.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    boolean createRevoke(String contractIdString, String description, String markString) throws ServiceException;
}
