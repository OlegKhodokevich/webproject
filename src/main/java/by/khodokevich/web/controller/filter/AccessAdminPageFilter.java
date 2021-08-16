package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.model.entity.UserRole;
import by.khodokevich.web.model.entity.UserStatus;
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
 * Filter which don't pass users except admin on specify page.
 * We specify url pattern "/pages/admin/*".
 * In case users hasn't an access he will be redirected to main page.
 *
 * @author Oleg Khodokevich
 *
 */
@WebFilter("/pages/admin/*")
public class AccessAdminPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AccessAdminPageFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Start AccessAdminPageFilter.");
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String activeUserRoleString = (String) session.getAttribute(ACTIVE_USER_ROLE);
        logger.debug(" User role = " + activeUserRoleString);
        if (activeUserRoleString == null || !activeUserRoleString.equalsIgnoreCase(UserRole.ADMIN.name())) {
            logger.debug("User wil throw out.");
            ((HttpServletResponse) servletResponse).sendRedirect(PagePath.MAIN_PAGE);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
