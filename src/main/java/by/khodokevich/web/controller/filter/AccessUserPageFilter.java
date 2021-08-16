package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * Filter which pass users except guest on specify page.
 * We specify url pattern "/pages/user/*".
 * In case users hasn't an access he will be redirected to main page.
 *
 * @author Oleg Khodokevich
 *
 */
@WebFilter("/pages/user/*")
public class AccessUserPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AccessUserPageFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Start AccessUserPageFilter.");
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String activeUserRoleString = (String) session.getAttribute(ACTIVE_USER_ROLE);
        logger.debug(" User role = " + activeUserRoleString);
        if (activeUserRoleString == null || activeUserRoleString.equalsIgnoreCase(UserRole.GUEST.name())) {
            logger.debug("User wil throw out.");
            ((HttpServletResponse) servletResponse).sendRedirect(PagePath.MAIN_PAGE);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}

