package by.khodokevich.web.validator;

import by.khodokevich.web.entity.Specialization;
import by.khodokevich.web.service.CheckingResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static by.khodokevich.web.command.ParameterAttributeType.*;
import static by.khodokevich.web.util.RegexpManager.getRegexp;

public class OrderDataValidator {
    private static final Logger logger = LogManager.getLogger(UserDataValidator.class);
    private static final String KEY_REGEXP_TITLE = "regexp.order.title";
    private static final String KEY_REGEXP_DESCRIPTION = "regexp.order.description";
    private static final String KEY_REGEXP_ADDRESS = "regexp.order.address";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public static Map<String, String> checkOrderData(Map<String, String> orderData) {
        String title = orderData.get(TITLE);
        String description = orderData.get(DESCRIPTION);
        String address = orderData.get(ADDRESS);
        String completionDateString = orderData.get(COMPLETION_DATE);
        String specializationString = orderData.get(SPECIALIZATION);

        Map<String, String> answerMap = new HashMap<>();
        boolean result = isTitleValid(title) && isDescriptionValid(description) && isAddressValid(address)
                && isCompletionDateValid(completionDateString) && isSpecializationValid(specializationString);

        if (result) {
            answerMap.put(RESULT, CheckingResult.SUCCESS.name());
        } else {
            if (isTitleValid(title)) {
                answerMap.put(TITLE, title);
            }
            if (isDescriptionValid(description)) {
                answerMap.put(DESCRIPTION, description);
            }
            if (isAddressValid(address)) {
                answerMap.put(ADDRESS, address);
            }
            if (isCompletionDateValid(completionDateString)) {
                answerMap.put(COMPLETION_DATE, completionDateString);
            }
            if (isSpecializationValid(specializationString)) {
                answerMap.put(SPECIALIZATION, specializationString);
            }
            answerMap.put(RESULT, CheckingResult.NOT_VALID.name());
        }
        return answerMap;
    }

    public static boolean isTitleValid(String title) {
        String regexp = getRegexp(KEY_REGEXP_TITLE);
        return title.matches(regexp);
    }

    public static boolean isDescriptionValid(String description) {
        String regexp = getRegexp(KEY_REGEXP_DESCRIPTION);
        return description.matches(regexp);
    }

    public static boolean isAddressValid(String address) {
        String regexp = getRegexp(KEY_REGEXP_ADDRESS);
        return address.matches(regexp);
    }

    public static boolean isSpecializationValid(String specialization) {
        Specialization[] specializations = Specialization.values();
        boolean result = Arrays.stream(specializations).anyMatch((s) -> s.name().equalsIgnoreCase(specialization));
        return result;
    }

    public static boolean isCompletionDateValid(String completionDateString) {
        boolean result;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        try {
            Date completionDate = dateFormat.parse(completionDateString);
            Date currentDate = new Date();
            result = !completionDate.before(currentDate);
        } catch (ParseException e) {
            result = false;
        }
        return result;
    }

}
