package org.kuali.hr.time.department.lunch.rule;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentLunchRuleMaintTest extends TkTestCase {
	
	private static final String TEST_CODE = "admin";		
	private static String TEST_CODE_DEPT_INVALID = "INVALID";
	private static Long TEST_CODE_WORKAREA_INVALID = 9999L;
	
	private static Long deptLunchRuleIdWithInvalidDept = 1L;	
	private static Long deptLunchRuleIdWithInvalidWorkArea = 3L;

	/**
	 * Test to check whether it is showing error message for Department on maintenance screen
	 * if we supply non exist deptId
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDepartmentLunchRuleMaintForDeptErrorMessage()
			throws Exception {
		HtmlPage departmentLunchRuleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.DEPT_LUNCH_RULE_MAINT_URL);
		departmentLunchRuleLookUp = HtmlUnitUtil.clickInputContainingText(
				departmentLunchRuleLookUp, "search");
		assertTrue("Page contains test DepartmentLunchRule",
				departmentLunchRuleLookUp.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				departmentLunchRuleLookUp, "edit",
				deptLunchRuleIdWithInvalidDept.toString());
		HtmlInput inputForDept = HtmlUnitUtil.getInputContainingText(maintPage,
				TEST_CODE_DEPT_INVALID);
		inputForDept.setValueAttribute(TEST_CODE_DEPT_INVALID);
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		System.out.println(resultantPageAfterEdit.asText());
		assertTrue("Maintenance Page contains error message for dept",
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
	public void testDepartmentLunchRuleMaintForWorkAreaErrorMessage()
			throws Exception {
		HtmlPage departmentLunchRuleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.DEPT_LUNCH_RULE_MAINT_URL);
		departmentLunchRuleLookUp = HtmlUnitUtil.clickInputContainingText(
				departmentLunchRuleLookUp, "search");
		assertTrue("Page contains test DepartmentLunchRule",
				departmentLunchRuleLookUp.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				departmentLunchRuleLookUp, "edit",
				deptLunchRuleIdWithInvalidWorkArea.toString());
		HtmlInput inputForWorkArea = HtmlUnitUtil.getInputContainingText(maintPage,
				Long.toString(TEST_CODE_WORKAREA_INVALID));
		inputForWorkArea.setValueAttribute(Long.toString(TEST_CODE_WORKAREA_INVALID));
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");		
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		assertTrue("Maintenance Page contains contains error message for workarea",
				resultantPageAfterEdit.asText().contains(
						"The specified workArea '" + TEST_CODE_WORKAREA_INVALID
								+ "' does not exist."));
	}

	@Test
	public void testDepartmentLunchRuleMaintForMaxShiftHourErrorMessage() throws Exception {
		HtmlPage departmentLunchRuleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.DEPT_LUNCH_RULE_MAINT_URL);
		departmentLunchRuleLookUp = HtmlUnitUtil.clickInputContainingText(
				departmentLunchRuleLookUp, "search");
		assertTrue("Page contains test DepartmentLunchRule",
				departmentLunchRuleLookUp.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				departmentLunchRuleLookUp, "edit",
				deptLunchRuleIdWithInvalidWorkArea.toString());
		
		setFieldValue(maintPage, TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "shiftHours", "34");
		setFieldValue(maintPage, "document.documentHeader.documentDescription", "test");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");		
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		assertTrue("Maintenance Page contains contains error message for Shift Hours",
				resultantPageAfterEdit.asText().contains("Shift Hour cannot be greater than 24."));
	}
	
	@Test
	public void testDepartmentLunchRuleMaint() throws Exception {
		HtmlPage departmentLunchRuleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.DEPT_LUNCH_RULE_MAINT_URL);
		departmentLunchRuleLookUp = HtmlUnitUtil.clickInputContainingText(
				departmentLunchRuleLookUp, "search");
		assertTrue("Page contains test DepartmentLunchRule",
				departmentLunchRuleLookUp.asText().contains(
						TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				departmentLunchRuleLookUp, "edit",
				deptLunchRuleIdWithInvalidDept.toString());
		assertTrue("Maintenance Page contains test DepartmentLunchRule",
				maintPage.asText().contains(TEST_CODE.toString()));
	}
}
