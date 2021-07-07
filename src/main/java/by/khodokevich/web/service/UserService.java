package by.khodokevich.web.service;

import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Map<String, String> register(Map<String, String> userData) throws ServiceException;

    Optional<User> logOn(Map<String, String> userData) throws ServiceException;

    CheckingResultType activateUser(String eMail, String token) throws ServiceException;

}
