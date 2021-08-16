package by.khodokevich.web.controller.command;

import by.khodokevich.web.model.entity.Region;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.CITY;

/**
 * This class is used to get session attributes.
 */
public class SessionData {
    /**
     * Method put user params into session
     *
     * @param userData Map of user params
     * @param session of servlet
     */
    public static void setSessionUserData(Map<String, String> userData, HttpSession session) {
        Region region = Region.valueOf(userData.get(REGION).toUpperCase());
        session.setAttribute(FIRST_NAME, userData.get(FIRST_NAME));
        session.setAttribute(LAST_NAME, userData.get(LAST_NAME));
        session.setAttribute(E_MAIL, userData.get(E_MAIL));
        session.setAttribute(PHONE, userData.get(PHONE));
        session.setAttribute(REGION,region);
        session.setAttribute(CITY, userData.get(CITY));
    }
}
