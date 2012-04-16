package org.kuali.hr.lm.accrualcategory.validation;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class AccrualCategoryValidationTest extends TkTestCase{
	private static final String ACCRUAL_CATEGORY = "testAC";
	private static final String ERROR_LEAVE_PLAN = "The specified leavePlan 'IU-SM-W' does not exist";
	@Test
	public void testValidateStartEndUnits() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains test AccrualCategory", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlUnitUtil.createTempFile(accrualCategoryLookup);
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		Assert.assertTrue("Maintenance Page contains test AccrualCategory",maintPage.asText().contains(ACCRUAL_CATEGORY));
	}
	
	@Test
	public void testMinWorkPercentageField() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains test AccrualCategory", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		HtmlUnitUtil.createTempFile(maintPage);
		Assert.assertTrue("Maintenance Page contains test AccrualCategory",maintPage.asText().contains("Min Percent Worked to Earn Accrual"));
	}
	
	// KPME-1347 Kagata
	@Test
	public void testAccrualIntervalEarnField() throws Exception {
		
		// make sure page loads
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains test AccrualCategory", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		//HtmlUnitUtil.createTempFile(maintPage);
		Assert.assertTrue("Maintenance Page contains test AccrualCategory",maintPage.asText().contains("Bi-Weekly"));
	}
	
	// KPME-1347 Kagata 
	@Test
	public void testDefaultLeaveCodeField() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains test AccrualCategory", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		//HtmlUnitUtil.createTempFile(maintPage);
		Assert.assertTrue("Maintenance Page contains test AccrualCategory",maintPage.asText().contains("Default Leave Code"));
	}
	
	@Test
	public void testHasRules() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		HtmlPage accrualCategoryCreate = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "AccrualCategory");
		//HtmlUnitUtil.createTempFile(accrualCategoryCreate);
		Assert.assertTrue("Page contains test AccrualCategory", accrualCategoryCreate.asText().contains("AccrualCategory Maintenance"));
		Assert.assertTrue("Maintenance Page contains test AccrualCategory", accrualCategoryCreate.asText().contains("Category Has Rules"));
	}
	@Test
	public void testMaxBalFlag() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		HtmlPage accrualCategoryCreate = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "AccrualCategory");
		//HtmlUnitUtil.createTempFile(accrualCategoryCreate);
		Assert.assertTrue("Page contains test AccrualCategory", accrualCategoryCreate.asText().contains("New Accrual Category Rule"));
		Assert.assertTrue("Maintenance Page contains test AccrualCategory", accrualCategoryCreate.asText().contains("Max Bal Flag"));
	}
	//@Test
	/*public void testValidateStartEndUnitsForErrorMessages() throws Exception {		
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		assertTrue("Page contains test Accrual Category", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		assertTrue("Maintenance Page contains test AccrualCategory",maintPage.asText().contains(ACCRUAL_CATEGORY));
		
		HtmlForm form = maintPage.getFormByName("KualiForm");
	  	assertNotNull("Search form was missing from page.", maintPage);
	  	
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_KPME1252");

		setFieldValue(maintPage, "document.newMaintainableObject.effectiveDate", "04/01/2011");
		
        HtmlInput inputForStartUnit = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.start");
        inputForStartUnit.setValueAttribute("10");

        HtmlInput inputForEndUnit = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.end");
        inputForEndUnit.setValueAttribute("24");

        HtmlInput inputForAccrualRate = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.accrualRate");
        inputForAccrualRate.setValueAttribute("0.5");

        HtmlInput inputForMaxBalance = HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.maxBalance");
        inputForMaxBalance.setValueAttribute("300");
        
        HtmlCheckBoxInput checkbox = (HtmlCheckBoxInput) HtmlUnitUtil.getInputContainingText(maintPage, "document.newMaintainableObject.add.accrualCategoryRules.active");
        checkbox.setChecked(true);
        
	  	HtmlElement element = maintPage.getElementByName("methodToCall.route");
	  	maintPage = element.click();
	  	HtmlUnitUtil.createTempFile(maintPage);
        HtmlPage resultantPageAfterEdit = HtmlUnitUtil.clickInputContainingText(maintPage, "submit");
        
        HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
        
		assertTrue("Maintenance Page contains test startEndOverLapErrormessage", resultantPageAfterEdit.asText().contains("Start and End units should not have gaps or overlaps."));
	}*/
	
	@Test
	public void testValidationOfLeavePlan() throws Exception {
		HtmlPage accrualCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCRUAL_CATEGORY_MAINT_URL);
		accrualCategoryLookup = HtmlUnitUtil.clickInputContainingText(accrualCategoryLookup, "search");
		Assert.assertTrue("Page contains test Accrual Category", accrualCategoryLookup.asText().contains(ACCRUAL_CATEGORY));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accrualCategoryLookup, "edit", "lmAccrualCategoryId=3000");
		Assert.assertTrue("Maintenance Page contains test AccrualCategory",maintPage.asText().contains(ACCRUAL_CATEGORY));
		
		HtmlForm form = maintPage.getFormByName("KualiForm");
		Assert.assertNotNull("Search form was missing from page.", maintPage);
	  	
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_KPME1355");

		setFieldValue(maintPage, "document.newMaintainableObject.leavePlan", "IU-SM-W");
	
		maintPage = maintPage.getElementByName("methodToCall.route").click();
	    HtmlUnitUtil.createTempFile(maintPage);
	    Assert.assertTrue("page text does not contain:\n" + ERROR_LEAVE_PLAN, maintPage.asText().contains(ERROR_LEAVE_PLAN));
	}
	
}
