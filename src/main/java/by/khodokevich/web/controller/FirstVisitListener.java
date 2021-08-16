package by.khodokevich.web.controller;

import by.khodokevich.web.model.entity.UserRole;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * This listener check user session. If it isn't valid FirstVisitListener create it and
 * set attribute - user's role.
 *
 * @author Oleg Khodokevich
 */
@WebListener
public class FirstVisitListener implements ServletRequestListener {
    private static final Logger logger = LogManager.getLogger(FirstVisitListener.class);

    @Override
    public void requestInitialized(ServletRequestEvent sre) {

        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        if (!request.isRequestedSessionIdValid()) {
            HttpSession session = request.getSession();
            session.setAttribute(ACTIVE_USER_ROLE, UserRole.GUEST.name());
            logger.debug("Has been set guest's role.");
        }
    }
}
