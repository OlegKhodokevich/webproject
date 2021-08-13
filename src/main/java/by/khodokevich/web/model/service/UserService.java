package by.khodokevich.web.model.service;

import by.khodokevich.web.model.entity.Pagination;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserStatus;
import by.khodokevich.web.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Map<String, String> register(Map<String, String> userData) throws ServiceException;

    Map<String,String> logOn(Map<String, String> userData) throws ServiceException;

    CheckingResult activateUser(String eMail, String token) throws ServiceException;

    Optional<User> findDefineUser(long userId) throws ServiceException;

    UserStatus getUserStatus(long userId) throws ServiceException;

    boolean changeUserStatus(long userId, UserStatus status) throws ServiceException;

    Map<String, String> updateUser(Map<String, String> userData) throws ServiceException;

    Map<String, String> updateUserWithoutPassword(Map<String, String> userData) throws ServiceException;

    List<User> findAllUserOnPage(Pagination pagination) throws ServiceException;

}
