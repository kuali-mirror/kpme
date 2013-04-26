/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.time.shiftdiff.rule.service;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.TKContext;
import org.kuali.kpme.tklm.common.TKUtils;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.service.ShiftDifferentialRuleService;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;

/**
 *
 * @author djunk
 *
 */
public class ShiftDifferentialRuleServiceProcessTest extends KPMETestCase {


	public static final String USER_PRINCIPAL_ID = "admin";
	private DateTime JAN_AS_OF_DATE = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());


	/**
	 * Test with boundary carryover and overlapping rules.
	 *
	 * Rule 1:
	 *
	 * Runs on Tu, Wed, Th on the interval: [22:00, 4:00)
	 * Max Gap: 15 minutes
	 * Min Hours: 3
	 *
	 * Rule 2:
	 *
	 * Runs on Tu, Th on the interval: [23:00, 2:00)
	 * Max Gap: 2 hours
	 * Min Hours: 3
	 *
	 * Rule 3:
	 *
	 * Runs on W, Th on the interval: [5:00, 12:00)
	 * Max Gap: 15 minutes
	 * Min Hours: 7 hours
	 *
	 * Rule 4:
	 *
	 * Runs on W on the interval: [5:00, 12:00)
	 * Max Gap: 15 minutes
	 * Min Hours: 5
	 *
	 *
	 * |--------------+----+------------+------------|
	 * | Tu : 8/31/10 | XX | W : 9/1/10 | Th: 9/2/10 |
	 * |--------------+----+------------+------------|
	 * | 9:45p - 11:45| XX | Mid - 5a   | 5p - 11p   |
	 * |              | XX | 6a - Noon  |            |
	 * |--------------+----+------------+------------|
     *
     *
     * Aug 31: 2h  : 21:45 - 23:45 (Tue) **
     *                           [1: 5h 45m]  // [2: 2h 45m] - Not qualifying, min hours must be 3.
     * Sep  1: 5h  : 00:00 - 05:00 (Wed) **
     * Sep  1: 6h  : 06:00 - 12:00 (Wed) [4: 6h]
     *
     * Sep  1: 2h  : 22:00 - 24:00 (Wed)
     * Sep  2: 1h  : 00:00 - 01:00 (Thu) [1: 3h]
     *
     * Sep  2: 6h  : 17:00 - 22:00 (Thu)
     *
     * 1: [22:00,  4:00) (Tue/Wed/Thu) minimum: 3h gap: 15m
     * 2: [23:00,  2:00) (Tue/Thu)     minimum: 3h gap: 2h
     * 3: [05:00, 12:00) (Wed/Thu)     minimum: 7h gap: 15m
     * 4: [05:00, 12:00) (Wed)         minimum: 5h gap: 15m
     *
	 */
	@SuppressWarnings("serial")
	@Test
	public void testProcessTimesheetBoundaryCarryoverOverlapCase() throws Exception {
        DateTimeZone tz = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
		// Create the Rule    Sun,   Mon,   Tue,  Wed,   Thu,  Fri,  Sat
		boolean[] dayArray = {false, false, true, true, true, true, true};
		// Matches HR Job ID #1 (job # 30)
		Long jobNumber = 30L;
		Long workArea = 0L;
		this.createShiftDifferentialRule(
				"BWS-CAL", "REG", "PRM", "SD1", "SD1", "SD1",
				(new DateTime(2010, 8, 31, 22, 0, 0, 0, tz)),
				(new DateTime(2010, 8, 31,  4, 0, 0, 0, tz)),
				new BigDecimal(3), // minHours
				new BigDecimal("0.25"), // maxGap
				dayArray);

        dayArray = new boolean [] {false, false, true, false, true, true, true};
		this.createShiftDifferentialRule(
				"BWS-CAL", "REG", "PRM", "SD1", "SD1", "SD1",
				(new DateTime(2010, 8, 31, 23, 0, 0, 0, tz)),
				(new DateTime(2010, 8, 31,  2, 0, 0, 0, tz)),
				new BigDecimal(3), // minHours
				new BigDecimal("2.0"), // maxGap
				dayArray);

		dayArray = new boolean[] {false, false, false, true, true, false, false};
		this.createShiftDifferentialRule(
				"BWS-CAL", "REG", "PRM", "SD1", "SD1", "SD1",
				(new DateTime(2010, 8, 31, 5, 0, 0, 0, tz)),
				(new DateTime(2010, 8, 31,  12, 0, 0, 0, tz)),
				new BigDecimal("7.0"), // minHours
				new BigDecimal(".25"), // maxGap
				dayArray);
		dayArray = new boolean[] {false, false, false, true, false, false, false};
		this.createShiftDifferentialRule(
				"BWS-CAL", "REG", "PRM", "SD1", "SD1", "SD1",
				(new DateTime(2010, 8, 31, 5, 0, 0, 0, tz)),
				(new DateTime(2010, 8, 31,  12, 0, 0, 0, tz)),
				new BigDecimal("5"), // minHours
				new BigDecimal("0.25"), // maxGap
				dayArray);

		// Timeblocks

		// August
		DateTime beginPeriodDate = new DateTime(2010, 8, 15, 0, 0, 0, 0, tz);
        DateTime endPeriodDate = new DateTime(2010, 9, 1, 0, 0, 0, 0);
		CalendarEntry endOfAugust = HrServiceLocator.getCalendarEntryService().getCalendarEntryByIdAndPeriodEndDate("2", endPeriodDate);
		DateTime start = new DateTime(2010, 8, 31, 21, 45, 0, 0, tz);
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", endOfAugust);
		Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment("admin", new AssignmentDescriptionKey("30_30_30"), beginPeriodDate.toLocalDate());
		blocks.addAll(TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "RGN", start, 1, new BigDecimal(2), BigDecimal.ZERO));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, endOfAugust, HrServiceLocator.getCalendarService().getCalendar(endOfAugust.getHrCalendarId()), true);
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSumsFlatList("August Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(2));}},aggregate);
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(new ArrayList<TimeBlock>(), aggregate.getFlattenedTimeBlockList(), TKContext.getPrincipalId());


		// September

		start = new DateTime(2010, 9, 1, 0, 0, 0, 0, tz);
		CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDates("admin", start.toLocalDate().toDateTimeAtStartOfDay());
		tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", payCalendarEntry);
		blocks = new ArrayList<TimeBlock>();
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 1, new BigDecimal("5"), "RGN", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(6), 1, new BigDecimal("6"), "RGN", jobNumber, workArea));
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(22), 1, new BigDecimal("2"), "RGN", jobNumber, workArea));
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusDays(1), 1, new BigDecimal("1"), "RGN", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusDays(1).plusHours(17), 1, new BigDecimal("6"), "RGN", jobNumber, workArea));
		setDocumentIdOnBlocks(blocks, tdoc.getDocumentId());
        
		aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry, HrServiceLocator.getCalendarService().getCalendar(payCalendarEntry.getHrCalendarId()), true);
		
		TkTestUtils.verifyAggregateHourSumsFlatList("September Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(20));}},aggregate);

		// Verify carry over and applied PRM bucket
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSumsFlatList("September Post-Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal("14.75"));put("RGN", new BigDecimal(20));}},aggregate);
	}

    private void setDocumentIdOnBlocks(List<TimeBlock> blocks, String id) {
        for (TimeBlock b : blocks) {
            b.setDocumentId(id);
        }
    }


	/**
	 * Test where previous time sheet contains hours that should be added to
	 * the next pay periods first day shift.
	 *
	 * Runs on Tu, Th on the interval: [22:00, 4:00)
	 * Max Gap: 15 minutes
	 * Min Hours: 3
	 *
	 * |--------------+----+------------+-------------|
	 * | Tu : 8/31/10 | XX | W : 9/1/10 | Th : 9/2/10 |
	 * |--------------+----+------------+-------------|
	 * | 10pm - Mid   | XX | Mid - 5am  | 5pm - 11pm  |
	 * |--------------+----+------------+-------------|
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("serial")
	@Test
	public void testProcessShiftTimesheeetBoundaryCarryoverCase() throws Exception {
		// Create the Rule    Sun,   Mon,   Tue,  Wed,   Thu,  Fri,  Sat
		boolean[] dayArray = {false, false, true, false, true, true, true};
		// Matches HR Job ID #1 (job # 30)
		Long jobNumber = 30L;
		Long workArea = 0L;

        DateTimeZone tz = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
		this.createShiftDifferentialRule(
				"BWS-CAL",
				"REG",
				"PRM",
				"SD1",
				"SD1",
				"SD1",
				(new DateTime(2010, 8, 31, 22, 0, 0, 0, tz)),
				(new DateTime(2010, 8, 31,  5, 0, 0, 0, tz)),
				new BigDecimal(3), // minHours
				new BigDecimal("0.25"), // maxGap
				dayArray);

		// August
		DateTime endPeriodDate = new DateTime(2010, 9, 1, 0, 0, 0, 0);
        CalendarEntry endOfAugust = HrServiceLocator.getCalendarEntryService().getCalendarEntryByIdAndPeriodEndDate("2", endPeriodDate);
		DateTime start = new DateTime(2010, 8, 31, 22, 0, 0, 0, tz);
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", endOfAugust);
		Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment("admin", new AssignmentDescriptionKey("30_30_30"), endOfAugust.getBeginPeriodFullDateTime().toLocalDate());
		blocks.addAll(TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "RGN", start, 1, new BigDecimal(2), BigDecimal.ZERO));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, endOfAugust, HrServiceLocator.getCalendarService().getCalendar(endOfAugust.getHrCalendarId()), true);



		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSumsFlatList("August Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(2));}},aggregate);
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(new ArrayList<TimeBlock>(), aggregate.getFlattenedTimeBlockList(), TKContext.getPrincipalId());


		// September
		start = new DateTime(2010, 9, 1, 0, 0, 0, 0, tz);
		CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDates("admin", start.toLocalDate().toDateTimeAtStartOfDay());
		tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", payCalendarEntry);
		blocks = new ArrayList<TimeBlock>();
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 1, new BigDecimal("5"), "RGN", jobNumber, workArea));
		aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry, HrServiceLocator.getCalendarService().getCalendar(payCalendarEntry.getHrCalendarId()), true);
		TkTestUtils.verifyAggregateHourSumsFlatList("September Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(5));}},aggregate);

		// Verify carry over and applied PRM bucket
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSumsFlatList("September Post-Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(7));put("RGN", new BigDecimal(5));}},aggregate);
	}

	@SuppressWarnings("serial")
	@Test
	/**
	 * Runs on every day on the interval: [16:00, 24:00)
	 * Max Gap: 15 minutes
	 * Min Hours: 4
	 *
	 * Added some extra time blocks that are not in the shift interval, but
	 * close to the time blocks that are.
	 *
	 * @throws Exception
	 */
	public void testProcessShiftSimpleNoisyCase() throws Exception {
		// Create the Rule
		boolean[] dayArray = {true, true, true, true, true, true, true};
		// Matches HR Job ID #1 (job # 30)
		Long jobNumber = 30L;
		Long workArea = 0L;
		this.createShiftDifferentialRule(
				"BWS-CAL",
				"REG",
				"PRM",
				"SD1",
				"SD1",
				"SD1",
				(new DateTime(2010, 3, 29, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone())),
				(new DateTime(2010, 3, 30, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())),
				new BigDecimal(4), // minHours
				new BigDecimal("15"), // maxGap
				dayArray);

		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDates("admin", start);
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal("4"), "RGN", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(4).plusMinutes(15), 2, new BigDecimal("2"), "RGN", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(new DateTime(2010, 3, 29, 12, 58, 0, 0, TKUtils.getSystemDateTimeZone()), 2, new BigDecimal(1), "RGN", jobNumber, workArea));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(14));}},aggregate,2);

		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start);
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

		// Verify post-Rule Run
		TkTestUtils.verifyAggregateHourSums("Post Rules Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(8));put("RGN", new BigDecimal(14));}},aggregate,2);
	}

	@SuppressWarnings("serial")
	@Test
	/**
	 * Runs on every day on the interval: [16:00, 24:00)
	 * Max Gap: 15 minutes
	 * Min Hours: 4
	 *
	 * @throws Exception
	 */
	public void testProcessShiftSimpleCase() throws Exception {
		// Create the Rule
		boolean[] dayArray = {true, true, true, true, true, true, true};
		// Matches HR Job ID #1 (job # 30)
		Long jobNumber = 30L;
		Long workArea = 0L;
		this.createShiftDifferentialRule(
				"BWS-CAL",
				"REG",
				"PRM",
				"SD1",
				"SD1",
				"SD1",
				(new DateTime(2010, 3, 29, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone())),
				(new DateTime(2010, 3, 30, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())),
				new BigDecimal(4), // minHours
				new BigDecimal("15"), // maxGap
				dayArray);

		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDates("admin", start);
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal("4"), "REG", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(4).plusMinutes(15), 2, new BigDecimal("2"), "REG", jobNumber, workArea));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("REG", new BigDecimal(12));}},aggregate,2);

		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start);
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

		// Verify post-Rule Run
		TkTestUtils.verifyAggregateHourSums("Post Rules Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(8));put("REG", new BigDecimal(12));}},aggregate,2);
	}

	/**
	 * Stores the Shift Differential Rule in the database for testing.
	 *
	 * dayBooleans[] is a 7 element array of booleans, [0, 6] is [sun, sat]
	 */
	private void createShiftDifferentialRule(String pyCalendarGroup, String fromEarnGroup, String premiumEarnCode, String location, String payGrade, String hrSalGroup, DateTime startTime, DateTime endTime, BigDecimal minHours, BigDecimal maxGap, boolean dayBooleans[]) {
		Assert.assertTrue("Wrong number of day booleans", dayBooleans.length == 7);

		ShiftDifferentialRuleService service = TkServiceLocator.getShiftDifferentialRuleService();
		ShiftDifferentialRule sdr = new ShiftDifferentialRule();

		sdr.setBeginTime(new Time(startTime.getMillis()));
		sdr.setEndTime(new Time(endTime.getMillis()));
		sdr.setMinHours(minHours);
		sdr.setMaxGap(maxGap);
		sdr.setActive(true);
		sdr.setUserPrincipalId(USER_PRINCIPAL_ID);
		sdr.setEffectiveLocalDate(JAN_AS_OF_DATE.toLocalDate());
		sdr.setLocation(location);
		sdr.setPayGrade(payGrade);
		sdr.setHrSalGroup(hrSalGroup);
		sdr.setFromEarnGroup(fromEarnGroup);
		sdr.setPyCalendarGroup(pyCalendarGroup);
		sdr.setEarnCode(premiumEarnCode);

		for (int i=0; i<dayBooleans.length; i++) {
			switch(i) {
			case 0:
				sdr.setSunday(dayBooleans[i]);
				break;
			case 1:
				sdr.setMonday(dayBooleans[i]);
				break;
			case 2:
				sdr.setTuesday(dayBooleans[i]);
				break;
			case 3:
				sdr.setWednesday(dayBooleans[i]);
				break;
			case 4:
				sdr.setThursday(dayBooleans[i]);
				break;
			case 5:
				sdr.setFriday(dayBooleans[i]);
				break;
			case 6:
				sdr.setSaturday(dayBooleans[i]);
				break;
			}
		}

		service.saveOrUpdate(sdr);

		ShiftDifferentialRule sdrBack = service.getShiftDifferentialRule(sdr.getTkShiftDiffRuleId());

        DateTimeZone tz = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        LocalTime orig_start = new LocalTime(sdr.getBeginTime(), tz);
		LocalTime orig_end = new LocalTime(sdr.getEndTime(), tz);

		LocalTime stored_start = new LocalTime(sdrBack.getBeginTime(), tz);
		LocalTime stored_end = new LocalTime(sdrBack.getEndTime(), tz);

		Assert.assertTrue("Start times not equal.", orig_start.equals(stored_start));
		Assert.assertTrue("End times not equal.", orig_end.equals(stored_end));
	}


    @Ignore
    @Test
    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a timeblock on two days, one day has normal REG shift eligible
     * hours, one day has HOL time.
     *
     * Modified version of the simple case, SDR from 12:00 to 17:00, every day,
     * must have at least 4 hours with a maximum 15 minute gap.
     *
     */
    public void simpleCaseWithWorkSchedule() throws Exception {
		// Create the Rule
		boolean[] dayArray = {true, true, true, true, true, true, true};
		// Matches HR Job ID #1 (job # 30)
		Long jobNumber = 30L;
		Long workArea = 0L;
		this.createShiftDifferentialRule(
				"BWS-CAL",
				"REG",
				"PRM",
				"SD1",
				"SD1",
				"SD1",
				(new DateTime(2010, 3, 29, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())),
				(new DateTime(2010, 3, 29, 17, 0, 0, 0, TKUtils.getSystemDateTimeZone())),
				new BigDecimal(4), // minHours
				new BigDecimal("0.25"), // maxGap
				dayArray);

		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
		DateTime start = new DateTime(2010, 3, 29, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime holtime = new DateTime(2010, 3, 30, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDates("admin", start);
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start,   1, new BigDecimal("4"), "REG", jobNumber, workArea));
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(holtime, 1, new BigDecimal("4"), "HOL", jobNumber, workArea));

		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("REG", new BigDecimal(4));put("HOL", new BigDecimal(4));}},aggregate,2);

		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start);
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

		// Verify post-Rule Run
		TkTestUtils.verifyAggregateHourSums("Post Rules Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(8));put("REG", new BigDecimal(4));}},aggregate,2);

    }
 
}
