package by.khodokevich.web.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDataValidatorFirstNameTest {


    @Test(dataProvider = "validate_first_name", groups = {"user_validation"})
    public void testIsFirstNameValid(String firstName, boolean expectedResult) throws ServiceException {
        boolean actualResult =  UserDataValidator.isFirstNameValid(firstName);

        Assert.assertEquals(actualResult, expectedResult);
    }


    @DataProvider(name = "validate_first_name")
    public Object[][] createData() {
        return new Object[][]{
                {"Peter", true},
                {"peter", true},
                {"peterpeterpeter", true},
                {"P", true},
                {"Питер", true},
                {"PeterTT", true},
                {"", false},
                {"Питер1", false},
                {"Peter#", false},
                {"Peter1", false}};
    }
}