package org.kuali.hr.time.earngroup.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class EarnGroupMaintenanceTest extends TkTestCase {
	private static final java.sql.Date TEST_DATE=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	private static final String EARN_CODE = "RGN";
	private static Long tkEarnGroupId;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		List<EarnGroupDefinition> earnGroups = new ArrayList<EarnGroupDefinition>();
		EarnGroupDefinition definition = new EarnGroupDefinition();
		definition.setEarnCode(EARN_CODE);
		earnGroups.add(definition);
		
		EarnGroup earnGroup = new EarnGroup();
		earnGroup.setDescr("test");
		earnGroup.setEffectiveDate(TEST_DATE);
		earnGroup.setShowSummary(true);
		earnGroup.setActive(true);
		earnGroup.setEarnGroups(earnGroups);
		KNSServiceLocator.getBusinessObjectService().save(earnGroup);	
		
		tkEarnGroupId = earnGroup.getTkEarnGroupId();		
	}

	@Override
	public void tearDown() throws Exception {
		EarnGroup earnGroupObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(EarnGroup.class, tkEarnGroupId);			
		KNSServiceLocator.getBusinessObjectService().delete(earnGroupObj);				
		super.tearDown();
	}
	
	@Test
	//tests EarnGroupValidation
	public void testSubmitEarnGroupMaint() throws Exception {
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.earngroup.EarnGroup&methodToCall=start";
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	assertNotNull(page);

		HtmlTextInput text  = (HtmlTextInput) page.getHtmlElementById("document.documentHeader.documentDescription");
		text.setValueAttribute("test");
		text  = (HtmlTextInput) page.getHtmlElementById("document.newMaintainableObject.descr");
		text.setValueAttribute("Test Earn Group");
		text  = (HtmlTextInput) page.getHtmlElementById("document.newMaintainableObject.effectiveDate");
		text.setValueAttribute("12/01/2010");
		
		HtmlCheckBoxInput checkbox = (HtmlCheckBoxInput) page.getHtmlElementById("document.newMaintainableObject.showSummary");
		checkbox.setChecked(true);
		checkbox = (HtmlCheckBoxInput) page.getHtmlElementById("document.newMaintainableObject.active");
		checkbox.setChecked(true);
		
		// add an Earn code that is being used by another active earn group, submit, should generate error message
		text  = (HtmlTextInput) page.getHtmlElementById("document.newMaintainableObject.add.earnGroups.earnCode");
		text.setValueAttribute(EARN_CODE);
		HtmlElement element = page.getElementByName("methodToCall.addLine.earnGroups.(!!org.kuali.hr.time.earngroup.EarnGroupDefinition!!).(:::;2;:::).anchor2");
		HtmlPage page1 = element.click();
		assertFalse("Page contains Error", page1.asText().contains("error"));
		element = page.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
		assertTrue("Maintenance Page contains error messages",finalPage.asText().contains(EARN_CODE + " is used by another earn group - 'test'."));
		
		//delete this earn code
		element = finalPage.getElementByName("methodToCall.deleteLine.earnGroups.(!!.line0.(:::;3;:::).anchor2");
		HtmlPage page2 = element.click();
		assertFalse("Page contains Error", page2.asText().contains("error"));
		
		//add an earn code that is not being used, submit, should get success message
		text  = (HtmlTextInput) page.getHtmlElementById("document.newMaintainableObject.add.earnGroups.earnCode");
		text.setValueAttribute("SDR");
		element = page.getElementByName("methodToCall.addLine.earnGroups.(!!org.kuali.hr.time.earngroup.EarnGroupDefinition!!).(:::;2;:::).anchor2");
		page1 = element.click();
		assertFalse("Page contains Error", page1.asText().contains("error"));
		element = page.getElementByName("methodToCall.route");
        finalPage = element.click();
        assertTrue("Maintenance page is submitted successfully", finalPage.asText().contains("Document was successfully submitted."));
		assertTrue("Maintenance page is submitted successfully", finalPage.asText().contains("Status: 	 FINAL"));
	}
	
	
}
