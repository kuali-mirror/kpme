package org.kuali.kpme.edo.base.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.edo.util.Log;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;
import org.kuali.rice.core.web.listener.KualiInitializeListener;
import org.kuali.rice.kew.api.KewApiConstants;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContextEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: lfox
 * Date: 8/27/12
 * Time: 9:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoInitializeListener extends KualiInitializeListener {
    private static final Logger LOG = Logger.getLogger(EdoInitializeListener.class);

    public static String ALTERNATE_LOG4J_FILE = null;

    private static final String DEFAULT_SPRING_BEANS_REPLACEMENT_VALUE = "${bootstrap.spring.file}";
    private static final String WEB_BOOTSTRAP_SPRING_FILE = "web.bootstrap.spring.file";

    private XmlWebApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        configureInitialLogging();
        // Set env.code from param, if present, dev if not.
        // Add some settings to allow for dynamic environment prefix changes.
        if (System.getProperty("env.code") == null) {
            System.setProperty("env.code", "dev");
        }
        long startInit = System.currentTimeMillis();
        LOG.info("Initializing Kuali Rice Application...");

        List<String> configLocations = new ArrayList<String>();
        String additionalConfigLocations = System.getProperty(KewApiConstants.ADDITIONAL_CONFIG_LOCATIONS_PARAM);
        if (!StringUtils.isBlank(additionalConfigLocations)) {
            String[] additionalConfigLocationArray = additionalConfigLocations.split(",");
            for (String additionalConfigLocation : additionalConfigLocationArray) {
                configLocations.add(additionalConfigLocation);
            }
        }

        String bootstrapSpringBeans = "";
        if (!StringUtils.isBlank(System.getProperty(WEB_BOOTSTRAP_SPRING_FILE))) {
            bootstrapSpringBeans = System.getProperty(WEB_BOOTSTRAP_SPRING_FILE);
        } else if (!StringUtils.isBlank(sce.getServletContext().getInitParameter(WEB_BOOTSTRAP_SPRING_FILE))) {
            String bootstrapSpringInitParam = sce.getServletContext().getInitParameter(WEB_BOOTSTRAP_SPRING_FILE);
            // if the value comes through as ${bootstrap.spring.beans}, we ignore it
            if (!DEFAULT_SPRING_BEANS_REPLACEMENT_VALUE.equals(bootstrapSpringInitParam)) {
                bootstrapSpringBeans = bootstrapSpringInitParam;
                LOG.info("Found bootstrap Spring Beans file defined in servlet context: " + bootstrapSpringBeans);
            }
        }

        Properties baseProps = new Properties();
        try  {
            baseProps.putAll(getContextParameters(sce.getServletContext()));
            baseProps.putAll(System.getProperties());
            JAXBConfigImpl config = new JAXBConfigImpl(baseProps);
            ConfigContext.init(config);

        } catch (Throwable t){
            LOG.error("Error caught in init "+t);
        }


        context = new XmlWebApplicationContext();
        if (!StringUtils.isEmpty(bootstrapSpringBeans)) {
            context.setConfigLocation(bootstrapSpringBeans);
        }
        context.setServletContext(sce.getServletContext());

        try {
            context.refresh();
        } catch (RuntimeException e) {
            LOG.error("problem during context.refresh()", e);

            throw e;
        }

        context.start();
        long endInit = System.currentTimeMillis();
        LOG.info("...Kuali Rice Application successfully initialized, startup took " + (endInit - startInit) + " ms.");

    }


    public void configureInitialLogging() {
        if (StringUtils.isBlank(ALTERNATE_LOG4J_FILE)) {
            Log.configureLog4j("classpath:log4j.properties");
        } else {
            Log.configureLog4j(ALTERNATE_LOG4J_FILE);
        }
    }
}
