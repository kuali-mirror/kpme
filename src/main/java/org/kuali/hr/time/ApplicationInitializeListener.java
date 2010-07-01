package org.kuali.hr.time;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.config.RiceConfigurer;
import org.springframework.web.context.ContextLoaderListener;

public class ApplicationInitializeListener extends ContextLoaderListener implements ServletContextListener {
	
    private static Logger LOG = Logger.getLogger(ApplicationInitializeListener.class);
    private RiceConfigurer rice;
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
//        ConfigContext.getCurrentContextConfig().setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()));
//        servletContextEvent.getServletContext().setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, Context.getApplicationContext());
//        servletContextEvent.getServletContext().setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, Context.getLocaleResolver());
//        servletContextEvent.getServletContext().setAttribute(Context.getLocalizationContextName(), Context.getLocalizationContext());
//
//        HredocServiceLocator.getInstance().setApplicationContext((ConfigurableApplicationContext)Context.getApplicationContext());
//        IUServiceLocator.getInstance().overrideApplicationContext(Context.getApplicationContext());
//        try{
//        	TKServiceLocator.getConfigService().verifyDataSources();
//        	TKServiceLocator.getConfigService().setupMessageFetcher();
//        }
//        catch(Exception e){
//        	throw new RuntimeException(e);
//        } 
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
    	if (rice != null) {
    		try {
    		    rice.stop();
    		} catch (Exception e) {
    			LOG.error("Failed to shutdown Rice and Workflow.", e);
    		}
    	}
    	rice = null;
        LOG.info("Finished contextDestroyed(ServletContextEvent servletContextEvent) Method");
        LogManager.shutdown();
    }

}