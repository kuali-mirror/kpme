package org.kuali.kpme.edo.util;

import org.apache.log4j.PropertyConfigurator;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * <p>Title: Log</p>
 * <p>Description: An object that handles HRE logging-related tasks</p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Indiana University</p>
 * @author Ailish Byrne
 */

public class Log {
    public static String LOG4J_SETTINGS_FILE_NAME = "log4j.properties";
    private static int SETTINGS_CHANGE_WATCH_INTERVAL = 1000;
    private static final String MDC_DOCUMENT_ID = "DocumentID";
    private static final String MDC_SESSION_ID = "SessionID";
    private static final String UNKNOWN_STATIC_LOG_VALUE = "UNKNOWN";

    public static void configureLogging() {
        PropertyConfigurator.configureAndWatch("/opt/java/settings/" + ConfigContext.getCurrentContextConfig().getApplicationName() + "/" + System.getProperty("env.code") + "/" + LOG4J_SETTINGS_FILE_NAME, SETTINGS_CHANGE_WATCH_INTERVAL);
    }

    protected static void configureLogging(String pathToLogSettings) {
        PropertyConfigurator.configureAndWatch(pathToLogSettings, SETTINGS_CHANGE_WATCH_INTERVAL);
    }

    public static void setStaticDocumentId(Object documentId) {
        if ((documentId != null) && !"".equals(documentId.toString().trim())) {
            org.apache.log4j.MDC.put(MDC_DOCUMENT_ID, documentId.toString());
        }
        else {
            org.apache.log4j.MDC.put(MDC_DOCUMENT_ID, UNKNOWN_STATIC_LOG_VALUE);
        }
    }

    public static String getStaticDocumentId() {
        if (org.apache.log4j.MDC.get(MDC_DOCUMENT_ID) != null) {
            return (String)org.apache.log4j.MDC.get(MDC_DOCUMENT_ID);
        }
        else {
            return null;
        }
    }

    protected static void setStaticSessionId(String sessionId) {
        if ((sessionId != null) && !"".equals(sessionId.trim())) {
            org.apache.log4j.MDC.put(MDC_SESSION_ID, sessionId);
        }
        else {
            org.apache.log4j.MDC.put(MDC_SESSION_ID, UNKNOWN_STATIC_LOG_VALUE);
        }
    }

    public static String getStaticSessionId() {
        if (org.apache.log4j.MDC.get(MDC_SESSION_ID) != null) {
            return (String)org.apache.log4j.MDC.get(MDC_SESSION_ID);
        }
        else {
            return null;
        }
    }
	public static void configureLog4j(String filePath) {
		try {
			if (filePath.contains("classpath")) {
				DefaultResourceLoader drl = new DefaultResourceLoader();
				PropertyConfigurator.configure(drl.getResource(filePath).getURL());
			} else {
				PropertyConfigurator.configureAndWatch(filePath, 1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to configure logging properly.", e);
		}
	}
}