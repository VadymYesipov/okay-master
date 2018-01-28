package ua.khpi.yesipov.project.listeners;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        log("Servlet context initialization starts");

        ServletContext servletContext = sce.getServletContext();
        initLog4J(servletContext);
        log("Servlet context initialization finished");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        log("Servlet context destruction starts");
        // do nothing
        log("Servlet context destruction finished");
    }

    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }

    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext.getRealPath(
                    "WEB-INF/log4j.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        log("Log4J initialization finished");
    }
}
