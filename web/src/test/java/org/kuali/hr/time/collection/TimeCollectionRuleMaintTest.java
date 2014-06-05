/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.hr.time.collection;

import java.util.Collections;
import java.util.Random;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.utils.TkTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

@FunctionalTest
public class TimeCollectionRuleMaintTest extends KPMEWebTestCase {

	private static final String TEST_CODE = "X";
	private static final LocalDate TEST_DATE = LocalDate.now();
	private static final String TEST_CODE_DEPARTMENT_VALID = "_test";

	private static String timeCollectionRuleId;
	private static String timeCollectionRuleIdWithInvalidWorkArea;

	private static String TEST_CODE_INVALID_DEPT = "INVALID_DEPT";
	private static Long TEST_CODE_INVALID_WORKAREA = 2L;
	private static String PAY_TYPE_ERROR = "The specified payType '%' does not exist.";
	
	private static final String TEST_GRP_KEY_CD = "DEFAULT";

	/**
	 * Test to check whether it is showing error message on maintenance screen
	 * if we supply non exist deptId
	 *
	 * @throws Exception
	 */
	@Test
	public void testTimeCollectionRuleMaintForDeptErrorMessage() throws Exception {
		String baseUrl = TkTestConstants.Urls.TIME_COLLECTION_RULE_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(page, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.effectiveDate", "01/01/2010");
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.dept", TEST_CODE_INVALID_DEPT);
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.workArea", "30");
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.payType", "BW");
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.groupKeyCode", TEST_GRP_KEY_CD);
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(page, "submit");
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		Assert.assertTrue("Maintenance Page contains test timeCollectionRule",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ TEST_CODE_INVALID_DEPT
								+ "' does not exist."));
		
		HtmlUnitUtil.setFieldValue(resultantPageAfterEdit, "document.newMaintainableObject.payType", "%");
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
				timeCollectionRuleId);
		Assert.assertTrue("Maintenance Page contains test timeCollectionRule",
				maintPage.asText().contains(TEST_CODE));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		DepartmentBo department = new DepartmentBo();
		department.setDept(TEST_CODE_DEPARTMENT_VALID);
		department.setChart(TEST_CODE_DEPARTMENT_VALID);
		department.setDescription(TEST_CODE_DEPARTMENT_VALID);
		department.setOrg(TEST_CODE_DEPARTMENT_VALID);
		department.setGroupKeyCode(TEST_GRP_KEY_CD);
		//department.setLocation("BL");
		department.setEffectiveLocalDate(TEST_DATE);
        department.setActive(Boolean.TRUE);
        department.setUserPrincipalId(TEST_CODE);
		department = KRADServiceLocatorWeb.getLegacyDataAdapter().save(department);
		
		TimeCollectionRule timeCollectionRule = new TimeCollectionRule();
		timeCollectionRule.setDept(TEST_CODE_DEPARTMENT_VALID);
		timeCollectionRule.setEffectiveLocalDate(TEST_DATE);
		timeCollectionRule.setTimestamp(TKUtils.getCurrentTimestamp());
		timeCollectionRule.setUserPrincipalId(TEST_CODE);
        timeCollectionRule.setActive(true);
        timeCollectionRule.setPayType("%");
        timeCollectionRule.setGroupKeyCode(TEST_GRP_KEY_CD);
        timeCollectionRule = KRADServiceLocatorWeb.getLegacyDataAdapter().save(timeCollectionRule);
		timeCollectionRuleId = timeCollectionRule.getTkTimeCollectionRuleId();

		TimeCollectionRule timeCollectionRuleWIthInvalidWorkArea = new TimeCollectionRule();
		// setting workAreaId for which Workarea doesn't exist .
		Random randomObj = new Random();
		for (;;) {
			long workAreaIndex = randomObj.nextInt();
			int count = HrServiceLocator.getWorkAreaService().getWorkAreaCount(null, workAreaIndex);

			if (count == 0) {
				TEST_CODE_INVALID_WORKAREA = new Long(workAreaIndex);
				break;
			}
		}
		timeCollectionRuleWIthInvalidWorkArea
				.setDept(TEST_CODE_DEPARTMENT_VALID);
		timeCollectionRuleWIthInvalidWorkArea.setEffectiveLocalDate(TEST_DATE);
        timeCollectionRuleWIthInvalidWorkArea.setActive(true);
        timeCollectionRuleWIthInvalidWorkArea.setPayType("%");
        timeCollectionRuleWIthInvalidWorkArea.setGroupKeyCode(TEST_GRP_KEY_CD);
		timeCollectionRuleWIthInvalidWorkArea.setTimestamp(TKUtils.getCurrentTimestamp());
		timeCollectionRuleWIthInvalidWorkArea.setUserPrincipalId(TEST_CODE);
		timeCollectionRuleWIthInvalidWorkArea
				.setWorkArea(TEST_CODE_INVALID_WORKAREA);
        timeCollectionRuleWIthInvalidWorkArea = KRADServiceLocatorWeb.getLegacyDataAdapter().save(
				timeCollectionRuleWIthInvalidWorkArea);
		timeCollectionRuleIdWithInvalidWorkArea = timeCollectionRuleWIthInvalidWorkArea
				.getTkTimeCollectionRuleId();

	}

	@Override
	public void tearDown() throws Exception {
		// cleaning up
		TimeCollectionRule timeCollectionRuleObj = KRADServiceLocatorWeb.getLegacyDataAdapter()
                .findByPrimaryKey(TimeCollectionRule.class, Collections.singletonMap("tkTimeCollectionRuleId", timeCollectionRuleId));
        //Map<String, String> criteria = new (Collections()).singletonMap("dept")
        //Collection<TimeCollectionRule> rules = KRADServiceLocatorWeb.getLegacyDataAdapter().findMatching(TimeCollectionRule.class, )
		KRADServiceLocatorWeb.getLegacyDataAdapter().delete(
				timeCollectionRuleObj);

		timeCollectionRuleObj = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(TEST_CODE_DEPARTMENT_VALID,
									TEST_CODE_INVALID_WORKAREA, "%", TEST_GRP_KEY_CD, LocalDate.now());
		//timeCollectionRuleObj = KRADServiceLocatorWeb.getLegacyDataAdapter()
        //        .findByPrimaryKey(TimeCollectionRule.class, Collections.singletonMap("tkTimeCollectionRuleId", timeCollectionRuleIdWithInvalidWorkArea));
		KRADServiceLocatorWeb.getLegacyDataAdapter().delete(
				timeCollectionRuleObj);

		DepartmentBo deptObj = DepartmentBo.from(HrServiceLocator.getDepartmentService().getDepartment(TEST_CODE_DEPARTMENT_VALID, TEST_GRP_KEY_CD, LocalDate.now()));
		KRADServiceLocatorWeb.getLegacyDataAdapter().delete(deptObj);
		super.tearDown();
	}

}
