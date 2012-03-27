package org.kuali.hr.lm.leaveadjustment;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveAdjustmentMaintTest extends TkTestCase{
	
	private static final String PRINCIPAL_ID = "admin";
	
	private static final String EFFECTIVE_DATE_REQUIRED = "Effective Date (Effective Date) is a required field.";
	private static final String PRINCIPAL_ID_REQUIRED = "Principal Id (Principal Id) is a required field.";
	private static final String LEAVE_PLAN_REQUIRED = "Leave Plan (Leave Plan) is a required field.";
	private static final String LEAVE_CODE_REQUIRED = "Leave Code (Leave Code) is a required field.";
	private static final String ACCRUAL_CATEGORY_REQUIRED = "Accrual Category (Accrual Category) is a required field.";
	private static final String DES_REQUIRED = "Description (Description) is a required field.";
	private static final String ADJUSTMENT_AMOUNT_REQUIRED = "Adjustment Amount (Adjustment Amount) is a required field.";
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.LEAVE_ADJUSTMENT_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	assertNotNull("Could not locate submit button", input);
	  	
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertTrue("page text does not contain:\n" + EFFECTIVE_DATE_REQUIRED, page.asText().contains(EFFECTIVE_DATE_REQUIRED));
	    assertTrue("page text does not contain:\n" + PRINCIPAL_ID_REQUIRED, page.asText().contains(PRINCIPAL_ID_REQUIRED));
	    assertTrue("page text does not contain:\n" + LEAVE_PLAN_REQUIRED, page.asText().contains(LEAVE_PLAN_REQUIRED));
	    assertTrue("page text does not contain:\n" + ACCRUAL_CATEGORY_REQUIRED, page.asText().contains(ACCRUAL_CATEGORY_REQUIRED));
	    assertTrue("page text does not contain:\n" + LEAVE_CODE_REQUIRED, page.asText().contains(LEAVE_CODE_REQUIRED));
	    assertTrue("page text does not contain:\n" + DES_REQUIRED, page.asText().contains(DES_REQUIRED));
	    setFieldValue(page, "document.newMaintainableObject.adjustmentAmount", "");
	    element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	    assertTrue("page text does not contain:\n" + ADJUSTMENT_AMOUNT_REQUIRED, page.asText().contains(ADJUSTMENT_AMOUNT_REQUIRED));
	}
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage leaveAdjustmentLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_ADJUSTMENT_MAINT_URL);
		leaveAdjustmentLookup = HtmlUnitUtil.clickInputContainingText(leaveAdjustmentLookup, "search");
		System.out.println(leaveAdjustmentLookup.asXml());
		assertTrue("Page contains test LeaveAdjustment", leaveAdjustmentLookup.asText().contains("AC1"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(leaveAdjustmentLookup, "edit", "lmLeaveAdjustmentId=3000");
		assertTrue("Maintenance Page contains test LeaveAdjustment", maintPage.asText().contains("AC1"));	 
	}
	
	@Test
	public void testAddNew() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.LEAVE_ADJUSTMENT_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form);
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Leave Adjustment - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2011");
	    setFieldValue(page, "document.newMaintainableObject.principalId", "test");
	    setFieldValue(page, "document.newMaintainableObject.accrualCategory", "myAC");	//nonexist accrual catetory
	    setFieldValue(page, "document.newMaintainableObject.leaveCode", "myLC");	//nonexist leave Code
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	HtmlUnitUtil.createTempFile(page);
	  	assertTrue("page text contains:\n" + "'myAC' does not exist", page.asText().contains("'myAC' does not exist"));
	  	  	
	  	setFieldValue(page, "document.newMaintainableObject.accrualCategory", "AC1"); // existing accrual category
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertFalse("page text contains:\n" + "'AC1' does not exist", page.asText().contains("'AC1' does not exist"));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.principalId", PRINCIPAL_ID); // existing principal hr attributes
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertFalse("page text contains:\n" + "'IU-SM' does not exist", page.asText().contains("'IU-SM' does not exist"));
	}
	
	@Test
	public void testFutureEffectiveDate() throws Exception {
		this.futureEffectiveDateValidation(TkTestConstants.Urls.LEAVE_ADJUSTMENT_MAINT_NEW_URL);
	}
}
