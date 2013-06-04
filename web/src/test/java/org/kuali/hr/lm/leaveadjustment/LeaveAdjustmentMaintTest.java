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
package org.kuali.hr.lm.leaveadjustment;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.tklm.utils.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveAdjustmentMaintTest extends KPMEWebTestCase{
    private static final Logger LOG = Logger.getLogger(LeaveAdjustmentMaintTest.class);
	private static final String PRINCIPAL_ID = "admin";
	
	private static final String EFFECTIVE_DATE_REQUIRED = "Effective Date (Effective Date) is a required field.";
	private static final String PRINCIPAL_ID_REQUIRED = "Principal Id (Principal Id) is a required field.";
	private static final String LEAVE_PLAN_REQUIRED = "Leave Plan (Leave Plan) is a required field.";
	private static final String EARN_CODE_REQUIRED = "Earn Code (Earn Code) is a required field.";
	private static final String ACCRUAL_CATEGORY_REQUIRED = "Accrual Category (Accrual Category) is a required field.";
	private static final String DES_REQUIRED = "Description (Description) is a required field.";
	private static final String ADJUSTMENT_AMOUNT_REQUIRED = "Adjustment Amount (Adjustment Amount) is a required field.";
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.LEAVE_ADJUSTMENT_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text does not contain:\n" + EFFECTIVE_DATE_REQUIRED, page.asText().contains(EFFECTIVE_DATE_REQUIRED));
	  	Assert.assertTrue("page text does not contain:\n" + PRINCIPAL_ID_REQUIRED, page.asText().contains(PRINCIPAL_ID_REQUIRED));
	  	Assert.assertTrue("page text does not contain:\n" + LEAVE_PLAN_REQUIRED, page.asText().contains(LEAVE_PLAN_REQUIRED));
	  	Assert.assertTrue("page text does not contain:\n" + ACCRUAL_CATEGORY_REQUIRED, page.asText().contains(ACCRUAL_CATEGORY_REQUIRED));
	  	Assert.assertTrue("page text does not contain:\n" + EARN_CODE_REQUIRED, page.asText().contains(EARN_CODE_REQUIRED));
	  	Assert.assertTrue("page text does not contain:\n" + DES_REQUIRED, page.asText().contains(DES_REQUIRED));
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.adjustmentAmount", "");
	    element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text does not contain:\n" + ADJUSTMENT_AMOUNT_REQUIRED, page.asText().contains(ADJUSTMENT_AMOUNT_REQUIRED));
	}
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage leaveAdjustmentLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.LEAVE_ADJUSTMENT_MAINT_URL);
		leaveAdjustmentLookup = HtmlUnitUtil.clickInputContainingText(leaveAdjustmentLookup, "search");
		LOG.debug(leaveAdjustmentLookup.asXml());
		Assert.assertTrue("Page contains test LeaveAdjustment", leaveAdjustmentLookup.asText().contains("AC1"));
		Assert.assertFalse("Page should not contain edit action", leaveAdjustmentLookup.asText().contains("edit")); 
	}
	
	@Test
	public void testAddNew() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.LEAVE_ADJUSTMENT_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.documentHeader.documentDescription", "Leave Adjustment - test");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.principalId", "admin");
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.accrualCategory", "myAC");	//nonexist accrual catetory
	    HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.earnCode", "myLC");	//nonexist leave Code
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	HtmlUnitUtil.createTempFile(page);
	  	Assert.assertTrue("page text contains:\n" + "'myAC' does not exist", page.asText().contains("'myAC' does not exist"));
	  	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.accrualCategory", "AC1"); // existing accrual category
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.earnCode", "testLC");
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains:\n" + "'AC1' does not exist", page.asText().contains("'AC1' does not exist"));
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.principalId", "admin"); // existing principal hr attributes
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains:\n" + "'IU-SM' does not exist", page.asText().contains("'IU-SM' does not exist"));
	  	
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.earnCode", "EC");	//fraction allowed is 99.9
	  	HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.adjustmentAmount", "2.45");
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text does not contain amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 1 decimal point."));
	}
}
