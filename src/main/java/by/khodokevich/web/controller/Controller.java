package by.khodokevich.web.controller;

import by.khodokevich.web.controller.command.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Controller the main controller which manage commands.
 * This class define command and execute it.
 *
 * @author Oleg Khodokevich
 */
@WebServlet(name = "Controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(Controller.class);
    private final CommandProvider commandProvider = CommandProvider.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Start controller.");
        logger.debug(request.getParameter(ParameterAttributeType.COMMAND));

        String commandName = request.getParameter(ParameterAttributeType.COMMAND);
        logger.debug("commandName = " + commandName);
        Command command = commandProvider.getCommand(commandName);

        Router router = command.execute(request);
        switch (router.routerType()) {
            case REDIRECT -> response.sendRedirect(router.pagePath());
            case FORWARD -> {
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.pagePath());
                dispatcher.forward(request, response);
            }
            default -> {
                logger.error("Router type don't specify or it is unknown.");
                response.sendRedirect(PagePath.ERROR_PAGE);
            }
        }
    }
}
