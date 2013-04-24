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
package org.kuali.hr.time.overtime.weekly.rule;

import java.math.BigDecimal;
import java.util.Calendar;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.tklm.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WeeklyOvertimeRuleMaintenanceTest extends KPMETestCase {
	
	protected static final String TEST_CODE="TST";
	private static final BigDecimal TEST_NO=new BigDecimal(10);
	private static String weeklyOvertimeRuleId;	
	private static final LocalDate TEST_DATE = LocalDate.now();
	private static final java.sql.Timestamp TEST_TIME_STAMP=new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	
	@Test
	public void testWeeklyOvertimeRuleMaint() throws Exception {
		HtmlPage maintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.WEEKLY_OVERTIME_RULE_MAINT_URL);
		Assert.assertTrue("Maintenance Page contains test WeeklyOvertimeRule",maintPage.asText().contains(TEST_CODE));
		
		// test Convert from EarnCodeGroup has overtime earn codes error		
		HtmlPage newMaintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.WEEKLY_OVERTIME_RULE_MAINT_NEW_URL);
		setFieldValue(newMaintPage, "document.documentHeader.documentDescription", "Test");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.effectiveDate", "01/01/2010");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHoursEarnGroup", "REG");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertFromEarnGroup", "OVT");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertToEarnCode", "RGN");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.step", "1");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHours", "8");
//		HtmlPage finalPage = HtmlUnitUtil.clickInputContainingText(newMaintPage, "add");
		HtmlElement element = (HtmlElement)newMaintPage.getElementById("methodToCall.addLine.lstWeeklyOvertimeRules.(!!org.kuali.hr.tklm.time.overtime.weekly.rule.WeeklyOvertimeRule!!)");
        HtmlPage finalPage = element.click();
		Assert.assertTrue("Maintenance Page should contains EarnCodeGroup has overtime earn code error",
				finalPage.asText().contains("Earn Group 'OVT' has overtime earn codes."));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		WeeklyOvertimeRule weeklyOvertimeRule = new WeeklyOvertimeRule();
		weeklyOvertimeRule.setActive(true);
		weeklyOvertimeRule.setConvertFromEarnGroup(TEST_CODE);
		weeklyOvertimeRule.setConvertToEarnCode(TEST_CODE);
		weeklyOvertimeRule.setEffectiveLocalDate(TEST_DATE);
		weeklyOvertimeRule.setMaxHours(TEST_NO);
		weeklyOvertimeRule.setMaxHoursEarnGroup(TEST_CODE);
		weeklyOvertimeRule.setStep(TEST_NO);
		weeklyOvertimeRule.setTimestamp(TEST_TIME_STAMP);
		weeklyOvertimeRule.setUserPrincipalId(TEST_CODE);	
		KRADServiceLocator.getBusinessObjectService().save(weeklyOvertimeRule);		
		weeklyOvertimeRuleId=weeklyOvertimeRule.getTkWeeklyOvertimeRuleId();		
	}

	@Override
	public void tearDown() throws Exception {	
		//clean up
		WeeklyOvertimeRule weeklyOvertimeRuleObj= KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(WeeklyOvertimeRule.class, weeklyOvertimeRuleId);			
		KRADServiceLocator.getBusinessObjectService().delete(weeklyOvertimeRuleObj);				
		super.tearDown();
	}

}
