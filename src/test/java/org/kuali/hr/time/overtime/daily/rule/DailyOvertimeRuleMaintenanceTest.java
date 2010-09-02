package org.kuali.hr.time.overtime.daily.rule;

import java.math.BigDecimal;
import java.util.Calendar;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DailyOvertimeRuleMaintenanceTest extends TkTestCase{
	
	private static final String TEST_CODE="_T";
	private static final Long TEST_ID=20L;
	private static final BigDecimal TEST_NO=new BigDecimal(10);		
	private static final java.sql.Date TEST_DATE=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	private static final java.sql.Timestamp TEST_TIMESTAMP=new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	private static Long dailyOvertimeRuleId;
	
	@Test
	public void testDailyOvertimeRuleMaint() throws Exception {
		HtmlPage dailyOvertimeRuleLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DAILY_OVERTIME_RULE_MAINT_URL);
		dailyOvertimeRuleLookUp = HtmlUnitUtil.clickInputContainingText(dailyOvertimeRuleLookUp, "search");
		assertTrue("Page contains test DailyOvertimeRule", dailyOvertimeRuleLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(dailyOvertimeRuleLookUp, "edit",dailyOvertimeRuleId.toString());		
		assertTrue("Maintenance Page contains test DailyOvertimeRule",maintPage.asText().contains(TEST_CODE.toString()));		
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		DailyOvertimeRule dailyOvertimeRule = new DailyOvertimeRule();
		dailyOvertimeRule.setActive(true);
		dailyOvertimeRule.setEffectiveDate(TEST_DATE);
		dailyOvertimeRule.setLocation(TEST_CODE);
		dailyOvertimeRule.setMaxGap(TEST_NO);
		dailyOvertimeRule.setDeptId(TEST_CODE);
		dailyOvertimeRule.setHrPaytype(TEST_CODE);
		dailyOvertimeRule.setOvertimePref(TEST_CODE);
		dailyOvertimeRule.setShiftHours(TEST_NO);
		dailyOvertimeRule.setTaskId(TEST_ID);
		dailyOvertimeRule.setTimeStamp(TEST_TIMESTAMP);
		dailyOvertimeRule.setUserPrincipalId(TEST_CODE);
		dailyOvertimeRule.setWorkAreaId(TEST_ID);
		KNSServiceLocator.getBusinessObjectService().save(dailyOvertimeRule);		
		dailyOvertimeRuleId=dailyOvertimeRule.getTkDailyOvertimeRuleId();		
	}

	@Override
	public void tearDown() throws Exception {		
		DailyOvertimeRule dailyOvertimeRuleObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(DailyOvertimeRule.class, dailyOvertimeRuleId);			
		KNSServiceLocator.getBusinessObjectService().delete(dailyOvertimeRuleObj);				
		super.tearDown();
	}
}

