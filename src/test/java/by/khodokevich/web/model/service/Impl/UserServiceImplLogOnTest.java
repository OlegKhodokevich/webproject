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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@Listeners(MockitoTestNGListener.class)
public class UserServiceImplLogOnTest { //TODO It doesn't work
    private static final Logger logger = LogManager.getLogger(UserServiceImplLogOnTest.class);

    @Mock
    private UserDaoImpl daoMoc;

    @Mock
    private Optional<User> optionalUserMoc;

    @InjectMocks
    private UserService userService;


    private Map<String,String> userData;
    private Map<String,String> actualAnswerData;
    private User user;
    private long userId;
    private String password;
    private String firstName;
    private String lastName;
    private String eMail;
    private String phone;
    private String regionString;
    private String city;
    private String status;
    private String role;
    private String repeatPassword;
    private String url;
    private Map<String,String> expectedAnswerData;

    @BeforeClass
    public void beforeClass() throws DaoException {
        optionalUserMoc = mock(Optional.class);
        daoMoc = mock(UserDaoImpl.class);
        userService = ServiceProvider.USER_SERVICE;
        userId = 1;
        firstName = "Ivan";
        lastName = "Ivanov";
        eMail = "ivanov@gmail.com";
        phone = "+375293372547";
        regionString = "MINSK_REGION";
        city = "Minsk";
        status = UserStatus.CONFIRMED.name();
        role = UserRole.CUSTOMER.name();
        password = "Password1!";
        repeatPassword = "Password1!";
        url = "http://localhost:8080/";

        userData =new HashMap<>();
        userData.put(E_MAIL, eMail);
        userData.put(PASSWORD, password);
        userData.put(URL, url);
        userData.put("isTest", "true");
        user = new UserBuilder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .eMail(eMail)
                .phone(phone)
                .region(RegionBelarus.valueOf(regionString))
                .city(city)
                .status(UserStatus.CONFIRMED)
                .role(UserRole.CUSTOMER)
                .buildUser();
        Optional<User> optionalUser = Optional.ofNullable(user);

        when(optionalUserMoc.isPresent()).thenReturn(true);
        when(optionalUserMoc.get()).thenReturn(user);
        when(daoMoc.findUserByEMail(Mockito.any(String.class))).thenReturn(optionalUserMoc);

//        when(dao.findUserPasswordById(userId)).thenReturn(password);
//        ((UserServiceImpl) userService).setUserDao(daoMoc);
    }


    @Test
    public void testLogOn() throws ServiceException {

        actualAnswerData = userService.logOn(userData);
        expectedAnswerData = new HashMap<>();
        expectedAnswerData.put(RESULT, CheckingResult.SUCCESS.name());

        expectedAnswerData.put(USER_ID, String.valueOf(userId));
        expectedAnswerData.put(FIRST_NAME, firstName);
        expectedAnswerData.put(LAST_NAME, lastName);
        expectedAnswerData.put(E_MAIL, eMail);
        expectedAnswerData.put(PHONE, phone);
        expectedAnswerData.put(REGION, regionString);
        expectedAnswerData.put(status, city);
        expectedAnswerData.put(role, city);
        assertEquals(actualAnswerData, expectedAnswerData);
    }
}