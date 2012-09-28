/**
 * Copyright 2004-2012 The Kuali Foundation
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

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class WeeklyOvertimeRuleGroupMaintenanceTest extends WeeklyOvertimeRuleMaintenanceTest {

	protected static final String OVERTIME_EARN_GROUP="OVT";
	@Test
	//tests WeeklyOvertimeRuleValidation
	public void testSubmitWeeklyOvertimeRuleGroupMaint() throws Exception {
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRuleGroup&methodToCall=edit";
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	Assert.assertNotNull(page);

    	HtmlForm form = page.getFormByName("KualiForm");
    	Assert.assertNotNull("Search form was missing from page.", form);

    	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
    	Assert.assertNotNull("Could not locate submit button", input);
    	setFieldValue(page, "document.documentHeader.documentDescription", "test");
		HtmlElement element = page.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
        //HtmlUnitUtil.createTempFile(finalPage, "final");
        Assert.assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified maxHoursEarnGroup '" + TEST_CODE + "' does not exist."));
        Assert.assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified convertFromEarnGroup '" + TEST_CODE + "' does not exist."));
        Assert.assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified convertToEarnCode '" + TEST_CODE + "' does not exist."));

		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.effectiveDate", "4/01/2011");
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHoursEarnGroup", "REG");
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertFromEarnGroup", "REG");
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertToEarnCode", OVERTIME_EARN_GROUP);
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHours", "5");
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.step", "10");
        element = page.getElementById("methodToCall.addLine.lstWeeklyOvertimeRules.(!!org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule!!)");
        Assert.assertNotNull("Missing Add Line button", element);

        HtmlPage addPage = element.click();
        //HtmlUnitUtil.createTempFile(addPage, "worgmt");

        Assert.assertTrue("Maintenance Page contains error messages",addPage.asText().contains("Rule already exists for step '10'."));
	}

}
