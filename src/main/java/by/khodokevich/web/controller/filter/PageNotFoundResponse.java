package by.khodokevich.web.controller.filter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

public class PageNotFoundResponse extends HttpServletResponseWrapper {
    public PageNotFoundResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        this.setStatus(sc);
    }
}
