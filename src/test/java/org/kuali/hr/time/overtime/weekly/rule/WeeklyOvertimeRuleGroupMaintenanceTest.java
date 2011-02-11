package org.kuali.hr.time.overtime.weekly.rule;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;



public class WeeklyOvertimeRuleGroupMaintenanceTest extends WeeklyOvertimeRuleMaintenanceTest {
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
		HtmlTextInput text  = (HtmlTextInput) page.getHtmlElementById("document.documentHeader.documentDescription");
		text.setValueAttribute("test");
		HtmlElement element = page.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
		assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified maxHoursEarnGroup '" + TEST_CODE + "' does not exist."));
		assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified convertFromEarnGroup '" + TEST_CODE + "' does not exist."));
		assertTrue("Maintenance Page contains error messages",finalPage.asText().contains("The specified convertToEarnCode '" + TEST_CODE + "' does not exist."));
	}

}
