package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

@WebFilter
public class AccessUserPageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        if (!((String) session.getAttribute(ACTIVE_USER_ROLE)).equalsIgnoreCase(UserRole.GUEST.name())){
            ((HttpServletResponse)servletResponse).sendRedirect(PagePath.MAIN_PAGE);
        }
    }
}

