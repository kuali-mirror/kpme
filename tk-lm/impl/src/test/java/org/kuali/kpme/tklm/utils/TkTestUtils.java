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
package org.kuali.kpme.tklm.utils;

import org.apache.log4j.Logger;
import org.joda.time.*;
import org.junit.Assert;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockService;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.flsa.FlsaDay;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.rice.kew.api.exception.WorkflowException;

import java.math.BigDecimal;
import java.util.*;

public class TkTestUtils {

	private static final Logger LOG = Logger.getLogger(TkTestUtils.class);

	public static TimesheetDocument populateBlankTimesheetDocument(DateTime calDate, String principalId) {
		try {
			TimesheetDocument timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId,
							 HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates(principalId,
                                    calDate));
			for(TimeBlock timeBlock : timesheet.getTimeBlocks()){
				TkServiceLocator.getTimeBlockService().deleteTimeBlock(timeBlock);
			}

			return timesheet;
		} catch (WorkflowException e) {
			throw new RuntimeException("Problem fetching document");
		}
	}

	public static TimesheetDocument populateTimesheetDocument(DateTime calDate, String principalId) {
		try {
			TimesheetDocument timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId,
							 HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates(principalId,
									calDate));
			for(TimeBlock timeBlock : timesheet.getTimeBlocks()){
				TkServiceLocator.getTimeBlockService().deleteTimeBlock(timeBlock);
			}

			//refetch clean document
			timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId,
					 HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates(principalId, calDate));
			List<TimeBlock> timeBlocks = new LinkedList<TimeBlock>();
			for(int i = 0;i<5;i++){
				TimeBlock timeBlock = createTimeBlock(timesheet, i+1, 10);
				timeBlocks.add(timeBlock);
				timesheet.setTimeBlocks(timeBlocks);
			}
			
			//Add a TEX accrual earn code
			TimeBlock timeBlock = createTimeBlock(timesheet, 1, 4,"TEX");
			timeBlocks.add(timeBlock);
			timesheet.setTimeBlocks(timeBlocks);
			return timesheet;

		} catch (WorkflowException e) {
			throw new RuntimeException("Problem fetching document");
		}
	}

	/**
	 * Helper method to create regular time blocks for use in testing.
	 *
	 * @param start
	 * @param days
	 * @param hours
	 * @param earnCode
	 * @param jobNumber
	 * @param workArea
	 * @return
	 */
	public static List<TimeBlock> createUniformTimeBlocks(DateTime start, int days, BigDecimal hours, String earnCode, Long jobNumber, Long workArea) {
		return TkTestUtils.createUniformTimeBlocks(start, days, hours, earnCode, jobNumber, workArea, null, null, null);
	}

	public static List<TimeBlock> createUniformTimeBlocks(DateTime start, int days, BigDecimal hours, String earnCode, Long jobNumber, Long workArea, Long task, String groupKeyCode, String documentId) {
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();

		for (int i=0; i<days; i++) {
			DateTime ci = start.plusDays(i);
			DateTime co = ci.plusHours(hours.intValue());
			TimeBlockBo block = TkTestUtils.createDummyTimeBlock(ci, co, hours, earnCode, jobNumber, workArea);
			block.setTask(task);
            block.setGroupKeyCode(groupKeyCode);
			block.setDocumentId(documentId);
            blocks.add(TimeBlockBo.to(block));

		}

		return blocks;
	}

	public static TimeBlockBo createDummyTimeBlock(DateTime clockIn, DateTime clockOut, BigDecimal hours, String earnCode) {
		return TkTestUtils.createDummyTimeBlock(clockIn, clockOut, hours, earnCode, -1L, -1L);
	}

	public static TimeBlockBo createDummyTimeBlock(DateTime clockIn, DateTime clockOut, BigDecimal hours, String earnCode, Long jobNumber, Long workArea) {
		TimeBlockBo block = new TimeBlockBo();
		block.setBeginDateTime(clockIn);
		block.setEndDateTime(clockOut);
		block.setHours(hours);
        block.setBeginTimeDisplay(clockIn);
        block.setEndTimeDisplay(clockOut);

        block.setEarnCode(earnCode);
		block.setJobNumber(jobNumber);
		block.setWorkArea(workArea);

		TimeHourDetailBo thd = new TimeHourDetailBo();
		thd.setHours(hours);
		thd.setEarnCode(earnCode);
		List<TimeHourDetailBo> timeHourDetails = new ArrayList<TimeHourDetailBo>();
		timeHourDetails.add(thd);
		block.setTimeHourDetails(timeHourDetails);

		return block;
	}

	public static TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, int dayInPeriod, int numHours){
		return createTimeBlock(timesheetDocument, dayInPeriod, numHours,"RGN");
	}
	public static TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, int dayInPeriod, int numHours, String earnCode){
		TimeBlockBo timeBlock = new TimeBlockBo();
		DateTime beginPeriodDateTime = timesheetDocument.getCalendarEntry().getBeginPeriodFullDateTime();
		DateTime beginDateTime = beginPeriodDateTime.plusDays(dayInPeriod).withHourOfDay(8).withMinuteOfHour(0);
		DateTime endDateTime = beginDateTime.plusHours(numHours);
		
		timeBlock.setDocumentId(timesheetDocument.getDocumentId());
		timeBlock.setBeginDateTime(beginDateTime);
		timeBlock.setBeginTimeDisplay(beginDateTime);
		timeBlock.setEndDateTime(endDateTime);
		timeBlock.setEndTimeDisplay(endDateTime);
		timeBlock.setEarnCode(earnCode);
        timeBlock.setGroupKeyCode("IU-BL");
		timeBlock.setJobNumber(1L);
		timeBlock.setWorkArea(1234L);
		timeBlock.setTask(1L);
		timeBlock.setHours((new BigDecimal(numHours)).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING));

		return TimeBlockBo.to(timeBlock);
	}

	public static List<Job> getJobs(LocalDate calDate, String principalId){
		return HrServiceLocator.getJobService().getJobs(principalId, calDate);
	}

	@SuppressWarnings("serial")
	public static void verifyAggregateHourSumsFlatList(String msg, final Map<String,BigDecimal> ecToHoursMap, TkTimeBlockAggregate aggregate) {
		// Initializes sum map to zeros, since we only care about the entires
		// that were passed in.
		Map<String,BigDecimal> ecToSumMap = new HashMap<String,BigDecimal>() {{ for (String ec : ecToHoursMap.keySet()) { put(ec, BigDecimal.ZERO); }}};

		for (TimeBlock bl : aggregate.getFlattenedTimeBlockList()) {
			for (TimeHourDetail thd : bl.getTimeHourDetails()) {
				if (ecToSumMap.containsKey(thd.getEarnCode())) {
					ecToSumMap.put(thd.getEarnCode(), ecToSumMap.get(thd.getEarnCode()).add(thd.getHours()));
                }
            }
        }

		// Assert that our values are correct.
		for (String key : ecToHoursMap.keySet()) {
			Assert.assertEquals(
					msg + " >> ("+key+") Wrong number of hours expected: " + ecToHoursMap.get(key) + " found: " + ecToSumMap.get(key) + " :: ",
					0,
					ecToHoursMap.get(key).compareTo(ecToSumMap.get(key)));
        }
	}

	/**
	 * Helper method to verify that the aggregate contains the correct sums as
	 * indicated in the ecToHoursMapping, on a SINGLE given flsaWeek.
	 *
	 * Warning! Contains Assertions, use only with Test Cases.
	 *
	 * @param ecToHoursMap ex: { 'REG' => 40, 'OVT' => 10 }
	 * @param aggregate An aggregate object containing the time blocks
	 * @param flsaWeek 0 indexed start week (pulling from aggregate)
	 */
	@SuppressWarnings("serial")
	public static void verifyAggregateHourSums(String principalId, String msg, final Map<String,BigDecimal> ecToHoursMap, TkTimeBlockAggregate aggregate, int flsaWeek) {
		// Initializes sum map to zeros, since we only care about the entires
		// that were passed in.
		Map<String,BigDecimal> ecToSumMap = new HashMap<String,BigDecimal>() {{ for (String ec : ecToHoursMap.keySet()) { put(ec, BigDecimal.ZERO); }}};

		List<FlsaWeek> flsaWeeks = aggregate.getFlsaWeeks(DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(principalId)), 0, false);
		Assert.assertTrue(msg + " >> Not enough FLSA weeks to verify aggregate hours, max: " + (flsaWeeks.size() - 1), flsaWeeks.size() > flsaWeek);

		// Build our Sum Map.
		FlsaWeek week = flsaWeeks.get(flsaWeek);
		List<FlsaDay> flsaDays = week.getFlsaDays();
		for (FlsaDay day : flsaDays) {
			for (TimeBlock bl : day.getAppliedTimeBlocks()) {
				for (TimeHourDetail thd : bl.getTimeHourDetails()) {
					if (ecToSumMap.containsKey(thd.getEarnCode())) {
						ecToSumMap.put(thd.getEarnCode(), ecToSumMap.get(thd.getEarnCode()).add(thd.getHours()));
                    }
                }
            }
        }


		// Assert that our values are correct.
		for (String key : ecToHoursMap.keySet()) {
			Assert.assertEquals(
					msg + " >> ("+key+") Wrong number of hours expected: " + ecToHoursMap.get(key) + " found: " + ecToSumMap.get(key) + " :: ",
					0,
					ecToHoursMap.get(key).compareTo(ecToSumMap.get(key)));
        }
	}


	public static void verifyAggregateHourSums(final Map<String,BigDecimal> ecToHoursMap, TkTimeBlockAggregate aggregate, int flsaWeek) {
		TkTestUtils.verifyAggregateHourSums("admin", "", ecToHoursMap, aggregate, flsaWeek);
	}


	/**
	 * Helper method to generate time blocks suitable for db persistence in
	 * unit tests.
	 */
	public static List<TimeBlock> createUniformActualTimeBlocks(TimesheetDocument timesheetDocument, Assignment assignment, String earnCode, DateTime start, int days, BigDecimal hours, BigDecimal amount, String principalId) {
		TimeBlockService service = TkServiceLocator.getTimeBlockService();
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();

		for (int i=0; i<days; i++) {
			DateTime ci = start.plusDays(i);
			DateTime co = ci.plusHours(hours.intValue());

			blocks.addAll(service.buildTimeBlocks(timesheetDocument.getPrincipalId(), timesheetDocument.getCalendarEntry(),
                    assignment, earnCode, timesheetDocument.getDocumentId(), ci, co, hours, amount,
                    false, false, principalId, null, null));
		}

		return blocks;
	}

	public static Map<DateTime, BigDecimal> getDateToHoursMap(TimeBlockBo timeBlock, TimeHourDetailBo timeHourDetail) {
		Map<DateTime, BigDecimal> dateToHoursMap = new HashMap<DateTime, BigDecimal>();
		DateTime beginTime = timeBlock.getBeginDateTime();
		DateTime endTime = timeBlock.getEndDateTime();

		Days d = Days.daysBetween(beginTime, endTime);
		int numberOfDays = d.getDays();
		if (numberOfDays < 1) {
			dateToHoursMap.put(timeBlock.getBeginDateTime(), timeHourDetail.getHours());
			return dateToHoursMap;
		}
		DateTime currentTime = beginTime;
		for (int i = 0; i < numberOfDays; i++) {
			DateTime nextDayAtMidnight = currentTime.plusDays(1);
			nextDayAtMidnight = nextDayAtMidnight.hourOfDay().setCopy(12);
			nextDayAtMidnight = nextDayAtMidnight.minuteOfDay().setCopy(0);
			nextDayAtMidnight = nextDayAtMidnight.secondOfDay().setCopy(0);
			nextDayAtMidnight = nextDayAtMidnight.millisOfSecond().setCopy(0);
			Duration dur = new Duration(currentTime, nextDayAtMidnight);
			long duration = dur.getStandardSeconds();
			BigDecimal hrs = new BigDecimal(duration / 3600, HrConstants.MATH_CONTEXT);
			dateToHoursMap.put(currentTime, hrs);
			currentTime = nextDayAtMidnight;
		}
		Duration dur = new Duration(currentTime, endTime);
		long duration = dur.getStandardSeconds();
		BigDecimal hrs = new BigDecimal(duration / 3600, HrConstants.MATH_CONTEXT);
		dateToHoursMap.put(currentTime, hrs);

		return dateToHoursMap;
	}

}
