package by.khodokevich.web.model.service.Impl;

import by.khodokevich.web.model.builder.UserBuilder;
import by.khodokevich.web.model.dao.impl.UserDaoImpl;
import by.khodokevich.web.model.entity.RegionBelarus;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserRole;
import by.khodokevich.web.model.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.CheckingResult;
import by.khodokevich.web.model.service.ServiceProvider;
import by.khodokevich.web.model.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.RESULT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@Listeners(MockitoTestNGListener.class)
public class UserServiceRegistrationNotValidTest {

    @Mock
    private UserDaoImpl dao;

    @InjectMocks
    private UserService userService;

    private Map<String,String> userData;
    private Map<String,String> actualAnswerData;
    private User user;
    private String password;
    private String firstName;
    private String lastName;
    private String eMail;
    private String phone;
    private String regionString;
    private String city;
    private String repeatPassword;
    private String url;
    private Map<String,String> expectedAnswerData;

    @BeforeClass
    public void beforeClass() throws DaoException {
        dao = mock(UserDaoImpl.class);
        userService = ServiceProvider.USER_SERVICE;
//        ((UserServiceImpl) userService).setUserDao(dao);  TODO
        firstName = "Ivan1";
        lastName = "Ivanov";
        eMail = "ivanov@gmail.com";
        phone = "+375293372547";
        regionString = "MINSK_REGION";
        city = "Minsk";
        password = "Password1";
        repeatPassword = "Password1";
        url = "http://localhost:8080/";

        userData =new HashMap<>();
        userData.put(FIRST_NAME, firstName);
        userData.put(LAST_NAME, lastName);
        userData.put(E_MAIL, eMail);
        userData.put(PHONE, phone);
        userData.put(REGION, regionString);
        userData.put(CITY, city);
        userData.put(PASSWORD, password);
        userData.put(REPEATED_PASSWORD, repeatPassword);
        userData.put(URL, url);
        userData.put("isTest", "true");
        user = new UserBuilder().firstName(firstName)
                .lastName(lastName)
                .eMail(eMail)
                .phone(phone)
                .region(RegionBelarus.valueOf(regionString))
                .city(city)
                .status(UserStatus.DECLARED)
                .role(UserRole.CUSTOMER)
                .buildUser();
        when(dao.register(user, password)).thenReturn(true);
        when(dao.findUserByEMail(eMail)).thenReturn(Optional.empty());
        when(dao.checkIsUserExistByPhone(phone)).thenReturn(false);

    }


    @Test
    public void testRegister() throws ServiceException {

        actualAnswerData = userService.register(userData);
        expectedAnswerData = new HashMap<>();
        expectedAnswerData.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswerData.put(LAST_NAME, lastName);
        expectedAnswerData.put(E_MAIL, eMail);
        expectedAnswerData.put(PHONE, phone);
        expectedAnswerData.put(REGION, regionString);
        expectedAnswerData.put(CITY, city);
        assertEquals(actualAnswerData, expectedAnswerData);
    }

}
