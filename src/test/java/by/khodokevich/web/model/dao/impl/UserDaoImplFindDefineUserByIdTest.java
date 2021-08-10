package by.khodokevich.web.model.dao.impl;

import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.entity.RegionBelarus;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserRole;
import by.khodokevich.web.model.entity.UserStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;


public class UserDaoImplFindDefineUserByIdTest {

    @Test(groups = "user_dao")
    public void testFindEntityById1() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        try(by.khodokevich.web.model.dao.EntityTransaction transaction = new EntityTransaction()){
            transaction.beginSingleQuery(userDao);
            long userId = 44;
            Optional<User> actualOptionalUser = userDao.findEntityById(userId);

            Optional<User> expectedOptionalUser = Optional.of(new User(44, "Иван", "Иванов", "Test@test.by", "+375291228877", RegionBelarus.MINSK_REGION, "Минск", UserStatus.CONFIRMED, UserRole.CUSTOMER));

            Assert.assertEquals(actualOptionalUser, expectedOptionalUser);
        }
    }

    @Test(groups = "user_dao")
    public void testFindEntityById2() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        try(by.khodokevich.web.model.dao.EntityTransaction transaction = new EntityTransaction()){
            transaction.beginSingleQuery(userDao);
            long userId = 1;
            Optional<User> actualOptionalUser = userDao.findEntityById(userId);

            Optional<User> expectedOptionalUser = Optional.empty();

            Assert.assertEquals(actualOptionalUser, expectedOptionalUser);
        }
    }

    @Test(groups = "user_dao")
    public void testFindEntityById3() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        try(by.khodokevich.web.model.dao.EntityTransaction transaction = new EntityTransaction()){
            transaction.beginSingleQuery(userDao);
            long userId = -1;
            Optional<User> actualOptionalUser = userDao.findEntityById(userId);

            Optional<User> expectedOptionalUser = Optional.empty();
            Assert.assertEquals(actualOptionalUser, expectedOptionalUser);
        }
    }

}