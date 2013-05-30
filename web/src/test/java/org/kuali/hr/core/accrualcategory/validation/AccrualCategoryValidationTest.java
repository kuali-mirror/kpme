/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.core.accrualcategory.validation;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.hr.core.HrTestConstants;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.accrualcategory.validation.AccrualCategoryValidation;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class AccrualCategoryValidationTest extends KPMETestCase{
	private static final String ACCRUAL_CATEGORY = "testAC";
	private static final String ERROR_LEAVE_PLAN = "The specified leavePlan 'IU-SM-W' does not exist";
	@Test
	public void testValidateStartEndUnits() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains '" + ACCRUAL_CATEGORY +"'", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlUnitUtil.createTempFile(accrualCategoryLookup);
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		Assert.assertTrue("Maintenance Page contains test AccrualCategory",maintPage.asText().contains(ACCRUAL_CATEGORY));
	}
	
	@Test
	public void testMinWorkPercentageField() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains '" + ACCRUAL_CATEGORY +"'", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		HtmlUnitUtil.createTempFile(maintPage);
		Assert.assertTrue("Maintenance Page contains 'Min Percent Worked to Earn Accrual'",maintPage.asText().contains("Min Percent Worked to Earn Accrual"));
	}
	
	// KPME-1347 Kagata
	@Test
	public void testAccrualIntervalEarnField() throws Exception {
		
		// make sure page loads
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains test Accrual Category", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		//HtmlUnitUtil.createTempFile(maintPage);
		// Bi-weekly was removed from the list and replaced by pay calendar
		Assert.assertFalse("Maintenance Page contains 'Bi-Weekly'",maintPage.asText().contains("Bi-Weekly"));
		Assert.assertTrue("Maintenance Page contains 'Pay Calendar'",maintPage.asText().contains("Pay Calendar"));
	}
	
	// KPME-1347 Kagata 
	@Test
	public void testDefaultLeaveCodeField() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains '" + ACCRUAL_CATEGORY +"'", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		//HtmlUnitUtil.createTempFile(maintPage);
		Assert.assertTrue("Maintenance Page contains 'Default Earn Code'",maintPage.asText().contains("Default Earn Code"));
	}
	
	@Test
	public void testHasRules() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		HtmlPage accrualCategoryCreate = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "AccrualCategory");
		//HtmlUnitUtil.createTempFile(accrualCategoryCreate);
		Assert.assertTrue("Page contains 'Accrual Category Maintenance'", accrualCategoryCreate.asText().contains("Accrual Category Maintenance"));
		Assert.assertTrue("Maintenance Page contains 'Category Has Rules'", accrualCategoryCreate.asText().contains("Category Has Rules"));
	}
	@Test
	public void testMaxBalFlag() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		HtmlPage accrualCategoryCreate = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "AccrualCategory");
		//HtmlUnitUtil.createTempFile(accrualCategoryCreate);
		Assert.assertTrue("Page contains 'New Accrual Category Rule'", accrualCategoryCreate.asText().contains("New Accrual Category Rule"));
		Assert.assertTrue("Maintenance Page contains 'Max Bal Flag'", accrualCategoryCreate.asText().contains("Max Bal Flag"));
	}
	//@Test
	/*public void testValidateStartEndUnitsForErrorMessages() throws Exception {		
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		assertTrue("Page contains test Accrual Category", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		assertTrue("Maintenance Page contains test AccrualCategory",maintPage.asText().contains(ACCRUAL_CATEGORY));
		
		HtmlForm form = maintPage.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", maintPage);
	  	
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_KPME1252");

		setFieldValue(maintPage, "document.newMaintainableObject.effectiveDate", "04/01/2011");
		
        HtmlInput inputForStartUnit = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.start");
        inputForStartUnit.setValueAttribute("10");

        HtmlInput inputForEndUnit = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.end");
        inputForEndUnit.setValueAttribute("24");

        HtmlInput inputForAccrualRate = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.accrualRate");
        inputForAccrualRate.setValueAttribute("0.5");

        HtmlInput inputForMaxBalance = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.maxBalance");
        inputForMaxBalance.setValueAttribute("300");
        
        HtmlCheckBoxInput checkbox = (HtmlCheckBoxInput) HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.active");
        checkbox.setChecked(true);
        
	  	HtmlElement element = maintPage.getElementByName("methodToCall.route");
	  	maintPage = element.click();
	  	HtmlUnitUtil.createTempFile(maintPage);
        HtmlPage resultantPageAfterEdit = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");
        
        HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
        
		assertTrue("Maintenance Page contains test startEndOverLapErrormessage", resultantPageAfterEdit.asText().contains("Start and End units should not have gaps or overlaps."));
	}*/
	
	@Test
	public void testValidationOfLeavePlan() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains test Accrual Category", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		Assert.assertTrue("Maintenance Page contains test AccrualCategory",maintPage.asText().contains(ACCRUAL_CATEGORY));
		
		HtmlForm form = maintPage.getFormByName("KualiForm");
		Assert.assertNotNull("Search form was missing from page.", maintPage);
	  	
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_KPME1355");

		setFieldValue(maintPage, "document.newMaintainableObject.leavePlan", "IU-SM-W");
	
		maintPage = ((HtmlElement)maintPage.getElementByName("methodToCall.route")).click();
	    HtmlUnitUtil.createTempFile(maintPage);
	    Assert.assertTrue("page text does not contain:\n" + ERROR_LEAVE_PLAN, maintPage.asText().contains(ERROR_LEAVE_PLAN));
	}
	
	@Test
	public void testValidateAccrualRules() {
		List<AccrualCategoryRule> accrualCategoryRules = new ArrayList<AccrualCategoryRule>();
		AccrualCategoryRule rule1 = new AccrualCategoryRule();
		rule1.setStart(0L);
		rule1.setEnd(50L);
		accrualCategoryRules.add(rule1);
		AccrualCategoryRule rule2 = new AccrualCategoryRule();
		rule2.setStart(60L);	// gap
		rule2.setEnd(999L);
		accrualCategoryRules.add(rule2);
		
		boolean valid = new AccrualCategoryValidation().validateAccrualRulesGapOverlap(accrualCategoryRules);
		Assert.assertFalse("There should be gap error", valid);
		
		rule2.setStart(50L);	// correct
		valid = new AccrualCategoryValidation().validateAccrualRulesGapOverlap(accrualCategoryRules);
		Assert.assertTrue("There should not be any error", valid);
		
		rule2.setStart(45L);	//overlap
		valid = new AccrualCategoryValidation().validateAccrualRulesGapOverlap(accrualCategoryRules);
		Assert.assertFalse("There should be overlap error", valid);
	}
	
}
