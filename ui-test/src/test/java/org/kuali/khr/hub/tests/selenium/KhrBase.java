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
