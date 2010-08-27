package org.kuali.hr.time.web;

import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;


public class UICalendarTest extends SeleneseTestCase {

	private static final String BASE_URL = "http://localhost:8080/tk-dev/";
	private Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox3 C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe", BASE_URL);
	private SeleniumServer seleniumServer;

	public void setUp() throws Exception {
		seleniumServer = new SeleniumServer();
	    seleniumServer.start();
	    selenium.start();
	}

	public void tearDown() throws Exception {
		 selenium.stop();
	     seleniumServer.stop();
	}

	public void testCalendar() throws Exception {

		selenium.open(BASE_URL + "TimeDetail.do");

		selenium.click("//input[@name='login']");
		selenium.waitForPageToLoad("5000");
		selenium.mouseDownAt("css=td.fc-day1", "0,0");
		selenium.mouseUpAt("css=td.fc-day1", "0,0");
		selenium.type("beginTimeField", "08:00 AM");
		selenium.type("endTimeField", "05:00 PM");
		selenium.click("acrossDays");
		selenium.click("//div[11]/button[1]");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (selenium.isTextPresent("HRMS Java Team : RGNX")) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		selenium.mouseOver("//a[contains(@id,'delete-link')]");
		selenium.clickAt("//a[contains(@id,'delete-link')]", "0,0");
	}
}

