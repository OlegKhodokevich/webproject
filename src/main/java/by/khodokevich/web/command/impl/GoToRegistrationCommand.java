package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.*;
import by.khodokevich.web.entity.RegionBelarus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.command.ParameterAttributeType.*;

public class GoToRegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToRegistrationCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(REGIONS, RegionBelarus.getRegions());
        logger.debug(RegionBelarus.getRegions());
        Router router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);


        return router;
    }

}
