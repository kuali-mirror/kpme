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
package org.kuali.khr.pm.pages;

import static org.kuali.khr.hub.tests.selenium.SeleniumTestSuite.waitHere;
import static org.kuali.khr.hub.util.KhrTestConstants.Urls.PositionReportGroupSubCategoryLookup;

import org.kuali.khr.hub.pages.Lookup;
import org.kuali.khr.hub.pages.Page;
import org.kuali.khr.hub.util.Helper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PositionReportGroupSubCategoryLookup extends Lookup implements Page
{
	private WebDriver driver;

	public PositionReportGroupSubCategoryLookup(WebDriver driver) {
		this.driver = driver;
	}

	// TODO look at elements on this page that should be moved up to base class Lookup
	
	@FindBy(xpath = "//*span[matches(.,'Position Report Group Sub Category Lookup')]")
	public WebElement PositionReportGroupSubCategoryLookupTitle;
	
	@FindBy(xpath = "//*label[matches(.,'Effective Date:')]")
	public WebElement EffectiveDateLabel;

	@FindBy(xpath = "//*label[matches(.,'Group Key:')]")
	public WebElement GroupKeyLabel;
	
	@FindBy(xpath = "//input[@name='lookupCriteria[groupKeyCode]']/following-sibling::input[1]")
	public WebElement GroupKeyLookupButton;
	
	@FindBy(xpath = "//*label[matches(.,'Institution:')]")
	public WebElement InstitutionLabel;
	
	@FindBy(xpath = "//input[@name='lookupCriteria[institutionCode]']/following-sibling::input[1]")
	public WebElement InstitutionLookupButton;
	
	@FindBy(xpath = "//*label[matches(.,'Location:')]")
	public WebElement LocationLabel;
	
	@FindBy(xpath = "//input[@name='lookupCriteria[locationId]']/following-sibling::input[1]")
	public WebElement LocationLookupButton;
	
	
	@FindBy(xpath = "//*label[matches(.,'Position Report Group Sub Category:')]")
	public WebElement PositionReportGroupSubCategoryLabel;
	
	@FindBy(xpath = "//*label[matches(.,'Position Report Group:')]")
	public WebElement PositionReportGroupLabel;
	
	@FindBy(xpath = "//input[@name='lookupCriteria[positionReportGroup]']/following-sibling::input[1]")
	private WebElement PositionReportGroupLookupButton;
	
	@FindBy(xpath = "//*label[matches(.,'Position Report Sub Category:')]")
	public WebElement PositionReportSubCategoryLabel;
	
	@FindBy(name = "lookupCriteria[positionReportSubCat]")
	private WebElement PositionReportSubCategoryField;
	
	@FindBy(xpath = "//input[@name='lookupCriteria[positionReportSubCat]']/following-sibling::input[1]")
	private WebElement PositionReportSubCategoryLookupButton;
	
	@FindBy(xpath = "//*label[matches(.,'Show History:')]")
	public WebElement ShowHistoryLabel;
	
	// TODO find identifier for radio buttons
	@FindBy(id = "u100074_control_0")
	private WebElement ShowHistoryYesBtn;
	
	// TODO find identifier for radio buttons and get next sibling for label
	@FindBy(xpath = "//*label[matches(.,'')]")
	public WebElement ShowHistoryYesLabel;
	
	// TODO find identifier for radio buttons
	@FindBy(id = "u100074_control_1")
	private WebElement ShowHistoryNoBtn;
	
	// TODO find identifier for radio buttons and get next sibling for label
	@FindBy(xpath = "//*fieldset[@id='u100074_fieldset']/span[3]/label")
	public WebElement ShowHistoryNoLabel;
	
	@FindBy(xpath = "//*label[matches(.,'Active:')]")
	public WebElement ActiveLabel;
	
	// TODO find identifier for radio buttons
	@FindBy(css = "#u100080_control_0")
	private WebElement ActiveYesBtn;
	
	// TODO find identifier for radio buttons and get next sibling for label
	@FindBy(xpath = "//*fieldset[@id='u100080_fieldset']/span[2]/label")
	public WebElement ActiveYesLabel;
	
	// TODO find identifier for radio buttons
	@FindBy(css = "#u100080_control_1")
	private WebElement ActiveNoBtn;
	
	// TODO find identifier for radio buttons and get next sibling for label
	@FindBy(xpath = "//*fieldset[@id='u100080_fieldset']/span[3]/label")
	public WebElement ActiveNoLabel;
	
	// TODO find identifier for radio buttons
	@FindBy(css = "#u100080_control_2")
	private WebElement ActiveBothBtn;
	
	@FindBy(xpath = "//*label[matches(.,'Both')]")
	public WebElement ActiveBothLabel;
	
	@FindBy(xpath = "//button[contains(text(), 'Search')]")
	public WebElement SearchButton;
	
	@FindBy(xpath = "//button[contains(text(), 'Clear Values')]")
	private WebElement ClearValuesButton;
	
	@FindBy(xpath = "//button[contains(text(), 'Cancel')]")
	private WebElement CancelButton;
	
	@FindBy(xpath = "//button[contains(text(), 'Close')]")
	private WebElement CloseButton;
	

	@FindBy(xpath = "//*label[matches(.,'Group Key Id:')]")
	public WebElement GroupKeyIdLabel;
	
	@FindBy(xpath = "//*label[matches(.,'Group Key Lookup ')]")
	public WebElement GroupKeyLookupTitle;

	@FindBy(xpath = "//*label[matches(.,'Institution Lookup ')]")
	public WebElement InstitutionLookupTitle;
	
	@FindBy(xpath = "//*label[matches(.,'Institution Code:')]")
	public WebElement InstitutionCodeLabel;
	
	// TODO set up test for calendar object and test calendar objects on this page
	

	@FindBy(xpath = "//*label[matches(.,'Actions')]")
	public WebElement ResultsActionsLabel;
	
	@FindBy(xpath ="//label[matches(.,'Effective Date')]")
	public WebElement ResultsEffectiveDateLabel;
	
	@FindBy(xpath ="//*label[matches(.,'Position Report Group Sub Category ')]")
	public WebElement ResultsPositionReportGroupSubCategoryLabel;
	
	@FindBy(className = "dataTables_info")
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
	
	
	public void lookupInstitution(String value) {
		ClearValuesButton.click();
		waitHere();
		InstitutionCodeField.sendKeys(value);
		search();
	}
	

	public void switchFrame(String framename)
	{
		Helper.switchFrame(framename, driver);
	}
	

	public void switchToDefaultFrame()
	{
		Helper.switchToDefaultFrame(driver);
	}
	
	public void closeLookupLightbox()
	{
		CloseButton.click();
	}
	
	
}
