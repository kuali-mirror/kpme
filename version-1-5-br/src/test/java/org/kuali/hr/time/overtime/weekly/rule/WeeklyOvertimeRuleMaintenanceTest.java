package org.kuali.hr.time.overtime.weekly.rule;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WeeklyOvertimeRuleMaintenanceTest extends TkTestCase {
	
	protected static final String TEST_CODE="TST";
	private static final BigDecimal TEST_NO=new BigDecimal(10);
	private static String weeklyOvertimeRuleId;	
	private static final java.sql.Date TEST_DATE=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	private static final java.sql.Timestamp TEST_TIME_STAMP=new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	
	@Test
	public void testWeeklyOvertimeRuleMaint() throws Exception {
		HtmlPage weeklyOvertimeRuleLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.WEEKLY_OVERTIME_RULE_MAINT_URL);
		weeklyOvertimeRuleLookUp = HtmlUnitUtil.clickInputContainingText(weeklyOvertimeRuleLookUp, "search");
		assertTrue("Page contains test WeeklyOvertimeRule", weeklyOvertimeRuleLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(weeklyOvertimeRuleLookUp, "edit",weeklyOvertimeRuleId.toString());		
		assertTrue("Maintenance Page contains test WeeklyOvertimeRule",maintPage.asText().contains(TEST_CODE.toString()));	
		
		// test Convert from EarnGroup has overtime earn codes error		
		HtmlPage newMaintPage = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.WEEKLY_OVERTIME_RULE_MAINT_NEW_URL);
		setFieldValue(newMaintPage, "document.documentHeader.documentDescription", "Test");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.effectiveDate", "01/01/2010");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHoursEarnGroup", "REG");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertFromEarnGroup", "OVT");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.convertToEarnCode", "RGN");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.step", "1");
		setFieldValue(newMaintPage, "document.newMaintainableObject.add.lstWeeklyOvertimeRules.maxHours", "8");
//		HtmlPage finalPage = HtmlUnitUtil.clickInputContainingText(newMaintPage, "add");
		HtmlElement element = newMaintPage.getElementById("methodToCall.addLine.lstWeeklyOvertimeRules.(!!org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule!!)");
        HtmlPage finalPage = element.click();
		assertTrue("Maintenance Page should contains EarnGroup has overtime earn code error",
				finalPage.asText().contains("Earn Group 'OVT' has overtime earn codes."));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		WeeklyOvertimeRule weeklyOvertimeRule = new WeeklyOvertimeRule();
		weeklyOvertimeRule.setActive(true);
		weeklyOvertimeRule.setConvertFromEarnGroup(TEST_CODE);
		weeklyOvertimeRule.setConvertToEarnCode(TEST_CODE);
		weeklyOvertimeRule.setEffectiveDate(TEST_DATE);
		weeklyOvertimeRule.setMaxHours(TEST_NO);
		weeklyOvertimeRule.setMaxHoursEarnGroup(TEST_CODE);
		weeklyOvertimeRule.setStep(TEST_NO);
		weeklyOvertimeRule.setTimestamp(TEST_TIME_STAMP);
		weeklyOvertimeRule.setUserPrincipalId(TEST_CODE);	
		KNSServiceLocator.getBusinessObjectService().save(weeklyOvertimeRule);		
		weeklyOvertimeRuleId=weeklyOvertimeRule.getTkWeeklyOvertimeRuleId();		
	}

	@Override
	public void tearDown() throws Exception {	
		//clean up
		WeeklyOvertimeRule weeklyOvertimeRuleObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(WeeklyOvertimeRule.class, weeklyOvertimeRuleId);			
		KNSServiceLocator.getBusinessObjectService().delete(weeklyOvertimeRuleObj);				
		super.tearDown();
	}

}
