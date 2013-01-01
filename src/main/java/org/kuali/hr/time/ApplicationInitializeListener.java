/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
		    //TkServiceLocator.stop();
		} catch (Exception e) {
			LOG.error("Failed to stop TK app lifecycle", e);
			throw new RuntimeException("Failed to stop TK app lifecycle", e);
		}
        super.contextDestroyed(servletContextEvent);
        LOG.info("Finished contextDestroyed(ServletContextEvent servletContextEvent) Method");
        LogManager.shutdown();
    }

}