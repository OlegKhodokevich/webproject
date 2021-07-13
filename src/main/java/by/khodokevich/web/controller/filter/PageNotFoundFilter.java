package by.khodokevich.web.controller.filter;

import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAttributeType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import java.io.IOException;
//@WebFilter(filterName = "PageNotFoundFilter", urlPatterns = {"/*"})
//public class PageNotFoundFilter implements Filter {
//    private static final Logger logger = LogManager.getLogger(XSSFilter.class);
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        logger.debug("Init filter for redirect to error 404 page.");
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        logger.debug("Start filter for redirect to error 404 page.");
//        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//        PageNotFoundResponse pageNotFoundResponse = new PageNotFoundResponse(httpServletResponse);
//        filterChain.doFilter(servletRequest, pageNotFoundResponse);
//        if (pageNotFoundResponse.getStatus() == 404) {
//            httpServletResponse.sendRedirect("/pages/error404.jsp");
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
//            session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.ERROR404_PAGE);
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
