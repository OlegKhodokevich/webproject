package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Set;

import static by.khodokevich.web.controller.command.InformationMessage.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

@WebFilter
public class CurrentArchivedUserFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CurrentArchivedUserFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Start CurrentArchivedUserFilter");
        Set<Long> archivedUserSet = (Set<Long>) servletRequest.getServletContext().getAttribute(SET_ARCHIVE_USERS);
        logger.debug("archivedUserSet = " + archivedUserSet);
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        Long activeUserId = (Long) session.getAttribute(ACTIVE_USER_ID);
        logger.debug("activeUserId = " + activeUserId);

        if (!archivedUserSet.isEmpty() && activeUserId != null && archivedUserSet.contains(activeUserId)) {
            session.invalidate();
            session = ((HttpServletRequest) servletRequest).getSession();
            session.setAttribute(MESSAGE, MASSAGE_KEY);
            archivedUserSet.remove(activeUserId);
            ((HttpServletResponse) servletResponse).sendRedirect(PagePath.MAIN_PAGE);
            logger.debug("User was throw out. Id = " + activeUserId);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
