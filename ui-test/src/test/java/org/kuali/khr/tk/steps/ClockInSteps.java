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
package org.kuali.khr.tk.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.kuali.khr.hub.pages.Login;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.tk.pages.Clock;
import org.kuali.khr.tk.pages.TimeDetail;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ClockInSteps {
	private Login login;
	private Clock clock;
	private TimeDetail time_detail;
	private String username = "timecoll";
	private List<Map<String, String>> clockinInfos = new ArrayList<>();
	WebDriver driver;

	public ClockInSteps() {
		driver = SeleniumBase.getDriver();
		login = new Login(driver);
		clock = new Clock(driver);
		time_detail = new TimeDetail(driver);
	}

	@Given("that a clock user with multiple assignments logs into Time Managment")
	public void goLogin() {
		login.go(username);

	}

	@When("she clocks in to multiple assignments and clocks out of the assignments")
	public void clockInClockOut() throws InterruptedException {
		clock.goToClock();
		clockinInfos.add(clock.clockIn(1));
//		Thread.sleep(20000);
		clock.clockOut();
		clockinInfos.add(clock.clockIn(2));
//		Thread.sleep(20000);
		clock.clockOut();

	}

	@Then("the Time Detail displays the correct assignments on the correct timeblocks")
	public void displayTimeBlock() {
		time_detail.goToTimeDetail();
		Assert.assertTrue(time_detail.checkTimeBlock(clockinInfos));

		SeleniumBase.destroyDriver();

	}
}