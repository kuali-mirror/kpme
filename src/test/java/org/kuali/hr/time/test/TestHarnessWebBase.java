package org.kuali.hr.time.test;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.kuali.rice.core.config.Config;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.config.spring.ConfigFactoryBean;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.resourceloader.ResourceLoader;
import org.kuali.rice.core.web.jetty.JettyServer;
import org.mortbay.jetty.webapp.WebAppClassLoader;

public class TestHarnessWebBase extends TestHarnessBase {
    
    private static final Logger LOG = Logger.getLogger(TestHarnessWebBase.class);
    
    private static final int DEFAULT_PORT = 8090;
    private static final String CONTEXT_NAME = "/tk-dev";
    private static final String WEBAPP_ROOT = "/src/main/webapp";

    private static JettyServer server;

    
    @BeforeClass
    public static void startJettyServer() throws Exception {
	ConfigFactoryBean.CONFIG_OVERRIDE_LOCATION = "classpath:META-INF/tk-test-config.xml";
	TestHarnessWebBase.server = new JettyServer(DEFAULT_PORT, CONTEXT_NAME, WEBAPP_ROOT);
	server.setFailOnContextFailure(true);
	server.setTestMode(true);
	server.start();
	// establish the GlobalResourceLoader and ConfigContext for the
	// classloader of the test harness
	addWebappsToContext();
    }

    @AfterClass
    public static void stopJettyServer() throws Exception {
	try {
	    if (server != null) {
		server.stop();
	    }
	} finally {
	    server = null;
	}
    }
    
    @Override
    @Before
    public void setUp() throws Exception {
	super.setUp();
    }

    @Override
    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }

    
    /**
     * Adds all ResourceLoaders registered to WebAppClassLoaders to the GlobalResourceLoader. 
     * Overrides the current context config with the Config registered to the (last) WebAppClassLoader
     */
    public static void addWebappsToContext() {
	for (Map.Entry<ClassLoader, Config> configEntry : ConfigContext.getConfigs()) {
	    if (configEntry.getKey() instanceof WebAppClassLoader) {
		ResourceLoader rl = GlobalResourceLoader.getResourceLoader(configEntry.getKey());
		if (rl == null) {
		    //
		    //Assert.fail("didn't find resource loader for workflow test harness web app");
		} else {
		    ConfigContext.overrideConfig(Thread.currentThread().getContextClassLoader(), configEntry.getValue());
		    GlobalResourceLoader.addResourceLoader(rl);
		}
	    }
	}
    }
}
