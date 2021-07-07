package by.khodokevich.web.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;


@WebFilter(urlPatterns = {"/controller"}, initParams = {
        @WebInitParam(name = "characterEncoding", value = "UTF-8", description = "Encoding Param")})
public class CharsetFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("characterEncoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
