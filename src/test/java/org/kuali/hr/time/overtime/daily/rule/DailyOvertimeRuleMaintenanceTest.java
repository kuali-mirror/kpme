package org.kuali.hr.time.overtime.daily.rule;


import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DailyOvertimeRuleMaintenanceTest extends TkTestCase{
		
	private static final String TEST_CODE="BL";		
	private static String TEST_CODE_INVALID_DEPT_ID ="INVALID";
	private static Long TEST_CODE_INVALID_TASK_ID =-1L;
	private static Long TEST_CODE_INVALID_WORK_AREA_ID =-1L;	
	private static Long dailyOvertimeRuleId = 5L;
	
	@Test
	public void testDailyOvertimeRuleMaint() throws Exception {
		HtmlPage dailyOvertimeRuleLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DAILY_OVERTIME_RULE_MAINT_URL);
		dailyOvertimeRuleLookUp = HtmlUnitUtil.clickInputContainingText(dailyOvertimeRuleLookUp, "search");
		assertTrue("Page contains test DailyOvertimeRule", dailyOvertimeRuleLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(dailyOvertimeRuleLookUp, "edit",dailyOvertimeRuleId.toString());		
		assertTrue("Maintenance Page contains test DailyOvertimeRule",maintPage.asText().contains(TEST_CODE.toString()));		
	}
	
	@Test
	public void testDailyOvertimeRuleMaintForErrorMessages() throws Exception {
		HtmlPage dailyOvertimeRuleLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DAILY_OVERTIME_RULE_MAINT_URL);
		dailyOvertimeRuleLookUp = HtmlUnitUtil.clickInputContainingText(dailyOvertimeRuleLookUp, "search");
		assertTrue("Page contains test DailyOvertimeRule", dailyOvertimeRuleLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(dailyOvertimeRuleLookUp, "edit",dailyOvertimeRuleId.toString());		
		assertTrue("Maintenance Page contains test DailyOvertimeRule",maintPage.asText().contains(TEST_CODE.toString()));
		
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		System.out.println(resultantPageAfterEdit.asText());
		assertTrue("Maintenance Page contains test deptErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified Department '"
								+ TEST_CODE_INVALID_DEPT_ID
								+ "' does not exist."));
		
		assertTrue("Maintenance Page contains test Workarea ",
				resultantPageAfterEdit.asText().contains(
						"The specified Workarea '"
								+ TEST_CODE_INVALID_WORK_AREA_ID
								+ "' does not exist."));
		
		assertTrue("Maintenance Page contains test Task ",
				resultantPageAfterEdit.asText().contains(
						"The specified Task '"
								+ TEST_CODE_INVALID_TASK_ID
								+ "' does not exist."));		
	}
}

