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

}
