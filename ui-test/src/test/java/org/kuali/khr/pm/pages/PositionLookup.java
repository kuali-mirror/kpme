package org.kuali.khr.pm.pages;

import static org.kuali.khr.hub.util.KhrTestConstants.Urls.POSITION_LOOKUP_URL;
import static org.kuali.khr.hub.tests.selenium.SeleniumTestSuite.waitHere;
import org.kuali.khr.hub.pages.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PositionLookup implements Page {

	private WebDriver driver;

	public PositionLookup(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(id = "u100012_control")
	private WebElement PositionNumberField;

	@FindBy(id = "u100006")
	private WebElement SearchButton;

	@FindBy(id = "u100007")
	private WebElement ClearValuesButton;

	@FindBy(id = "u100103_info")
	private WebElement results;

	public void lookupPositionNumber(String value) {
		ClearValuesButton.click();
		waitHere();
		PositionNumberField.sendKeys(value);
		search();
	}

	public void search() {
		SearchButton.click();
	}

	public String checkResults() {
		waitHere();
		String resultstring = results.getText();
		return resultstring;
	}

	@Override
	public void gotoPage() {
		driver.get(POSITION_LOOKUP_URL);
	}



}
