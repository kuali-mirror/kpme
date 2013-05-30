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
package org.kuali.hr.lm.timeoff;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.util.HrTestConstants;
import org.kuali.kpme.core.util.TKUtils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SystemScheduledTimeOffMaintTest extends KPMETestCase{
	private static final String EFFECTIVE_DATE_REQUIRED = "Effective Date (Effective Date) is a required field.";
	private static final String LEAVE_PLAN_REQUIRED = "Leave Plan (Leave Plan) is a required field.";
	private static final String ACCRUAL_CATEGORY_REQUIRED = "Accrual Category (Accrual Category) is a required field.";
	private static final String EARN_CODE_REQUIRED = "Earn Code (Earn Code) is a required field.";
	private static final String ACCRUED_DATE_REQUIRED = "Accrued Date (Accrued Date) is a required field.";
	private static final String LOCATION_REQUIRED = "Location (Location) is a required field.";
	private static final String DESCRIPTION_REQUIRED = "Description (Desc) is a required field.";
	private static final String AMOUNT_OF_TIME_REQUIRED = "Amount of Time (Amount of Time) is a required field.";
	private static final String PREMIUM_HOLIDAY_REQUIRED = "Premium Holiday (Premium Holiday) is a required field.";
	private static final String ACCRUED_DATE_PAST_ERROR = "'Accrued Date' needs to be a future date.";
	private static final String SCHEDULED_TO_DATE_PAST_ERROR = "'Scheduled Time Off Date' needs to be a future date.";
	private static final String SUCCESS_MESSAGE = "Document was successfully submitted.";
	private static final String ERROR_LEAVE_CODE = "The specified earnCode 'testLCL' does not exist";
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.TIME_OFF_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	//default value for premium holiday is no
	  	Assert.assertTrue("Preminum Holiday is not default to No", page.getHtmlElementById("document.newMaintainableObject.premiumHolidayNo").asText().equals("checked"));
	  		  	
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text does not contain:\n" + EFFECTIVE_DATE_REQUIRED, page.asText().contains(EFFECTIVE_DATE_REQUIRED));
	    Assert.assertTrue("page text does not contain:\n" + LEAVE_PLAN_REQUIRED, page.asText().contains(LEAVE_PLAN_REQUIRED));
	    Assert.assertTrue("page text does not contain:\n" + ACCRUAL_CATEGORY_REQUIRED, page.asText().contains(ACCRUAL_CATEGORY_REQUIRED));
	    Assert.assertTrue("page text does not contain:\n" + EARN_CODE_REQUIRED, page.asText().contains(EARN_CODE_REQUIRED));
	    
	    Assert.assertTrue("page text does not contain:\n" + ACCRUED_DATE_REQUIRED, page.asText().contains(ACCRUED_DATE_REQUIRED));
	    Assert.assertTrue("page text does not contain:\n" + LOCATION_REQUIRED, page.asText().contains(LOCATION_REQUIRED));
	    Assert.assertTrue("page text does not contain:\n" + DESCRIPTION_REQUIRED, page.asText().contains(DESCRIPTION_REQUIRED));
	    Assert.assertTrue("page text does not contain:\n" + AMOUNT_OF_TIME_REQUIRED, page.asText().contains(AMOUNT_OF_TIME_REQUIRED));
	    //there should be a default value for premium holiday
	    Assert.assertFalse("page text contains:\n" + PREMIUM_HOLIDAY_REQUIRED, page.asText().contains(PREMIUM_HOLIDAY_REQUIRED));
	}
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage sstoLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.TIME_OFF_MAINT_URL);
		sstoLookup = HtmlUnitUtil.clickInputContainingText(sstoLookup, "search");
		HtmlUnitUtil.createTempFile(sstoLookup);
		Assert.assertTrue("Page contains test SystemScheduledTimeOff", sstoLookup.asText().contains("TLC"));
		Assert.assertTrue("Page contains test SystemScheduledTimeOff", sstoLookup.asText().contains("EC"));
		Assert.assertFalse("Page contains test SystemScheduledTimeOff", sstoLookup.asText().contains("InactiveLP"));

		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(sstoLookup, "edit");
		Assert.assertTrue("Maintenance Page contains test SystemScheduledTimeOff",maintPage.asText().contains("EC"));	
	}
	
	@Test
	public void testErrorMessages() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.TIME_OFF_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	setFieldValue(page, "document.documentHeader.documentDescription", "System Scheduled Time Off - test");
	  	// use past dates
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2011");
	    setFieldValue(page, "document.newMaintainableObject.accruedDate", "04/01/2011");
	    setFieldValue(page, "document.newMaintainableObject.scheduledTimeOffDate", "04/01/2011");
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text does not contain:\n" + HrTestConstants.EFFECTIVE_DATE_ERROR, page.asText().contains(HrTestConstants.EFFECTIVE_DATE_ERROR));
	  	Assert.assertTrue("page text does not contain:\n" + ACCRUED_DATE_PAST_ERROR, page.asText().contains(ACCRUED_DATE_PAST_ERROR));
	  	Assert.assertTrue("page text does not contain:\n" + SCHEDULED_TO_DATE_PAST_ERROR, page.asText().contains(SCHEDULED_TO_DATE_PAST_ERROR));
	}
	
	@Test
	// test for jiar1363
	public void testGetLeavePlanAccrualCategoryFromSelectedEarnCode() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.TIME_OFF_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form); 	
	  	
	  	// add 150 days in the future, need to add dates instead of month 
	  	// because if we happen to be running the test on the 31 of a month, some future months do not have 31st 
	  	LocalDate validDate = LocalDate.now().plusDays(150);
	  	String validDateString = TKUtils.formatDate(validDate);

        setFieldValue(page, "document.documentHeader.documentDescription", "something clever...");
	  	setFieldValue(page, "document.newMaintainableObject.effectiveDate", validDateString);
	    setFieldValue(page, "document.newMaintainableObject.earnCode", "EC");
        setFieldValue(page, "document.newMaintainableObject.descr", "this is my description");
        setFieldValue(page, "document.newMaintainableObject.amountofTime", "8");
        setFieldValue(page, "document.newMaintainableObject.location", "CST");
        setFieldValue(page, "document.newMaintainableObject.accruedDate", validDateString);
	    
	    page = ((HtmlElement)page.getElementByName("methodToCall.route")).click();
	    HtmlUnitUtil.createTempFile(page);
	    Assert.assertTrue("page text contains:\n" + "testLP", page.asText().contains("testLP"));
	    Assert.assertTrue("page text contains:\n" + "testAC", page.asText().contains("testAC"));
	}
	
	@Test
	//test for jiar1363
	public void testValidateLeaveCode() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.TIME_OFF_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form); 	
	  	
	  	// add 150 days in the future, need to add dates instead of month 
	  	// because if we happen to be running the test on the 31 of a month, some future months do not have 31st
	  	LocalDate validDate = LocalDate.now().plusDays(150);
	  	String validDateString = TKUtils.formatDate(validDate);
	  	
	  	setFieldValue(page, "document.newMaintainableObject.effectiveDate", validDateString);
	  	setFieldValue(page, "document.newMaintainableObject.earnCode", "testLCL"); 
	    
	    page = ((HtmlElement)page.getElementByName("methodToCall.route")).click();
	    HtmlUnitUtil.createTempFile(page);
	    Assert.assertTrue("page text does not contain:\n" + ERROR_LEAVE_CODE, page.asText().contains(ERROR_LEAVE_CODE));
	}
}
