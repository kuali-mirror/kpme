package org.kuali.hr.time.overtime.daily.rule;


import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DailyOvertimeRuleMaintenanceTest extends TkTestCase{
		
	private static final String TEST_CODE="BL";		
	private static String TEST_CODE_INVALID_DEPT_ID ="INVALID";
	private static Long TEST_CODE_INVALID_TASK_ID =-1L;
	private static Long TEST_CODE_INVALID_WORK_AREA_ID =-1L;	
	private static Long dailyOvertimeRuleId = 5L;
	
	@Test
	public void testDailyOvertimeRuleMaint() throws Exception {
		DailyOvertimeRule dor = new DailyOvertimeRule();
		HtmlPage dailyOvertimeRuleLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DAILY_OVERTIME_RULE_MAINT_URL);
		dailyOvertimeRuleLookUp = HtmlUnitUtil.clickInputContainingText(dailyOvertimeRuleLookUp, "search");
		HtmlUnitUtil.createTempFile(dailyOvertimeRuleLookUp);
		assertTrue("Page contains test DailyOvertimeRule", dailyOvertimeRuleLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(dailyOvertimeRuleLookUp, "edit",dailyOvertimeRuleId.toString());		
		assertTrue("Maintenance Page contains test DailyOvertimeRule",maintPage.asText().contains(TEST_CODE.toString()));		
	}
	
	@Test
	public void testDailyOvertimeRuleMaintForErrorMessages() throws Exception {
		HtmlPage dailyOvertimeRuleLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DAILY_OVERTIME_RULE_MAINT_URL);
		dailyOvertimeRuleLookUp = HtmlUnitUtil.clickInputContainingText(dailyOvertimeRuleLookUp, "search");
		assertTrue("Page contains test DailyOvertimeRule", dailyOvertimeRuleLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(dailyOvertimeRuleLookUp, "edit",dailyOvertimeRuleId.toString());		
		assertTrue("Maintenance Page contains test DailyOvertimeRule",maintPage.asText().contains(TEST_CODE.toString()));
		
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		System.out.println(resultantPageAfterEdit.asText());
		assertTrue("Maintenance Page contains test deptErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ TEST_CODE_INVALID_DEPT_ID
								+ "' does not exist."));
		
		// test Convert from EarnGroup has overtime earn codes error
		setFieldValue(resultantPageAfterEdit, "document.newMaintainableObject.fromEarnGroup", "OVT");
		HtmlPage finalPage = HtmlUnitUtil.clickInputContainingText(resultantPageAfterEdit, "submit");
		assertTrue("Maintenance Page should contains EarnGroup has overtime earn code error",
				finalPage.asText().contains("Earn Group 'OVT' has overtime earn codes."));
		
	}

	@Override
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		DailyOvertimeRule dor = new DailyOvertimeRule();
		dor.setLocation("BL");
		dor.setPaytype("HR");
		dor.setEffectiveDate(TKUtils.getCurrentDate());
		dor.setUserPrincipalId("admin");
		dor.setDept(TEST_CODE_INVALID_DEPT_ID);
		dor.setWorkArea(TEST_CODE_INVALID_WORK_AREA_ID);
		dor.setMaxGap(new BigDecimal(1.0));
		dor.setMinHours(new BigDecimal(2));
		dor.setActive(true);
		dor.setFromEarnGroup("RGN");
		dor.setEarnCode("OVT");
		
		KNSServiceLocator.getBusinessObjectService().save(dor);
		dailyOvertimeRuleId = dor.getTkDailyOvertimeRuleId();
	}

	@Override
	public void tearDown() throws Exception {
		KNSServiceLocator.getBusinessObjectService().deleteMatching(DailyOvertimeRule.class, new HashMap());
		super.tearDown();
	}
}

