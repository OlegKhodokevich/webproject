package by.khodokevich.web.util.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrderDataValidatorSpecializationTest {

    @Test(dataProvider = "validate_specialization", groups = {"order_validation"})
    public void testIsSpecializationValid(String specialization, boolean expectedResult) throws ServiceException {
        boolean actualResult = OrderDataValidator.isTitleValid(specialization);

        Assert.assertEquals(actualResult, expectedResult);
    }


    @DataProvider(name = "validate_specialization")
    public Object[][] createData() {
        return new Object[][]{
                {"ELECTRICAL", true},
                {"PLUMBING", true},
                {"PLASTERING", true},
                {"LAYING_TILES", true},
                {"PAINTING", true},
                {"WALLPAPERING", true},
                {"CEMENT_FLOOR", true},
                {"FLOOR_COVERING", true},
                {"CARPENTRY_WORK", true},
                {"TURNKEY_HOUSE", true},
                {"ROOF", true},
                {"MONOLITE", true},
                {"BRICKLAYING", true},
                {"FASAD", true},
                {"LANDSCAPING", true},

                {"LANDSCAPINg", true},

                {"ELECTRICAL1", true},
                {"PLUMBING2", true},
                {"PLASTERING3", true},
                {"LAYING_TILES5", true},
                {"PAINTING1", true},
                {"WALLPAPERING1", true},
                {"CEMENT_FLOOR1", true},
                {"FLOOR_COVERING1", true},
                {"CARPENTRY_WORK1", true},
                {"TURNKEY_HOUSe1", true},
                {"ROOF1", true},
                {"MONOLITE1", true},
                {"BRICKLAYING1", true},
                {"FASAD1", true},
                {"LANDSCAPING1", true},

                {".", false},
                {"", false},
                {" ", false}};
    }
}