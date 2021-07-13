package by.khodokevich.web.controller.filter;

import by.khodokevich.web.command.ParameterAttributeType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;


@WebFilter(urlPatterns = {"/controller"}, initParams = {
        @WebInitParam(name = "characterEncoding", value = "UTF-8", description = "Encoding Param")})
public class CharsetFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CharsetFilter.class);

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Init FilterConfig filter");
        encoding = filterConfig.getInitParameter("characterEncoding");
        if (encoding ==null) {
            encoding = "UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Start Charset filter");
//        HttpSession session = ((HttpServletRequest)request).getSession();         TODO delet if unnecessary
//        Locale locale = (Locale) session.getAttribute(ParameterAttributeType.LOCALE);
//        if (locale == null) {
//            session.setAttribute(ParameterAttributeType.LOCALE, Locale.getDefault());
//        }
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
