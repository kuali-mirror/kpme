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
package org.kuali.kpme.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.kpme.core.rice.test.lifecycles.KPMEXmlDataLoaderLifecycle;
import org.kuali.kpme.core.util.ClearDatabaseLifecycle;
import org.kuali.kpme.core.util.DatabaseCleanupDataLifecycle;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.LoadDatabaseDataLifeCycle;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.lifecycle.BaseLifecycle;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.impl.services.CoreImplServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.rice.test.RiceInternalSuiteDataTestCase;
import org.kuali.rice.test.TestHarnessServiceLocator;
import org.kuali.rice.test.TransactionalLifecycle;
import org.springframework.cache.CacheManager;

/**
 *  Default test base for a full KPME unit test.
 */
public abstract class KPMEUnitTestCase extends RiceInternalSuiteDataTestCase {

	private static final String FILE_PREFIX = System.getProperty("user.dir") + "/../../db/src/main/config/workflow/";

	private TransactionalLifecycle transactionalLifecycle;
	
	@Override
	protected String getModuleName() {
		return "kpme";
	}

	@Override
	public void setUp() throws Exception {
	    if (System.getProperty("basedir") == null) {
	        System.setProperty("basedir", System.getProperty("user.dir") + "/");
	    }
	    
		super.setUp();

		GlobalVariables.setMessageMap(new MessageMap());
		
		final boolean needsSpring = false;
		if (needsSpring) {
			transactionalLifecycle = new TransactionalLifecycle();
			//transactionalLifecycle.setTransactionManager(KRADServiceLocatorInternal.getTransactionManager());
			transactionalLifecycle.start();
		}

	    new ClearDatabaseLifecycle().start();
	
		new LoadDatabaseDataLifeCycle(this.getClass()).start();
	
	    GlobalVariables.setUserSession(new UserSession("admin"));

	}
	
	@Override
	public void tearDown() throws Exception {
	    // runs custom SQL at the end of each test.
	    // useful for difficult to reset test additions, not handled by
	    // our ClearDatabaseLifecycle.
		HrContext.clearTargetUser();
	    new DatabaseCleanupDataLifecycle(this.getClass()).start();

		final boolean needsSpring = true;
		if (needsSpring) {
		    if ( (transactionalLifecycle != null) && (transactionalLifecycle.isStarted()) ) {
		        transactionalLifecycle.stop();
		    }
		}
		
		GlobalVariables.setMessageMap(new MessageMap());
		
		super.tearDown();
	}

    @Override
    protected List<Lifecycle> getPerTestLifecycles() {
        List<Lifecycle> lifecycles = super.getPerTestLifecycles();
        lifecycles.add(new ClearCacheLifecycle());
        return lifecycles;
    }

	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> lifecycles = super.getPerTestLifecycles();
	    lifecycles.add(new Lifecycle() {
			boolean started = false;
	
			public boolean isStarted() {
				return this.started;
			}
	
			public void start() throws Exception {
				setModuleName(getModuleName());
				setBaseDirSystemProperty(getModuleName());
				Config config = getTestHarnessConfig();
				ConfigContext.init(config);
				this.started = true;
			}
	
			public void stop() throws Exception {
				this.started = false;
			}
		});
	    /**
	     * Loads the TestHarnessSpringBeans.xml file which obtains connections to the DB for us
	     */
	    lifecycles.add(getTestHarnessSpringResourceLoader());
	
	    /**
	     * Establishes the TestHarnessServiceLocator so that it has a reference to the Spring context
	     * created from TestHarnessSpringBeans.xml
	     */
	    lifecycles.add(new BaseLifecycle() {
	        @Override
	        public void start() throws Exception {
	            TestHarnessServiceLocator.setContext(getTestHarnessSpringResourceLoader().getContext());
	            super.start();
	        }
	    });
	
	    ClearDatabaseLifecycle clearDatabaseLifecycle = new ClearDatabaseLifecycle();
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_RULE_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_RULE_RSP_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_DLGN_RSP_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_RULE_ATTR_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_RULE_TMPL_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_DOC_TYP_T");
	    lifecycles.add(clearDatabaseLifecycle);
	
	    File[] files = new File(FILE_PREFIX).listFiles();
	    if (files != null) {
	        for (File file : files) {
	            if (file.getName().endsWith(".xml")) {
	                lifecycles.add(new KPMEXmlDataLoaderLifecycle(FILE_PREFIX + file.getName()));
	            }
	        }
	    }
		return lifecycles;
	}

    public class ClearCacheLifecycle extends BaseLifecycle {
        private final Logger LOG = Logger.getLogger(ClearCacheLifecycle.class);

        @Override
        public void start() throws Exception {
            long startTime = System.currentTimeMillis();
            LOG.info("Starting cache flushing");
            List<CacheManager> cms = new ArrayList<CacheManager>(CoreImplServiceLocator.getCacheManagerRegistry().getCacheManagers());
            for (CacheManager cm : cms) {
                for (String cacheName : cm.getCacheNames()) {
                    //LOG.info("Clearing cache: " + cacheName);
                    cm.getCache(cacheName).clear();
                }
            }
            long endTime = System.currentTimeMillis();
            LOG.info("Caches cleared in " + (endTime - startTime) + "ms");
        }

        @Override
        public void stop() throws Exception {
            super.stop();
        }

    }
    
    public static String getBaseURL() {
	    return ConfigContext.getCurrentContextConfig().getProperty("application.url");
    }
    
    public static String getContext() {
    	return "/" + ConfigContext.getCurrentContextConfig().getProperty("app.context.name");
    }

    public static String getTempDir() {
	return ConfigContext.getCurrentContextConfig().getProperty("temp.dir");
    }

    public static Integer getPort() {
	return new Integer(ConfigContext.getCurrentContextConfig().getProperty("kns.test.port"));
    }

}
