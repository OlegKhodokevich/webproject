package by.khodokevich.web.controller.listener;

import by.khodokevich.web.model.connection.CustomConnectionPool;
import by.khodokevich.web.model.connection.TimerPoolConnectionProvider;
import by.khodokevich.web.model.entity.Region;
import by.khodokevich.web.model.entity.Revoke;
import by.khodokevich.web.model.entity.Specialization;
import by.khodokevich.web.model.entity.UserStatus;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Timer;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * This class-listener sets common attributes to ServletContext and fills connection's pool during initialization.
 * This class destroy pool during destroying ServletContext.
 *
 * @author Oleg Khodokevich
 */
@WebListener
public class ContextListenerImpl implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ServletContextListener.class);
    private static final int DELAY = 60 * 60 * 1000;
    private static final int PERIOD = 8 * 60 * 60 * 1000;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Init context.");
        ServletContextListener.super.contextInitialized(sce);
        sce.getServletContext().setAttribute(REGION_MAP, Region.getRegionMap());
        sce.getServletContext().setAttribute(REGION_LIST, Region.getRegionList());
        sce.getServletContext().setAttribute(SPECIALIZATION_LIST, Specialization.getSpecializationList());
        sce.getServletContext().setAttribute(SPECIALIZATION_MAP, Specialization.getSpecializationMap());
        sce.getServletContext().setAttribute(STATUS_LIST, UserStatus.getStatusList());
        sce.getServletContext().setAttribute(SET_ARCHIVE_USERS, new HashSet<Long>());
        sce.getServletContext().setAttribute(MARK_LIST, Revoke.getMarkList());

        CustomConnectionPool.getInstance();
        TimerPoolConnectionProvider timerPoolConnectionProvider = new TimerPoolConnectionProvider();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerPoolConnectionProvider, DELAY, PERIOD);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Destroy context.");

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        connectionPool.destroyPool();
    }
}
