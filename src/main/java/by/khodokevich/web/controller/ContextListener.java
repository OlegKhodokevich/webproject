package by.khodokevich.web.controller;

import by.khodokevich.web.exception.PoolConnectionException;
import by.khodokevich.web.model.connection.CustomConnectionPool;
import by.khodokevich.web.model.entity.RegionBelarus;
import by.khodokevich.web.model.entity.Specialization;
import by.khodokevich.web.model.entity.UserStatus;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Init context.");
        ServletContextListener.super.contextInitialized(sce);
        sce.getServletContext().setAttribute(REGION_MAP, RegionBelarus.getRegionMap());
        sce.getServletContext().setAttribute(REGION_LIST, RegionBelarus.getRegionList());
        sce.getServletContext().setAttribute(SPECIALIZATION_LIST, Specialization.getSpecializationList());
        sce.getServletContext().setAttribute(SPECIALIZATION_MAP, Specialization.getSpecializationMap());
        sce.getServletContext().setAttribute(STATUS_LIST, UserStatus.getStatusList());
        sce.getServletContext().setAttribute(SET_ARCHIVE_USERS, new HashSet<Long>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Destroy context.");

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        try {
            connectionPool.destroyPool();
        } catch (PoolConnectionException e) {
            logger.error("Can't destroy connection's pool.", e);
        }
    }
}
