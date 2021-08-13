package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.CommandProvider;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.impl.common.AllOrderOnPageCommand;
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

import static by.khodokevich.web.controller.command.ParameterAttributeType.ACTIVE_USER_ROLE;
import static by.khodokevich.web.controller.command.ParameterAttributeType.COMMAND;

@WebFilter
public class CacheFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CacheFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Start CacheFilter.");
        String commandName = servletRequest.getParameter(COMMAND);
        logger.debug("command name = " + commandName);
        if (commandName != null) {
            List<UserRole> userRoleList = CommandProvider.getInstance().getRoleList(commandName);
            if (!userRoleList.contains(UserRole.GUEST)) {
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                logger.debug(httpResponse.getHeader("Cache-Control"));
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}

