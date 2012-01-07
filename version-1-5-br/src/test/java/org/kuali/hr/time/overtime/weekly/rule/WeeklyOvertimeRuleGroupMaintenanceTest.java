package org.kuali.hr.time.overtime.weekly.rule;

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
    	assertNotNull(page);
   
    	HtmlForm form = page.getFormByName("KualiForm");
    	assertNotNull("Search form was missing from page.", form);
    	
    	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
    	assertNotNull("Could not locate submit button", input);
    	setFieldValue(page, "document.documentHeader.documentDescription", "test");
		HtmlElement element = page.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
		assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified maxHoursEarnGroup '" + TEST_CODE + "' does not exist."));
		assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified convertFromEarnGroup '" + TEST_CODE + "' does not exist."));
		assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified convertToEarnCode '" + TEST_CODE + "' does not exist."));
		
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.effectiveDate", "4/01/2011");
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHoursEarnGroup", OVERTIME_EARN_GROUP);
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertFromEarnGroup", OVERTIME_EARN_GROUP);
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertToEarnCode", OVERTIME_EARN_GROUP);
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHours", "5");
		setFieldValue(page, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.step", "10");
		
		element = page.getElementByName("methodToCall.addLine.lstWeeklyOvertimeRules.(!!org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule!!).(:::;1;:::).anchor1");
        HtmlPage addPage = element.click();
        assertTrue("Maintenance Page contains error messages",addPage.asText().contains("Rule already exists for step '10'."));
        
	}

}
