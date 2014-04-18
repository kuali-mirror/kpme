package org.kuali.khr.hub.pages;

import static org.kuali.khr.hub.util.KhrTestConstants.BASE_URL;
import static org.kuali.khr.hub.util.KhrTestConstants.Urls.LOGOUT_URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {
	private WebDriver driver;

	public Login(WebDriver driver) {
		this.driver = driver;
	}

	public void go(String username) {

		// Open Login Page
		driver.get(LOGOUT_URL);
		driver.get(BASE_URL);

		// Enter text in login box
		driver.findElement(By.id("Rice-UserName_control")).sendKeys(username);

		// Click Login button
		driver.findElement(By.id("Rice-LoginButton")).click();
	}

}
