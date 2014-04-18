package org.kuali.khr.hub.pages;

import static org.kuali.khr.hub.util.KhrTestConstants.BASE_URL;
import static org.kuali.khr.hub.util.KhrTestConstants.Urls.LOGOUT_URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage implements Page {
	private WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(name = "login_user")
	private WebElement userField;

	@FindBy(id = "Rice-LoginButton")
	private WebElement loginBtn;

	public void gotoPage() {
		// Open Login Page
		driver.get(LOGOUT_URL);
		driver.get(BASE_URL);
	}

	public void login(String username) {
		enterLogin(username);
		clickLoginButton();
	}

	public void enterLogin(String username) {
		userField.clear();
		userField.sendKeys(username);
	}

	public void clickLoginButton() {
		loginBtn.click();
	}

}
