package by.khodokevich.web.service;

import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserStatus;
import by.khodokevich.web.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Map<String, String> register(Map<String, String> userData) throws ServiceException;

    Map<String,String> logOn(Map<String, String> userData) throws ServiceException;

    CheckingResult activateUser(String eMail, String token) throws ServiceException;

    Optional<User> findDefineUser(long userId) throws ServiceException;

    UserStatus getUserStatus(long userId) throws ServiceException;
}
