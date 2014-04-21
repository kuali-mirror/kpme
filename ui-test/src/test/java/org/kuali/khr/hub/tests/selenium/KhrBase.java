package org.kuali.khr.hub.tests.selenium;

import org.kuali.khr.hub.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.kuali.khr.hub.util.SeleniumBase;

public class KhrBase {
	private static WebDriver driver = SeleniumBase.getDriver();
	private static LoginPage login;
	
	public static void login(String username)
	{
		login = PageFactory.initElements(driver, LoginPage.class);
		login.gotoPage();
		login.login(username);
	}

}
