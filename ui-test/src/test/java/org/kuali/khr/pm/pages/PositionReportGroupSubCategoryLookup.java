package org.kuali.khr.pm.pages;

import static org.kuali.khr.hub.tests.selenium.SeleniumTestSuite.waitHere;
import static org.kuali.khr.hub.util.KhrTestConstants.Urls.PositionReportGroupSubCategoryLookup;

import java.util.List;

import org.kuali.khr.hub.pages.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PositionReportGroupSubCategoryLookup implements Page
{
	private WebDriver driver;

	public PositionReportGroupSubCategoryLookup(WebDriver driver) {
		this.driver = driver;
	}
	
	@FindBy(css = ".uif-headerText-span")
	public WebElement PositionReportGroupSubCategoryLookupTitle;
	
	@FindBy(css = "#u22u100012_label")
	public WebElement EffectiveDateLabel;
	
	@FindBy(css = "#u100012From_control")
	private WebElement EffectiveDateFromField;
	
	@FindBy(css = "#u100012From_control")
	private WebElement EffectiveDateToField;

	@FindBy(css = "#u100018_label")
	public WebElement GroupKeyLabel;
	
	@FindBy(css = "#u100018_control")
	private WebElement GroupKeyField;
	
	@FindBy(css = "#u100025")
	private WebElement GroupKeyLookupButton;
	
	@FindBy(css = "#u100028_label")
	public WebElement InstitutionLabel;
	
	@FindBy(css = "#u100028_control")
	private WebElement InstitutionField;
	
	@FindBy(css = "#u100035")
	private WebElement InstitutionLookupButton;
	
	@FindBy(css = "#u100038_label")
	public WebElement LocationLabel;
	
	@FindBy(css = "#u100038_control")
	private WebElement LocationField;
	
	@FindBy(css = "#u100045")
	private WebElement LocationSearchButton;
	
	
	@FindBy(css = "#u100048_label")
	public WebElement PositionReportGroupSubCategoryLabel;
	
	@FindBy(css = "#u100048_control")
	private WebElement PositionReportGroupSubCategoryField;
	
	@FindBy(css = "#u100054_label")
	public WebElement PositionReportGroupLabel;
	
	@FindBy(css = "#u100054_control")
	private WebElement PositionReportGroupField;
	
	@FindBy(css = "#u100061")
	private WebElement PositionReportGroupSearchButton;
	
	@FindBy(css = "#u100064_label")
	public WebElement PositionReportSubCategoryLabel;
	
	@FindBy(css = "#u100064_control")
	private WebElement PositionReportSubCategoryField;
	
	@FindBy(css = "#u100071")
	private WebElement PositionReportSubCategorySearchButton;
	
	@FindBy(css = "#u100074_label")
	public WebElement ShowHistoryLabel;
	
	@FindBy(css = "#u100074_control_0")
	private WebElement ShowHistoryYesBtn;
	
	@FindBy(xpath = "//fieldset[@id='u100074_fieldset']/span[2]/label")
	public WebElement ShowHistoryYesLabel;
	
	@FindBy(css = "#u100074_control_1")
	private WebElement ShowHistoryNoBtn;
	
	@FindBy(xpath = "//fieldset[@id='u100074_fieldset']/span[3]/label")
	
	public WebElement ShowHistoryNoLabel;
	
	@FindBy(css = "#u100080_label")
	public WebElement ActiveLabel;
	
	@FindBy(css = "#u100080_control_0")
	private WebElement ActiveYesBtn;
	
	@FindBy(xpath = "//fieldset[@id='u100080_fieldset']/span[2]/label")
	public WebElement ActiveYesLabel;
	
	@FindBy(css = "#u100080_control_1")
	private WebElement ActiveNoBtn;
	
	@FindBy(xpath = "//fieldset[@id='u100080_fieldset']/span[3]/label")
	public WebElement ActiveNoLabel;
	
	@FindBy(css = "#u100080_control_2")
	private WebElement ActiveBothBtn;
	
	@FindBy(xpath = "//fieldset[@id='u100080_fieldset']/span[4]/label")
	public WebElement ActiveBothLabel;
	
	@FindBy(css = "#u100006")
	public WebElement SearchButton;
	
	@FindBy(css = "#u100007")
	private WebElement ClearValuesButton;
	
	@FindBy(css = "#u100008")
	private WebElement CancelButton;
	
	// TODO set up test for calendar object and test calendar objects on this page
	
	@FindBy(tagName = "iframe_portlet_container_div")
	public WebElement iframe;
	
	@FindBy(xpath = "//th[1]/span[1]/label[@id='u100121_c1']")
	private WebElement ActionsLabel;
	
	@FindBy(css = "#u100087_info")
	private WebElement results;
	
	
	@Override
	public void gotoPage() {
		driver.get(PositionReportGroupSubCategoryLookup);
	}
	
	public void search() {
		SearchButton.click();
	}
	
	
	public void lookupGroupKey(String value) {
		ClearValuesButton.click();
		waitHere();
		GroupKeyField.sendKeys(value);
		search();
	}
	
	public void lookupPositionReportGroupSubCategory(String value) {
		ClearValuesButton.click();
		waitHere();
		PositionReportGroupSubCategoryField.sendKeys(value);
		search();
	}
	
	public String checkResults() {
		waitHere();
		String resultstring = results.getText();
		return resultstring;
	}
	
	public String getActionsLabel() {
		waitHere();
		String action = ActionsLabel.getText();
		return action;
	}
	
	public GroupKeyLookup gotoGroupKeyLookup()
	{
		waitHere();
		GroupKeyLookupButton.click();
		waitHere();
	    return PageFactory.initElements(driver, GroupKeyLookup.class);
	}
	

	

}
