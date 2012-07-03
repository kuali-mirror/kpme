package org.kuali.hr.time;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.web.format.DateFormatter;
import org.kuali.rice.core.web.format.Formatter;
import org.kuali.rice.core.web.listener.KualiInitializeListener;

public class ApplicationInitializeListener extends KualiInitializeListener {
	
    private static Logger LOG = Logger.getLogger(ApplicationInitializeListener.class);
    //private RiceConfigurer rice;
    public static String ALTERNATE_LOG4J_FILE = null;
    
    public void contextInitialized(ServletContextEvent servletContextEvent) {
	TkConfiguration.baseApplicationSetup();
    	servletContextEvent.getServletContext().setAttribute("TkConstants", new TkConstants());
        LOG.info("Started contextInitialized(ServletContextEvent servletContextEvent) Method");
        try{
            //TkServiceLocator.start();
        } catch(Exception e){
		LOG.error("Failed to start TK app lifecycle", e);
		throw new RuntimeException("Failed to start TK app lifecycle", e);
        }
        super.contextInitialized(servletContextEvent);
        Formatter.registerFormatter(java.util.Date.class, DateFormatter.class);
        LOG.info("\n\n\n\n\nTimekeeping started.  Have a nice day :)\n\n\n\n\n\n");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.info("Started contextDestroyed(ServletContextEvent servletContextEvent) Method");
        try {
	    TkServiceLocator.stop();
	} catch (Exception e) {
		LOG.error("Failed to stop TK app lifecycle", e);
		throw new RuntimeException("Failed to stop TK app lifecycle", e);
	}
        super.contextDestroyed(servletContextEvent);
//    	if (rice != null) {
//    		try {
//    		    rice.stop();
//    		} catch (Exception e) {
//    			LOG.error("Failed to shutdown Rice and Workflow.", e);
//    		}
//    	}
//    	rice = null;
        LOG.info("Finished contextDestroyed(ServletContextEvent servletContextEvent) Method");
        LogManager.shutdown();
    }

}