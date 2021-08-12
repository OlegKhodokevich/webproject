package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

@WebFilter
public class CurrentArchivedUserFilter implements Filter {
    private static final String MASSAGE_KEY = "error.status_archived";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Set<Long> archivedUserSet = (Set<Long>) servletRequest.getServletContext().getContext(SET_ARCHIVE_USERS);
        Long activeUserId = (Long) servletRequest.getAttribute(ACTIVE_USER_ID);
        if (!archivedUserSet.isEmpty() && activeUserId != null && archivedUserSet.contains(activeUserId)) {
            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
            session.invalidate();
            archivedUserSet.remove(activeUserId);
            ((HttpServletResponse) servletResponse).sendRedirect(PagePath.TO_MAIN_PAGE + "?massage=" + MASSAGE_KEY);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
