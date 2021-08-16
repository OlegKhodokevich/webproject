package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.CommandProvider;
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
import java.util.List;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * Filter which check if user is able to invoke command by his role.
 * In case user's role isn't appropriate user will be redirected to main page.
 *
 * @author Oleg Khodokevich
 *
 */
@WebFilter
public class AccessRoleFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AccessRoleFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Start AccessRoleFilter");
        String commandName = servletRequest.getParameter(COMMAND);
        logger.debug("command name = " + commandName);
        if (commandName != null) {
            List<UserRole> userRoleList = CommandProvider.getInstance().getRoleList(commandName);
            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
            String roleString = (String) session.getAttribute(ACTIVE_USER_ROLE);
            UserRole role = UserRole.valueOf(roleString);
            logger.debug("command = " + commandName + " ,active role = " + roleString);

            if (!userRoleList.contains(role)) {
                ((HttpServletResponse) servletResponse).sendRedirect(PagePath.MAIN_PAGE);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
