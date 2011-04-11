package org.kuali.hr.time.clock.web;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
