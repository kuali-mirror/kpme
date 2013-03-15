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
package org.kuali.hr.time.workschedule;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Ignore
public class WorkScheduleMaintTest extends KPMETestCase{
	
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
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.WORK_SCHEDULE_MAINT_URL);
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
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.WORK_SCHEDULE_MAINT_URL);
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
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.WORK_SCHEDULE_MAINT_URL);
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
