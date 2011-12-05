package org.kuali.hr.time.collection.rule;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.junit.Test;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

public class TimeCollectionRuleMaintTest extends TkTestCase {

	private static final String TEST_CODE = "X";
	private static final java.sql.Date TEST_DATE = new java.sql.Date(Calendar
			.getInstance().getTimeInMillis());
	private static final String TEST_CODE_DEPARTMENT_VALID = "_test";

	private static Long timeCollectionRuleIdWithInvalidDept;
	private static Long timeCollectionRuleIdWithInvalidWorkArea;
	private static Long deptId;


	private static String TEST_CODE_INVALID_DEPT_ID = "0";
	private static Long TEST_CODE_INVALID_WORKAREA = 2L;

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
		HtmlUnitUtil.createTempFile(timeCollectionRuleLookup);
		assertTrue("Page contains test timeCollectionRule",
				timeCollectionRuleLookup.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				timeCollectionRuleLookup, "edit",
				timeCollectionRuleIdWithInvalidDept.toString());
		HtmlUnitUtil.createTempFile(maintPage);
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		assertTrue("Maintenance Page contains test timeCollectionRule",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ TEST_CODE_INVALID_DEPT_ID
								+ "' does not exist."));
		assertTrue("Maintenance Page contains test timeCollectionRule",
				resultantPageAfterEdit.asText().contains("Clock User needs to be checked if Hr Distribution is checked."));
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
		HtmlUnitUtil.createTempFile(maintPage);
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
		Department department = new Department();
		department.setDept(TEST_CODE_DEPARTMENT_VALID);
		department.setChart(TEST_CODE_DEPARTMENT_VALID);
		department.setDescription(TEST_CODE_DEPARTMENT_VALID);
		department.setOrg(TEST_CODE_DEPARTMENT_VALID);
		department.setLocation("TST");
		KNSServiceLocator.getBusinessObjectService().save(department);
		deptId = department.getHrDeptId();
		TimeCollectionRule timeCollectionRuleWIthInvalidDept = new TimeCollectionRule();
		// setting deptId for which Department doesn't exist .
		Random randomObj = new Random();
		for (;;) {
			long deptIdIndex = randomObj.nextInt();
			Criteria crit = new Criteria();
			crit.addEqualTo("dept", deptIdIndex);
			Query query = QueryFactory.newQuery(Department.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);


			if (count == 0) {
				TEST_CODE_INVALID_DEPT_ID = Long.toString(deptIdIndex);
				break;
			}
		}
		timeCollectionRuleWIthInvalidDept.setDept(TEST_CODE_INVALID_DEPT_ID);
		timeCollectionRuleWIthInvalidDept.setEffectiveDate(TEST_DATE);
		timeCollectionRuleWIthInvalidDept.setHrsDistributionF(true);
		timeCollectionRuleWIthInvalidDept.setTimestamp(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		timeCollectionRuleWIthInvalidDept.setUserPrincipalId(TEST_CODE);
		// timeCollectionRule.setWorkArea(TEST_ID_LONG);
		KNSServiceLocator.getBusinessObjectService().save(
				timeCollectionRuleWIthInvalidDept);
		timeCollectionRuleIdWithInvalidDept = timeCollectionRuleWIthInvalidDept
				.getTkTimeCollectionRuleId();

		TimeCollectionRule timeCollectionRuleWIthInvalidWorkArea = new TimeCollectionRule();
		// setting workAreaId for which Workarea doesn't exist .
		for (;;) {
			long workAreaIndex = randomObj.nextInt();
			Criteria crit = new Criteria();
			crit.addEqualTo("workArea", workAreaIndex);
			Query query = QueryFactory.newQuery(WorkArea.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);


			if (count == 0) {
				TEST_CODE_INVALID_WORKAREA = new Long(workAreaIndex);
				break;
			}
		}
		timeCollectionRuleWIthInvalidWorkArea
				.setDept(TEST_CODE_DEPARTMENT_VALID);
		timeCollectionRuleWIthInvalidWorkArea.setEffectiveDate(TEST_DATE);
		timeCollectionRuleWIthInvalidWorkArea.setHrsDistributionF(true);
		timeCollectionRuleWIthInvalidWorkArea.setTimestamp(new Timestamp(
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

		timeCollectionRuleObj = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(TEST_CODE_DEPARTMENT_VALID,
									TEST_CODE_INVALID_WORKAREA, TKUtils.getCurrentDate());
		timeCollectionRuleObj = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(TimeCollectionRule.class,
						timeCollectionRuleIdWithInvalidWorkArea);
		KNSServiceLocator.getBusinessObjectService().delete(
				timeCollectionRuleObj);

		Department deptObj = TkServiceLocator.getDepartmentService().getDepartment(TEST_CODE_DEPARTMENT_VALID, TKUtils.getCurrentDate());
		KNSServiceLocator.getBusinessObjectService().delete(deptObj);
		super.tearDown();
	}

}
