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
package org.kuali.hr.time.overtime.daily.rule;


import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DailyOvertimeRuleMaintenanceTest extends KPMETestCase{
		
	private static final String TEST_CODE="BL";		
	private static String TEST_CODE_INVALID_DEPT_ID ="INVALID";
	private static Long TEST_CODE_INVALID_TASK_ID =-1L;
	private static Long TEST_CODE_INVALID_WORK_AREA_ID =-1L;	
	private static String dailyOvertimeRuleId = "5";
	
	@Test
	public void testDailyOvertimeRuleMaint() throws Exception {
		DailyOvertimeRule dor = new DailyOvertimeRule();
		HtmlPage dailyOvertimeRuleLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DAILY_OVERTIME_RULE_MAINT_URL);
		dailyOvertimeRuleLookUp = HtmlUnitUtil.clickInputContainingText(dailyOvertimeRuleLookUp, "search");
		HtmlUnitUtil.createTempFile(dailyOvertimeRuleLookUp);
		Assert.assertTrue("Page contains test DailyOvertimeRule", dailyOvertimeRuleLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(dailyOvertimeRuleLookUp, "edit",dailyOvertimeRuleId.toString());		
		Assert.assertTrue("Maintenance Page contains test DailyOvertimeRule",maintPage.asText().contains(TEST_CODE.toString()));		
	}
	
	@Test
	public void testDailyOvertimeRuleMaintForErrorMessages() throws Exception {
		HtmlPage dailyOvertimeRuleLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DAILY_OVERTIME_RULE_MAINT_URL);
		dailyOvertimeRuleLookUp = HtmlUnitUtil.clickInputContainingText(dailyOvertimeRuleLookUp, "search");
		Assert.assertTrue("Page contains test DailyOvertimeRule", dailyOvertimeRuleLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(dailyOvertimeRuleLookUp, "edit",dailyOvertimeRuleId.toString());		
		Assert.assertTrue("Maintenance Page contains test DailyOvertimeRule",maintPage.asText().contains(TEST_CODE.toString()));
		
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		System.out.println(resultantPageAfterEdit.asText());
		Assert.assertTrue("Maintenance Page contains test deptErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ TEST_CODE_INVALID_DEPT_ID
								+ "' does not exist."));
		
		// test Convert from EarnCodeGroup has overtime earn codes error
		setFieldValue(resultantPageAfterEdit, "document.newMaintainableObject.fromEarnGroup", "OVT");
		HtmlPage finalPage = HtmlUnitUtil.clickInputContainingText(resultantPageAfterEdit, "submit");
		Assert.assertTrue("Maintenance Page should contains EarnCodeGroup has overtime earn code error",
				finalPage.asText().contains("Earn Group 'OVT' has overtime earn codes."));
		
	}

	@Override
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		DailyOvertimeRule dor = new DailyOvertimeRule();
		dor.setLocation("BL");
		dor.setPaytype("HR");
		dor.setEffectiveDate(TKUtils.getCurrentDate());
		dor.setUserPrincipalId("admin");
		dor.setDept(TEST_CODE_INVALID_DEPT_ID);
		dor.setWorkArea(TEST_CODE_INVALID_WORK_AREA_ID);
		dor.setMaxGap(new BigDecimal(1.0));
		dor.setMinHours(new BigDecimal(2));
		dor.setActive(true);
		dor.setFromEarnGroup("RGN");
		dor.setEarnCode("OVT");
		
		KRADServiceLocator.getBusinessObjectService().save(dor);
		dailyOvertimeRuleId = dor.getTkDailyOvertimeRuleId();
	}

	@Override
	public void tearDown() throws Exception {
		KRADServiceLocator.getBusinessObjectService().deleteMatching(DailyOvertimeRule.class, new HashMap());
		super.tearDown();
	}
}

