package org.kuali.hr.time.workschedule;

import java.util.Calendar;
import java.util.Random;
import org.junit.Test;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WorkScheduleMaintTest extends TkTestCase{

	private static final String TEST_CODE = "0";	
	private static final Long TEST_ID = 20L;	
	private static final String TEST_CODE_DEPARTMENT_VALID = "_test";	
	private static final java.sql.Date TEST_DATE = new java.sql.Date(Calendar
			.getInstance().getTimeInMillis());
	
	private static String TEST_CODE_DEPT_INVALID = "0";
	private static Long TEST_CODE_WORKAREA_INVALID = 0l;
	
	private static Long workScheduleWithInvalidDept;
	private static Long workScheduleIdWithInvalidWorkArea;

	/**
	 * Test to check whether it is showing error message for Department on maintenance screen
	 * if we supply non exist deptId
	 * 
	 * @throws Exception
	 */
	@Test
	public void testworkScheduleMaintForDeptErrorMessage()
			throws Exception {
		HtmlPage workScheduleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.WORK_SCHEDULE_MAINT_URL);
		workScheduleLookUp = HtmlUnitUtil.clickInputContainingText(
				workScheduleLookUp, "search");
		assertTrue("Page contains test workSchedule",
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
	public void testWorkScheduleMaintForWorkAreaErrorMessage()
			throws Exception {
		HtmlPage workScheduleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.WORK_SCHEDULE_MAINT_URL);
		workScheduleLookUp = HtmlUnitUtil.clickInputContainingText(
				workScheduleLookUp, "search");
		assertTrue("Page contains test workSchedule",
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
		assertTrue("Maintenance Page contains contains error message for workarea",
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
		assertTrue("Page contains test workSchedule",
				workScheduleLookUp.asText().contains(
						TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				workScheduleLookUp, "edit",
				workScheduleWithInvalidDept.toString());
		assertTrue("Maintenance Page contains test WorkSchedule ",
				maintPage.asText().contains(TEST_CODE.toString()));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		WorkSchedule workSchedulewithInvalidDept = new WorkSchedule();
		// setting deptId for which Department doesn't exist .
		Random randomObj = new Random();
		for (;;) {
			long deptIdIndex = randomObj.nextInt();
			Department deptObj = KNSServiceLocator.getBusinessObjectService()
					.findBySinglePrimaryKey(Department.class, deptIdIndex);
			if (deptObj == null) {
				TEST_CODE_DEPT_INVALID = Long.toString(deptIdIndex);
				break;
			}
		}
		
		workSchedulewithInvalidDept.setEffectiveDate(TEST_DATE);		
		workSchedulewithInvalidDept.setPrincipalId(TEST_ID);
		workSchedulewithInvalidDept.setWorkScheduleDesc(TEST_CODE);		
		workSchedulewithInvalidDept.setActive(true);		 
		workSchedulewithInvalidDept.setWorkArea(TEST_ID);
		workSchedulewithInvalidDept.setDept(TEST_CODE_DEPT_INVALID) ;
		KNSServiceLocator.getBusinessObjectService().save(
				workSchedulewithInvalidDept);
		workScheduleWithInvalidDept = workSchedulewithInvalidDept.getHrWorkScheduleId();

		Department department = new Department();
		department.setChart(TEST_CODE_DEPARTMENT_VALID);
		department.setDept(TEST_CODE_DEPARTMENT_VALID);
		department.setDescription(TEST_CODE_DEPARTMENT_VALID);
		department.setOrg(TEST_CODE_DEPARTMENT_VALID);
		KNSServiceLocator.getBusinessObjectService().save(department);
		
		
		WorkSchedule workSchedulewithInvalidWorkArea = new WorkSchedule();

		// setting workAreaID for which WorkArea doesn't exist .
		for (;;) {
			long workAreaIndex = randomObj.nextInt();
			WorkArea workAreaObj = KNSServiceLocator.getBusinessObjectService()
					.findBySinglePrimaryKey(WorkArea.class, workAreaIndex);
			if (workAreaObj == null) {
				TEST_CODE_WORKAREA_INVALID = new Long(workAreaIndex);
				break;
			}
		}
		workSchedulewithInvalidWorkArea.setEffectiveDate(TEST_DATE);		
		workSchedulewithInvalidWorkArea.setPrincipalId(TEST_ID);
		workSchedulewithInvalidWorkArea.setWorkScheduleDesc(TEST_CODE);		
		workSchedulewithInvalidWorkArea.setActive(true);		 
		workSchedulewithInvalidWorkArea.setWorkArea(TEST_CODE_WORKAREA_INVALID);
		workSchedulewithInvalidWorkArea.setDept(TEST_CODE_DEPARTMENT_VALID) ;
		KNSServiceLocator.getBusinessObjectService().save(
				workSchedulewithInvalidWorkArea);
		workScheduleIdWithInvalidWorkArea = workSchedulewithInvalidWorkArea.getHrWorkScheduleId();

	}

	@Override
	public void tearDown() throws Exception {
		WorkSchedule workScheduleObj = KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						WorkSchedule.class, workScheduleWithInvalidDept);
		KNSServiceLocator.getBusinessObjectService().delete(workScheduleObj);
		workScheduleObj = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(WorkSchedule.class,
						workScheduleIdWithInvalidWorkArea);
		KNSServiceLocator.getBusinessObjectService().delete(workScheduleObj);
		
		Department deptObj = KNSServiceLocator.getBusinessObjectService()
		.findBySinglePrimaryKey(Department.class,
				TEST_CODE_DEPARTMENT_VALID);
		KNSServiceLocator.getBusinessObjectService().delete(deptObj);
		
		super.tearDown();
	}

}
