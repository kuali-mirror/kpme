package org.kuali.hr.time.earngroup.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
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
		earnGroup.setEarnGroup("testGroup");
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
	public void testEditExistingEarnGroup() throws Exception {
		HtmlPage earnGroupLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EARN_GROUP_MAINT_URL);
		earnGroupLookUp = HtmlUnitUtil.clickInputContainingText(earnGroupLookUp, "search");
		assertTrue("Page contains test Earn Group", earnGroupLookUp.asText().contains("test"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnGroupLookUp, "edit", tkEarnGroupId.toString());		
		assertTrue("Maintenance Page contains test ClockLog",maintPage.asText().contains("test"));
		HtmlTextInput text  = (HtmlTextInput) maintPage.getHtmlElementById("document.documentHeader.documentDescription");
		text.setValueAttribute("test1");
		HtmlElement element = maintPage.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
        assertTrue("Maintenance page is submitted successfully", finalPage.asText().contains("Document was successfully submitted."));
	
	}
	
	
	@Test
	//tests EarnGroupValidation
	public void testSubmitEarnGroupMaint() throws Exception {
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.earngroup.EarnGroup&methodToCall=start";
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	assertNotNull(page);

		HtmlTextInput text  = (HtmlTextInput) page.getHtmlElementById("document.documentHeader.documentDescription");
		text.setValueAttribute("test");
		text  = (HtmlTextInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "descr");
		text.setValueAttribute("Test Earn Group");
		text  = (HtmlTextInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "earnGroup");
		text.setValueAttribute("Test");
		// set an old effective date so that the earn code that's added later is not effective by that date 
		text  = (HtmlTextInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "effectiveDate");
		text.setValueAttribute("12/01/2008");
		
		HtmlCheckBoxInput checkbox = (HtmlCheckBoxInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "showSummary");
		checkbox.setChecked(true);
		checkbox = (HtmlCheckBoxInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "active");
		checkbox.setChecked(true);
				
		// add an Earn code that is being used by another active earn group, submit, should generate error message
		text  = (HtmlTextInput) page.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "add.earnGroups.earnCode");
		text.setValueAttribute(EARN_CODE);
		HtmlElement element = page.getElementByName("methodToCall.addLine.earnGroups.(!!org.kuali.hr.time.earngroup.EarnGroupDefinition!!).(:::;2;:::).anchor2");
		HtmlPage page1 = element.click();
		//error for earn code not being effective by the effectiveDate of the earn group
		assertTrue("Maintenance Page contains error messages",page1.asText().contains("The specified Earncode '" + EARN_CODE + "' does not exist."));
      
        // set the effective date to one that works for the earn code
        text  = (HtmlTextInput) page1.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "effectiveDate");
		text.setValueAttribute("12/01/2010");
		element = page1.getElementByName("methodToCall.addLine.earnGroups.(!!org.kuali.hr.time.earngroup.EarnGroupDefinition!!).(:::;2;:::).anchor2");
		HtmlPage page2 = element.click();
		assertFalse("Page contains Error", page2.asText().contains("error"));
		
		// add the same earn code again to get the duplicate error
		text  = (HtmlTextInput) page2.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "add.earnGroups.earnCode");
		text.setValueAttribute(EARN_CODE);
		element = page2.getElementByName("methodToCall.addLine.earnGroups.(!!org.kuali.hr.time.earngroup.EarnGroupDefinition!!).(:::;2;:::).anchor2");
		page1 = element.click();
		assertTrue("Maintenance Page contains error messages",page1.asText().contains(EARN_CODE + " is already a part of this earngroup."));
		
		
		element = page1.getElementByName("methodToCall.route");
        HtmlPage finalPage = element.click();
		// error for earn code that is being used by another earn group
        assertTrue("Maintenance Page contains error messages", finalPage.asText().contains(EARN_CODE + " is used by another earn group - 'test'."));
		
		//delete this earn code
		element = finalPage.getElementByName("methodToCall.deleteLine.earnGroups.(!!.line0.(:::;3;:::).anchor2");
		HtmlPage page3 = element.click();
		assertFalse("Page contains Error", page3.asText().contains("error"));
		
		//add an earn code that is not being used, submit, should get success message
		text  = (HtmlTextInput) page3.getHtmlElementById(TkTestConstants.DOC_NEW_ELEMENT_ID_PREFIX + "add.earnGroups.earnCode");
		text.setValueAttribute("SDR");
		element = page3.getElementByName("methodToCall.addLine.earnGroups.(!!org.kuali.hr.time.earngroup.EarnGroupDefinition!!).(:::;2;:::).anchor2");
		page1 = element.click();
		assertFalse("Page contains Error", page1.asText().contains("error"));
		element = page1.getElementByName("methodToCall.route");
        finalPage = element.click();
        assertTrue("Maintenance page is submitted successfully", finalPage.asText().contains("Document was successfully submitted."));
		assertTrue("Maintenance page is submitted successfully", finalPage.asText().contains("Status: 	 FINAL"));
	}
	
	
}
