package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public class WeeklyOvertimeRuleServiceTest extends TkTestCase {
	
	private static Date DEFAULT_EFFDT = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
	private static Long DEFAULT_JOB_NUMBER = 30L;
	private static Long DEFAULT_WORK_AREA = 30L;
		
	
	@SuppressWarnings("serial")
	@Test
	/**
	 * This test should create 10 hours of OVT and leave 40 hours of REG remaining.
	 * It operates WITHIN a standard week.
	 * 
	 */
	public void testProcessSimpleStandardWeek() throws Exception {
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		DateTime start = new DateTime(2010, 1, 4, 5, 0, 0, 0, DateTimeZone.forID("EST"));
		timeBlocks = TkTestUtils.createUniformTimeBlocks(start, 5, BigDecimal.TEN, "REG", DEFAULT_JOB_NUMBER, DEFAULT_WORK_AREA);
		PayCalendarDates payCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates("admin", DEFAULT_EFFDT);

		// Check our initial data.
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry);
		TkTestUtils.verifyAggregateHourSums(new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.ZERO);put("REG", new BigDecimal(50));}},aggregate,1);
		
		// Create the rule.
		this.setupWeeklyOvertimeRule("REG", "OVT", "REG", 1, new BigDecimal(40), DEFAULT_EFFDT);		
		TimesheetDocument timesheetDocument = TkTestUtils.populateBlankTimesheetDocument(DEFAULT_EFFDT);
		timesheetDocument.setTimeBlocks(timeBlocks);
		TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, aggregate);
		
		// Check the rule for OVT applied data.
		TkTestUtils.verifyAggregateHourSums(new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.TEN);put("REG", new BigDecimal(40));}},aggregate,1);
	}
	
	@Test
	/**
	 * This test should create 10 hours of OVT on the first FLSA week of the
	 * current pay period.  The first FLSA week has 40 REG hours, the previous
	 * pay period was a partial week and contained 10 REG hours.
	 *  
	 * |--------+----+-------+-------+-------+-------|
	 * | day -1 | fb | day 1 | day 2 | day 3 | day 4 |
	 * |--------+----+-------+-------+-------+-------|
	 * |     10 | XX |    10 |    10 |    10 |    10 |
	 * |--------+----+-------+-------+-------+-------|
	 */
	public void testProcessPreviousMonthFlsaBoundary() throws Exception {
		// TODO Fill this in.
	}
	
	/**
	 * Helper method that creates a weekly overtime rule.
	 */
	private WeeklyOvertimeRule setupWeeklyOvertimeRule(String fromEarnGroup, String toEarnCode, String maxHoursEarnGroup, int step, BigDecimal maxHours, Date effectiveDate){
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