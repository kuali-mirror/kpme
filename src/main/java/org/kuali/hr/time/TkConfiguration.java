package org.kuali.hr.time;

import java.util.TimeZone;

import org.apache.log4j.PropertyConfigurator;
import org.kuali.hr.time.util.TkConstants;
import org.springframework.core.io.DefaultResourceLoader;

public class TkConfiguration {

    public static void baseApplicationSetup() {
	TkConfiguration.setTimeZone();
	TkConfiguration.configureInitialLogging();
    }

    public static void setTimeZone() {
	// TODO: Fix this -- For now we are using default system time zone.
	//TimeZone.setDefault(TkConstants.GMT_TIME_ZONE);
    }

    public static void configureInitialLogging() {
	configureLog4j("classpath:log4j_default.properties");
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
