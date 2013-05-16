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
package org.kuali.hr.time.clock.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

@Ignore
public class ClockSeleniumTest extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8090/");
		try {
			selenium.start();
		} catch (Exception e) {
		}
	}

	@Ignore
	public void testClockSelenium() throws Exception {
		selenium.open("/tk-dev/Clock.do");
		selenium.type("__login_user", "admin");
		selenium.click("//input[@name='login']");
		selenium.waitForPageToLoad("30000");
		
		//total hours exceeds the original hours
		selenium.click("//input[@name='distributeTime']");
		selenium.waitForPopUp("distributePopup", "30000");
		selenium.selectWindow("distributePopup");  //select the new window
		selenium.getBodyText();
		selenium.click("//input[@name='editTimeBlock']");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@name='addTimeBlock']");  // add two time blocks
		selenium.click("//input[@name='addTimeBlock']");
		selenium.click("//input[@name='saveTimeBlock']");
		assertTrue(selenium.isTextPresent("Total Hours entered not equel to the hours of the original time block"));
		
		// new time blocks has overlaps with existing time blocks
		selenium.type("beginTimeField", "08:15 AM");
		selenium.close();  // close the new window
		selenium.selectWindow(null); // go back to the original window
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
