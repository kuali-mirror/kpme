package org.kuali.hr.time.shiftdiff.rule;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ShiftDifferentialRuleMaintTest extends KPMETestCase{
	private static final String TEST_CODE="_T";
		
	private static final BigDecimal TEST_NO=new BigDecimal(2);
	private static String shiftDifferentialRuleId;
	private static final String TEST_TIME= "11:00 PM";
	private static final Date TEST_DATE= new Date(Calendar.getInstance().getTimeInMillis());
	
	
	@Test
	public void testShiftDifferentialRuleMaint() throws Exception {	 
		HtmlPage shiftDifferentialRuleLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.SHIFT_DIFFERENTIAL_RULE_MAINT_URL);
		shiftDifferentialRuleLookup = HtmlUnitUtil.clickInputContainingText(shiftDifferentialRuleLookup, "search");
		Assert.assertTrue("Page contains test ShiftDifferentialRule", shiftDifferentialRuleLookup.asText().contains(TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(shiftDifferentialRuleLookup, "edit",shiftDifferentialRuleId.toString());
		Assert.assertTrue("Maintenance Page contains test ShiftDifferentialRule",maintPage.asText().contains(TEST_CODE));	 
	}
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.SHIFT_DIFFERENTIAL_RULE_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	// the following fields should have default values
	  	String errorMessage = "Min. Hours (Min. Hours) is a required field.";
	  	Assert.assertFalse("page text contains:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage = "Begin Time (00:00 AM) (Begin Time) is a required field.";
	    Assert.assertFalse("page text contains:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage = "End Time (00:00 AM) (End Time) is a required field.";
	    Assert.assertFalse("page text contains:\n" + errorMessage, page.asText().contains(errorMessage));
	    errorMessage = "Max. Gap Minutes (Max. Gap Minutes) is a required field.";
	    Assert.assertFalse("page text contains:\n" + errorMessage, page.asText().contains(errorMessage));
	  	
	    //remove the default values
	  	setFieldValue(page, "document.newMaintainableObject.minHours", "");
	  	setFieldValue(page, "document.newMaintainableObject.beginTime", "");
	  	setFieldValue(page, "document.newMaintainableObject.endTime", "");
	  	setFieldValue(page, "document.newMaintainableObject.maxGap", "");
	  	element = page.getElementByName("methodToCall.route");
	  	HtmlPage nextPage = element.click();
	  	errorMessage = "Effective Date (Effective Date) is a required field.";
	  	Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Location (Location) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Sal. Group (Sal. Group) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Pay Grade (Pay Grade) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Earn Code (Earn Code) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "From Earn Group (From Earn Group) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Begin Time (00:00 AM) (Begin Time) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "End Time (00:00 AM) (End Time) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Max. Gap Minutes (Max. Gap Minutes) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Pay Calendar Group (Pay Calendar Group) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	    errorMessage = "Min. Hours (Min. Hours) is a required field.";
	    Assert.assertTrue("page text does not contain:\n" + errorMessage, nextPage.asText().contains(errorMessage));
	}


	@Override
	public void setUp() throws Exception {
		super.setUp();
		ShiftDifferentialRule shiftDifferentialRule = new ShiftDifferentialRule();
		shiftDifferentialRule.setActive(true);
		//shiftDifferentialRule.setBeginTime(TEST_TIME);
		shiftDifferentialRule.setEarnCode(TEST_CODE);
		shiftDifferentialRule.setEffectiveDate(TEST_DATE);
		//shiftDifferentialRule.setEndTime(TEST_TIME);
		shiftDifferentialRule.setLocation(TEST_CODE);
		shiftDifferentialRule.setMaxGap(new BigDecimal(2));
		shiftDifferentialRule.setMinHours(TEST_NO);
		shiftDifferentialRule.setPayGrade(TEST_CODE);	
		shiftDifferentialRule.setPyCalendarGroup("BW-CAL1");
		shiftDifferentialRule.setSunday(true);	
		shiftDifferentialRule.setMonday(true);
		shiftDifferentialRule.setTuesday(true);
		shiftDifferentialRule.setWednesday(true);
		shiftDifferentialRule.setThursday(true);
		shiftDifferentialRule.setFriday(true);
		shiftDifferentialRule.setSaturday(true);
		shiftDifferentialRule.setPyCalendarGroup("TEST");
		
		KRADServiceLocator.getBusinessObjectService().save(shiftDifferentialRule);
		shiftDifferentialRuleId = shiftDifferentialRule.getTkShiftDiffRuleId();
	}

	@Override
	public void tearDown() throws Exception {				
		ShiftDifferentialRule shiftDifferentialRuleObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(ShiftDifferentialRule.class, shiftDifferentialRuleId);		 
		KRADServiceLocator.getBusinessObjectService().delete(shiftDifferentialRuleObj);
		super.tearDown();
	}
}

