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
