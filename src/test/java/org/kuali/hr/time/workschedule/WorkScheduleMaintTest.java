package org.kuali.hr.time.workschedule;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Ignore
public class WorkScheduleMaintTest extends TkTestCase{
	
	private static final String TEST_CODE = "test-schedule";		
		
	private static String TEST_CODE_DEPT_INVALID = "INVALID";
	private static Long TEST_CODE_WORKAREA_INVALID = -1L;
	
	private static Long workScheduleWithInvalidDept = 11L;
	private static Long workScheduleIdWithInvalidWorkArea = 12L;

	/**
	 * Test to check whether it is showing error message for Department on maintenance screen
	 * if we supply non exist deptId
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testworkScheduleMaintForDeptErrorMessage()
			throws Exception {
		HtmlPage workScheduleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.WORK_SCHEDULE_MAINT_URL);
		workScheduleLookUp = HtmlUnitUtil.clickInputContainingText(
				workScheduleLookUp, "search");
		Assert.assertTrue("Page contains test workSchedule",
				workScheduleLookUp.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				workScheduleLookUp, "edit",
				workScheduleWithInvalidDept.toString());
		HtmlInput inputForDept = HtmlUnitUtil.getInputContainingText(maintPage,
				TEST_CODE_DEPT_INVALID);
		inputForDept.setValueAttribute(TEST_CODE_DEPT_INVALID);
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");		
		Assert.assertTrue("Maintenance Page contains error message for dept",
				resultantPageAfterEdit.asText().contains(
						"The specified department '" + TEST_CODE_DEPT_INVALID
								+ "' does not exist."));
	}

	/**
	 * Test to check whether it is showing error message for WorkArea on maintenance screen
	 * if we supply non exist workArea
	 * 
	 * @throws Exception
	 */
	@Test
	public void testWorkScheduleMaintForWorkAreaErrorMessage()
			throws Exception {
		HtmlPage workScheduleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.WORK_SCHEDULE_MAINT_URL);
		workScheduleLookUp = HtmlUnitUtil.clickInputContainingText(
				workScheduleLookUp, "search");
		Assert.assertTrue("Page contains test workSchedule",
				workScheduleLookUp.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				workScheduleLookUp, "edit",
				workScheduleIdWithInvalidWorkArea.toString());
		HtmlInput inputForWorkArea = HtmlUnitUtil.getInputContainingText(maintPage,
				Long.toString(TEST_CODE_WORKAREA_INVALID));
		inputForWorkArea.setValueAttribute(Long.toString(TEST_CODE_WORKAREA_INVALID));
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");		
		Assert.assertTrue("Maintenance Page contains contains error message for workarea",
				resultantPageAfterEdit.asText().contains(
						"The specified workarea '" + TEST_CODE_WORKAREA_INVALID
								+ "' does not exist."));
	}

	@Test
	public void testWorkScheduleRuleMaint() throws Exception {
		HtmlPage workScheduleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.WORK_SCHEDULE_MAINT_URL);
		workScheduleLookUp = HtmlUnitUtil.clickInputContainingText(
				workScheduleLookUp, "search");
		Assert.assertTrue("Page contains test workSchedule",
				workScheduleLookUp.asText().contains(
						TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				workScheduleLookUp, "edit",
				workScheduleWithInvalidDept.toString());
		Assert.assertTrue("Maintenance Page contains test WorkSchedule ",
				maintPage.asText().contains(TEST_CODE.toString()));
	}
}
