package by.khodokevich.web.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDataValidatorRepeatPasswordTest {

    @Test(dataProvider = "validate_repeat_password", groups = {"user_validation"})
    public void testIsRepeatedPasswordValid(String password, String repeated_password, boolean expectedResult) throws ServiceException {
        boolean actualResult = UserDataValidator.isRepeatedPasswordValid(password, repeated_password);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_repeat_password")
    public Object[][] createData() {
        return new Object[][]{
                {"Password1", "Password1", true},
                {"Password1!", "Password1!", true},
                {"Password16658", "Password16658", true},
                {"Password1ad2333:##@", "Password1ad2333:##@", true},
                {"Password1ad2333:##", "Password1ad2333:##@", false},
                {"Password16658", "Password16758", false},
                {"Password1!", "password1!", false}};
    }
}