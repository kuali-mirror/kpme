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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Lookup
{
	
	@FindBy(name = "lookupCriteria[rangeLowerBoundKeyPrefix_effectiveDate]")
	protected WebElement EffectiveDateFromField;
	
	@FindBy(name = "lookupCriteria[effectiveDate]")
	protected WebElement EffectiveDateToField;
	
	@FindBy(name = "lookupCriteria[groupKeyCode]")
	protected WebElement GroupKeyField;
	
	@FindBy(name = "lookupCriteria[institutionCode]")
	protected WebElement InstitutionCodeField;
	
	@FindBy(name = "lookupCriteria[institutionCode]")
	protected WebElement InstitutionField;
	
	@FindBy(name = "lookupCriteria[locationId]")
	protected WebElement LocationField;
	
	@FindBy(name = "lookupCriteria[pstnRptGrpSubCat]")
	protected WebElement PositionReportGroupSubCategoryField;
	
	@FindBy(name = "lookupCriteria[positionReportGroup]")
	protected WebElement PositionReportGroupField;

    @FindBy(xpath = "//button[contains(text(), 'Search')]")
    public WebElement SearchButton;

    @FindBy(xpath = "//button[contains(text(), 'Clear Values')]")
    protected WebElement ClearValuesButton;

    @FindBy(xpath = "//button[contains(text(), 'Cancel')]")
    protected WebElement CancelButton;

    @FindBy(xpath = "//button[contains(text(), 'Close')]")
    protected WebElement CloseButton;

}
