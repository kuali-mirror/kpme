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
package org.kuali.hr.time.overtime.weekly;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRule;
import org.kuali.kpme.tklm.utils.TkTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@FunctionalTest
public class WeeklyOvertimeRuleMaintenanceTest extends KPMEWebTestCase {
	
	protected static final String TEST_CODE="TST";
	private static final BigDecimal TEST_NO=new BigDecimal(10);
	private static String weeklyOvertimeRuleId;	
	private static final LocalDate TEST_DATE = LocalDate.now();
	
	@Test
	public void testWeeklyOvertimeRuleMaint() throws Exception {
		HtmlPage maintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.WEEKLY_OVERTIME_RULE_MAINT_URL);
		Assert.assertTrue("Maintenance Page contains test WeeklyOvertimeRule",maintPage.asText().contains(TEST_CODE));
		
		// test Convert from EarnCodeGroup has overtime earn codes error		
		HtmlPage newMaintPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.WEEKLY_OVERTIME_RULE_MAINT_NEW_URL);
		HtmlUnitUtil.setFieldValue(newMaintPage, "document.documentHeader.documentDescription", "Test");
		HtmlUnitUtil.setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.effectiveDate", "01/01/2010");
		HtmlUnitUtil.setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHoursEarnGroup", "REG");
		HtmlUnitUtil.setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertFromEarnGroup", "OVT");
		HtmlUnitUtil.setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertToEarnCode", "RGN");
		HtmlUnitUtil.setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.step", "1");
		HtmlUnitUtil.setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHours", "8");
//		HtmlPage finalPage = HtmlUnitUtil.clickInputContainingText(newMaintPage, "add");
		HtmlElement element = (HtmlElement)newMaintPage.getElementById("methodToCall.addLine.lstWeeklyOvertimeRules.(!!org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRule!!)");
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
		weeklyOvertimeRule.setTimestamp(TKUtils.getCurrentTimestamp());
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
