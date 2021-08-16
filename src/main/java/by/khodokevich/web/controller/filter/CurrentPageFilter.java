package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.ParameterAttributeType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter which set param current page or command each time when user move to another page or command.
 * There is for command "set local". It helps move back on same page where user has changed local.
 *
 * @author Oleg Khodokevich
 *
 */
@WebFilter
public class CurrentPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CurrentPageFilter.class);
    private static final String REFERER = "referer";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Start CurrentPageFilter");
        logger.debug("command = " + servletRequest.getParameter("command"));
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(true);
        String url = request.getHeader(REFERER);
        url = url != null ? url : PagePath.MAIN_PAGE;
        session.setAttribute(ParameterAttributeType.CURRENT_PAGE, url);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
