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
