package by.khodokevich.web.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDataValidatorRegionTest {

    @Test(dataProvider = "validate_region", groups = {"user_validation"})
    public void testIsRegionValid(String region, boolean expectedResult) throws ServiceException {
        boolean actualResult = UserDataValidator.isRegionValid(region);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_region")
    public Object[][] createData() {
        return new Object[][]{
                {"MINSK_REGION", true},
                {"HOMYEL_REGION", true},
                {"MAHILOU_REGION", true},
                {"VITEBSK_REGION", true},
                {"HRODNA_REGION", true},
                {"BREST_REGION", true},
                {"HOMYEL_REGION1", false},
                {"MAHILOU_REGION1", false},
                {"VITEBSK_REGION2", false},
                {"HRODNA_REGION3", false},
                {"BREST_REGION4", false},
                {" ", false}};
    }
}