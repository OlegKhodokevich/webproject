package by.khodokevich.web.validator;

import by.khodokevich.web.entity.RegionBelarus;
import by.khodokevich.web.service.CheckingResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.khodokevich.web.util.RegexpManager.*;
import static by.khodokevich.web.command.ParameterAttributeType.*;

public class UserDataValidator {
    private static final Logger logger = LogManager.getLogger(UserDataValidator.class);
    private static final String KEY_REGEXP_FIRSTNAME = "regexp.user.firstName";
    private static final String KEY_REGEXP_LASTNAME = "regexp.user.lastName";
    private static final String KEY_REGEXP_EMAIL = "regexp.user.eMail";
    private static final String KEY_REGEXP_PHONE = "regexp.user.phone";
    private static final String KEY_REGEXP_CITY = "regexp.user.city";
    private static final String KEY_REGEXP_PASSWORD = "regexp.user.password";

    public static Map<String, String> checkUserData(Map<String, String> userData) {
        String firstName = userData.get(FIRST_NAME);
        String lastName = userData.get(LAST_NAME);
        String eMail = userData.get(E_MAIL);
        String phone = userData.get(PHONE);
        String region = userData.get(REGION);
        String city = userData.get(CITY);
        String password = userData.get(PASSWORD);
        String repeatedPassword = userData.get(REPEATED_PASSWORD);

        Map<String, String> answerMap = new HashMap<>();

        boolean result = isFirstNameValid(firstName) && isLastNameValid(lastName) && isEMailValid(eMail)
                && isCityValid(city) && isPhoneValid(phone) && isPasswordValid(password)
                && isRepeatedPasswordValid(password, repeatedPassword) && isRegionValid(region);
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

    public static boolean isFirstNameValid(String firstName) {
        String regexp = getRegexp(KEY_REGEXP_FIRSTNAME);
        return firstName.matches(regexp);
    }

    public static boolean isLastNameValid(String lastName) {
        String regexp = getRegexp(KEY_REGEXP_LASTNAME);
        return lastName.matches(regexp);
    }

    public static boolean isEMailValid(String eMail) {
        String regexp = getRegexp(KEY_REGEXP_EMAIL);
        return eMail.matches(regexp);
    }

    public static boolean isPhoneValid(String phone) {
        String regexp = getRegexp(KEY_REGEXP_PHONE);
        return phone.matches(regexp);
    }

    public static boolean isCityValid(String city) {
        String regexp = getRegexp(KEY_REGEXP_CITY);
        return city.matches(regexp);
    }

    public static boolean isPasswordValid(String password) {
        String regexp = getRegexp(KEY_REGEXP_PASSWORD);
        return password.matches(regexp);
    }

    public static boolean isRepeatedPasswordValid(String password, String repeatedPassword) {
        return password.equals(repeatedPassword);
    }

    public static boolean isRegionValid(String region) {
        RegionBelarus[] regions = RegionBelarus.values();
        boolean result = Arrays.stream(regions).anyMatch((s) -> s.name().equalsIgnoreCase(region));
        return result;
    }
}
