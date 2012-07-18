package org.kuali.hr.lm.leavedonation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TkConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveDonationMaintTest extends TkTestCase{
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage lcLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_DONATION_MAINT_URL);
		lcLookup = HtmlUnitUtil.clickInputContainingText(lcLookup, "search");
		assertTrue("Page contains test Donated Account Category", lcLookup.asText().contains("dAC"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(lcLookup, "edit");
		assertTrue("Maintenance Page contains test Donated Account Category",maintPage.asText().contains("dAC"));
		assertTrue("Maintenance Page contains test Earn Codes",maintPage.asText().contains("LC-TEST2"));
	}
	
	@Test
	public void testCreatingLeaveBlocks() throws Exception {
		Date START_DATE = new Date((new DateTime(2012, 4, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		Date END_DATE = new Date((new DateTime(2012, 4, 2, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		
		List<LeaveBlock> leaveBlockList;
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("testuser1", START_DATE, END_DATE);
		assertTrue("There are leave blocks for princiapl id " + "testuser1", leaveBlockList.isEmpty());
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("testuser2", START_DATE, END_DATE);
		assertTrue("There are leave blocks for princiapl id " + "testuser2", leaveBlockList.isEmpty());
		
		List<LeaveBlockHistory> historyList = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("testuser1", null);
		assertTrue("There are leave block histories for princiapl id testuser1", historyList.isEmpty());
		historyList = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("testuser2", null);
		assertTrue("There are leave block histories for princiapl id testuser2", historyList.isEmpty());
		
		String baseUrl = TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form);
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Leave Donation - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    setFieldValue(page, "document.newMaintainableObject.donorsPrincipalID", "testuser1");
	    setFieldValue(page, "document.newMaintainableObject.donatedAccrualCategory", "testAC");	
	    setFieldValue(page, "document.newMaintainableObject.donatedEarnCode", "EC");
	    setFieldValue(page, "document.newMaintainableObject.amountDonated", "10.00");
	    
	    setFieldValue(page, "document.newMaintainableObject.recipientsPrincipalID", "testuser2");
	    setFieldValue(page, "document.newMaintainableObject.recipientsAccrualCategory", "testAC");	
	    setFieldValue(page, "document.newMaintainableObject.recipientsEarnCode", "EC");
	    setFieldValue(page, "document.newMaintainableObject.amountReceived", "8.00");
	    
	    setFieldValue(page, "document.newMaintainableObject.description", "test-donation");
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	page.asText();
	  	assertTrue("page text does not contain: ", page.asText().contains("success"));
	  	
	  	leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("testuser1", START_DATE, END_DATE);
	  	assertTrue("There should be 1 leave blocks for emplyee 'testUser1', not " + leaveBlockList.size(), leaveBlockList.size()== 1);
	  	LeaveBlock lb = leaveBlockList.get(0);
	  	assertTrue("Hours of the leave block for donor 'testuser1' should be 10, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(10)));
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("testuser2", START_DATE, END_DATE);
		assertTrue("There should be 1 leave blocks for emplyee 'testUser2', not " + leaveBlockList.size(), leaveBlockList.size()== 1);
		lb = leaveBlockList.get(0);
	  	assertTrue("Hours of the leave block for recipient 'testuser2' should be 8, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(8)));
	  	
		historyList = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("testuser1", null);
		assertTrue("There should be 1 leave block histories for princiapl id testuser1"+ historyList.size(), historyList.size()== 1);
		LeaveBlockHistory lbh = historyList.get(0);
	  	assertTrue("Hours of the leave block history for donor 'testuser1' should be 10, not " + lbh.getLeaveAmount().toString(), lbh.getLeaveAmount().equals(new BigDecimal(10)));
		historyList = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories("testuser2", null);
		assertTrue("There should be 1 leave block histories for princiapl id testuser2"+ historyList.size(), historyList.size()== 1);
		lbh = historyList.get(0);
	  	assertTrue("Hours of the leave block history for recipient 'testuser2' should be 8, not " + lbh.getLeaveAmount().toString(), lbh.getLeaveAmount().equals(new BigDecimal(8)));
	}
	
	@Test
	public void testValidation() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form);
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Leave Donation - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2012");
	    setFieldValue(page, "document.newMaintainableObject.donatedEarnCode", "EC");	//fraction allowed is 99
	  	setFieldValue(page, "document.newMaintainableObject.amountDonated", "2.45");
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertTrue("page text does not contain donated amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	  	setFieldValue(page, "document.newMaintainableObject.amountDonated", "2");
	  	page = page.getElementByName("methodToCall.route").click();
	  	assertFalse("page text contains donated amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.recipientsEarnCode", "EC");	//fraction allowed is 99
	  	setFieldValue(page, "document.newMaintainableObject.amountReceived", "3.822");
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertTrue("page text does not contain received amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	  	setFieldValue(page, "document.newMaintainableObject.amountReceived", "3");
	  	page = page.getElementByName("methodToCall.route").click();
	  	assertFalse("page text contains received amount fraction error.", page.asText().contains("Earn Code 'EC' only allows 0 decimal point."));
	}
	
	// commented out this test, KPME-1207, effectiveDate can be past, current or future
	//@Test
	/*public void testFutureEffectiveDate() throws Exception {
		this.futureEffectiveDateValidation(TkTestConstants.Urls.LEAVE_DONATION_MAINT_NEW_URL);
	}*/
}
