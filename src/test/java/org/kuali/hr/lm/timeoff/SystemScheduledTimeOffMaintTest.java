package org.kuali.hr.lm.timeoff;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SystemScheduledTimeOffMaintTest extends TkTestCase{
	private static final String EFFECTIVE_DATE_REQUIRED = "Effective Date (Effective Date) is a required field.";
	private static final String LEAVE_PLAN_REQUIRED = "Leave Plan (Leave Plan) is a required field.";
	private static final String ACCRUAL_CATEGORY_REQUIRED = "Accrual Category (Accrual Category) is a required field.";
	private static final String LEAVE_CODE_REQUIRED = "Leave Code (Leave Code) is a required field.";
	private static final String ACCRUED_DATE_REQUIRED = "Accrued Date (Accrued Date) is a required field.";
	private static final String LOCATION_REQUIRED = "Location (Location) is a required field.";
	private static final String DESCRIPTION_REQUIRED = "Description (Desc) is a required field.";
	private static final String AMOUNT_OF_TIME_REQUIRED = "Amount of Time (Amount of Time) is a required field.";
	private static final String PREMIUM_HOLIDAY_REQUIRED = "Premium Holiday (Premium Holiday) is a required field.";
	private static final String ACCRUED_DATE_PAST_ERROR = "'Accrued Date' needs to be a future date.";
	private static final String SCHEDULED_TO_DATE_PAST_ERROR = "'Scheduled Time Off Date' needs to be a future date.";
	private static final String EXPIRATION_DATE_FUTURE_ERROR = "'Expiration Date' must be a future date that is NOT more than a year away from current date.";
	private static final String SUCCESS_MESSAGE = "Document was successfully submitted.";
	private static final String ERROR_LEAVE_CODE = "The specified leaveCode 'testLCL' does not exist";
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.TIME_OFF_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	assertNotNull("Could not locate submit button", input);
	  	//default value for premium holiday is no
	  	assertTrue("Preminum Holiday is not default to No", page.getHtmlElementById("document.newMaintainableObject.premiumHolidayNo").asText().equals("checked"));
	  		  	
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertTrue("page text does not contain:\n" + EFFECTIVE_DATE_REQUIRED, page.asText().contains(EFFECTIVE_DATE_REQUIRED));
	    assertTrue("page text does not contain:\n" + LEAVE_PLAN_REQUIRED, page.asText().contains(LEAVE_PLAN_REQUIRED));
	    assertTrue("page text does not contain:\n" + ACCRUAL_CATEGORY_REQUIRED, page.asText().contains(ACCRUAL_CATEGORY_REQUIRED));
	    assertTrue("page text does not contain:\n" + LEAVE_CODE_REQUIRED, page.asText().contains(LEAVE_CODE_REQUIRED));
	    
	    assertTrue("page text does not contain:\n" + ACCRUED_DATE_REQUIRED, page.asText().contains(ACCRUED_DATE_REQUIRED));
	    assertTrue("page text does not contain:\n" + LOCATION_REQUIRED, page.asText().contains(LOCATION_REQUIRED));
	    assertTrue("page text does not contain:\n" + DESCRIPTION_REQUIRED, page.asText().contains(DESCRIPTION_REQUIRED));
	    assertTrue("page text does not contain:\n" + AMOUNT_OF_TIME_REQUIRED, page.asText().contains(AMOUNT_OF_TIME_REQUIRED));
	    //there should be a default value for premium holiday
	    assertFalse("page text contains:\n" + PREMIUM_HOLIDAY_REQUIRED, page.asText().contains(PREMIUM_HOLIDAY_REQUIRED));
	}
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage sstoLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.TIME_OFF_MAINT_URL);
		sstoLookup = HtmlUnitUtil.clickInputContainingText(sstoLookup, "search");
		HtmlUnitUtil.createTempFile(sstoLookup);
		assertTrue("Page contains test SystemScheduledTimeOff", sstoLookup.asText().contains("TLC"));
		assertFalse("Page contains test SystemScheduledTimeOff", sstoLookup.asText().contains("InactiveLP"));
		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(sstoLookup, "edit");
		assertTrue("Maintenance Page contains test SystemScheduledTimeOff",maintPage.asText().contains("TLC"));	
	}
	
	@Test
	public void testErrorMessages() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.TIME_OFF_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form);
	  	setFieldValue(page, "document.documentHeader.documentDescription", "System Scheduled Time Off - test");
	  	// use past dates
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2011");
	    setFieldValue(page, "document.newMaintainableObject.accruedDate", "04/01/2011");
	    setFieldValue(page, "document.newMaintainableObject.scheduledTimeOffDate", "04/01/2011");
	    setFieldValue(page, "document.newMaintainableObject.expirationDate", "04/01/2011");
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertTrue("page text does not contain:\n" + TkTestConstants.EFFECTIVE_DATE_ERROR, page.asText().contains(TkTestConstants.EFFECTIVE_DATE_ERROR));
	  	assertTrue("page text does not contain:\n" + ACCRUED_DATE_PAST_ERROR, page.asText().contains(ACCRUED_DATE_PAST_ERROR));
	  	assertTrue("page text does not contain:\n" + SCHEDULED_TO_DATE_PAST_ERROR, page.asText().contains(SCHEDULED_TO_DATE_PAST_ERROR));
	  	assertTrue("page text does not contain:\n" + EXPIRATION_DATE_FUTURE_ERROR, page.asText().contains(EXPIRATION_DATE_FUTURE_ERROR));	  	
	}
	
	@Test
	// test for jiar1363
	public void testGetLeavePlanAccrualCategoryFromSelectedLeaveCode() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.TIME_OFF_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form); 	
	  	
	  	Calendar validDate = Calendar.getInstance();
	  	// add 150 days in the future, need to add dates instead of month 
	  	// because if we happen to be running the test on the 31 of a month, some future months do not have 31st 
	  	validDate.add(Calendar.DATE, 150);   	
	  	String validDateString = new SimpleDateFormat("MM/dd/yyyy").format(validDate.getTime());
	  	
	  	setFieldValue(page, "document.newMaintainableObject.effectiveDate", validDateString);
	    setFieldValue(page, "document.newMaintainableObject.leaveCode", "testLC"); 
	    
	    page = page.getElementByName("methodToCall.route").click();
	    HtmlUnitUtil.createTempFile(page);
	    assertTrue("page text contains:\n" + "testLP", page.asText().contains("testLP"));
	    assertTrue("page text contains:\n" + "testAC", page.asText().contains("testAC"));
	}
	
	@Test
	//test for jiar1363
	public void testValidateLeaveCode() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.TIME_OFF_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form); 	
	  	
	  	Calendar validDate = Calendar.getInstance();
	  	// add 150 days in the future, need to add dates instead of month 
	  	// because if we happen to be running the test on the 31 of a month, some future months do not have 31st 
	  	validDate.add(Calendar.DATE, 150);   	
	  	String validDateString = new SimpleDateFormat("MM/dd/yyyy").format(validDate.getTime());
	  	
	  	setFieldValue(page, "document.newMaintainableObject.effectiveDate", validDateString);
	  	setFieldValue(page, "document.newMaintainableObject.leaveCode", "testLCL"); 
	    
	    page = page.getElementByName("methodToCall.route").click();
	    HtmlUnitUtil.createTempFile(page);
	    assertTrue("page text does not contain:\n" + ERROR_LEAVE_CODE, page.asText().contains(ERROR_LEAVE_CODE));
	}
}
