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
package org.kuali.hr.time.shiftdiff.rule;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ShiftDifferentialRuleMaintTest extends KPMETestCase{
	private static final String TEST_CODE="_T";
		
	private static final BigDecimal TEST_NO=new BigDecimal(2);
	private static String shiftDifferentialRuleId;
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.SHIFT_DIFFERENTIAL_RULE_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	// the following fields should have default values
	  	String errorMessage = "Min. Hours (Min. Hours) is a required field.";
	  	Assert.assertFalse("page text contains:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage = "Begin Time (00:00 AM) (Begin Time) is a required field.";
	    Assert.assertFalse("page text contains:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage = "End Time (00:00 AM) (End Time) is a required field.";
	    Assert.assertFalse("page text contains:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage = "Max. Gap Minutes (Max. Gap Minutes) is a required field.";
	    Assert.assertFalse("page text contains:\n" + errorMessage, page.asText().contains(errorMessage));
	  	
	    //remove the default values
	  	setFieldValue(page, "document.newMaintainableObject.minHours", "");
	  	setFieldValue(page, "document.newMaintainableObject.beginTime", "");
	  	setFieldValue(page, "document.newMaintainableObject.endTime", "");
	  	setFieldValue(page, "document.newMaintainableObject.maxGap", "");
	  	element = page.getElementByName("methodToCall.route");
	  	HtmlPage nextPage = element.click();
	  	errorMessage = "Effective Date (Effective Date) is a required field.";
	  	Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Location (Location) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Sal. Group (Sal. Group) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Pay Grade (Pay Grade) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Earn Code (Earn Code) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "From Earn Group (From Earn Group) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Begin Time (00:00 AM) (Begin Time) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "End Time (00:00 AM) (End Time) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Max. Gap Minutes (Max. Gap Minutes) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Pay Calendar Group (Pay Calendar Group) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Min. Hours (Min. Hours) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	}


	@Override
	public void setUp() throws Exception {
		super.setUp();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        Date testDate = new Date(yesterday.getTimeInMillis());
		ShiftDifferentialRule shiftDifferentialRule = new ShiftDifferentialRule();
		shiftDifferentialRule.setActive(true);
		//shiftDifferentialRule.setBeginTime(TEST_TIME);
		shiftDifferentialRule.setEarnCode(TEST_CODE);
		shiftDifferentialRule.setEffectiveDate(testDate);
		//shiftDifferentialRule.setEndTime(TEST_TIME);
		shiftDifferentialRule.setLocation(TEST_CODE);
		shiftDifferentialRule.setMaxGap(new BigDecimal(2));
		shiftDifferentialRule.setMinHours(TEST_NO);
		shiftDifferentialRule.setPayGrade(TEST_CODE);	
		shiftDifferentialRule.setPyCalendarGroup("BW-CAL");
		shiftDifferentialRule.setSunday(true);	
		shiftDifferentialRule.setMonday(true);
		shiftDifferentialRule.setTuesday(true);
		shiftDifferentialRule.setWednesday(true);
		shiftDifferentialRule.setThursday(true);
		shiftDifferentialRule.setFriday(true);
		shiftDifferentialRule.setSaturday(true);
		shiftDifferentialRule.setPyCalendarGroup("TEST");
		
		ShiftDifferentialRule rule = KRADServiceLocator.getBusinessObjectService().save(shiftDifferentialRule);
		shiftDifferentialRuleId = rule.getTkShiftDiffRuleId();
	}

	@Override
	public void tearDown() throws Exception {				
		ShiftDifferentialRule shiftDifferentialRuleObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(ShiftDifferentialRule.class, shiftDifferentialRuleId);		 
		KRADServiceLocator.getBusinessObjectService().delete(shiftDifferentialRuleObj);
		super.tearDown();
	}
}

