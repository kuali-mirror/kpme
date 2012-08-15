package org.kuali.hr.lm.leavedonation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TkConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveDonationMaintTest extends KPMETestCase{
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage lcLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_DONATION_MAINT_URL);
		lcLookup = HtmlUnitUtil.clickInputContainingText(lcLookup, "search");
		Assert.assertTrue("Page contains test Donated Account Category", lcLookup.asText().contains("dAC"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(lcLookup, "edit");
		Assert.assertTrue("Maintenance Page contains test Donated Account Category",maintPage.asText().contains("dAC"));
		Assert.assertTrue("Maintenance Page contains test Earn Codes",maintPage.asText().contains("LC-TEST2"));
	}
	
	@Test
	public void testCreatingLeaveBlocks() throws Exception {
		Date START_DATE = new Date((new DateTime(2012, 4, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		Date END_DATE = new Date((new DateTime(2012, 4, 2, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		
		List<LeaveBlock> leaveBlockList;
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("10001", START_DATE, END_DATE);
		Assert.assertTrue("There are leave blocks for principal id " + "10001", leaveBlockList.isEmpty());
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("10003", START_DATE, END_DATE);
		Assert.assertTrue("There are leave blocks for principal id " + "10003", leaveBlockList.isEmpty());
		
		List<LeaveBlockHistory> historyList = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("10001", null);
		Assert.assertTrue("There are leave block histories for princiapl id 10001", historyList.isEmpty());
		historyList = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("10003", null);
		Assert.assertTrue("There are leave block histories for princiapl id 10003", historyList.isEmpty());
		
		String baseUrl = TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Leave Donation - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    setFieldValue(page, "document.newMaintainableObject.donorsPrincipalID", "10001");
	    setFieldValue(page, "document.newMaintainableObject.donatedAccrualCategory", "testAC");	
	    setFieldValue(page, "document.newMaintainableObject.donatedEarnCode", "EC");
	    setFieldValue(page, "document.newMaintainableObject.amountDonated", "10");
	    
	    setFieldValue(page, "document.newMaintainableObject.recipientsPrincipalID", "10003");
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
	  	
	  	leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("10001", START_DATE, END_DATE);
	  	Assert.assertTrue("There should be 1 leave blocks for emplyee '10001', not " + leaveBlockList.size(), leaveBlockList.size()== 1);
	  	LeaveBlock lb = leaveBlockList.get(0);
	  	Assert.assertTrue("Hours of the leave block for donor '10001' should be -10, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(-10)));
	  	Assert.assertTrue("Request status of the leave block for donor '10001' should be Approved, not " + lb.getRequestStatus()
	  			, lb.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED));
	  	
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("10003", START_DATE, END_DATE);
		Assert.assertTrue("There should be 1 leave blocks for emplyee '10003', not " + leaveBlockList.size(), leaveBlockList.size()== 1);
		lb = leaveBlockList.get(0);
		Assert.assertTrue("Hours of the leave block for recipient '10003' should be 8, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(8)));
		Assert.assertTrue("Request status of the leave block for donor '10003' should be Approved, not " + lb.getRequestStatus()
	  			, lb.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED));
	  	
		historyList = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("10001", null);
		Assert.assertTrue("There should be 1 leave block histories for princiapl id 10001"+ historyList.size(), historyList.size()== 1);
		LeaveBlockHistory lbh = historyList.get(0);
		Assert.assertTrue("Hours of the leave block history for donor '10001' should be -10, not " + lbh.getLeaveAmount().toString(), lbh.getLeaveAmount().equals(new BigDecimal(-10)));
		Assert.assertTrue("Request status of the leave block history for donor '10001' should be Approved, not " + lbh.getRequestStatus()
	  			, lbh.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED));
		historyList = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("10003", null);
		Assert.assertTrue("There should be 1 leave block histories for princiapl id 10003"+ historyList.size(), historyList.size()== 1);
		lbh = historyList.get(0);
		Assert.assertTrue("Hours of the leave block history for recipient '10003' should be 8, not " + lbh.getLeaveAmount().toString(), lbh.getLeaveAmount().equals(new BigDecimal(8)));
		Assert.assertTrue("Request status of the leave block history for donor '10003' should be Approved, not " + lbh.getRequestStatus()
	  			, lbh.getRequestStatus().equals(LMConstants.REQUEST_STATUS.APPROVED));
	}
	
	@Test
	public void testValidation() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
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
	  	page = page.getElementByName("methodToCall.route").click();
	  	Assert.assertFalse("page text contains donated amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.recipientsEarnCode", "EC");	//fraction allowed is 99
	  	setFieldValue(page, "document.newMaintainableObject.amountReceived", "3.822");
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertTrue("page text does not contain received amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	  	setFieldValue(page, "document.newMaintainableObject.amountReceived", "3");
	  	page = page.getElementByName("methodToCall.route").click();
	  	Assert.assertFalse("page text contains received amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	}
	
	// commented out this test, KPME-1207, effectiveDate can be past, current or future
	//@Test
	/*public void testFutureEffectiveDate() throws Exception {
		this.futureEffectiveDateValidation(TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL);
	}*/
}
