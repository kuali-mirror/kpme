package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public class WeeklyOvertimeRuleServiceTest extends TkTestCase {
	
	private static Date DEFAULT_EFFDT = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
	private static Long DEFAULT_JOB_NUMBER = 30L;
	private static Long DEFAULT_WORK_AREA = 30L;
	
	@Test
	public void testProcessWeeklyOvertimeRuleCase1() throws Exception {
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		
		DateTime clockIn = new DateTime(2010, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("EST"));
		DateTime clockOut = new DateTime(2010, 1, 1, 23, 0, 0, 0, DateTimeZone.forID("EST"));
		BigDecimal hours = new BigDecimal(23);
		String earnCode = "REG";
		
		TimeBlock block = TkTestUtils.createDummyTimeBlock(clockIn, clockOut, hours, earnCode, DEFAULT_JOB_NUMBER, DEFAULT_WORK_AREA);
		timeBlocks.add(block);

		clockIn = new DateTime(2010, 1, 2, 0, 0, 0, 0, DateTimeZone.forID("EST"));
		clockOut = new DateTime(2010, 1, 2, 23, 0, 0, 0, DateTimeZone.forID("EST"));
		block = TkTestUtils.createDummyTimeBlock(clockIn, clockOut, hours, earnCode, DEFAULT_JOB_NUMBER, DEFAULT_WORK_AREA);
		timeBlocks.add(block);

		clockIn = new DateTime(2010, 1, 3, 0, 0, 0, 0, DateTimeZone.forID("EST"));
		clockOut = new DateTime(2010, 1, 3, 23, 0, 0, 0, DateTimeZone.forID("EST"));
		block = TkTestUtils.createDummyTimeBlock(clockIn, clockOut, hours, earnCode, DEFAULT_JOB_NUMBER, DEFAULT_WORK_AREA);
		timeBlocks.add(block);
		
		PayCalendarDates payCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates("admin", DEFAULT_EFFDT);
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry);

		this.setupWeeklyOvertimeRules("REG", "OVT", "REG", 1, new BigDecimal(40), DEFAULT_EFFDT);
		
		TimesheetDocument timesheetDocument = TkTestUtils.populateBlankTimesheetDocument(DEFAULT_EFFDT);
		timesheetDocument.setTimeBlocks(timeBlocks);
		
		TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, aggregate);
	}
	
	
	
	private WeeklyOvertimeRule setupWeeklyOvertimeRules(String fromEarnGroup, String toEarnCode, String maxHoursEarnGroup, int step, BigDecimal maxHours, Date effectiveDate){
		WeeklyOvertimeRule weeklyOverTimeRule = new WeeklyOvertimeRule();
		weeklyOverTimeRule.setActive(true);
		weeklyOverTimeRule.setConvertFromEarnGroup(fromEarnGroup);
		weeklyOverTimeRule.setConvertToEarnCode(toEarnCode);
		weeklyOverTimeRule.setMaxHoursEarnGroup(maxHoursEarnGroup);
		weeklyOverTimeRule.setStep(new BigDecimal(step));
		weeklyOverTimeRule.setMaxHours(maxHours);
		weeklyOverTimeRule.setEffectiveDate(effectiveDate);
		
		TkServiceLocator.getWeeklyOvertimeRuleService().saveOrUpdate(weeklyOverTimeRule);
		return weeklyOverTimeRule;
	}
		
}
