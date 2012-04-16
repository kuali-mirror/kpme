package org.kuali.hr.time.test;

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
public abstract class KNSTestCase extends RiceInternalSuiteDataTestCase {

	private static final String SQL_FILE = "classpath:DefaultSuiteTestData.sql";
	private static final String XML_FILE = "classpath:DefaultSuiteTestData.xml";
	
	private String contextName = "/knstest";
	private String relativeWebappRoot = "/../kns/src/test/webapp";
    
	
	private TransactionalLifecycle transactionalLifecycle;
	
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoaderLifecycle(SQL_FILE, ";").start();
	}

	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle(XML_FILE));
		return suiteLifecycles;
	}

	@Override
	protected Lifecycle getLoadApplicationLifecycle() {
		return new BaseLifecycle() {
			public void start() throws Exception {
				new JettyServerLifecycle(getPort(), getContextName(), getRelativeWebappRoot()).start();
				super.start();
			}
		};	
	}

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
		return "kns";
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
