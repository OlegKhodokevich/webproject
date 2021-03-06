package by.khodokevich.web.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * This filter clears request from symbol like  "<",">"
 * See XSSRequestWrapper
 *
 * @author Oleg Khodokevich
 */
@WebFilter("/XSSFilter")
public class XSSFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(XSSFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Start XSS filter");
        filterChain.doFilter(new XSSRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }
}
