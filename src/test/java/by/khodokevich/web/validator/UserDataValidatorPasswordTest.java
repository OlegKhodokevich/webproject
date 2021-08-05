package by.khodokevich.web.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDataValidatorPasswordTest {

    @Test(dataProvider = "validate_password", groups = {"user_validation"})
    public void testIsPasswordValid(String password, boolean expectedResult) {
        boolean actualResult = UserDataValidator.isPasswordValid(password);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_password")
    public Object[][] createData() {
        return new Object[][]{
                {"Password1", true},
                {"Password1!", true},
                {"Password16658", true},
                {"Password12333:##.\'@", true},
                {"password1", false},
                {"Pas", false},
                {"Password2112345678910", false},
                {"password1!32312safd@", false}};
    }
}