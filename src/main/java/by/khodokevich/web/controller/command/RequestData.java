package by.khodokevich.web.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * This class is used to get request and session params and attributes.
 */
public class RequestData {
    private static final Logger logger = LogManager.getLogger(RequestData.class);

    /**
     * Method get order information from request
     *
     * @param request of servlet
     * @return Map with order params
     */
    public static Map<String, String> getRequestOrderData(HttpServletRequest request) {
        logger.info("Start getRequestOrderData(HttpServletRequest request).");
        String orderId = request.getParameter(ORDER_ID);
        String title = request.getParameter(TITLE);
        String description = request.getParameter(DESCRIPTION);
        String address = request.getParameter(ADDRESS);
        String completionDate = request.getParameter(COMPLETION_DATE);
        String specialization = request.getParameter(SPECIALIZATION);
        HttpSession session = request.getSession();
        String userIdString;
        if (((String) session.getAttribute(ACTIVE_USER_ROLE)).equalsIgnoreCase("ADMIN")) {
            userIdString = String.valueOf(session.getAttribute(USER_ID));
        } else {
            userIdString = String.valueOf(session.getAttribute(ACTIVE_USER_ID));
        }

        Map<String, String> orderData = new HashMap<>();
        orderData.put(ORDER_ID, orderId);
        orderData.put(TITLE, title);
        orderData.put(DESCRIPTION, description);
        orderData.put(ADDRESS, address);
        orderData.put(COMPLETION_DATE, completionDate);
        orderData.put(SPECIALIZATION, specialization);
        orderData.put(USER_ID, userIdString);
        return orderData;
    }

    /**
     * Method get user information from request
     *
     * @param request of servlet
     * @return Map with user params
     */
    public static Map<String, String> getRequestUserData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String activeId = String.valueOf(session.getAttribute(ACTIVE_USER_ID));
        String activeRole = (String) session.getAttribute(ACTIVE_USER_ROLE);
        String userIdString = request.getParameter(USER_ID);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String eMail = request.getParameter(E_MAIL);
        String phone = request.getParameter(PHONE);
        String regionString = request.getParameter(REGION);
        String city = request.getParameter(CITY);
        String password = request.getParameter(PASSWORD);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD);
        String confirmedPassword = request.getParameter(CONFIRMED_PASSWORD);


        Map<String, String> userData = new HashMap<>();
        userData.put(ACTIVE_USER_ID, activeId);
        userData.put(ACTIVE_USER_ROLE, activeRole);
        userData.put(USER_ID, userIdString);
        userData.put(FIRST_NAME, firstName);
        userData.put(LAST_NAME, lastName);
        userData.put(E_MAIL, eMail);
        userData.put(PHONE, phone);
        userData.put(REGION, regionString);
        userData.put(CITY, city);
        userData.put(PASSWORD, password);
        userData.put(REPEATED_PASSWORD, repeatedPassword);
        userData.put(CONFIRMED_PASSWORD, confirmedPassword);
        userData.put(URL, request.getRequestURL().toString());
        return userData;
    }
}
