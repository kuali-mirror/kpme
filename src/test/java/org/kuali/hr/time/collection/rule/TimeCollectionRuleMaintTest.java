package org.kuali.hr.time.collection.rule;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import org.junit.Test;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.service.KNSServiceLocator;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TimeCollectionRuleMaintTest extends TkTestCase {
	
	private static final String TEST_CODE = "X";	
	private static final java.sql.Date TEST_DATE = new java.sql.Date(Calendar
			.getInstance().getTimeInMillis());
	private static final String TEST_CODE_DEPARTMENT_VALID = "_test";
	
	private static Long timeCollectionRuleIdWithInvalidDept;
	private static Long timeCollectionRuleIdWithInvalidWorkArea;
	
	private static String TEST_CODE_INVALID_DEPT_ID = "0";
	private static Long TEST_CODE_INVALID_WORKAREA = 0l;

	/**
	 * Test to check whether it is showing error message on maintenance screen
	 * if we supply non exist deptId
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTimeCollectionRuleMaintForDeptErrorMessage() throws Exception {
		HtmlPage timeCollectionRuleLookup = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.TIME_COLLECTION_RULE_MAINT_URL);
		timeCollectionRuleLookup = HtmlUnitUtil.clickInputContainingText(
				timeCollectionRuleLookup, "search");
		assertTrue("Page contains test timeCollectionRule",
				timeCollectionRuleLookup.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				timeCollectionRuleLookup, "edit",
				timeCollectionRuleIdWithInvalidDept.toString());
		HtmlInput inputForDept = HtmlUnitUtil.getInputContainingText(maintPage,
				TEST_CODE_INVALID_DEPT_ID);
		inputForDept.setValueAttribute(TEST_CODE_INVALID_DEPT_ID);
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		assertTrue("Maintenance Page contains test timeCollectionRule",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ TEST_CODE_INVALID_DEPT_ID
								+ "' does not exist."));
	}

	
	@Test
	public void testTimeCollectionRuleMaintForWorkAreaErrorMessage() throws Exception {
		HtmlPage timeCollectionRuleLookup = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.TIME_COLLECTION_RULE_MAINT_URL);
		timeCollectionRuleLookup = HtmlUnitUtil.clickInputContainingText(
				timeCollectionRuleLookup, "search");
		assertTrue("Page contains test timeCollectionRule",
				timeCollectionRuleLookup.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				timeCollectionRuleLookup, "edit",
				timeCollectionRuleIdWithInvalidWorkArea.toString());
		HtmlInput inputForWorkArea = HtmlUnitUtil.getInputContainingText(maintPage,
				Long.toString(TEST_CODE_INVALID_WORKAREA));
		inputForWorkArea.setValueAttribute(Long.toString(TEST_CODE_INVALID_WORKAREA));
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		assertTrue("Maintenance Page contains test timeCollectionRule",
				resultantPageAfterEdit.asText().contains(
						"The specified workarea '"
								+ TEST_CODE_INVALID_WORKAREA
								+ "' does not exist."));
	}

	/**
	 * Test to load maint. screen
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTimeCollectionRuleMaint() throws Exception {
		HtmlPage timeCollectionRuleLookup = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.TIME_COLLECTION_RULE_MAINT_URL);
		timeCollectionRuleLookup = HtmlUnitUtil.clickInputContainingText(
				timeCollectionRuleLookup, "search");
		assertTrue("Page contains test timeCollectionRule",
				timeCollectionRuleLookup.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				timeCollectionRuleLookup, "edit",
				timeCollectionRuleIdWithInvalidDept.toString());
		assertTrue("Maintenance Page contains test timeCollectionRule",
				maintPage.asText().contains(TEST_CODE));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		TimeCollectionRule timeCollectionRuleWIthInvalidDept = new TimeCollectionRule();
		// setting deptId for which Department doesn't exist .
		Random randomObj = new Random();
		for (;;) {
			long deptIdIndex = randomObj.nextInt();
			Department deptObj = KNSServiceLocator.getBusinessObjectService()
					.findBySinglePrimaryKey(Department.class, deptIdIndex);
			if (deptObj == null) {
				TEST_CODE_INVALID_DEPT_ID = Long.toString(deptIdIndex);
				break;
			}
		}
		timeCollectionRuleWIthInvalidDept.setDept(TEST_CODE_INVALID_DEPT_ID);
		timeCollectionRuleWIthInvalidDept.setEffDate(TEST_DATE);
		timeCollectionRuleWIthInvalidDept.setHrsDistributionF(true);
		timeCollectionRuleWIthInvalidDept.setTimeStamp(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		timeCollectionRuleWIthInvalidDept.setUserPrincipalId(TEST_CODE);
		// timeCollectionRule.setWorkArea(TEST_ID_LONG);
		KNSServiceLocator.getBusinessObjectService().save(
				timeCollectionRuleWIthInvalidDept);
		timeCollectionRuleIdWithInvalidDept = timeCollectionRuleWIthInvalidDept
				.getTkTimeCollectionRuleId();

		// saving department
		Department department = new Department();
		department.setChart(TEST_CODE_DEPARTMENT_VALID);
		department.setDept(TEST_CODE_DEPARTMENT_VALID);
		department.setDescription(TEST_CODE_DEPARTMENT_VALID);
		department.setOrg(TEST_CODE_DEPARTMENT_VALID);
		KNSServiceLocator.getBusinessObjectService().save(department);
		
		TimeCollectionRule timeCollectionRuleWIthInvalidWorkArea = new TimeCollectionRule();
		// setting workAreaId for which Workarea doesn't exist .
		for (;;) {
			long workAreaIndex = randomObj.nextInt();
			WorkArea workAreaObj = KNSServiceLocator.getBusinessObjectService()
					.findBySinglePrimaryKey(WorkArea.class, workAreaIndex);
			if (workAreaObj == null) {
				TEST_CODE_INVALID_WORKAREA = new Long(workAreaIndex);
				break;
			}
		}
		timeCollectionRuleWIthInvalidWorkArea
				.setDept(TEST_CODE_DEPARTMENT_VALID);
		timeCollectionRuleWIthInvalidWorkArea.setEffDate(TEST_DATE);
		timeCollectionRuleWIthInvalidWorkArea.setHrsDistributionF(true);
		timeCollectionRuleWIthInvalidWorkArea.setTimeStamp(new Timestamp(
				Calendar.getInstance().getTimeInMillis()));
		timeCollectionRuleWIthInvalidWorkArea.setUserPrincipalId(TEST_CODE);
		timeCollectionRuleWIthInvalidWorkArea
				.setWorkArea(TEST_CODE_INVALID_WORKAREA);
		KNSServiceLocator.getBusinessObjectService().save(
				timeCollectionRuleWIthInvalidWorkArea);
		timeCollectionRuleIdWithInvalidWorkArea = timeCollectionRuleWIthInvalidWorkArea
				.getTkTimeCollectionRuleId();

	}

	@Override
	public void tearDown() throws Exception {
		// cleaning up
		TimeCollectionRule timeCollectionRuleObj = KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						TimeCollectionRule.class,
						timeCollectionRuleIdWithInvalidDept);
		KNSServiceLocator.getBusinessObjectService().delete(
				timeCollectionRuleObj);

		timeCollectionRuleObj = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(TimeCollectionRule.class,
						timeCollectionRuleIdWithInvalidWorkArea);
		KNSServiceLocator.getBusinessObjectService().delete(
				timeCollectionRuleObj);

		Department deptObj = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(Department.class,
						TEST_CODE_DEPARTMENT_VALID);
		KNSServiceLocator.getBusinessObjectService().delete(deptObj);		
		super.tearDown();
	}

}
