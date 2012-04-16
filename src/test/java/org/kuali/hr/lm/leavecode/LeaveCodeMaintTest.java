package org.kuali.hr.lm.leavecode;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveCodeMaintTest extends TkTestCase{
	private static final String SUCCESS_MESSAGE = "Document was successfully submitted.";
	private static final String ERROR_MESSAGE_ALLOW_NEGATIVE_ACC_BAN_REQUIRED = "Allow Negative Accrual Balance (Allow Negative Accrual Balance) is a required field";//KPME-1350
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage lcLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_CODE_MAINT_URL);
		lcLookup = HtmlUnitUtil.clickInputContainingText(lcLookup, "search");
		Assert.assertTrue("Page contains test Leave Code", lcLookup.asText().contains("testLC"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(lcLookup, "edit");
		Assert.assertTrue("Maintenance Page contains test Leave Code",maintPage.asText().contains("testLC"));	 
	}
	
	@Test
	public void testFutureEffectiveDate() throws Exception {
		this.futureEffectiveDateValidation(TkTestConstants.Urls.LEAVE_CODE_MAINT_NEW_URL);
	}

	@Test
	public void testGetLeavePlanBasedOnAccrualCategory() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.LEAVE_CODE_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	Assert.assertTrue("page text contains:\n" + "Leave Code Maintenance", page.asText().contains("Leave Code Maintenance"));
	  	Assert.assertTrue("page text contains:\n" + "Allow Scheduled Leave", page.asText().contains("Allow Scheduled Leave"));
	  	
	  	Calendar validDate = Calendar.getInstance();
	  	validDate.add(java.util.Calendar.MONTH, 5); // 5 month in the future
	  	String validDateString = Integer.toString(validDate.get(Calendar.MONTH)) + "/" + Integer.toString(validDate.get(Calendar.DAY_OF_MONTH)) 
	  		+ "/" + Integer.toString(validDate.get(Calendar.YEAR)); 
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Leave Code - test");
	    setFieldValue(page, "document.newMaintainableObject.defaultAmountofTime", "25"); // a wrong default amount of time
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", validDateString); // jira1360
	    setFieldValue(page, "document.newMaintainableObject.accrualCategory", "AC1"); // jira1360
	    
	    HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	HtmlUnitUtil.createTempFile(page);
	  	Assert.assertTrue("page text contains:\n" + "should be between 0 and 24", page.asText().contains("should be between 0 and 24"));
	  	Assert.assertTrue("page text contains:\n" + "IU-SM", page.asText().contains("IU-SM"));
	}
}
