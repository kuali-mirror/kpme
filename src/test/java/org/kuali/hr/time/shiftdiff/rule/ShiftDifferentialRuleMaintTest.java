package org.kuali.hr.time.shiftdiff.rule;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ShiftDifferentialRuleMaintTest extends TkTestCase{
	private static final String TEST_CODE="_T";
		
	private static final BigDecimal TEST_NO=new BigDecimal(10);
	private static Long shiftDifferentialRuleId;
	private static final String TEST_TIME= "11:00 PM";
	private static final Date TEST_DATE= new Date(Calendar.getInstance().getTimeInMillis());
	
	
	@Test
	public void testShiftDifferentialRuleMaint() throws Exception {	 
		HtmlPage shiftDifferentialRuleLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.SHIFT_DIFFERENTIAL_RULE_MAINT_URL);
		shiftDifferentialRuleLookup = HtmlUnitUtil.clickInputContainingText(shiftDifferentialRuleLookup, "search");
		assertTrue("Page contains test ShiftDifferentialRule", shiftDifferentialRuleLookup.asText().contains(TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(shiftDifferentialRuleLookup, "edit",shiftDifferentialRuleId.toString());
		assertTrue("Maintenance Page contains test ShiftDifferentialRule",maintPage.asText().contains(TEST_CODE));	 
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
		shiftDifferentialRule.setMaxGap(TEST_NO);
		shiftDifferentialRule.setMinHours(TEST_NO);
		shiftDifferentialRule.setPayGrade(TEST_CODE);	
		shiftDifferentialRule.setCalendarGroup("BW-CAL1");
		shiftDifferentialRule.setDay0(true);		
		shiftDifferentialRule.setDay1(true);
		shiftDifferentialRule.setDay2(true);
		shiftDifferentialRule.setDay3(true);
		shiftDifferentialRule.setDay4(true);
		shiftDifferentialRule.setDay5(true);
		shiftDifferentialRule.setDay6(true);
		shiftDifferentialRule.setCalendarGroup("TEST");	
		
		
		KNSServiceLocator.getBusinessObjectService().save(shiftDifferentialRule);
		shiftDifferentialRuleId = shiftDifferentialRule.getTkShiftDiffRuleId();
	}

	@Override
	public void tearDown() throws Exception {				
		ShiftDifferentialRule shiftDifferentialRuleObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(ShiftDifferentialRule.class, shiftDifferentialRuleId);		 
		KNSServiceLocator.getBusinessObjectService().delete(shiftDifferentialRuleObj);
		super.tearDown();
	}
}

