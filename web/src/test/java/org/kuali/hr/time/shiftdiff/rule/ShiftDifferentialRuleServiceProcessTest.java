/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.hr.time.shiftdiff.rule;

import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.calendar.CalendarBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.service.ShiftDifferentialRuleService;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.TimesheetUtils;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.kpme.tklm.utils.TkTestUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author djunk
 *
 */
@FunctionalTest
public class ShiftDifferentialRuleServiceProcessTest extends KPMEWebTestCase {


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
        DateTimeZone tz = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
		// Create the Rule    Sun,   Mon,   Tue,  Wed,   Thu,  Fri,  Sat
		boolean[] dayArray = {false, false, true, true, true, true, true};
		// Matches HR Job ID #1 (job # 30)
		Long jobNumber = 30L;
		Long workArea = 0L;
		this.createShiftDifferentialRule(
				"BWS-CAL", "REG", "PRM", "IN", "SD1", "SD1",// // changed from "SD1" to "IN" for changes of adding groupKeyCode to Job
				(new LocalTime(22, 0)),
				(new LocalTime(4, 0)),
				new BigDecimal(3), // minHours
				new BigDecimal("15.00"), // maxGap
				dayArray);


		// Timeblocks

		// August
		DateTime beginPeriodDate = new DateTime(2010, 8, 15, 0, 0, 0, 0, tz);
		CalendarEntry endOfAugust =  HrServiceLocator.getCalendarEntryService().getCalendarEntryByIdAndPeriodEndDate("2", new DateTime(2010, 9, 1, 0, 0, 0, 0));
		DateTime start = new DateTime(2010, 8, 31, 21, 45, 0, 0, tz);
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", endOfAugust);
        Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment("admin", AssignmentDescriptionKey.get("IU-IN_30_30_30"), beginPeriodDate.toLocalDate());
		blocks.addAll(TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "RGN", start, 1, new BigDecimal(2), BigDecimal.ZERO, "admin"));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, endOfAugust, HrServiceLocator.getCalendarService().getCalendar(endOfAugust.getHrCalendarId()), true);
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSumsFlatList("August Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(2));}},aggregate);
		TkServiceLocator.getTimeBlockService().saveOrUpdateTimeBlocks(new ArrayList<TimeBlock>(), aggregate.getFlattenedTimeBlockList(), "admin");


		// September

		start = new DateTime(2010, 9, 1, 0, 0, 0, 0, tz);
		CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start.toLocalDate().toDateTimeAtStartOfDay());
		tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", payCalendarEntry);
		blocks = new ArrayList<TimeBlock>();
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 1, new BigDecimal("5"), "RGN", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(6), 1, new BigDecimal("6"), "RGN", jobNumber, workArea));
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(22), 1, new BigDecimal("2"), "RGN", jobNumber, workArea));
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusDays(1), 1, new BigDecimal("1"), "RGN", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusDays(1).plusHours(17), 1, new BigDecimal("6"), "RGN", jobNumber, workArea));
		blocks = setDocumentIdOnBlocks(blocks, tdoc.getDocumentId());
        
		aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry, HrServiceLocator.getCalendarService().getCalendar(payCalendarEntry.getHrCalendarId()), true);
		
		TkTestUtils.verifyAggregateHourSumsFlatList("September Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(20));}},aggregate);

		// Verify carry over and applied PRM bucket
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSumsFlatList("September Post-Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal("8.75"));put("RGN", new BigDecimal(22));}},aggregate);
	}

    private List<TimeBlock> setDocumentIdOnBlocks(List<TimeBlock> blocks, String id) {
        List<TimeBlock> updatedTimeBlocks = new ArrayList<TimeBlock>();
        for (TimeBlock b : blocks) {
            TimeBlock.Builder builder = TimeBlock.Builder.create(b);
            builder.setDocumentId(id);
            updatedTimeBlocks.add(builder.build());
        }
        return updatedTimeBlocks;
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

        DateTimeZone tz = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
		this.createShiftDifferentialRule(
				"BWS-CAL",
				"REG",
				"PRM",
				"IN",  // changed from "SD1" to "IN" for changes of adding groupKeyCode to Job
				"SD1",
				"SD1",
				(new LocalTime(22, 0)),
				(new LocalTime(5, 0)),
				new BigDecimal(3), // minHours
				new BigDecimal("0.25"), // maxGap
				dayArray);

		// August
		DateTime endPeriodDate = new DateTime(2010, 9, 1, 0, 0, 0, 0);
        CalendarEntry endOfAugust =  HrServiceLocator.getCalendarEntryService().getCalendarEntryByIdAndPeriodEndDate("2", endPeriodDate);
		DateTime start = new DateTime(2010, 8, 31, 22, 0, 0, 0, tz);
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", endOfAugust);
        Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment("admin", AssignmentDescriptionKey.get("IU-IN_30_30_30"), endOfAugust.getBeginPeriodFullDateTime().toLocalDate());
		blocks.addAll(TkTestUtils.createUniformActualTimeBlocks(tdoc, assignment, "RGN", start, 1, new BigDecimal(2), BigDecimal.ZERO, "admin"));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, endOfAugust, HrServiceLocator.getCalendarService().getCalendar(endOfAugust.getHrCalendarId()), true);



		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSumsFlatList("August Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(2));}},aggregate);
		TkServiceLocator.getTimeBlockService().saveOrUpdateTimeBlocks(new ArrayList<TimeBlock>(), aggregate.getFlattenedTimeBlockList(), "admin");


		// September
		start = new DateTime(2010, 9, 1, 0, 0, 0, 0, tz);
		CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start.toLocalDate().toDateTimeAtStartOfDay());
		tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", payCalendarEntry);
		blocks = new ArrayList<TimeBlock>();
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 1, new BigDecimal("5"), "RGN", jobNumber, workArea));
		aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry, HrServiceLocator.getCalendarService().getCalendar(payCalendarEntry.getHrCalendarId()), true);
		TkTestUtils.verifyAggregateHourSumsFlatList("September Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(5));}},aggregate);

		// Verify carry over and applied PRM bucket
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);
		TkTestUtils.verifyAggregateHourSumsFlatList("September Post-Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(7));put("RGN", new BigDecimal(7));}},aggregate);
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
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
		this.createShiftDifferentialRule(
				"BWS-CAL",
				"REG",
				"PRM",
				"IN", // changed from "SD1" to "IN" for changes of adding groupKeyCode to Job
				"SD1",
				"SD1",
				(new LocalTime(16, 0)),
				(new LocalTime(0, 0)),
				new BigDecimal(4), // minHours
				new BigDecimal("15"), // maxGap
				dayArray);

		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, zone);
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal("4"), "RGN", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(4).plusMinutes(15), 2, new BigDecimal("2"), "RGN", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(new DateTime(2010, 3, 29, 12, 58, 0, 0, zone), 2, new BigDecimal(1), "RGN", jobNumber, workArea));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("admin", "Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("RGN", new BigDecimal(14));}},aggregate,2);

		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

		// Verify post-Rule Run
		TkTestUtils.verifyAggregateHourSums("admin", "Post Rules Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(8));put("RGN", new BigDecimal(14));}},aggregate,2);
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
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
		this.createShiftDifferentialRule(
				"BWS-CAL",
				"REG",
				"PRM",
				"IN", // changed from "SD1" to "IN" for changes of adding groupKeyCode to Job
				"SD1",
				"SD1",
				(new LocalTime(16, 0)), //4pm
				(new LocalTime(0, 0)),  //midnight
				new BigDecimal(4), // minHours
				new BigDecimal("15.00"), // maxGap minutes
				dayArray);

		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
		DateTime start = new DateTime(2010, 3, 29, 14, 0, 0, 0, zone);
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start, 2, new BigDecimal("4"), "REG", jobNumber, workArea));
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start.plusHours(4).plusMinutes(15), 2, new BigDecimal("2"), "REG", jobNumber, workArea));
		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("REG", new BigDecimal(12));}},aggregate,2);

		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

		// Verify post-Rule Run
		TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(8));put("REG", new BigDecimal(12));}},aggregate,2);
	}

	/**
	 * Stores the Shift Differential Rule in the database for testing.
	 *
	 * dayBooleans[] is a 7 element array of booleans, [0, 6] is [sun, sat]
	 */
	private void createShiftDifferentialRule(String pyCalendarGroup, String fromEarnGroup, String premiumEarnCode, String location, String payGrade, String hrSalGroup, LocalTime startTime, LocalTime endTime, BigDecimal minHours, BigDecimal maxGap, boolean dayBooleans[]) {
		Assert.assertTrue("Wrong number of day booleans", dayBooleans.length == 7);

		ShiftDifferentialRuleService service = TkServiceLocator.getShiftDifferentialRuleService();
		ShiftDifferentialRule sdr = new ShiftDifferentialRule();

		sdr.setBeginTime(new Time(startTime.toDateTimeToday().getMillis()));
		sdr.setEndTime(new Time(endTime.toDateTimeToday().getMillis()));
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
        sdr.setRuleType("default");

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

        DateTimeZone tz = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        LocalTime orig_start = new LocalTime(sdr.getBeginTime());
		LocalTime orig_end = new LocalTime(sdr.getEndTime());

		LocalTime stored_start = new LocalTime(sdrBack.getBeginTime());
		LocalTime stored_end = new LocalTime(sdrBack.getEndTime());

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
				"IN",  // changed from "SD1" to "IN" for changes of adding groupKeyCode to Job
				"SD1",
				"SD1",
				(new LocalTime(12, 0)),
				(new LocalTime(17, 0)),
				new BigDecimal(4), // minHours
				new BigDecimal("15.00"), // maxGap
				dayArray);

		// Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
		DateTime start = new DateTime(2010, 3, 29, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime holtime = new DateTime(2010, 3, 30, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);
		blocks.addAll(TkTestUtils.createUniformTimeBlocks(start,   1, new BigDecimal("4"), "REG", jobNumber, workArea));
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(holtime, 1, new BigDecimal("4"), "HOL", jobNumber, workArea));

		TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

		// Verify pre-Rule Run
		TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("REG", new BigDecimal(4));put("HOL", new BigDecimal(4));}},aggregate,2);

		// Run Rule
		TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
		tdoc.setTimeBlocks(blocks);
		TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

		// Verify post-Rule Run
		TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(8));put("REG", new BigDecimal(4));}},aggregate,2);

    }



    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 24 hour timeblock that spans two different shift, both exceeding the min hours
     */
    @Test
    public void overlapMultipleShiftsWithSameTimeBlock() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        List<TimeBlock> blocks = new ArrayList<TimeBlock>();
        DateTime tbStart = new DateTime(2010, 3, 29, 0, 0, 0, 0, zone);

        //24 time block (midnight to midnight)
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart, 1, new BigDecimal("24"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("REG", new BigDecimal(24));}},aggregate,2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(17));put("REG", new BigDecimal(24));}},aggregate,2);

    }

    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a two 24 hour timeblocks that span three different shifts, all exceeding the min hours
     */
    @Test
    public void overlapMultipleShiftsWithMultipleTimeBlocks() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        List<TimeBlock> blocks = new ArrayList<TimeBlock>();
        DateTime tbStart = new DateTime(2010, 3, 29, 0, 0, 0, 0, zone);

        //24 time block (midnight to midnight)
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart, 1, new BigDecimal("24"), "REG", jobNumber, workArea));

        //24 time block (midnight to midnight)
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart.plusDays(1), 1, new BigDecimal("24"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new HashMap<String,BigDecimal>() {{put("PRM", BigDecimal.ZERO);put("REG", new BigDecimal(48));}},aggregate,2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        //overlaps from 12a-8a (8 hours), 3p-8a (17 hours), and 3p - 12a (9 hours) == 34 hours
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new HashMap<String,BigDecimal>() {{put("PRM", new BigDecimal(34));put("REG", new BigDecimal(48));}},aggregate,2);

    }

    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 24 hour timeblock that spans two different shift, both exceeding the min hours
     */
    @Test
    public void overlapMultipleShiftsWithSameTimeBlockExceedingMinOnOneShift() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        List<TimeBlock> blocks = new ArrayList<TimeBlock>();
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        //reg timeblock 3am - midnight
        DateTime tbStart = new DateTime(2010, 3, 30, 3, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart,   1, new BigDecimal("21"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", BigDecimal.ZERO)
                .put("REG", new BigDecimal(21)).build(), aggregate, 2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", new BigDecimal(9))
                .put("REG", new BigDecimal(21)).build(),
                aggregate,
                2);

    }

    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 24 hour timeblock that spans two different shift, both exceeding the min hours
     */
    @Test
    public void overlapMultipleShiftsWithSameTimeBlockExceedingMinOnFirstShift() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        List<TimeBlock> blocks = new ArrayList<TimeBlock>();
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        //reg timeblock 1am - 9pm
        DateTime tbStart = new DateTime(2010, 3, 30, 1, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart,   1, new BigDecimal("19"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", BigDecimal.ZERO)
                .put("REG", new BigDecimal(19)).build(), aggregate, 2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("PRM", new BigDecimal(7))
                        .put("REG", new BigDecimal(19)).build(),
                aggregate,
                2);

    }

    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 17 hour timeblock that spans two different shift, neither exceeding the min hours
     */
    @Test
    public void overlapMultipleShiftsWithSameTimeBlocNeitherExceedingMin() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        List<TimeBlock> blocks = new ArrayList<TimeBlock>();
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        //reg timeblock 3am - 8pm
        DateTime tbStart = new DateTime(2010, 3, 30, 3, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart,   1, new BigDecimal("17"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", BigDecimal.ZERO)
                .put("REG", new BigDecimal(17)).build(), aggregate, 2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("PRM", BigDecimal.ZERO)
                        .put("REG", new BigDecimal(17)).build(),
                aggregate,
                2);
    }

    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 17 hour timeblock that spans two different shift, neither exceeding the min hours
     */
    @Test
    public void multipleTimeBlocksOvernightExceedingMin() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        List<TimeBlock> blocks = new ArrayList<TimeBlock>();
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        //reg timeblock 10pm - midnight
        DateTime tbStart1 = new DateTime(2010, 3, 30, 22, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart1,   1, new BigDecimal("2"), "REG", jobNumber, workArea));
        //reg timeblock midnight - 5am
        DateTime tbStart2 = new DateTime(2010, 3, 31, 0, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart2,   1, new BigDecimal("5"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", BigDecimal.ZERO)
                .put("REG", BigDecimal.valueOf(7)).build(), aggregate, 2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("PRM", BigDecimal.valueOf(7))
                        .put("REG", BigDecimal.valueOf(7)).build(),
                aggregate,
                2);
    }

    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 17 hour timeblock that spans two different shift, neither exceeding the min hours
     */
    @Test
    public void multipleTimeBlocksOvernightExceedingMinWithSixtyMinuteGap() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        List<TimeBlock> blocks = new ArrayList<TimeBlock>();
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        //reg timeblock 10pm - midnight
        DateTime tbStart1 = new DateTime(2010, 3, 30, 22, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart1,   1, new BigDecimal("2"), "REG", jobNumber, workArea));
        //reg timeblock 1am - 5am
        DateTime tbStart2 = new DateTime(2010, 3, 31, 1, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart2,   1, new BigDecimal("4"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", BigDecimal.ZERO)
                .put("REG", BigDecimal.valueOf(6)).build(), aggregate, 2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("PRM", BigDecimal.valueOf(6))
                        .put("REG", BigDecimal.valueOf(6)).build(),
                aggregate,
                2);
    }

    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 17 hour timeblock that spans two different shift, neither exceeding the min hours
     */
    @Test
    public void multipleTimeBlocksOvernightExceedingMinWithNinetyMinuteGap() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        List<TimeBlock> blocks = new ArrayList<TimeBlock>();
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        //reg timeblock 10pm - midnight
        DateTime tbStart1 = new DateTime(2010, 3, 30, 22, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart1,   1, new BigDecimal("2"), "REG", jobNumber, workArea));
        //reg timeblock 1:30am - 5:30pm
        DateTime tbStart2 = new DateTime(2010, 3, 31, 1, 30, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart2,   1, new BigDecimal("4"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", BigDecimal.ZERO)
                .put("REG", BigDecimal.valueOf(6)).build(), aggregate, 2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("PRM", BigDecimal.valueOf(6))
                        .put("REG", BigDecimal.valueOf(6)).build(),
                aggregate,
                2);
    }

    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 17 hour timeblock that spans two different shift, neither exceeding the min hours
     */
    @Test
    public void multipleTimeBlocksOvernightExceedingMinButExceedingGap() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        List<TimeBlock> blocks = new ArrayList<TimeBlock>();
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        //reg timeblock 10pm - midnight
        DateTime tbStart1 = new DateTime(2010, 3, 30, 22, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart1,   1, new BigDecimal("2"), "REG", jobNumber, workArea));
        //reg timeblock 1:36am - 5:36am
        DateTime tbStart2 = new DateTime(2010, 3, 31, 1, 36, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart2,   1, new BigDecimal("4"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", BigDecimal.ZERO)
                .put("REG", BigDecimal.valueOf(6)).build(), aggregate, 2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("PRM", BigDecimal.valueOf(0))
                        .put("REG", BigDecimal.valueOf(6)).build(),
                aggregate,
                2);
    }


    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 17 hour timeblock that spans two different shift, neither exceeding the min hours
     */
    @Test
    public void threeBlocksWithinSameShift() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        List<TimeBlock> blocks = new ArrayList<TimeBlock>();

        //reg timeblock 3pm - 5pm
        DateTime tbStart1 = new DateTime(2010, 3, 30, 15, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart1,   1, new BigDecimal("2"), "REG", jobNumber, workArea));

        // 6pm - 9pm
        DateTime tbStart2 = new DateTime(2010, 3, 30, 18, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart2,   1, new BigDecimal("3"), "REG", jobNumber, workArea));

        // 10pm - 11pm
        DateTime tbStart3 = new DateTime(2010, 3, 30, 22, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart3,   1, new BigDecimal("1"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", BigDecimal.ZERO)
                .put("REG", BigDecimal.valueOf(6)).build(), aggregate, 2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("PRM", BigDecimal.valueOf(6))
                        .put("REG", BigDecimal.valueOf(6)).build(),
                aggregate,
                2);
    }

    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 17 hour timeblock that spans two different shift, neither exceeding the min hours
     */
    @Test
    public void fourBlocksWithinSameShiftSpanningTwoDays() {
        // Create the Rule
        boolean[] dayArray = {true, true, true, true, true, true, true};
        // Matches HR Job ID #1 (job # 30)
        Long jobNumber = 30L;
        Long workArea = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();

        //3pm to 8am, 6 hour minimum, 90 minute max gap
        this.createShiftDifferentialRule(
                "BWS-CAL",
                "REG",
                "PRM",
                "IN",
                "SD1",
                "SD1",
                (new LocalTime(15, 0)),
                (new LocalTime(8, 0)),
                new BigDecimal(6), // minHours
                new BigDecimal("90.00"), // maxGap
                dayArray);

        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        List<TimeBlock> blocks = new ArrayList<TimeBlock>();

        //reg timeblock 5pm - 6pm
        DateTime tbStart1 = new DateTime(2010, 3, 30, 17, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart1,   1, new BigDecimal("1"), "REG", jobNumber, workArea));

        // 7:30pm - 8:30pm
        DateTime tbStart2 = new DateTime(2010, 3, 30, 19, 30, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart2,   1, new BigDecimal("1"), "REG", jobNumber, workArea));

        // 10pm - midnight
        DateTime tbStart3 = new DateTime(2010, 3, 30, 22, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart3,   1, new BigDecimal("2"), "REG", jobNumber, workArea));

        // 1:30am - 4:30am
        DateTime tbStart4 = new DateTime(2010, 3, 31, 1, 30, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart4,   1, new BigDecimal("3"), "REG", jobNumber, workArea));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("PRM", BigDecimal.ZERO)
                .put("REG", BigDecimal.valueOf(7)).build(), aggregate, 2);

        // Run Rule
        TimesheetDocument tdoc = TkTestUtils.populateBlankTimesheetDocument(start, "admin");
        tdoc.setTimeBlocks(blocks);
        TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(tdoc, aggregate);

        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums("admin","Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("PRM", BigDecimal.valueOf(7))
                        .put("REG", BigDecimal.valueOf(7)).build(),
                aggregate,
                2);
    }


    /**
     * Tests WorkSchedules impact on Shift Differential Rule: Simple Case
     *
     * Create a single 17 hour timeblock that spans two different shift, neither exceeding the min hours
     */
    @Test
    public void EarnGroupTest() {
        // Create the Rule
        // Matches HR Job ID #1 (job # 30)
        String principalId = "10112";


        Long jobNumber = 0L;
        String groupKey = "IU-IN";
        Long workArea = 1010L;
        Long task = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();


        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        TimesheetDocument td = TkTestUtils.populateBlankTimesheetDocument(start, principalId);

        List<TimeBlock> blocks = new ArrayList<TimeBlock>();

        //rgh - monday 8a-11a no-shift
        DateTime tbStart1 = new DateTime(2010, 3, 30, 8, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart1,   1, new BigDecimal("3"), "RGH", jobNumber, workArea, task, groupKey, td.getDocumentId()));

        //echr - monday 2p-5p - should have 3 hrs SHEG
        DateTime tbStart2 = new DateTime(2010, 3, 30, 14, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart2,   1, new BigDecimal("3"), "ECHR", jobNumber, workArea, task, groupKey, td.getDocumentId()));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums(principalId, "Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("RGH", BigDecimal.valueOf(3))
                .put("ECHR", BigDecimal.valueOf(3)).build(), aggregate, 2);

        // Run Rule

        //td.setTimeBlocks(blocks);
        List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(td);
        List<TimeBlock> initialBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(td, true);
        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(initialBlocks);

        //reset time block
        //List<TimeBlock> tbs = TkServiceLocator.getTimesheetService().resetTimeBlock(initialBlocks, td.getAsOfDate());
        TimesheetUtils.processTimeBlocksWithRuleChange(blocks, referenceTimeBlocks, leaveBlocks, td.getCalendarEntry(), td, HrContext.getPrincipalId());

        //refresh Timesheet
        td = TkServiceLocator.getTimesheetService().getTimesheetDocument(td.getDocumentId());
        aggregate = new TkTimeBlockAggregate(td.getTimeBlocks(), payCalendarEntry);
        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums(principalId, "Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("RGH", BigDecimal.valueOf(3))
                        .put("ECHR", BigDecimal.valueOf(3))
                        .put("SHEG", BigDecimal.valueOf(3)).build(),
                aggregate,
                2);
    }

    @Test
    public void calendarGroupTest() {
        // Create the Rule
        // Matches HR Job ID #1 (job # 30)
        String principalId = "10113";


        Long jobNumber = 0L;
        String groupKey = "IU-IN";
        Long workArea = 1010L;
        Long task = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();


        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 16, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates(principalId, start);

        TimesheetDocument td = TkTestUtils.populateBlankTimesheetDocument(start, principalId);

        List<TimeBlock> blocks = new ArrayList<TimeBlock>();

        //rgh - monday 8a-11a no-shift
        DateTime tbStart1 = new DateTime(2010, 3, 21, 8, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart1,   1, new BigDecimal("3"), "RGH", jobNumber, workArea, task, groupKey, td.getDocumentId()));

        //echr - monday 2p-5p - should have 3 hrs SHEG
        //DateTime tbStart2 = new DateTime(2010, 3, 30, 14, 0, 0, 0, zone);
        //blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart2,   1, new BigDecimal("3"), "ECHR", jobNumber, workArea, task, groupKey, td.getDocumentId()));

        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums(principalId, "Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("RGH", BigDecimal.valueOf(3)).build(), aggregate, 1);

        // Run Rule

        //td.setTimeBlocks(blocks);
        List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(td);
        List<TimeBlock> initialBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(td, true);
        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(initialBlocks);

        //reset time block
        //List<TimeBlock> tbs = TkServiceLocator.getTimesheetService().resetTimeBlock(initialBlocks, td.getAsOfDate());
        TimesheetUtils.processTimeBlocksWithRuleChange(blocks, referenceTimeBlocks, leaveBlocks, td.getCalendarEntry(), td, HrContext.getPrincipalId());

        //refresh Timesheet
        td = TkServiceLocator.getTimesheetService().getTimesheetDocument(td.getDocumentId());
        aggregate = new TkTimeBlockAggregate(td.getTimeBlocks(), payCalendarEntry);
        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums(principalId, "Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("RGH", BigDecimal.valueOf(3))
                        .put("SHCG", BigDecimal.valueOf(3))
                        .put("SHDY", BigDecimal.valueOf(0)).build(),
                aggregate,
                1);
    }

    @Test
    public void daysTest() {
        // Create the Rule
        // Matches HR Job ID #1 (job # 30)
        String principalId = "10114";


        Long jobNumber = 0L;
        String groupKey = "IU-IN";
        Long workArea = 1010L;
        Long task = 0L;
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();


        // Create Time Blocks (2 days, 2 blocks on each day, 15 minute gap between blocks, 4 hours total each.
        DateTime start = new DateTime(2010, 3, 21, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        CalendarEntry payCalendarEntry =  HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates("admin", start);

        TimesheetDocument td = TkTestUtils.populateBlankTimesheetDocument(start, principalId);

        List<TimeBlock> blocks = new ArrayList<TimeBlock>();

        //rgh - sunday 3p-5p shift: 2 hr SHDY
        DateTime tbStart1 = new DateTime(2010, 3, 28, 15, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart1,   1, new BigDecimal("2"), "RGH", jobNumber, workArea, task, groupKey, td.getDocumentId()));

        //rgh - monday 3p-5p - shift: none
        DateTime tbStart2 = new DateTime(2010, 3, 29, 15, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart2,   1, new BigDecimal("2"), "RGH", jobNumber, workArea, task, groupKey, td.getDocumentId()));

        //rgh - sun 10p- mon 3a - shift: 2hr SHDY (on sunday timeblock)
        DateTime tbStart3 = new DateTime(2010, 3, 28, 22, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart3,   1, new BigDecimal("2"), "RGH", jobNumber, workArea, task, groupKey, td.getDocumentId()));
        DateTime tbStart4 = new DateTime(2010, 3, 29, 0, 0, 0, 0, zone);
        blocks.addAll(TkTestUtils.createUniformTimeBlocks(tbStart4,   1, new BigDecimal("3"), "RGH", jobNumber, workArea, task, groupKey, td.getDocumentId()));


        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(blocks, payCalendarEntry);

        // Verify pre-Rule Run
        TkTestUtils.verifyAggregateHourSums(principalId, "Pre-Check", new ImmutableMap.Builder<String, BigDecimal>()
                .put("RGH", BigDecimal.valueOf(9)).build(), aggregate, 2);

        // Run Rule

        //td.setTimeBlocks(blocks);
        List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(td);
        List<TimeBlock> initialBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(td, true);
        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(initialBlocks);

        //reset time block
        //List<TimeBlock> tbs = TkServiceLocator.getTimesheetService().resetTimeBlock(initialBlocks, td.getAsOfDate());
        TimesheetUtils.processTimeBlocksWithRuleChange(blocks, referenceTimeBlocks, leaveBlocks, td.getCalendarEntry(), td, HrContext.getPrincipalId());

        //refresh Timesheet
        td = TkServiceLocator.getTimesheetService().getTimesheetDocument(td.getDocumentId());
        aggregate = new TkTimeBlockAggregate(td.getTimeBlocks(), payCalendarEntry);
        // Verify post-Rule Run
        TkTestUtils.verifyAggregateHourSums(principalId, "Post Rules Check", new ImmutableMap.Builder<String, BigDecimal>()
                        .put("RGH", BigDecimal.valueOf(9))
                        .put("SHDY", BigDecimal.valueOf(4)).build(),
                aggregate,
                2);
    }

}
