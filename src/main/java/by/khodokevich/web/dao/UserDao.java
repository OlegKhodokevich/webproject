package by.khodokevich.web.dao;

import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    boolean setUserStatus(long idUser, UserStatus userStatus) throws DaoException;

    UserStatus getUserStatus(long idUser) throws DaoException;

    boolean register(User user, String password) throws DaoException;

    Optional<User> findUserByEMail(String eMail) throws DaoException;

    Optional<User> findUserByPhone(String eMail) throws DaoException;

    String findUserPasswordById(long idUser) throws DaoException;

    boolean updateUserWithChangEMail(User entity, String password) throws DaoException;

    boolean updateUserWithoutChangEMail(User entity, String password) throws DaoException;
}
