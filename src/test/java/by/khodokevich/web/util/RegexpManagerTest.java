package by.khodokevich.web.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RegexpManagerTest {
    private static final String KEY_REGEXP_DESCRIPTION = "regexp.order.description";
    private static final String KEY_REGEXP_ADDRESS = "regexp.order.address";

    private static final String KEY_REGEXP_FIRSTNAME = "regexp.user.firstName";
    private static final String KEY_REGEXP_LASTNAME = "regexp.user.lastName";
    private static final String KEY_REGEXP_EMAIL = "regexp.user.eMail";
    private static final String KEY_REGEXP_PHONE = "regexp.user.phone";
    private static final String KEY_REGEXP_CITY = "regexp.user.city";
    private static final String KEY_REGEXP_PASSWORD = "regexp.user.password";

    @Test(groups = "regexp_manager")
    public void testGetRegexp1() {
        String key = "regexp.order.title";
        String actualResult = RegexpManager.getRegexp(key);
        String expectedResult = "^[\\p{Alpha}\u0430-\u044F\u0410-\u042F\u0451\u0401\\d][\\p{Alpha}\u0430-\u044F\u0410-\u042F\u0451\u0401\\d\\s\\p{Punct}№]{1,100}$";

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(groups = "regexp_manager")
    public void testGetRegexp2() {
        String key = "regexp.user.eMail";
        String actualResult = RegexpManager.getRegexp(key);
        String expectedResult = "^[A-Za-z0-9._%+-]{1,25}@[A-Za-z0-9.-]{1,14}\\.[A-Za-z]{2,6}$";

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(groups = "regexp_manager")
    public void testGetRegexp3() {
        String key = "regexp.user.password";
        String actualResult = RegexpManager.getRegexp(key);
        String expectedResult = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9!\"#$%&\'()*+,-\\./:;=?@\\u005C\\[\\]^_\\`\\{\\|\\}~]{6,20}$";

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(groups = "regexp_manager")
    public void testGetRegexp4() {
        String key = "regexp.user.phone";
        String actualResult = RegexpManager.getRegexp(key);
        String expectedResult = "^\\+375[0-9]{9}$";

        Assert.assertEquals(actualResult, expectedResult);
    }
}