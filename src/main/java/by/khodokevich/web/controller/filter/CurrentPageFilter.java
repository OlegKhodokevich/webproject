package by.khodokevich.web.controller.filter;

import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAttributeType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter
public class CurrentPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(XSSFilter.class);
    private static final String REFERER = "referer";
    private static final String PATH_REGEX = "/pages.+";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Start CurrentPageFilter");
        logger.debug("command = " + servletRequest.getParameter("command"));
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(true);
        String url = request.getHeader(REFERER);
        String path =substringPathWithRegex(url);
        session.setAttribute(ParameterAttributeType.CURRENT_PAGE, path);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String substringPathWithRegex(String url) {
        Pattern pattern = Pattern.compile(PATH_REGEX);
        String path = null;
        if (url != null) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                path = matcher.group();
            }else {
                path = PagePath.MAIN_PAGE;
            }
        }
        return path;
    }

    @Override
    public void destroy() {
    }
}
