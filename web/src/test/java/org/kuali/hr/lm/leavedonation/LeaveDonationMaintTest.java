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
package org.kuali.hr.lm.leavedonation;

import java.math.BigDecimal;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.tklm.common.TKUtils;
import org.kuali.hr.tklm.leave.LMConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.block.LeaveBlockHistory;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
public class LeaveDonationMaintTest extends KPMETestCase{
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage lcLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.LEAVE_DONATION_MAINT_URL);
		lcLookup = HtmlUnitUtil.clickInputContainingText(lcLookup, "search");
		Assert.assertTrue("Page contains test Donated Account Category", lcLookup.asText().contains("dAC"));
		Assert.assertFalse("Page should not contain edit action", lcLookup.asText().contains("edit"));
	}
	
	@Test
	public void testCreatingLeaveBlocks() throws Exception {
		DateTime START_DATE = new DateTime(2012, 4, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime END_DATE = new DateTime(2012, 4, 2, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		
		List<LeaveBlock> leaveBlockList;
		leaveBlockList = LmServiceLocator.getLeaveBlockService().getLeaveBlocks("testuser1", START_DATE.toLocalDate(), END_DATE.toLocalDate());
		Assert.assertTrue("There are leave blocks for principal id " + "testuser1", leaveBlockList.isEmpty());
		leaveBlockList = LmServiceLocator.getLeaveBlockService().getLeaveBlocks("testuser2", START_DATE.toLocalDate(), END_DATE.toLocalDate());
		Assert.assertTrue("There are leave blocks for principal id " + "testuser2", leaveBlockList.isEmpty());
		
		List<LeaveBlockHistory> historyList = LmServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("10001", null);
		Assert.assertTrue("There are leave block histories for princiapl id testuser1", historyList.isEmpty());
		historyList = LmServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("testuser2", null);
		Assert.assertTrue("There are leave block histories for princiapl id testuser2", historyList.isEmpty());
		
		String baseUrl = TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Leave Donation - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    setFieldValue(page, "document.newMaintainableObject.donorsPrincipalID", "testuser1");
	    setFieldValue(page, "document.newMaintainableObject.donatedAccrualCategory", "testAC");	
	    setFieldValue(page, "document.newMaintainableObject.donatedEarnCode", "EC");
	    setFieldValue(page, "document.newMaintainableObject.amountDonated", "10");
	    
	    setFieldValue(page, "document.newMaintainableObject.recipientsPrincipalID", "testuser2");
	    setFieldValue(page, "document.newMaintainableObject.recipientsAccrualCategory", "testAC");	
	    setFieldValue(page, "document.newMaintainableObject.recipientsEarnCode", "EC");
	    setFieldValue(page, "document.newMaintainableObject.amountReceived", "8");
	    
	    setFieldValue(page, "document.newMaintainableObject.description", "test-donation");
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	page.asText();
	  	Assert.assertTrue("page text does not contain: success ", page.asText().contains("success"));
	  	
	  	leaveBlockList = LmServiceLocator.getLeaveBlockService().getLeaveBlocks("testuser1", START_DATE.toLocalDate(), END_DATE.toLocalDate());
	  	Assert.assertTrue("There should be 1 leave blocks for emplyee 'testuser1', not " + leaveBlockList.size(), leaveBlockList.size()== 1);
	  	LeaveBlock lb = leaveBlockList.get(0);
	  	Assert.assertTrue("Hours of the leave block for donor 'testuser1' should be -10, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(-10)));
	  	Assert.assertTrue("Request status of the leave block for donor 'testuser1' should be Approved, not " + lb.getRequestStatus()
	  			, lb.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED));
	  	
		leaveBlockList = LmServiceLocator.getLeaveBlockService().getLeaveBlocks("testuser2", START_DATE.toLocalDate(), END_DATE.toLocalDate());
		Assert.assertTrue("There should be 1 leave blocks for emplyee 'testuser2', not " + leaveBlockList.size(), leaveBlockList.size()== 1);
		lb = leaveBlockList.get(0);
		Assert.assertTrue("Hours of the leave block for recipient 'testuser2' should be 8, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(8)));
		Assert.assertTrue("Request status of the leave block for donor 'testuser2' should be Approved, not " + lb.getRequestStatus()
	  			, lb.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED));
	  	
		historyList = LmServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("testuser1", null);
		Assert.assertTrue("There should be 1 leave block histories for princiapl id testuser1"+ historyList.size(), historyList.size()== 1);
		LeaveBlockHistory lbh = historyList.get(0);
		Assert.assertTrue("Hours of the leave block history for donor 'testuser1' should be -10, not " + lbh.getLeaveAmount().toString(), lbh.getLeaveAmount().equals(new BigDecimal(-10)));
		Assert.assertTrue("Request status of the leave block history for donor 'testuser1' should be Approved, not " + lbh.getRequestStatus()
	  			, lbh.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED));
		historyList = LmServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("testuser2", null);
		Assert.assertTrue("There should be 1 leave block histories for princiapl id testuser2"+ historyList.size(), historyList.size()== 1);
		lbh = historyList.get(0);
		Assert.assertTrue("Hours of the leave block history for recipient 'testuser2' should be 8, not " + lbh.getLeaveAmount().toString(), lbh.getLeaveAmount().equals(new BigDecimal(8)));
		Assert.assertTrue("Request status of the leave block history for donor 'testuser2' should be Approved, not " + lbh.getRequestStatus()
	  			, lbh.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED));
	}
	
	@Test
	public void testValidation() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Leave Donation - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    setFieldValue(page, "document.newMaintainableObject.donatedEarnCode", "EC");	//fraction allowed is 99
	  	setFieldValue(page, "document.newMaintainableObject.amountDonated", "2.45");
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text does not contain donated amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	  	setFieldValue(page, "document.newMaintainableObject.amountDonated", "2");
	  	page = ((HtmlElement)page.getElementByName("methodToCall.route")).click();
	  	Assert.assertFalse("page text contains donated amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.recipientsEarnCode", "EC");	//fraction allowed is 99
	  	setFieldValue(page, "document.newMaintainableObject.amountReceived", "3.822");
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text does not contain received amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	  	setFieldValue(page, "document.newMaintainableObject.amountReceived", "3");
	  	page = ((HtmlElement)page.getElementByName("methodToCall.route")).click();
	  	Assert.assertFalse("page text contains received amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	}
	
	// commented out this test, KPME-1207, effectiveDate can be past, current or future
	//@Test
	/*public void testFutureEffectiveDate() throws Exception {
		this.futureEffectiveDateValidation(TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL);
	}*/
}
