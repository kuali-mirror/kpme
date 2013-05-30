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
package org.kuali.hr.time.lunch.rule;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.util.HrTestConstants;
import org.kuali.kpme.core.util.HtmlUnitUtil;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentLunchRuleMaintTest extends KPMETestCase {
    private static final Logger LOG = Logger.getLogger(DepartmentLunchRuleMaintTest.class);
    private static final String TEST_CODE = "admin";
	private static String TEST_CODE_DEPT_INVALID = "INVALID";
	private static Long TEST_CODE_WORKAREA_INVALID = 9999L;
	
	private static Long deptLunchRuleIdWithInvalidDept = 1L;	
	private static Long deptLunchRuleIdWithInvalidWorkArea = 2L;

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
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.DEPT_LUNCH_RULE_MAINT_URL);
		departmentLunchRuleLookUp = HtmlUnitUtil.clickInputContainingText(
				departmentLunchRuleLookUp, "search");
		Assert.assertTrue("Page contains test DepartmentLunchRule",
				departmentLunchRuleLookUp.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				departmentLunchRuleLookUp, "edit",
				deptLunchRuleIdWithInvalidDept.toString());
		HtmlUnitUtil.createTempFile(maintPage);
		
		HtmlInput inputForDept = HtmlUnitUtil.getInputContainingText(maintPage,
				"newMaintainableObject.dept");
		inputForDept.setValueAttribute(TEST_CODE_DEPT_INVALID);
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		LOG.debug(resultantPageAfterEdit.asText());
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
	public void testDepartmentLunchRuleMaintForWorkAreaErrorMessage()
			throws Exception {
		HtmlPage departmentLunchRuleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.DEPT_LUNCH_RULE_MAINT_URL);
		departmentLunchRuleLookUp = HtmlUnitUtil.clickInputContainingText(
				departmentLunchRuleLookUp, "search");
		Assert.assertTrue("Page contains test DepartmentLunchRule",
				departmentLunchRuleLookUp.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				departmentLunchRuleLookUp, "edit",
				deptLunchRuleIdWithInvalidWorkArea.toString());
		HtmlUnitUtil.createTempFile(maintPage);
		
		HtmlInput inputForWorkArea = HtmlUnitUtil.getInputContainingText(maintPage,
				"newMaintainableObject.workArea");
		inputForWorkArea.setValueAttribute(Long.toString(TEST_CODE_WORKAREA_INVALID));
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");		
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		Assert.assertTrue("Maintenance Page contains contains error message for workarea",
				resultantPageAfterEdit.asText().contains(
						"The specified workArea '" + TEST_CODE_WORKAREA_INVALID
								+ "' does not exist."));
	}

	@Test
	public void testDepartmentLunchRuleMaintForMaxShiftHourErrorMessage() throws Exception {
		HtmlPage departmentLunchRuleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.DEPT_LUNCH_RULE_MAINT_URL);
		departmentLunchRuleLookUp = HtmlUnitUtil.clickInputContainingText(
				departmentLunchRuleLookUp, "search");
		Assert.assertTrue("Page contains test DepartmentLunchRule",
				departmentLunchRuleLookUp.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				departmentLunchRuleLookUp, "edit",
				deptLunchRuleIdWithInvalidWorkArea.toString());
		
		setFieldValue(maintPage, HrTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "shiftHours", "34");
		setFieldValue(maintPage, "document.documentHeader.documentDescription", "test");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");		
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		Assert.assertTrue("Maintenance Page contains contains error message for Shift Hours",
				resultantPageAfterEdit.asText().contains("Shift Hour cannot be greater than 24."));
	}
	
	@Test
	public void testDepartmentLunchRuleMaint() throws Exception {
		HtmlPage departmentLunchRuleLookUp = HtmlUnitUtil
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.DEPT_LUNCH_RULE_MAINT_URL);
		departmentLunchRuleLookUp = HtmlUnitUtil.clickInputContainingText(
				departmentLunchRuleLookUp, "search");
		Assert.assertTrue("Page contains test DepartmentLunchRule",
				departmentLunchRuleLookUp.asText().contains(
						TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				departmentLunchRuleLookUp, "edit",
				deptLunchRuleIdWithInvalidDept.toString());
		Assert.assertTrue("Maintenance Page contains test DepartmentLunchRule",
				maintPage.asText().contains(TEST_CODE.toString()));
	}
}
