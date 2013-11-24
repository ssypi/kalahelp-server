package kloSpringServer;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Context listener for de-registering jdbc-drivers after servlet closing
 */
public class JdbcDriverRegistrationListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(JdbcDriverRegistrationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("Deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.error(String.format("Error deregistering driver %s", driver), e);
                throw new RuntimeException(e);
            }

        }
    }
}
