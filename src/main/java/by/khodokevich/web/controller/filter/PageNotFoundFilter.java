package by.khodokevich.web.controller.filter;

import by.khodokevich.web.controller.command.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Filter which redirect user to "404 error" page if requested page isn't exist.
 * See PageNotFoundResponseWrapper.
 *
 * @author Oleg Khodokevich
 *
 */
@WebFilter(filterName = "PageNotFoundFilter", urlPatterns = {"/*"})
public class PageNotFoundFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(PageNotFoundFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Start filter for redirect to error 404 page.");
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        PageNotFoundResponseWrapper pageNotFoundResponseWrapper = new PageNotFoundResponseWrapper(httpServletResponse);
        logger.debug("Before error page. pageNotFoundResponseWrapper.getStatus() " + pageNotFoundResponseWrapper.getStatus());
        filterChain.doFilter(servletRequest, pageNotFoundResponseWrapper);
        if (httpServletResponse.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            logger.debug("Redirect error page. pageNotFoundResponseWrapper.getStatus() " + pageNotFoundResponseWrapper.getStatus());
            httpServletResponse.sendRedirect(PagePath.ERROR404_PAGE);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            logger.debug("Redirect error page. pageNotFoundResponseWrapper.getStatus() " + pageNotFoundResponseWrapper.getStatus());
        }
    }
}
