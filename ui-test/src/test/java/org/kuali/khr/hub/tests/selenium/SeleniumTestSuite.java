package org.kuali.khr.hub.tests.selenium;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.pm.tests.selenium.LookupPositionUIT;

@RunWith(Suite.class)
@SuiteClasses({ 
	LookupPositionUIT.class 
	})
public class SeleniumTestSuite {

	public static Test suite() {
		TestSuite testsuite = new TestSuite();
		return testsuite;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SeleniumBase.destroyDriver();
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}
