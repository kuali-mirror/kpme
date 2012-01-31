package org.kuali.hr.lm.employeeoverride;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class EmployeeOverrideMaintTest extends TkTestCase{
	private static String eoId;
	
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
	  	String errorMessage = "Effective Date (Effective Date) is a required field.";
	  	assertTrue("page text does not contain:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage = "Principal Id (Principal Id) is a required field.";
	    assertTrue("page text does not contain:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage = "Leave Plan (Leave Plan) is a required field.";
	    assertTrue("page text does not contain:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage = "Accrual Category (Accrual Category) is a required field.";
	    assertTrue("page text does not contain:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage= "Override Type (Override Type) is a required field.";
	    assertTrue("page text does not contain:\n" + errorMessage, page.asText().contains(errorMessage));
	}
	
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage eoLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EMPLOYEE_OVERRIDE_MAINT_URL);
		eoLookup = HtmlUnitUtil.clickInputContainingText(eoLookup, "search");
		assertTrue("Page contains test EmployeeOverride", eoLookup.asText().contains("testAC"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(eoLookup, "edit");
		assertTrue("Maintenance Page contains test ShiftDifferentialRule",maintPage.asText().contains("test"));	 
	}
		
	@Override
	public void setUp() throws Exception {
		super.setUp();
		EmployeeOverride eo = new EmployeeOverride();
		eo.setPrincipalId("111");
		eo.setEffectiveDate(new Date(Calendar.getInstance().getTimeInMillis()));
		eo.setLeavePlan("testLP");
		eo.setAccrualCategory("testAC");
		eo.setOverrideType("Max Balance");
		eo.setActive(true);
		KNSServiceLocator.getBusinessObjectService().save(eo);
		eoId = eo.getLmEmployeeOverrideId();
	}

	@Override
	public void tearDown() throws Exception {
		EmployeeOverride eo = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(eoId);
		KNSServiceLocator.getBusinessObjectService().delete(eo);
		super.tearDown();
	}

}
