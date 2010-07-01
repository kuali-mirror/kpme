package org.kuali.hr.time.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.kuali.rice.test.TransactionalLifecycle;
 
public class TestHarnessBase extends Assert {

    
    private static final Logger LOG = Logger.getLogger(TestHarnessBase.class);
    protected List<String> reports = new ArrayList<String>();
    @Rule public TestName testName = new TestName();
    protected TransactionalLifecycle transactionalLifecycle;

    @Before
    public void setUp() throws Exception {
	logBeforeRun();
	transactionalLifecycle = new TransactionalLifecycle();
	transactionalLifecycle.start();
    }

    @After
    public void tearDown() throws Exception {
	logAfterRun();
	try {
	    if (transactionalLifecycle != null) {
		transactionalLifecycle.stop();
	    }
	} finally {
	    transactionalLifecycle = null;
	}
    }

    protected void logBeforeRun() {
	LOG.info("##############################################################");
	LOG.info("# Starting test " + getFullTestName() + "...");
	LOG.info("# " + dumpMemory());
	LOG.info("##############################################################");
    }

    protected void logAfterRun() {
	LOG.info("##############################################################");
	LOG.info("# ...finished test " + getFullTestName());
	LOG.info("# " + dumpMemory());
	for (final String report : this.reports) {
	    LOG.info("# " + report);
	}
	LOG.info("##############################################################\n\n\n");
    }

    protected void report(final String report) {
	this.reports.add(report);
    }

    protected String getFullTestName() {
	return getClass().getSimpleName() + "." + getTestName();
    }
    
    public String getTestName() {
	return (testName != null ? testName.getMethodName() : "null");
    }

    protected String dumpMemory() {
	final long total = Runtime.getRuntime().totalMemory();
	final long free = Runtime.getRuntime().freeMemory();
	final long max = Runtime.getRuntime().maxMemory();
	return "[Memory] max: " + max + ", total: " + total + ", free: " + free;
    }

}
