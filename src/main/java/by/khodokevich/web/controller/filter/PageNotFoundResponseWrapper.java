package by.khodokevich.web.controller.filter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;


/**
 * This class serves PageNotFoundFilter
 * This class wraps up response and override method sendError.
 * This method invoke after chain.
 *
 * @author Oleg Khodokevich
 */
public class PageNotFoundResponseWrapper extends HttpServletResponseWrapper {
    public PageNotFoundResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        this.setStatus(sc);
    }
}
