package org.kuali.hr.time.overtime.daily.rule;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

import org.junit.Test;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DailyOvertimeRuleMaintenanceTest extends TkTestCase{
	
	private static final String TEST_CODE="_T";	
	private static final BigDecimal TEST_NO=new BigDecimal(10);		
	private static final java.sql.Date TEST_DATE=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	private static final java.sql.Timestamp TEST_TIMESTAMP=new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	
	private static String TEST_CODE_INVALID_DEPT_ID ="0";
	private static Long TEST_CODE_INVALID_TASK_ID =0L;
	private static Long TEST_CODE_INVALID_WORK_AREA_ID =0L;
	
	
	private static Long dailyOvertimeRuleId;
	
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

	@Override
	public void setUp() throws Exception {
		super.setUp();
		DailyOvertimeRule dailyOvertimeRule = new DailyOvertimeRule();
		dailyOvertimeRule.setActive(true);
		dailyOvertimeRule.setEffectiveDate(TEST_DATE);
		dailyOvertimeRule.setLocation(TEST_CODE);
		dailyOvertimeRule.setMaxGap(TEST_NO);
		dailyOvertimeRule.setUserPrincipalId(TEST_CODE);
		dailyOvertimeRule.setPaytype(TEST_CODE);
		dailyOvertimeRule.setOvertimePref(TEST_CODE);
		dailyOvertimeRule.setShiftHours(TEST_NO);		
		dailyOvertimeRule.setTimeStamp(TEST_TIMESTAMP);
		Random randomObj = new Random();
		
		//search for the dept which doesn't exist		
		for (;;) {
			long deptIdIndex = randomObj.nextInt();
			Department deptObj = KNSServiceLocator.getBusinessObjectService()
					.findBySinglePrimaryKey(Department.class, deptIdIndex);
			if (deptObj == null) {
				TEST_CODE_INVALID_DEPT_ID = Long.toString(deptIdIndex);
				break;
			}
		}
		dailyOvertimeRule.setDept(TEST_CODE_INVALID_DEPT_ID);
		//search for the Task which doesn't exist
		for (;;) {
			long taskIndex = randomObj.nextInt();
			Task taskObj = KNSServiceLocator.getBusinessObjectService()
					.findBySinglePrimaryKey(Task.class, taskIndex);
			if (taskObj == null) {
				TEST_CODE_INVALID_TASK_ID = new Long(taskIndex);
				break;
			}
		}
		dailyOvertimeRule.setTask(TEST_CODE_INVALID_TASK_ID);
		//search for the WorkArea which doesn't exist
		for (;;) {
			long workAreaIndex = randomObj.nextInt();
			WorkArea workAreaObj = KNSServiceLocator.getBusinessObjectService()
					.findBySinglePrimaryKey(WorkArea.class, workAreaIndex);
			if (workAreaObj == null) {
				TEST_CODE_INVALID_WORK_AREA_ID = new Long(workAreaIndex);
				break;
			}
		}
		dailyOvertimeRule.setWorkArea(TEST_CODE_INVALID_WORK_AREA_ID);
		
		KNSServiceLocator.getBusinessObjectService().save(dailyOvertimeRule);		
		dailyOvertimeRuleId=dailyOvertimeRule.getTkDailyOvertimeRuleId();		
	}

	@Override
	public void tearDown() throws Exception {		
		DailyOvertimeRule dailyOvertimeRuleObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(DailyOvertimeRule.class, dailyOvertimeRuleId);			
		KNSServiceLocator.getBusinessObjectService().delete(dailyOvertimeRuleObj);				
		super.tearDown();
	}
}

