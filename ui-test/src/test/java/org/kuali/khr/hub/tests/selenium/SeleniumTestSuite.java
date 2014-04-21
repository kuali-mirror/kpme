package org.kuali.khr.hub.tests.selenium;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.pm.tests.selenium.LookupPositionUIT;
import org.kuali.khr.pm.tests.selenium.PositionReportGroupSubCategoryLookupUIT;

@RunWith(Suite.class)
@SuiteClasses({ 
	LookupPositionUIT.class,
	PositionReportGroupSubCategoryLookupUIT.class
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
	
	public static void waitHere() {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
