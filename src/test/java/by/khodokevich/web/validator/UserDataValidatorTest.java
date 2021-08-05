package by.khodokevich.web.validator;

import by.khodokevich.web.service.CheckingResult;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static by.khodokevich.web.command.ParameterAttributeType.*;
import static by.khodokevich.web.command.ParameterAttributeType.REPEATED_PASSWORD;
import static org.testng.Assert.*;

public class UserDataValidatorTest {

    @Test(groups = {"user_validation"})
    public void testCheckUserData1() {
        Map<String,String> userData = new HashMap<>();
        userData.put(FIRST_NAME, "Ivan");
        userData.put(LAST_NAME, "Ivanov");
        userData.put(E_MAIL, "ivanov@gmail.com");
        userData.put(PHONE, "+375293372547");
        userData.put(REGION, "MINSK_REGION");
        userData.put(CITY, "Minsk");
        userData.put(PASSWORD, "Password2!");
        userData.put(REPEATED_PASSWORD, "Password2!");
        Map<String, String> actualAnswer = UserDataValidator.checkUserData(userData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.SUCCESS.name());

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"user_validation"})
    public void testCheckUserData2() {
        Map<String,String> userData = new HashMap<>();
        userData.put(FIRST_NAME, "Иван");
        userData.put(LAST_NAME, "Иванов");
        userData.put(E_MAIL, "ivanov@gmail.com");
        userData.put(PHONE, "+375293372547");
        userData.put(REGION, "MINSK_REGION");
        userData.put(CITY, "Минск");
        userData.put(PASSWORD, "Password2!");
        userData.put(REPEATED_PASSWORD, "Password2!");
        Map<String, String> actualAnswer = UserDataValidator.checkUserData(userData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.SUCCESS.name());

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"user_validation"})
    public void testCheckUserData3() {
        Map<String,String> userData = new HashMap<>();
        userData.put(FIRST_NAME, "Иван1");
        userData.put(LAST_NAME, "Иванов");
        userData.put(E_MAIL, "ivanov@gmail.com");
        userData.put(PHONE, "+375293372547");
        userData.put(REGION, "MINSK_REGION");
        userData.put(CITY, "Минск");
        userData.put(PASSWORD, "Password2!");
        userData.put(REPEATED_PASSWORD, "Password2!");
        Map<String, String> actualAnswer = UserDataValidator.checkUserData(userData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(LAST_NAME, "Иванов");
        expectedAnswer.put(E_MAIL, "ivanov@gmail.com");
        expectedAnswer.put(PHONE, "+375293372547");
        expectedAnswer.put(REGION, "MINSK_REGION");
        expectedAnswer.put(CITY, "Минск");

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"user_validation"})
    public void testCheckUserData4() {
        Map<String,String> userData = new HashMap<>();
        userData.put(FIRST_NAME, "Иван1");
        userData.put(LAST_NAME, "Иванов1");
        userData.put(E_MAIL, "ivanov@gmail.com");
        userData.put(PHONE, "+375293372547");
        userData.put(REGION, "MINSK_REGION");
        userData.put(CITY, "Минск");
        userData.put(PASSWORD, "Password2!");
        userData.put(REPEATED_PASSWORD, "Password2!");
        Map<String, String> actualAnswer = UserDataValidator.checkUserData(userData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(E_MAIL, "ivanov@gmail.com");
        expectedAnswer.put(PHONE, "+375293372547");
        expectedAnswer.put(REGION, "MINSK_REGION");
        expectedAnswer.put(CITY, "Минск");

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"user_validation"})
    public void testCheckUserData5() {
        Map<String,String> userData = new HashMap<>();
        userData.put(FIRST_NAME, "Иван");
        userData.put(LAST_NAME, "Иванов1");
        userData.put(E_MAIL, "iвvanov@gmail.com");
        userData.put(PHONE, "+175293372547");
        userData.put(REGION, "MINSK_REGION");
        userData.put(CITY, "Минск");
        userData.put(PASSWORD, "Password2!");
        userData.put(REPEATED_PASSWORD, "Password2!");
        Map<String, String> actualAnswer = UserDataValidator.checkUserData(userData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(FIRST_NAME, "Иван");
        expectedAnswer.put(REGION, "MINSK_REGION");
        expectedAnswer.put(CITY, "Минск");

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"user_validation"})
    public void testCheckUserData6() {
        Map<String,String> userData = new HashMap<>();
        userData.put(FIRST_NAME, "Иван");
        userData.put(LAST_NAME, "Иванов");
        userData.put(E_MAIL, "ivanov@gmail.com");
        userData.put(PHONE, "+375293372547");
        userData.put(REGION, "MINSK_REGION1");
        userData.put(CITY, "Минск");
        userData.put(PASSWORD, "Password2!");
        userData.put(REPEATED_PASSWORD, "Password2!");
        Map<String, String> actualAnswer = UserDataValidator.checkUserData(userData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(FIRST_NAME, "Иван");
        expectedAnswer.put(LAST_NAME, "Иванов");
        expectedAnswer.put(E_MAIL, "ivanov@gmail.com");
        expectedAnswer.put(PHONE, "+375293372547");
        expectedAnswer.put(CITY, "Минск");

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"user_validation"})
    public void testCheckUserData7() {
        Map<String,String> userData = new HashMap<>();
        userData.put(FIRST_NAME, "Иван");
        userData.put(LAST_NAME, "Иванов");
        userData.put(E_MAIL, "ivanov@gmail.com");
        userData.put(PHONE, "+375293372547");
        userData.put(REGION, "MINSK_REGION");
        userData.put(CITY, "Минск");
        userData.put(PASSWORD, "Password");
        userData.put(REPEATED_PASSWORD, "Password");
        Map<String, String> actualAnswer = UserDataValidator.checkUserData(userData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(FIRST_NAME, "Иван");
        expectedAnswer.put(LAST_NAME, "Иванов");
        expectedAnswer.put(E_MAIL, "ivanov@gmail.com");
        expectedAnswer.put(PHONE, "+375293372547");
        expectedAnswer.put(REGION, "MINSK_REGION");
        expectedAnswer.put(CITY, "Минск");

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"user_validation"})
    public void testCheckUserData8() {
        Map<String,String> userData = new HashMap<>();
        userData.put(FIRST_NAME, "Иван");
        userData.put(LAST_NAME, "Иванов");
        userData.put(E_MAIL, "ivanov@gmail.com");
        userData.put(PHONE, "+375293372547");
        userData.put(REGION, "MINSK_REGION");
        userData.put(CITY, "Минск");
        userData.put(PASSWORD, "Password");
        userData.put(REPEATED_PASSWORD, "PassworD");
        Map<String, String> actualAnswer = UserDataValidator.checkUserData(userData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(FIRST_NAME, "Иван");
        expectedAnswer.put(LAST_NAME, "Иванов");
        expectedAnswer.put(E_MAIL, "ivanov@gmail.com");
        expectedAnswer.put(PHONE, "+375293372547");
        expectedAnswer.put(REGION, "MINSK_REGION");
        expectedAnswer.put(CITY, "Минск");

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"user_validation"})
    public void testCheckUserData9() {
        Map<String,String> userData = new HashMap<>();
        userData.put(FIRST_NAME, "Иваннннннннннннннннннннннннннннннннннннннннннннн");
        userData.put(LAST_NAME, "Иванов");
        userData.put(E_MAIL, "ivanov@gmail.com");
        userData.put(PHONE, "+375293372547");
        userData.put(REGION, "MINSK_REGION");
        userData.put(CITY, "Минск");
        userData.put(PASSWORD, "Password");
        userData.put(REPEATED_PASSWORD, "PassworD");
        Map<String, String> actualAnswer = UserDataValidator.checkUserData(userData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(LAST_NAME, "Иванов");
        expectedAnswer.put(E_MAIL, "ivanov@gmail.com");
        expectedAnswer.put(PHONE, "+375293372547");
        expectedAnswer.put(REGION, "MINSK_REGION");
        expectedAnswer.put(CITY, "Минск");

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

}