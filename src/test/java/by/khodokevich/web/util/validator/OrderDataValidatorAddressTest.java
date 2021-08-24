package by.khodokevich.web.util.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrderDataValidatorAddressTest {

    @Test(dataProvider = "validate_address", groups = {"order_validation"})
    public void testIsAddressValid(String address, boolean expectedResult) throws ServiceException {
        boolean actualResult = OrderDataValidator.isAddressValid(address);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_address")
    public Object[][] createData() {
        return new Object[][]{
                {"Minsk", true},
                {"Minsk Korsh-Sablina house 8 ap.206 ", true},
                {"Minsk Korsh-Sablina house 8 ap. №206 ", true},
                {"Минск", true},
                {"Minsk Korsh-Sablina house 8 ap. №206 Minsk Korsh-Sablina house 8 ap. №206 Minsk Korsh-Sablina house 8 ap. №206 " +
                        "Minsk Korsh-Sablina house 8 ap. №206 Minsk Korsh-Sablina house 8 ap. №206 Minsk Korsh-Sablina house 8 ap. №206 " +
                        "Minsk Korsh-Sablina house 8 ap. №206 Minsk Korsh-Sablina house 8 ap. №206 Minsk Korsh-Sablina house 8 ap. №206 ", false},
                {".", false},
                {"", false},
                {" ", false}};
    }
}