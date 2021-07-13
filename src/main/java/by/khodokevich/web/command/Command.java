package by.khodokevich.web.command;

import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request);
}
