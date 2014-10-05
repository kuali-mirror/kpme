package org.kuali.kpme.edo.util;

import org.kuali.rice.core.api.config.property.ConfigContext;

public class TagSupport {
	public static final String DEPLOYMENT_STG = "stg";
	public static final String DEPLOYMENT_DEV = "dev";
	public static final String DEPLOYMENT_SND = "snd";

	public String getYear() {
		return EdoContext.getYear();
	}

	public boolean isStagingOrDevOrSnd() {
		String env = ConfigContext.getCurrentContextConfig().getProperty(
				"environment");

		return (env.equalsIgnoreCase(DEPLOYMENT_DEV) || env.equalsIgnoreCase(DEPLOYMENT_STG) || env.equalsIgnoreCase(DEPLOYMENT_SND));
	}

    public static boolean isNonProductionEnvironment() {
        String env = ConfigContext.getCurrentContextConfig().getProperty(
                "environment");

        return (env.equalsIgnoreCase(DEPLOYMENT_DEV) || env.equalsIgnoreCase(DEPLOYMENT_STG) || env.equalsIgnoreCase(DEPLOYMENT_SND));
    }
}
