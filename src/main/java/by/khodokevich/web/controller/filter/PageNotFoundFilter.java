package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.ParameterAttributeType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
@WebFilter(filterName = "PageNotFoundFilter", urlPatterns = {"/*"})
public class PageNotFoundFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(PageNotFoundFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("Init filter for redirect to error 404 page.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Start filter for redirect to error 404 page.");
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        PageNotFoundResponse pageNotFoundResponse = new PageNotFoundResponse(httpServletResponse);
        if (pageNotFoundResponse.getStatus() == 404) {
            httpServletResponse.sendRedirect(PagePath.ERROR404_PAGE);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(servletRequest, pageNotFoundResponse);
        }


    }

    @Override
    public void destroy() {

    }
}
