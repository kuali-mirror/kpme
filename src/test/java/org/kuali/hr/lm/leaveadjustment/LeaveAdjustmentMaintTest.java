package org.kuali.hr.lm.leaveadjustment;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveAdjustmentMaintTest extends TkTestCase{
	private static String leaveAdjustmentId;
	private static String acId;
	private static final String PRINCIPAL_ID = "admin";
	private static final Date TEST_DATE = new Date((new DateTime(2011,1,1,1,0,0,0,DateTimeZone.forID("EST"))).getMillis());
	private static final String EFFECTIVE_DATE_REQUIRED = "Effective Date (Effective Date) is a required field.";
	private static final String PRINCIPAL_ID_REQUIRED = "Principal Id (Principal Id) is a required field.";
	private static final String LEAVE_PLAN_REQUIRED = "Leave Plan (Leave Plan) is a required field.";
	private static final String LEAVE_CODE_REQUIRED = "Leave Code (Leave Code) is a required field.";
	private static final String ACCRUAL_CATEGORY_REQUIRED = "Accrual Category (Accrual Category) is a required field.";
	private static final String LEAVE_CODE_NOT_FOUND = "The specifed Leave Code 'testLC' does not exist.";
	private static final String LEAVE_PLAN_NOT_FOUND = "The specified Principal Id 'test' does not have a Leave Plan for the given Effective Date '2011-04-01'.";
	private static final String AC_NOT_FOUND = "The specifed Accrual Category 'test' does not exist.";
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
	}
	
	//@Test
	/*public void testLookupPage() throws Exception {	 
		HtmlPage leaveAdjustmentLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_ADJUSTMENT_MAINT_URL);
		leaveAdjustmentLookup = HtmlUnitUtil.clickInputContainingText(leaveAdjustmentLookup, "search");
		System.out.println(leaveAdjustmentLookup.asXml());
		assertTrue("Page contains test LeaveAdjustment", leaveAdjustmentLookup.asText().contains("testAC"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(leaveAdjustmentLookup, "edit");
		assertTrue("Maintenance Page contains test ShiftDifferentialRule", maintPage.asText().contains("test"));	 
	}*/
	
	@Test
	public void testAddNew() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.EMPLOYEE_OVERRIDE_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", form);
	  	
	  	setFieldValue(page, "document.documentHeader.documentDescription", "Leave Adjustment - test");
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
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		LeaveAdjustment leaveAdjustment = new LeaveAdjustment();
		leaveAdjustment.setEffectiveDate(TEST_DATE);
		leaveAdjustment.setPrincipalId(PRINCIPAL_ID);
		leaveAdjustment.setLeavePlan("testLP");
		leaveAdjustment.setAccrualCategory("testAC");
		leaveAdjustment.setLeaveCode("testLC");
		
		KNSServiceLocator.getBusinessObjectService().save(leaveAdjustment);
		leaveAdjustmentId = leaveAdjustment.getLmLeaveAdjustmentId();
		
		AccrualCategory ac = new AccrualCategory();
		ac.setAccrualCategory("testAC");
		ac.setActive(true);
		ac.setDescr("test");
		ac.setEffectiveDate(TEST_DATE);
		ac.setTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		ac.setLeavePlan("testLP");
		ac.setAccrualEarnInterval("");
		ac.setUnitOfTime("");
		KNSServiceLocator.getBusinessObjectService().save(ac);
		acId = ac.getLmAccrualCategoryId();
		
		PrincipalHRAttributes pha = new PrincipalHRAttributes();
		pha.setActive(true);
		pha.setEffectiveDate(TEST_DATE);
		pha.setPrincipalId(PRINCIPAL_ID);
		pha.setLeavePlan("testLP");
		KNSServiceLocator.getBusinessObjectService().save(pha);
	}

	@Override
	public void tearDown() throws Exception {
		LeaveAdjustment leaveAdjustment = TkServiceLocator.getLeaveAdjustmentService().getLeaveAdjustment(leaveAdjustmentId);
		KNSServiceLocator.getBusinessObjectService().delete(leaveAdjustment);
		AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(acId);
		KNSServiceLocator.getBusinessObjectService().delete(ac);
		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(PRINCIPAL_ID, TEST_DATE);
		KNSServiceLocator.getBusinessObjectService().delete(pha);
		super.tearDown();
	}

}
