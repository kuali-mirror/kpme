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
	private static final String PRINCIPAL_ID = "111";
	private static final String EFFECTIVE_DATE_REQUIRED = "Effective Date (Effective Date) is a required field.";
	private static final String PRINCIPAL_ID_REQUIRED = "Principal Id (Principal Id) is a required field.";
	private static final String LEAVE_PLAN_REQUIRED = "Leave Plan (Leave Plan) is a required field.";
	private static final String ACCRUAL_CATEGORY_REQUIRED = "Accrual Category (Accrual Category) is a required field.";
	private static final String OVERRIDE_TYPE_REQUIRED = "Override Type (Override Type) is a required field.";
	private static final String AC_NOT_FOUND = "The specifed Accrual Category 'test' does not exist.";
	private static final String LEAVE_PLAN_NOT_FOUND = "The specified Principal Id 'test' does not have a Leave Plan for the given Effective Date '2011-04-01'.";
	private static final String AC_LP_INCONSISTENT = "The specifed Accrual Category 'testAC' is inconsistent with the given Leave Plan ''.";
	
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
	    setFieldValue(page, "document.newMaintainableObject.principalId", "test");
	    setFieldValue(page, "document.newMaintainableObject.accrualCategory", "test");	//nonexist accrual catetory
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	assertNotNull("Could not locate submit button", input);
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertTrue("page text does not contain:\n" + AC_NOT_FOUND, page.asText().contains(AC_NOT_FOUND));
	  	assertTrue("page text does not contain:\n" + LEAVE_PLAN_NOT_FOUND, page.asText().contains(LEAVE_PLAN_NOT_FOUND));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.accrualCategory", "testAC"); // existing accrual category
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertFalse("page text contains:\n" + AC_NOT_FOUND, page.asText().contains(AC_NOT_FOUND));
	  	assertTrue("page text does not contain:\n" + LEAVE_PLAN_NOT_FOUND, page.asText().contains(LEAVE_PLAN_NOT_FOUND));
	  	assertTrue("page text does not contain:\n" + AC_LP_INCONSISTENT, page.asText().contains(AC_LP_INCONSISTENT));
	  	
	  	setFieldValue(page, "document.newMaintainableObject.principalId", PRINCIPAL_ID); // existing principal hr attributes
	  	element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	assertFalse("page text contains:\n" + LEAVE_PLAN_NOT_FOUND, page.asText().contains(LEAVE_PLAN_NOT_FOUND));
	  	assertFalse("page text contains:\n" + AC_LP_INCONSISTENT, page.asText().contains(AC_LP_INCONSISTENT));
	}
}
