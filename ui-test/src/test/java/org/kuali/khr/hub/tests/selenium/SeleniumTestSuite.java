/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.khr.hub.tests.selenium;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.pm.tests.selenium.PositionLookupUIT;
import org.kuali.khr.pm.tests.selenium.PositionReportGroupSubCategoryLookupUIT;

@RunWith(Suite.class)
@SuiteClasses({ 
	PositionLookupUIT.class,
	PositionReportGroupSubCategoryLookupUIT.class})
public class SeleniumTestSuite
{
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
