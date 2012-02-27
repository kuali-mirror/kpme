package org.kuali.hr.lm.employeeoverride;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class EmployeeOverrideMaintTest extends TkTestCase{
	private static final String EFFECTIVE_DATE_REQUIRED = "Effective Date (Effective Date) is a required field.";
	private static final String PRINCIPAL_ID_REQUIRED = "Principal Id (Principal Id) is a required field.";
	private static final String LEAVE_PLAN_REQUIRED = "Leave Plan (Leave Plan) is a required field.";
	private static final String ACCRUAL_CATEGORY_REQUIRED = "Accrual Category (Accrual Category) is a required field.";
	private static final String OVERRIDE_TYPE_REQUIRED = "Override Type (Override Type) is a required field.";
	private static final String AC_NOT_FOUND = "The specified Accrual Category 'test' does not exist.";
	private static final String PHA_NOT_FOUND = "The specified Principal HR Attributes 'test' does not exist.";
	private static final String LEAVE_PLAN_INCONSISTENT = "The specified principal id and Accrual Category have different leave plans.";
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.EMPLOYEE_OVERRIDE_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	assertNotNull("Could not locate submit button", input);
	  	
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertFalse("page text contains: Incident Report", page.asText().contains("Incident Report"));
	  	assertTrue("Errors number is wrong", page.asText().contains("7 error(s) found on page."));
	  	assertTrue("page text does not contain:\n" + EFFECTIVE_DATE_REQUIRED, page.asText().contains(EFFECTIVE_DATE_REQUIRED));
	    assertTrue("page text does not contain:\n" + PRINCIPAL_ID_REQUIRED, page.asText().contains(PRINCIPAL_ID_REQUIRED));
	    assertTrue("page text does not contain:\n" + LEAVE_PLAN_REQUIRED, page.asText().contains(LEAVE_PLAN_REQUIRED));
	    assertTrue("page text does not contain:\n" + ACCRUAL_CATEGORY_REQUIRED, page.asText().contains(ACCRUAL_CATEGORY_REQUIRED));
	    assertTrue("page text does not contain:\n" + OVERRIDE_TYPE_REQUIRED, page.asText().contains(OVERRIDE_TYPE_REQUIRED));
	}
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage eoLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EMPLOYEE_OVERRIDE_MAINT_URL);
		eoLookup = HtmlUnitUtil.clickInputContainingText(eoLookup, "search");
		assertTrue("Page contains test EmployeeOverride", eoLookup.asText().contains("testAC"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(eoLookup, "edit");
		assertTrue("Maintenance Page contains test EmployeeOverride",maintPage.asText().contains("test"));	 
	}
	
	@Test
	public void testAddNew() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.EMPLOYEE_OVERRIDE_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form);
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Employee Override - test");
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2011");
	    setFieldValue(page, "document.newMaintainableObject.principalId", "test");	// nonexist principal HR attributes
	    setFieldValue(page, "document.newMaintainableObject.accrualCategory", "test");	//nonexist accrual catetory
	    setFieldValue(page, "document.newMaintainableObject.overrideTypeMax Transfer Amount", "on");
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertTrue("page text does not contain:\n" + AC_NOT_FOUND, page.asText().contains(AC_NOT_FOUND));
	  	assertTrue("page text does not contain:\n" + PHA_NOT_FOUND, page.asText().contains(PHA_NOT_FOUND));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.principalId", "111"); // existing principal HR Attributes
	  	setFieldValue(page, "document.newMaintainableObject.accrualCategory", "testAC2"); // existing accrual category, inconsistent leave plan
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertFalse("page text contains:\n" + AC_NOT_FOUND, page.asText().contains(AC_NOT_FOUND));
	  	assertFalse("page text contains:\n" + PHA_NOT_FOUND, page.asText().contains(PHA_NOT_FOUND));
	  	assertTrue("page text does not contain:\n" + LEAVE_PLAN_INCONSISTENT, page.asText().contains(LEAVE_PLAN_INCONSISTENT));
	  	
	}
}
