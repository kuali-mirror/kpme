package org.kuali.hr.lm.leaveplan;

import org.junit.Test;

import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;

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
		assertTrue("Maintenance page contains:\n" + "Testing Leave Plan Months", resultPage.asText().contains("Testing Leave Plan Months"));
		
		//submit a leave plan with planning months changed
		HtmlPage leavePlanMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit", "5555");
		setFieldValue(leavePlanMaintPage, "document.documentHeader.documentDescription", "LeavePlan change planningMonths - test");
		setFieldValue(leavePlanMaintPage, "document.newMaintainableObject.effectiveDate", "01/01/2013");
		HtmlInput planningMonthsText = HtmlUnitUtil.getInputContainingText(leavePlanMaintPage, "document.newMaintainableObject.planningMonths");
		
		planningMonthsText.setValueAttribute("999"); //max value
		HtmlPage outputPage = HtmlUnitUtil.clickInputContainingText(leavePlanMaintPage, "submit");
		HtmlUnitUtil.createTempFile(outputPage);
		assertTrue("Maintenance page text contains:\n" + "Document was successfully submitted", outputPage.asText().contains("Document was successfully submitted"));
		assertTrue("Maintenance page contains:\n" + "Planning Months changed to 999", outputPage.asText().contains("999"));
		
	}
}
