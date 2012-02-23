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
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(leavePlan, "search");
		HtmlUnitUtil.createTempFile(resultPage);
		assertTrue("Maintenance page contains:\n" + "Testing Leave Plan Months", resultPage.asText().contains("Testing Leave Plan Months"));
		
		//submit a leave plan with planning months changed
		HtmlPage leavePlanMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit", "3000");
		setFieldValue(leavePlanMaintPage, "document.documentHeader.documentDescription", "LeavePlan change planningMonths - test");
		HtmlInput planningMonthsText = HtmlUnitUtil.getInputContainingText(leavePlanMaintPage, "document.newMaintainableObject.planningMonths");
		planningMonthsText.setValueAttribute("999"); //max value
		HtmlPage outputPage = HtmlUnitUtil.clickInputContainingText(leavePlanMaintPage, "submit");
		HtmlUnitUtil.createTempFile(outputPage);
		assertTrue("Maintenance page text contains:\n" + "Document was successfully submitted", outputPage.asText().contains("Document was successfully submitted"));
		assertTrue("Maintenance page contains:\n" + "Planning Months changed to 999", outputPage.asText().contains("999"));
		
		//look up the changed planning months
		HtmlPage fetchedLeavePlan = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_PLAN_MAINT_URL);
		HtmlPage fetchedPage = HtmlUnitUtil.clickInputContainingText(fetchedLeavePlan, "search");		
		HtmlUnitUtil.createTempFile(fetchedPage);
		assertTrue("Fetched maintenance page now is:\n" + "Planning Months for Leave Plan 3000 is 999", fetchedPage.asText().contains("999"));
	}
}
