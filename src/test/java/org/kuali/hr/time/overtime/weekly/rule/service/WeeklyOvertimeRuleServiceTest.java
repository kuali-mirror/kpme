/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * 
 * @author djunk
 *
 */
public class WeeklyOvertimeRuleServiceTest extends KPMETestCase {
	
	private static Date DEFAULT_EFFDT = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
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
		DateTime start = new DateTime(2010, 1, 4, 5, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		timeBlocks = TkTestUtils.createUniformTimeBlocks(start, 5, BigDecimal.TEN, "REG", DEFAULT_JOB_NUMBER, DEFAULT_WORK_AREA);
		CalendarEntries payCalendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates("admin", DEFAULT_EFFDT);

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
	
	@SuppressWarnings("serial")
	@Test
	/**
	 * OVT Hit on Current Month
	 * 
	 * This test should create 10 hours of OVT on the first FLSA week of the
	 * current pay period.  The first FLSA week has 20 REG hours, the previous
	 * pay period was a partial week and contained 30 REG hours.
	 * 
	 * march 29-31; april 1-4
	 *  
	 *  |--------+--------+--------+----+-------+-------|
	 *  |   29th |   30th |   31st | xx |   1st |   2nd |
	 *  |--------+--------+--------+----+-------+-------|
	 *  |     10 |     10 |     10 | xx |    10 |    10 |
	 *  |--------+--------+--------+----+-------+-------|
	 * 
	 *  4/1/2010 starts on a Thursday
	 */
	public void testProcessPreviousMonthFlsaBoundary() throws Exception {
		// March end time blocks: 3/29-3-31 [m, w]
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		DateTime start = new DateTime(2010, 3, 29, 5, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		Date beginPeriodDate = new Date(new DateTime(2010, 3, 15, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).getMillis());
		Date endPeriodDate = new Date(new DateTime(2010, 4, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).getMillis());
		CalendarEntries endOfMarch = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(beginPeriodDate, endPeriodDate);
		TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", endOfMarch);
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment("admin", new AssignmentDescriptionKey("30_30_30"), beginPeriodDate);
		timeBlocks = TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "RGN", start, 3, BigDecimal.TEN, BigDecimal.ZERO);
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(new ArrayList<TimeBlock>(), timeBlocks);
		tdoc.setTimeBlocks(timeBlocks);
		
		// Verify previous calendar times
		CalendarEntries payCalendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates("admin", new Date(start.getMillis()));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry);
		TkTestUtils.verifyAggregateHourSums("Prior month", new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.ZERO);put("RGN", new BigDecimal(30));}},aggregate,2);
				
		
		// April time blocks & document
		start = new DateTime(2010, 4, 1, 5, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		timeBlocks = TkTestUtils.createUniformTimeBlocks(start, 2, BigDecimal.TEN, "REG", DEFAULT_JOB_NUMBER, DEFAULT_WORK_AREA);
		payCalendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates("admin", new Date(start.getMillis()));
		aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry);
		TkTestUtils.verifyAggregateHourSums("Pre-Rules verification", new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.ZERO);put("REG", new BigDecimal(20));}},aggregate,0);
		TimesheetDocument timesheetDocument = TkTestUtils.populateBlankTimesheetDocument(new Date(start.getMillis()));
		timesheetDocument.setTimeBlocks(timeBlocks);
		
		// Create Rule
		this.setupWeeklyOvertimeRule("REG", "OVT", "REG", 1, new BigDecimal(40), DEFAULT_EFFDT);		

		// Apply
		TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, aggregate);		
		
		// Verify
		TkTestUtils.verifyAggregateHourSums("Overtime processed", new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.TEN);put("REG", new BigDecimal(10));}},aggregate,0);
	}
	
	
	@SuppressWarnings("serial")
	@Test
	/**
	 * OVT hit on previous month.
	 * 
	 * |------+------+------+------+----+-----+-----|
	 * | 27th | 28th | 29th | 30th | xx | 1st | 2nd |
	 * |------+------+------+------+----+-----+-----|
	 * |   11 |   11 |   11 |   11 | xx |  11 |  11 |
	 * |------+------+------+------+----+-----+-----|
	 */
	public void testProcessPreviousMonthFlsaOT() throws Exception {
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		DateTime start = new DateTime(2010, 6, 27, 5, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		Date beginPeriodDate = new Date(new DateTime(2010, 6, 15, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).getMillis());
		Date endPeriodDate = new Date(new DateTime(2010, 7, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).getMillis());
		CalendarEntries endOfJune = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(beginPeriodDate, endPeriodDate);
		TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", endOfJune);
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment("admin", new AssignmentDescriptionKey("30_30_30"), beginPeriodDate);
		timeBlocks = TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "RGN", start, 4, new BigDecimal(11), BigDecimal.ZERO);
		
		tdoc.setTimeBlocks(timeBlocks);
		
		// Create Rule
		this.setupWeeklyOvertimeRule("REG", "OVT", "REG", 1, new BigDecimal(40), DEFAULT_EFFDT);


		// Verify previous calendar times
		CalendarEntries payCalendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates("admin", new Date(start.getMillis()));
		
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry);
		// Create and Process Previous month to have totals set up correctly
		TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSums("Prior month", new HashMap<String,BigDecimal>() {{put("OVT", new BigDecimal(4));put("RGN", new BigDecimal(40));}},aggregate,2);
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(new ArrayList<TimeBlock>(), aggregate.getFlattenedTimeBlockList());
		
		// April time blocks & document
		start = new DateTime(2010, 7, 1, 5, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		timeBlocks = TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal(11), "RGN", DEFAULT_JOB_NUMBER, DEFAULT_WORK_AREA);
		payCalendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates("admin", new Date(start.getMillis()));
		aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry);
		TkTestUtils.verifyAggregateHourSums("Pre-Rules verification", new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.ZERO);put("RGN", new BigDecimal(22));}},aggregate,0);
		TimesheetDocument timesheetDocument = TkTestUtils.populateBlankTimesheetDocument(new Date(start.getMillis()));
		timesheetDocument.setTimeBlocks(timeBlocks);		

		// Apply
		TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, aggregate);		
		
		// Verify
		TkTestUtils.verifyAggregateHourSums("Overtime processed", new HashMap<String,BigDecimal>() {{put("OVT", new BigDecimal(22));put("RGN", BigDecimal.ZERO);}},aggregate,0);
	}
	
	@SuppressWarnings("serial")
	@Test
	/**
	 * Three step OVT Test
	 * 
	 * |------+------+------+------+----+-----+-----|
	 * | 27th | 28th | 29th | 30th | xx | 1st | 2nd |
	 * |------+------+------+------+----+-----+-----|
	 * |  ABC | XYZ  | ABC  |  XYZ | xx | ABC | REG | 
	 * |------+------+------+------+----+-----+-----|
	 * |   11 |   11 |   11 |   11 | xx |  11 |  11 |
	 * |------+------+------+------+----+-----+-----|
	 * 
	 * Contrived example using 3 steps that convert strangely coded hours to 
	 * REG via a multi-step process.  Eventually REG is converted to OVT using
	 * normal rules.
	 * 
	 * Step 1: g:SD3:[XYZ] to [ABC]
	 * Step 2: g:SD2:[ABC] to [REG]
	 * Step 3: g:REG:[REG] to [OVT]
	 * 
	 * XYZ -> ABC -> REG -> OVT
	 * 
	 * 27-30th:
	 * ABC: 1    
	 * XYZ: 1    
	 * REG: 40  
	 * OVT: 2    
	 * 
	 * 1st-2nd:
	 * ABC: 0    
	 * REG: 0    
	 * OVT: 22 
	 */
	public void testProcessThreeStepOvtRule() throws Exception {
		this.setupWeeklyOvertimeRule("REG", "OVT", "REG", 3, new BigDecimal(40), DEFAULT_EFFDT);
		this.setupWeeklyOvertimeRule("SD2", "RGN", "SD2", 2, new BigDecimal(1), DEFAULT_EFFDT);
		this.setupWeeklyOvertimeRule("SD3", "ABC", "SD3", 1, new BigDecimal(1), DEFAULT_EFFDT);

		
		List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
		DateTime start = new DateTime(2010, 6, 27, 5, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		Date beginPeriodDate = new Date(new DateTime(2010, 6, 15, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).getMillis());
		Date endPeriodDate = new Date(new DateTime(2010, 7, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).getMillis());
		CalendarEntries endOfJune = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(beginPeriodDate, endPeriodDate);
		TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", endOfJune);
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment("admin", new AssignmentDescriptionKey("30_30_30"), beginPeriodDate);		
		timeBlocks.addAll(TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "ABC", start, 1, new BigDecimal(11), BigDecimal.ZERO));
		timeBlocks.addAll(TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "XYZ", start.plusDays(1), 1, new BigDecimal(11), BigDecimal.ZERO));
		timeBlocks.addAll(TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "ABC", start.plusDays(2), 1, new BigDecimal(11), BigDecimal.ZERO));
		timeBlocks.addAll(TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "XYZ", start.plusDays(3), 1, new BigDecimal(11), BigDecimal.ZERO));		
		tdoc.setTimeBlocks(timeBlocks);

		// Verify previous calendar times
		CalendarEntries payCalendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates("admin", new Date(start.getMillis()));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry);
		// Create and Process Previous month to have totals set up correctly
		TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSums("Prior month", new HashMap<String,BigDecimal>() {{put("OVT", new BigDecimal(2));put("RGN", new BigDecimal(40));put("ABC", new BigDecimal(1));put("XYZ", new BigDecimal(1));}},aggregate,2);
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(new ArrayList<TimeBlock>(), aggregate.getFlattenedTimeBlockList());
		
		// April time blocks & document
		start = new DateTime(2010, 7, 1, 5, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		timeBlocks = TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal(11), "REG", DEFAULT_JOB_NUMBER, DEFAULT_WORK_AREA);
		payCalendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates("admin", new Date(start.getMillis()));
		aggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry);
		TkTestUtils.verifyAggregateHourSums("Pre-Rules verification", new HashMap<String,BigDecimal>() {{put("OVT", BigDecimal.ZERO);put("REG", new BigDecimal(22));}},aggregate,0);
		TimesheetDocument timesheetDocument = TkTestUtils.populateBlankTimesheetDocument(new Date(start.getMillis()));
		timesheetDocument.setTimeBlocks(timeBlocks);		

		// Apply
		TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, aggregate);		
		
		// Verify
		TkTestUtils.verifyAggregateHourSums("Overtime processed", new HashMap<String,BigDecimal>() {{put("ABC", BigDecimal.ZERO);put("OVT", new BigDecimal(22));put("REG", BigDecimal.ZERO);}},aggregate,0);		
	}
	
	/**
	 * Helper method that creates a weekly overtime rule.
	 */
	private WeeklyOvertimeRule setupWeeklyOvertimeRule(String fromEarnGroup, String toEarnCode, String maxHoursEarnGroup, int step, BigDecimal maxHours, Date effectiveDate){
		WeeklyOvertimeRule weeklyOvertimeRule = new WeeklyOvertimeRule();
		weeklyOvertimeRule.setActive(true);
		weeklyOvertimeRule.setConvertFromEarnGroup(fromEarnGroup);
		weeklyOvertimeRule.setConvertToEarnCode(toEarnCode);
		weeklyOvertimeRule.setMaxHoursEarnGroup(maxHoursEarnGroup);
		weeklyOvertimeRule.setStep(new BigDecimal(step));
		weeklyOvertimeRule.setMaxHours(maxHours);
		weeklyOvertimeRule.setEffectiveDate(effectiveDate);
		
		weeklyOvertimeRule.setTkWeeklyOvertimeRuleGroupId(1L);
		
		TkServiceLocator.getWeeklyOvertimeRuleService().saveOrUpdate(weeklyOvertimeRule);
		return weeklyOvertimeRule;
	}

	@Override
	public void tearDown() throws Exception {
		KRADServiceLocator.getBusinessObjectService().deleteMatching(WeeklyOvertimeRule.class, new HashMap());
		super.tearDown();
	}
		
}