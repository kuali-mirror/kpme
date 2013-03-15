/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.collection.rule;

import java.sql.Timestamp;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TimeCollectionRuleMaintTest extends KPMETestCase {

	private static final String TEST_CODE = "X";
	private static final java.sql.Date TEST_DATE = new java.sql.Date(Calendar
			.getInstance().getTimeInMillis());
	private static final String TEST_CODE_DEPARTMENT_VALID = "_test";

	private static String timeCollectionRuleIdWithInvalidDept;
	private static String timeCollectionRuleIdWithInvalidWorkArea;

	private static String TEST_CODE_INVALID_DEPT_ID = "0";
	private static Long TEST_CODE_INVALID_WORKAREA = 2L;
	private static String PAY_TYPE_ERROR = "The specified payType '%' does not exist.";

	/**
	 * Test to check whether it is showing error message on maintenance screen
	 * if we supply non exist deptId
	 *
	 * @throws Exception
	 */
	@Test
	public void testTimeCollectionRuleMaintForDeptErrorMessage() throws Exception {
		HtmlPage timeCollectionRuleLookup = HtmlUnitUtil
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.TIME_COLLECTION_RULE_MAINT_URL);
		timeCollectionRuleLookup = HtmlUnitUtil.clickInputContainingText(
				timeCollectionRuleLookup, "search");
		HtmlUnitUtil.createTempFile(timeCollectionRuleLookup);
		Assert.assertTrue("Page contains test timeCollectionRule",
				timeCollectionRuleLookup.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				timeCollectionRuleLookup, "edit",
				timeCollectionRuleIdWithInvalidDept);
		HtmlUnitUtil.createTempFile(maintPage);
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		Assert.assertTrue("Maintenance Page contains test timeCollectionRule",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ TEST_CODE_INVALID_DEPT_ID
								+ "' does not exist."));
		Assert.assertTrue("Maintenance Page contains test timeCollectionRule",
				resultantPageAfterEdit.asText().contains("Clock User needs to be checked if Hr Distribution is checked."));
		setFieldValue(resultantPageAfterEdit, "document.newMaintainableObject.payType", "%");
		resultantPageAfterEdit = HtmlUnitUtil.clickInputContainingText(resultantPageAfterEdit, "submit");
		Assert.assertFalse("Maintenance Page contains error" + PAY_TYPE_ERROR, 
				resultantPageAfterEdit.asText().contains(PAY_TYPE_ERROR));
		
	}


	@Test
	public void testTimeCollectionRuleMaintForWorkAreaErrorMessage() throws Exception {
		HtmlPage timeCollectionRuleLookup = HtmlUnitUtil
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.TIME_COLLECTION_RULE_MAINT_URL);
		timeCollectionRuleLookup = HtmlUnitUtil.clickInputContainingText(
				timeCollectionRuleLookup, "search");
		Assert.assertTrue("Page contains test timeCollectionRule",
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
		Assert.assertTrue("Maintenance Page contains test timeCollectionRule",
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
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.TIME_COLLECTION_RULE_MAINT_URL);
		timeCollectionRuleLookup = HtmlUnitUtil.clickInputContainingText(
				timeCollectionRuleLookup, "search");
		Assert.assertTrue("Page contains test timeCollectionRule",
				timeCollectionRuleLookup.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				timeCollectionRuleLookup, "edit",
				timeCollectionRuleIdWithInvalidDept);
		Assert.assertTrue("Maintenance Page contains test timeCollectionRule",
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
        department.setActive(Boolean.TRUE);
		department = KRADServiceLocator.getBusinessObjectService().save(department);
		TimeCollectionRule timeCollectionRuleWIthInvalidDept = new TimeCollectionRule();
		// setting deptId for which Department doesn't exist .
		Random randomObj = new Random();
		for (;;) {
			long deptIdIndex = randomObj.nextInt();
			int count = TkServiceLocator.getDepartmentService().getDepartmentCount(Long.toString(deptIdIndex));

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
        timeCollectionRuleWIthInvalidDept.setActive(true);
		// timeCollectionRule.setWorkArea(TEST_ID_LONG);
        timeCollectionRuleWIthInvalidDept = KRADServiceLocator.getBusinessObjectService().save(
				timeCollectionRuleWIthInvalidDept);
		timeCollectionRuleIdWithInvalidDept = timeCollectionRuleWIthInvalidDept
				.getTkTimeCollectionRuleId();

		TimeCollectionRule timeCollectionRuleWIthInvalidWorkArea = new TimeCollectionRule();
		// setting workAreaId for which Workarea doesn't exist .
		for (;;) {
			long workAreaIndex = randomObj.nextInt();
			int count = TkServiceLocator.getWorkAreaService().getWorkAreaCount(null, workAreaIndex);

			if (count == 0) {
				TEST_CODE_INVALID_WORKAREA = new Long(workAreaIndex);
				break;
			}
		}
		timeCollectionRuleWIthInvalidWorkArea
				.setDept(TEST_CODE_DEPARTMENT_VALID);
		timeCollectionRuleWIthInvalidWorkArea.setEffectiveDate(TEST_DATE);
		timeCollectionRuleWIthInvalidWorkArea.setHrsDistributionF(true);
        timeCollectionRuleWIthInvalidWorkArea.setActive(true);
		timeCollectionRuleWIthInvalidWorkArea.setTimestamp(new Timestamp(
				Calendar.getInstance().getTimeInMillis()));
		timeCollectionRuleWIthInvalidWorkArea.setUserPrincipalId(TEST_CODE);
		timeCollectionRuleWIthInvalidWorkArea
				.setWorkArea(TEST_CODE_INVALID_WORKAREA);
        timeCollectionRuleWIthInvalidWorkArea = KRADServiceLocator.getBusinessObjectService().save(
				timeCollectionRuleWIthInvalidWorkArea);
		timeCollectionRuleIdWithInvalidWorkArea = timeCollectionRuleWIthInvalidWorkArea
				.getTkTimeCollectionRuleId();

	}

	@Override
	public void tearDown() throws Exception {
		// cleaning up
		TimeCollectionRule timeCollectionRuleObj = KRADServiceLocator.getBusinessObjectService()
                .findByPrimaryKey(TimeCollectionRule.class, Collections.singletonMap("tkTimeCollectionRuleId", timeCollectionRuleIdWithInvalidDept));
        //Map<String, String> criteria = new (Collections()).singletonMap("dept")
        //Collection<TimeCollectionRule> rules = KRADServiceLocator.getBusinessObjectService().findMatching(TimeCollectionRule.class, )
		KRADServiceLocator.getBusinessObjectService().delete(
				timeCollectionRuleObj);

		timeCollectionRuleObj = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(TEST_CODE_DEPARTMENT_VALID,
									TEST_CODE_INVALID_WORKAREA, TKUtils.getCurrentDate());
		//timeCollectionRuleObj = KRADServiceLocator.getBusinessObjectService()
        //        .findByPrimaryKey(TimeCollectionRule.class, Collections.singletonMap("tkTimeCollectionRuleId", timeCollectionRuleIdWithInvalidWorkArea));
		KRADServiceLocator.getBusinessObjectService().delete(
				timeCollectionRuleObj);

		Department deptObj = TkServiceLocator.getDepartmentService().getDepartment(TEST_CODE_DEPARTMENT_VALID, TKUtils.getCurrentDate());
		KRADServiceLocator.getBusinessObjectService().delete(deptObj);
		super.tearDown();
	}

}
