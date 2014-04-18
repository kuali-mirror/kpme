package org.kuali.khr.tk.clock.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.kuali.khr.hub.pages.Login;
import org.kuali.khr.hub.util.SeleniumBase;
import org.kuali.khr.tk.clock.pages.Clock;
import org.kuali.khr.tk.time_detail.pages.TimeDetail;
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
		Thread.sleep(20000);
		clock.clockOut();
		clockinInfos.add(clock.clockIn(2));
		Thread.sleep(20000);
		clock.clockOut();

	}

	@Then("the Time Detail displays the correct assignments on the correct timeblocks")
	public void displayTimeBlock() {
		time_detail.goToTimeDetail();
		Assert.assertTrue(time_detail.checkTimeBlock(clockinInfos));

		SeleniumBase.destroyDriver();

	}
}