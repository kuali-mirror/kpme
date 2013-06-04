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
package org.kuali.hr.time.overtime.daily;


import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.tklm.utils.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DailyOvertimeRuleMaintenanceTest extends KPMEWebTestCase{
    private static final Logger LOG = Logger.getLogger(DailyOvertimeRuleMaintenanceTest.class);
	private static final String TEST_CODE="BL";		
	private static String TEST_CODE_INVALID_DEPT_ID ="INVALID";
	private static Long TEST_CODE_INVALID_TASK_ID =-1L;
	private static Long TEST_CODE_INVALID_WORK_AREA_ID =-1L;	
	private static String dailyOvertimeRuleId = "5";
	
	@Test
	public void testDailyOvertimeRuleMaintForErrorMessages() throws Exception {
		HtmlPage maintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.DAILY_OVERTIME_RULE_MAINT_NEW_URL);

		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_Description");
		
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.effectiveDate", "01/01/2010");
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.location", "BL");
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.paytype", "HR");
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.dept", TEST_CODE_INVALID_DEPT_ID);
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.workArea", TEST_CODE_INVALID_WORK_AREA_ID.toString());
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.maxGap", "1.0");
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.minHours", "2");
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.earnCode", "OVT");
		HtmlUnitUtil.setFieldValue(maintPage, "document.newMaintainableObject.fromEarnGroup", "RGN");
		
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		LOG.debug(resultantPageAfterEdit.asText());
		Assert.assertTrue("Maintenance Page contains test deptErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ TEST_CODE_INVALID_DEPT_ID
								+ "' does not exist."));
		
		// test Convert from EarnCodeGroup has overtime earn codes error
		HtmlUnitUtil.setFieldValue(resultantPageAfterEdit, "document.newMaintainableObject.fromEarnGroup", "OVT");
		HtmlPage finalPage = HtmlUnitUtil.clickInputContainingText(resultantPageAfterEdit, "submit");
		Assert.assertTrue("Maintenance Page should contains EarnCodeGroup has overtime earn code error",
				finalPage.asText().contains("Earn Group 'OVT' has overtime earn codes."));
		
	}

}