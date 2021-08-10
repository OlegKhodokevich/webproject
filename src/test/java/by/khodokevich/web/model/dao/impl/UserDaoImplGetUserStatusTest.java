package by.khodokevich.web.model.dao.impl;

import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserDaoImplGetUserStatusTest {

    @Test(groups = "user_dao")
    public void testGetUserStatus1() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        try(by.khodokevich.web.model.dao.EntityTransaction transaction = new EntityTransaction()){
            transaction.beginSingleQuery(userDao);
            long userId = 44;
            UserStatus actualStatus = userDao.getUserStatus(userId);
            UserStatus expectedStatus = UserStatus.CONFIRMED;

            Assert.assertEquals(actualStatus, expectedStatus);
        }
    }

    @Test(expectedExceptions = DaoException.class, groups = "user_dao")
    public void testGetUserStatus2() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        try(by.khodokevich.web.model.dao.EntityTransaction transaction = new EntityTransaction()){
            transaction.beginSingleQuery(userDao);
            long userId = 1;
            UserStatus actualStatus = userDao.getUserStatus(userId);
        }
    }
}