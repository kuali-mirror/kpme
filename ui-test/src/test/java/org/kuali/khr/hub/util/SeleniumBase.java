package org.kuali.khr.hub.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumBase {

	private static WebDriver driver;

	private SeleniumBase() {
		driver = new FirefoxDriver();
	}

	public static WebDriver getDriver() {

		if (driver == null) {
			new SeleniumBase();
		}
		return driver;
	}
	
	public static void destroyDriver()
	{
		driver.quit();
		driver = null;
	}
}
