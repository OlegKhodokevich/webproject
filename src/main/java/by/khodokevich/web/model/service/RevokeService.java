package by.khodokevich.web.model.service;

import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.Revoke;

import java.util.List;

public interface RevokeService {
    List<Revoke> findAllExecutorRevoke(long userExecutorId) throws ServiceException;

    boolean createRevoke(String contractIdString, String description, String markString) throws ServiceException;
}
