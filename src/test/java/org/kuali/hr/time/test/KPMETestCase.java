package org.kuali.hr.time.test;

import java.io.File;
import java.util.List;

import org.kuali.rice.core.api.lifecycle.BaseLifecycle;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.krad.service.KRADServiceLocatorInternal;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.rice.test.RiceInternalSuiteDataTestCase;
import org.kuali.rice.test.TransactionalLifecycle;
import org.kuali.rice.test.lifecycles.JettyServerLifecycle;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;
import org.kuali.rice.test.lifecycles.SQLDataLoaderLifecycle;


/**
 * Default test base for a full KNS enabled unit test.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public abstract class KPMETestCase extends RiceInternalSuiteDataTestCase {

	private static final String SQL_FILE = "classpath:DefaultSuiteTestData.sql";
	private static final String XML_FILE = "classpath:DefaultSuiteTestData.xml";
	
	private String contextName = "/knstest";
	private String relativeWebappRoot = "/../kns/src/test/webapp";
    
	
	private TransactionalLifecycle transactionalLifecycle;



	public void setUp() throws Exception {
		super.setUp();
		final boolean needsSpring = false;
		GlobalVariables.setMessageMap(new MessageMap());
		if (needsSpring) {
			transactionalLifecycle = new TransactionalLifecycle();
			transactionalLifecycle.setTransactionManager(KRADServiceLocatorInternal.getTransactionManager());
			transactionalLifecycle.start();
		}
	}

    /**
     * maven will set this property and find resources from the config based on it. This makes eclipse testing work because
     * we have to put the basedir in our config files in order to find things when testing from maven
     */
    @Override
    protected void setBaseDirSystemProperty(String moduleBaseDir) {
        if (System.getProperty("basedir") == null) {
            final String userDir = System.getProperty("user.dir");
            //String basedir = userDir + ((userDir.endsWith(File.separator + moduleBaseDir)) ? "" : File.separator + moduleBaseDir);
            //basedir = basedir.endsWith(File.separator) ? basedir.substring(0, basedir.length() - 1) : basedir;
            System.setProperty("basedir", userDir);
        }
    }
	
	public void tearDown() throws Exception {
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
	protected String getModuleName() {
		return "kpme";
	}

	protected String getContextName() {
		return contextName;
	}

	protected void setContextName(String contextName) {
		this.contextName = contextName;
	}

	protected int getPort() {
		return HtmlUnitUtil.getPort();
	}

	protected String getRelativeWebappRoot() {
		return relativeWebappRoot;
	}

	protected void setRelativeWebappRoot(String relativeWebappRoot) {
		this.relativeWebappRoot = relativeWebappRoot;
	}

}
