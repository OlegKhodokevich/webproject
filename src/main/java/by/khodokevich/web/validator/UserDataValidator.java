package by.khodokevich.web.validator;

import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.RegionBelarus;
import by.khodokevich.web.model.service.CheckingResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static by.khodokevich.web.util.RegexpManager.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

public class UserDataValidator {
    private static final Logger logger = LogManager.getLogger(UserDataValidator.class);
    private static final String KEY_REGEXP_FIRSTNAME = "regexp.user.firstName";
    private static final String KEY_REGEXP_LASTNAME = "regexp.user.lastName";
    private static final String KEY_REGEXP_EMAIL = "regexp.user.eMail";
    private static final String KEY_REGEXP_PHONE = "regexp.user.phone";
    private static final String KEY_REGEXP_CITY = "regexp.user.city";
    private static final String KEY_REGEXP_PASSWORD = "regexp.user.password";

    public static Map<String, String> checkUserData(Map<String, String> userData) throws ServiceException {

        Map<String, String> answerMap = checkUserDataWithoutPassword(userData);
        if (!isPasswordValid(userData.get(PASSWORD)) && isRepeatedPasswordValid(userData.get(PASSWORD),userData.get(REPEATED_PASSWORD))) {
            answerMap = userData;
        }
        return answerMap;
    }

    public static Map<String, String> checkUserDataWithoutPassword(Map<String, String> userData) throws ServiceException {
        String firstName = userData.get(FIRST_NAME);
        String lastName = userData.get(LAST_NAME);
        String eMail = userData.get(E_MAIL);
        String phone = userData.get(PHONE);
        String region = userData.get(REGION);
        String city = userData.get(CITY);

        Map<String, String> answerMap = new HashMap<>();

        boolean result = isFirstNameValid(firstName) && isLastNameValid(lastName) && isEMailValid(eMail)
                && isCityValid(city) && isPhoneValid(phone) && isRegionValid(region);
        if (result) {
            answerMap.put(RESULT, CheckingResult.SUCCESS.name());
        } else {
            if (isFirstNameValid(firstName)) {
                answerMap.put(FIRST_NAME, firstName);
            }
            if (isLastNameValid(lastName)) {
                answerMap.put(LAST_NAME, lastName);
            }
            if (isEMailValid(eMail)) {
                answerMap.put(E_MAIL, eMail);
            }
            if (isPhoneValid(phone)) {
                answerMap.put(PHONE, phone);
            }
            if (isCityValid(city)) {
                answerMap.put(CITY, city);
            }
            if (isRegionValid(region)) {
                answerMap.put(REGION, region);
            }
            answerMap.put(RESULT, CheckingResult.NOT_VALID.name());
        }
        return answerMap;
    }

    public static boolean isFirstNameValid(String firstName) throws ServiceException {
        if (firstName == null) {
            logger.error("firstName = null.");
            throw new ServiceException("firstName = null.");
        }
        String regexp = getRegexp(KEY_REGEXP_FIRSTNAME);
        return firstName.matches(regexp);
    }

    public static boolean isLastNameValid(String lastName) throws ServiceException {
        if (lastName == null) {
            logger.error("lastName = null.");
            throw new ServiceException("lastName = null.");
        }
        String regexp = getRegexp(KEY_REGEXP_LASTNAME);
        return lastName.matches(regexp);
    }

    public static boolean isEMailValid(String eMail) throws ServiceException {
        if (eMail == null) {
            logger.error("eMail = null.");
            throw new ServiceException("eMail = null.");
        }
        String regexp = getRegexp(KEY_REGEXP_EMAIL);
        return eMail.matches(regexp);
    }

    public static boolean isPhoneValid(String phone) throws ServiceException {
        if (phone == null) {
            logger.error("phone = null.");
            throw new ServiceException("phone = null.");
        }
        String regexp = getRegexp(KEY_REGEXP_PHONE);
        return phone.matches(regexp);
    }

    public static boolean isCityValid(String city) throws ServiceException {
        if (city == null) {
            logger.error("city = null.");
            throw new ServiceException("city = null.");
        }
        String regexp = getRegexp(KEY_REGEXP_CITY);
        return city.matches(regexp);
    }

    public static boolean isPasswordValid(String password) throws ServiceException {
        if (password == null) {
            logger.error("password = null.");
            throw new ServiceException("password = null.");
        }
        String regexp = getRegexp(KEY_REGEXP_PASSWORD);
        return password.matches(regexp);
    }

    public static boolean isRepeatedPasswordValid(String password, String repeatedPassword) throws ServiceException {
        if (repeatedPassword == null) {
            logger.error("repeatedPassword = null.");
            throw new ServiceException("repeatedPassword = null.");
        }
        boolean result = password.equals(repeatedPassword);
        return result;
    }

    public static boolean isRegionValid(String region) throws ServiceException {
        if (region == null) {
            logger.error("region = null.");
            throw new ServiceException("region = null.");
        }
        RegionBelarus[] regions = RegionBelarus.values();
        boolean result = Arrays.stream(regions).anyMatch((s) -> s.name().equalsIgnoreCase(region));
        return result;
    }
}
