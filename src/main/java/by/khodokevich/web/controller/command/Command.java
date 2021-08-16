package by.khodokevich.web.controller.command;

import jakarta.servlet.http.HttpServletRequest;

/**
 * This is function interface for all commands.
 */
@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request);
}
