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
package org.kuali.hr.time.overtime.weekly;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.FunctionalTest;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


@FunctionalTest
public class WeeklyOvertimeRuleGroupMaintenanceTest extends WeeklyOvertimeRuleMaintenanceTest {

	protected static final String OVERTIME_EARN_GROUP="OVT";
	@Test
	//tests WeeklyOvertimeRuleValidation
	public void testSubmitWeeklyOvertimeRuleGroupMaint() throws Exception {
    	String baseUrl = getBaseURL() + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRuleGroup&methodToCall=edit";
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
    	Assert.assertNotNull(page);

    	HtmlForm form = page.getFormByName("KualiForm");
    	Assert.assertNotNull("Search form was missing from page.", form);

    	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
    	Assert.assertNotNull("Could not locate submit button", input);
    	HtmlUnitUtil.setFieldValue(page, "document.documentHeader.documentDescription", "test");
		HtmlElement element = page.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
        //HtmlUnitUtil.createTempFile(finalPage, "final");
        Assert.assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified maxHoursEarnGroup '" + TEST_CODE + "' does not exist."));
        Assert.assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified convertFromEarnGroup '" + TEST_CODE + "' does not exist."));
        Assert.assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified convertToEarnCode '" + TEST_CODE + "' does not exist."));

		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.effectiveDate", "4/01/2011");
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHoursEarnGroup", "REG");
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertFromEarnGroup", "REG");
        HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.applyToEarnGroup", "REG");
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertToEarnCode", OVERTIME_EARN_GROUP);
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHours", "5");
		HtmlUnitUtil.setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.step", "10");
        element = (HtmlElement)page.getElementById("methodToCall.addLine.lstWeeklyOvertimeRules.(!!org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRule!!)");
        Assert.assertNotNull("Missing Add Line button", element);

        HtmlPage addPage = element.click();
        //HtmlUnitUtil.createTempFile(addPage, "worgmt");

        Assert.assertTrue("Maintenance Page contains error messages",addPage.asText().contains("Rule already exists for step '10'."));
	}

}
