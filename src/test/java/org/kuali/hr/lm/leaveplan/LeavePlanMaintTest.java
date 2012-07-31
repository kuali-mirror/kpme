package org.kuali.hr.lm.leaveplan;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeavePlanMaintTest extends TkTestCase {

	public static final String TEST_USER = "admin";
	
	
	
	@Test
	public void testLeavePlanMonthsSave() throws Exception {
		
		//get the page with planning months
		HtmlPage leavePlan = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_PLAN_MAINT_URL);
		System.out.println("Leave plan text page is : "+leavePlan.asText());
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(leavePlan, "search");
		HtmlUnitUtil.createTempFile(resultPage);
		System.out.println("Result page is : "+resultPage.asText());
		Assert.assertTrue("Maintenance page contains:\n" + "Testing Leave Plan Months", resultPage.asText().contains("Testing Leave Plan Months"));
		
		//submit a leave plan with planning months changed
		HtmlPage leavePlanMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit", "5555");
		setFieldValue(leavePlanMaintPage, "document.documentHeader.documentDescription", "LeavePlan change planningMonths - test");
		setFieldValue(leavePlanMaintPage, "document.newMaintainableObject.effectiveDate", "01/01/2013");
		HtmlInput planningMonthsText = HtmlUnitUtil.getInputContainingText(leavePlanMaintPage, "document.newMaintainableObject.planningMonths");
		
		// KPME- Kagata - value is between 1 and 24 now
		planningMonthsText.setValueAttribute("999"); //max value
		HtmlPage outputPage = HtmlUnitUtil.clickInputContainingText(leavePlanMaintPage, "submit");
		HtmlUnitUtil.createTempFile(outputPage);
		//assertTrue("Maintenance page text contains:\n" + "Document was successfully submitted", outputPage.asText().contains("Document was successfully submitted"));
		//assertTrue("Maintenance page contains:\n" + "Planning Months changed to 999", outputPage.asText().contains("999"));
		Assert.assertTrue("Maintenance page text contains:\n" + "\'Planning Months\' should be between 1 and 24", outputPage.asText().contains("\'Planning Months\' should be between 1 and 24"));
		
		planningMonthsText.setValueAttribute("0"); 
		outputPage = HtmlUnitUtil.clickInputContainingText(leavePlanMaintPage, "submit");
		HtmlUnitUtil.createTempFile(outputPage);
		Assert.assertTrue("Maintenance page text contains:\n" + "\'Planning Months\' should be between 1 and 24", outputPage.asText().contains("\'Planning Months\' should be between 1 and 24"));
		
		planningMonthsText.setValueAttribute("24"); 
		outputPage = HtmlUnitUtil.clickInputContainingText(leavePlanMaintPage, "submit");
		HtmlUnitUtil.createTempFile(outputPage);
		Assert.assertTrue("Maintenance page text contains:\n" + "Document was successfully submitted", outputPage.asText().contains("Document was successfully submitted"));
		Assert.assertTrue("Maintenance page contains:\n" + "Planning Months changed to 24", outputPage.asText().contains("24"));
		
	}
	
	// KPME-1250 Kagata
	@Test
	public void testInactivateLeavePlan() throws Exception {
		
		//get the page with planning months
		HtmlPage leavePlan = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_PLAN_MAINT_URL);
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(leavePlan, "search");
		HtmlUnitUtil.createTempFile(resultPage);
		Assert.assertTrue("Maintenance page contains:\n" + "Testing LP Inactive Flag", resultPage.asText().contains("Testing LP Inactive Flag"));
		
		HtmlPage leavePlanMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit", "2000");
		setFieldValue(leavePlanMaintPage, "document.documentHeader.documentDescription", "LeavePlan change active flag");
		HtmlCheckBoxInput activeCheckbox = (HtmlCheckBoxInput) HtmlUnitUtil.getInputContainingText(leavePlanMaintPage, "document.newMaintainableObject.active");
		activeCheckbox.setChecked(false);
			
		HtmlPage outputPage = HtmlUnitUtil.clickInputContainingText(leavePlanMaintPage, "submit");
		HtmlUnitUtil.createTempFile(outputPage);
		
	}
	
	// KPME-1489 Kagata
	@Test
	public void testRequiredFields() throws Exception {
		
		//get the page with planning months
		HtmlPage leavePlan = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_PLAN_MAINT_URL);
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(leavePlan, "search");
		HtmlUnitUtil.createTempFile(resultPage);
		Assert.assertTrue("Maintenance page contains:\n" + "Testing Leave Plan Months", resultPage.asText().contains("Testing Leave Plan Months"));
		
		HtmlPage leavePlanMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit", "5555");
		//submit a leave plan with planning months changed
		setFieldValue(leavePlanMaintPage, "document.documentHeader.documentDescription", "Testing required fields");
		HtmlInput planningMonthsText = HtmlUnitUtil.getInputContainingText(leavePlanMaintPage, "document.newMaintainableObject.planningMonths");
		HtmlInput calendarYearStartText = HtmlUnitUtil.getInputContainingText(leavePlanMaintPage, "document.newMaintainableObject.calendarYearStart");
		
		planningMonthsText.setValueAttribute("");
		calendarYearStartText.setValueAttribute("");
		HtmlPage outputPage = HtmlUnitUtil.clickInputContainingText(leavePlanMaintPage, "submit");
		HtmlUnitUtil.createTempFile(outputPage);
		Assert.assertTrue("Maintenance page text contains:\n" + "Planning Months (Planning Months) is a required field", outputPage.asText().contains("Planning Months (Planning Months) is a required field"));
		Assert.assertTrue("Maintenance page text contains:\n" + "Calendar Year Start (MM/DD) (Calendar Year Start (MM/DD)) is a required field", outputPage.asText().contains("Calendar Year Start (MM/DD) (Calendar Year Start (MM/DD)) is a required field"));
		
		setFieldValue(outputPage, "document.newMaintainableObject.effectiveDate", "");
		HtmlPage page = HtmlUnitUtil.clickInputContainingText(outputPage, "submit");
		Assert.assertTrue("Maintenance page text contains:\n" + "Effective Date (Effective Date) is a required field.", page.asText().contains("Effective Date (Effective Date) is a required field."));
		Assert.assertFalse("Maintenance page text contains:\n" + "Incident Report", page.asText().contains("Incident Report "));
		
	}
	
	
}
