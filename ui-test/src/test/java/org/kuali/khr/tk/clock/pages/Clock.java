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
package org.kuali.khr.tk.clock.pages;

import static org.junit.Assert.assertEquals;
import static org.kuali.khr.hub.util.KhrTestConstants.Urls.*;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Clock {
	private WebDriver driver;

	public Clock(WebDriver driver) {
		this.driver = driver;
	}

	public void goToClock() {
		// go to clock page
		WebElement clocktab = driver.findElement(By.id("clock"));
		String clocktabtext = clocktab.getText();
		assertEquals(clocktabtext, "Clock");

		clocktab.getAttribute("href");
		WebElement link = clocktab.findElement(By.xpath(".//a"));
		link.click();
		String urlclock = driver.getCurrentUrl();
		assertEquals(urlclock, CLOCK_PAGE_URL);

		WebElement clockElement = driver.findElement(By.id("clock"));
		String clocktext = clockElement.getText();
		assertEquals(clocktext, "Clock");
	}

	public Map<String, String> clockIn(int i) {

		Map<String, String> map = new HashMap<>();

		WebElement clockInButton = driver.findElement(By.id("clock-button"));
		String clockbuttontext = clockInButton.getAttribute("value");
		if (clockbuttontext.equals("Clock Out")) {
			clockInButton.click();
		}

		Select select = new Select(driver.findElement(By.id("selectedAssignment")));
		select.selectByIndex(i);
		assertEquals(clockbuttontext, "Clock In");
		clockInButton.click();

		int rowNumber = 3;
		int colNumber = 2;
		String status = driver.findElement(By.xpath("//div[@id='clock']/table/tbody/tr[" 
						+ rowNumber + "]/td[" 
						+ colNumber + "]")).getText();
		map.put("status", status);

		String assignment = driver.findElement(By.id("assignment-value")).getText();
		map.put("assignment", assignment);

		return map;
	}

	public void clockOut() {
		WebElement clockOutButton = driver.findElement(By.id("clock-button"));
		String clockbuttontext = clockOutButton.getAttribute("value");
		if (clockOutButton.equals("Clock In")) {
			clockOutButton.click();
		}
		
		assertEquals(clockbuttontext, "Clock Out");
		clockOutButton.click();
	}
}
