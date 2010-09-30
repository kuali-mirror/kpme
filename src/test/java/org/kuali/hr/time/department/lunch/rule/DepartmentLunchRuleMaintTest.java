package org.kuali.hr.time.department.lunch.rule;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

import org.junit.Test;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentLunchRuleMaintTest extends TkTestCase {

	
	private static final String TEST_CODE = "0";	
	private static final Long TEST_ID = 20L;
	private static final BigDecimal TEST_NO = new BigDecimal(10);
	private static final String TEST_CODE_DEPARTMENT_VALID = "_test";	
	private static final java.sql.Date TEST_DATE = new java.sql.Date(Calendar
			.getInstance().getTimeInMillis());
	private static final java.sql.Timestamp TEST_TIME_STAMP = new java.sql.Timestamp(Calendar
			.getInstance().getTimeInMillis());
	
	private static String TEST_CODE_DEPT_INVALID = "0";
	private static Long TEST_CODE_WORKAREA_INVALID = 0l;
	
	private static Long deptLunchRuleIdWithInvalidDept;
	private static Long deptId;
	private static Long deptLunchRuleIdWithInvalidWorkArea;

	/**
	 * Test to check whether it is showing error message for Department on maintenance screen
	 * if we supply non exist deptId
	 * 
	 * @throws Exception
	 */
	//@Test
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
	//@Test
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
		assertTrue("Maintenance Page contains contains error message for workarea",
				resultantPageAfterEdit.asText().contains(
						"The specified workarea '" + TEST_CODE_WORKAREA_INVALID
								+ "' does not exist."));
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

	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		Department department = new Department();
		department.setChart(TEST_CODE_DEPARTMENT_VALID);
		department.setDept(TEST_CODE_DEPARTMENT_VALID);
		department.setDescription(TEST_CODE_DEPARTMENT_VALID);
		department.setOrg(TEST_CODE_DEPARTMENT_VALID);
		department.setEffectiveDate(TEST_DATE);
		department.setDescription(TEST_CODE);
		department.setTimestamp(TEST_TIME_STAMP);	
		KNSServiceLocator.getBusinessObjectService().save(department);
		deptId = department.getTkDeptId();
		DeptLunchRule deptLunchRuleWIthInvalidDept = new DeptLunchRule();
//		// setting deptId for which Department doesn't exist .
//		Random randomObj = new Random();
//		for (;;) {
//			long deptIdIndex = randomObj.nextInt();
//			Department deptObj = KNSServiceLocator.getBusinessObjectService()
//					.findBySinglePrimaryKey(Department.class, deptIdIndex);
//			if (deptObj == null) {
//				TEST_CODE_DEPT_INVALID = Long.toString(deptIdIndex);
//				break;
//			}
//		}
		deptLunchRuleWIthInvalidDept.setActive(true);
		deptLunchRuleWIthInvalidDept.setDept(TEST_CODE_DEPARTMENT_VALID);
		deptLunchRuleWIthInvalidDept.setEffectiveDate(TEST_DATE);
		deptLunchRuleWIthInvalidDept.setJobNumber(TEST_ID);
		deptLunchRuleWIthInvalidDept.setMaxMins(TEST_NO);
		deptLunchRuleWIthInvalidDept.setPrincipalId(TEST_CODE);
		deptLunchRuleWIthInvalidDept.setRequiredClockFl(true);
		deptLunchRuleWIthInvalidDept.setUserPrincipalId(TEST_CODE);
		deptLunchRuleWIthInvalidDept.setWorkArea(TEST_ID);
		deptLunchRuleWIthInvalidDept.setTimestamp(TEST_TIME_STAMP);
		KNSServiceLocator.getBusinessObjectService().save(
				deptLunchRuleWIthInvalidDept);
		deptLunchRuleIdWithInvalidDept = deptLunchRuleWIthInvalidDept
				.getTkDeptLunchRuleId();

//		Department department = new Department();
//		department.setChart(TEST_CODE_DEPARTMENT_VALID);
//		department.setDept(TEST_CODE_DEPARTMENT_VALID);
//		department.setDescription(TEST_CODE_DEPARTMENT_VALID);
//		department.setOrg(TEST_CODE_DEPARTMENT_VALID);
//		department.setEffectiveDate(TEST_DATE);
//		department.setDescription(TEST_CODE);
//		department.setTimestamp(TEST_TIME_STAMP);	
//		KNSServiceLocator.getBusinessObjectService().save(department);
//		deptId = department.getTkDeptId();
//		DeptLunchRule deptLunchRuleWIthInvalidWorkArea = new DeptLunchRule();
//
//		// setting workAreaID for which WorkArea doesn't exist .
//		for (;;) {
//			long workAreaIndex = randomObj.nextInt();
//			WorkArea workAreaObj = KNSServiceLocator.getBusinessObjectService()
//					.findBySinglePrimaryKey(WorkArea.class, workAreaIndex);
//			if (workAreaObj == null) {
//				TEST_CODE_WORKAREA_INVALID = new Long(workAreaIndex);
//				break;
//			}
//		}
//		deptLunchRuleWIthInvalidWorkArea.setActive(true);
//		deptLunchRuleWIthInvalidWorkArea.setDept(TEST_CODE_DEPARTMENT_VALID);
//		deptLunchRuleWIthInvalidWorkArea.setEffectiveDate(TEST_DATE);
//		deptLunchRuleWIthInvalidWorkArea.setJobNumber(TEST_ID);
//		deptLunchRuleWIthInvalidWorkArea.setMaxMins(TEST_NO);
//		deptLunchRuleWIthInvalidWorkArea.setPrincipalId(TEST_CODE);
//		deptLunchRuleWIthInvalidWorkArea.setRequiredClockFl(true);
//		deptLunchRuleWIthInvalidWorkArea.setUserPrincipalId(TEST_CODE);
//		deptLunchRuleWIthInvalidWorkArea.setTimestamp(TEST_TIME_STAMP);
//		deptLunchRuleWIthInvalidWorkArea.setWorkArea(TEST_CODE_WORKAREA_INVALID);
//		KNSServiceLocator.getBusinessObjectService().save(
//				deptLunchRuleWIthInvalidWorkArea);
//		deptLunchRuleIdWithInvalidWorkArea = deptLunchRuleWIthInvalidWorkArea
//				.getTkDeptLunchRuleId();

	}

	@Override
	public void tearDown() throws Exception {
		DeptLunchRule deptLunchRuleObj = KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						DeptLunchRule.class, deptLunchRuleIdWithInvalidDept);
		KNSServiceLocator.getBusinessObjectService().delete(deptLunchRuleObj);
		deptLunchRuleObj = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(DeptLunchRule.class,
						deptLunchRuleIdWithInvalidWorkArea);
		KNSServiceLocator.getBusinessObjectService().delete(deptLunchRuleObj);
		
		Department deptObj = KNSServiceLocator.getBusinessObjectService()
		.findBySinglePrimaryKey(Department.class,
				deptId);
		KNSServiceLocator.getBusinessObjectService().delete(deptObj);
		
		super.tearDown();
	}

}
