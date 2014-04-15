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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockService;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetailContract;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.dao.ShiftDifferentialRuleDao;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionRequestStatus;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.AdHocToPrincipal;
import org.kuali.rice.kew.api.action.DocumentActionParameters;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ShiftDifferentialRuleServiceImpl implements ShiftDifferentialRuleService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ShiftDifferentialRuleServiceImpl.class);
	/**
	 * The maximum allowable time between timeblocks before we consider them to
	 * be day-boundary single time blocks.
	 */
	private ShiftDifferentialRuleDao shiftDifferentialRuleDao = null;

	private Map<Long,Set<ShiftDifferentialRule>> getJobNumberToShiftRuleMap(TimesheetDocument timesheetDocument) {
		Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts = new HashMap<Long,Set<ShiftDifferentialRule>>();
		PrincipalHRAttributesContract principalCal = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(timesheetDocument.getPrincipalId(),timesheetDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());

		for (Job job : timesheetDocument.getJobs()) {
			List<ShiftDifferentialRule> shiftDifferentialRules = getShiftDifferentalRules(job.getLocation(),job.getHrSalGroup(),job.getPayGrade(),principalCal.getPayCalendar(), 
					timesheetDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());
			if (shiftDifferentialRules.size() > 0)
				jobNumberToShifts.put(job.getJobNumber(), new HashSet<ShiftDifferentialRule>(shiftDifferentialRules));
		}

		return jobNumberToShifts;
	}

	protected Map<Long,List<TimeBlock.Builder>> getPreviousPayPeriodLastDayJobToTimeBlockMap(TimesheetDocument timesheetDocument, Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts) {
		Map<Long, List<TimeBlock.Builder>> jobNumberToTimeBlocksPreviousDay = null;

		// Get the last day of the last week of the previous pay period.
		// This is the only day that can have impact on the current day.
		List<TimeBlock> prevBlocks = TkServiceLocator.getTimesheetService().getPrevDocumentTimeBlocks(timesheetDocument.getPrincipalId(), timesheetDocument.getDocumentHeader().getBeginDateTime());
		if (prevBlocks.size() > 0) {
			TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(timesheetDocument.getPrincipalId(), timesheetDocument.getDocumentHeader().getBeginDateTime().toDateTime());
			if (prevTdh != null) {
				CalendarEntry prevPayCalendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarDatesByPayEndDate(timesheetDocument.getPrincipalId(), prevTdh.getEndDateTime(), HrConstants.PAY_CALENDAR_TYPE);
                Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(prevPayCalendarEntry.getHrCalendarId());
				TkTimeBlockAggregate prevTimeAggregate = new TkTimeBlockAggregate(prevBlocks, prevPayCalendarEntry, calendar, true);
				List<List<TimeBlock>> dayBlocks = prevTimeAggregate.getDayTimeBlockList();
				List<TimeBlock> previousPeriodLastDayBlocks = dayBlocks.get(dayBlocks.size() - 1);
				// Set back to null if there is nothing in the list.
				if (previousPeriodLastDayBlocks.size() > 0) {
					jobNumberToTimeBlocksPreviousDay = new HashMap<Long, List<TimeBlock.Builder>>();

					for (TimeBlock block : previousPeriodLastDayBlocks) {
						// Job Number to TimeBlock for Last Day of Previous Time
						// Period
						Long jobNumber = block.getJobNumber();
						if (jobNumberToShifts.containsKey(jobNumber)
                                && block.getBeginDateTime().compareTo(block.getEndDateTime()) != 0) {
							// we have a useful timeblock.
							List<TimeBlock.Builder> jblist = jobNumberToTimeBlocksPreviousDay.get(jobNumber);
							if (jblist == null) {
								jblist = new ArrayList<TimeBlock.Builder>();
								jobNumberToTimeBlocksPreviousDay.put(jobNumber, jblist);
							}
							jblist.add(TimeBlock.Builder.create(block));
						}
					}
				}
			}
		}

		return jobNumberToTimeBlocksPreviousDay;
	}

	protected boolean timeBlockHasEarnCode(Set<String> earnCodes, TimeBlockContract block) {
		boolean present = false;

		if (block != null && earnCodes != null)
			present = earnCodes.contains(block.getEarnCode());

		return present;
	}

    /**
     * Returns a BigDecimal representing the sum of all of the negative time
     * hour detail types. In this case, only LUN is considered. This can be
     * modified to add other considerations.
     *
     * @param block The Timeblock to inspect.
     *
     * @return A big decimal.
     */
    private BigDecimal negativeTimeHourDetailSum(TimeBlockContract block) {
        BigDecimal sum = BigDecimal.ZERO;

        if (block != null) {
            List<? extends TimeHourDetailContract> details = block.getTimeHourDetails();
            for (TimeHourDetailContract detail : details) {
                if (detail.getEarnCode().equals(HrConstants.LUNCH_EARN_CODE)) {
                    sum = sum.add(detail.getHours());
                }
            }
        }

        return sum;
    }

    @Override
    public List<TimeBlock> getTimeblocksOverlappingTimesheetShift(TimesheetDocument timesheetDocument) {
        Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts = getJobNumberToShiftRuleMap(timesheetDocument);
        CalendarEntry calEntry = timesheetDocument.getCalendarEntry();
        Interval beforeCalEntry = null;
        Interval afterCalEntry = null;
        if (jobNumberToShifts.isEmpty()) {
            return Collections.emptyList();
        }
        DateTime calEntryBegin = calEntry.getBeginPeriodFullDateTime();
        DateTime calEntryEnd = new DateTime(calEntry.getEndPeriodFullDateTime());
        boolean checkBefore = true;
        TimesheetDocumentHeader previousHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(timesheetDocument.getPrincipalId(), timesheetDocument.getCalendarEntry().getBeginPeriodFullDateTime());
        if (previousHeader != null
                && (DocumentStatus.FINAL.getCode().equals(previousHeader.getDocumentStatus())
                || DocumentStatus.CANCELED.getCode().equals(previousHeader.getDocumentStatus())
                || DocumentStatus.DISAPPROVED.getCode().equals(previousHeader.getDocumentStatus()))) {
            checkBefore = false;
        }
        for (Map.Entry<Long, Set<ShiftDifferentialRule>> entry : jobNumberToShifts.entrySet()) {
            for (ShiftDifferentialRule rule : entry.getValue()) {
                //Set<String> fromEarnGroup = TkServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(rule.getFromEarnGroup(), TKUtils.getTimelessDate(timesheetDocument.getCalendarEntry().getBeginPeriodDateTime()));

                LocalTime ruleStart = new LocalTime(rule.getBeginTime());
                LocalTime ruleEnd = new LocalTime(rule.getEndTime());

                DateTime beginShiftEnd = ruleEnd.toDateTime(calEntryBegin.minusDays(1));
                DateTime beginShiftStart = ruleStart.toDateTime(calEntryBegin.minusDays(1));

                Interval beginShift = adjustShiftDates(beginShiftStart, beginShiftEnd);
                if (beforeCalEntry == null) {
                    beforeCalEntry = new Interval(beginShift.getStart(), calEntryBegin.minusMillis(1));
                } else {
                    if (beginShift.getStart().isBefore(beforeCalEntry.getStart())) {
                        beforeCalEntry = new Interval(beginShift.getStart(), calEntryBegin.minusMillis(1));
                    }
                }

                DateTime endShiftEnd = ruleEnd.toDateTime(calEntryEnd);
                DateTime endShiftBegin = ruleStart.toDateTime(calEntryEnd);

                Interval endShift = adjustShiftDates(endShiftBegin, endShiftEnd);
                if (afterCalEntry == null) {
                    afterCalEntry = new Interval(calEntryEnd.plusMillis(1), endShift.getEnd());
                } else {
                    if (endShift.getEnd().isAfter(afterCalEntry.getEnd())) {
                        afterCalEntry = new Interval(calEntryEnd.plusMillis(1), endShift.getEnd());
                    }
                }
            }
        }
        //get timeblocks that occur within these shifts
        List<TimeBlock> intersectingTimeBlocks = new ArrayList<TimeBlock>();
        TimeBlockService tbService = TkServiceLocator.getTimeBlockService();
        if (checkBefore) {
            if (beforeCalEntry != null) {
                intersectingTimeBlocks.addAll(tbService.getIntersectingTimeBlocks(timesheetDocument.getPrincipalId(), beforeCalEntry.getStart(), beforeCalEntry.getEnd()));
            }
        }
        if (afterCalEntry != null) {
            intersectingTimeBlocks.addAll(tbService.getIntersectingTimeBlocks(timesheetDocument.getPrincipalId(), afterCalEntry.getStart(), afterCalEntry.getEnd()));
        }

        return intersectingTimeBlocks;
    }

    protected List<TimeBlock> getTimeblocksOverlappingBeginTimesheetShift(TimesheetDocument timesheetDocument) {
        Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts = getJobNumberToShiftRuleMap(timesheetDocument);
        CalendarEntry calEntry = timesheetDocument.getCalendarEntry();
        Interval beforeCalEntry = null;
        if (jobNumberToShifts.isEmpty()) {
            return Collections.emptyList();
        }
        DateTime calEntryBegin = calEntry.getBeginPeriodFullDateTime();
        for (Map.Entry<Long, Set<ShiftDifferentialRule>> entry : jobNumberToShifts.entrySet()) {
            for (ShiftDifferentialRule rule : entry.getValue()) {
                //Set<String> fromEarnGroup = TkServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(rule.getFromEarnGroup(), TKUtils.getTimelessDate(timesheetDocument.getCalendarEntry().getBeginPeriodDateTime()));

                LocalTime ruleStart = new LocalTime(rule.getBeginTime());
                LocalTime ruleEnd = new LocalTime(rule.getEndTime());

                DateTime beginShiftEnd = ruleEnd.toDateTime(calEntryBegin.minusDays(1));
                DateTime beginShiftStart = ruleStart.toDateTime(calEntryBegin.minusDays(1));

                Interval beginShift = adjustShiftDates(beginShiftStart, beginShiftEnd);
                if (beforeCalEntry == null) {
                    beforeCalEntry = new Interval(beginShift.getStart(), calEntryBegin.minusMillis(1));
                } else {
                    if (beginShift.getStart().isBefore(beforeCalEntry.getStart())) {
                        beforeCalEntry = new Interval(beginShift.getStart(), calEntryBegin.minusMillis(1));
                    }
                }
            }
        }
        //get timeblocks that occur within these shifts
        List<TimeBlock> intersectingTimeBlocks = new ArrayList<TimeBlock>();
        TimeBlockService tbService = TkServiceLocator.getTimeBlockService();
        intersectingTimeBlocks.addAll(tbService.getIntersectingTimeBlocks(timesheetDocument.getPrincipalId(), beforeCalEntry.getStart(), beforeCalEntry.getEnd()));
        return intersectingTimeBlocks;
    }

    public List<TimeBlock> getTimeblocksOverlappingEndTimesheetShift(TimesheetDocument timesheetDocument) {
        Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts = getJobNumberToShiftRuleMap(timesheetDocument);
        CalendarEntry calEntry = timesheetDocument.getCalendarEntry();
        Interval afterCalEntry = null;
        if (jobNumberToShifts.isEmpty()) {
            return Collections.emptyList();
        }
        DateTime calEntryEnd = calEntry.getEndPeriodFullDateTime();
        for (Map.Entry<Long, Set<ShiftDifferentialRule>> entry : jobNumberToShifts.entrySet()) {
            for (ShiftDifferentialRule rule : entry.getValue()) {
                //Set<String> fromEarnGroup = TkServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(rule.getFromEarnGroup(), TKUtils.getTimelessDate(timesheetDocument.getCalendarEntry().getBeginPeriodDateTime()));

                LocalTime ruleStart = new LocalTime(rule.getBeginTime());
                LocalTime ruleEnd = new LocalTime(rule.getEndTime());

                DateTime endShiftEnd = ruleEnd.toDateTime(calEntryEnd);
                DateTime endShiftBegin = ruleStart.toDateTime(calEntryEnd);

                Interval endShift = adjustShiftDates(endShiftBegin, endShiftEnd);
                if (afterCalEntry == null) {
                    afterCalEntry = new Interval(calEntryEnd.plusMillis(1), endShift.getEnd());
                } else {
                    if (endShift.getEnd().isAfter(afterCalEntry.getEnd())) {
                        afterCalEntry = new Interval(calEntryEnd.plusMillis(1), endShift.getEnd());
                    }
                }
            }
        }
        //get timeblocks that occur within these shifts
        List<TimeBlock> intersectingTimeBlocks = new ArrayList<TimeBlock>();
        TimeBlockService tbService = TkServiceLocator.getTimeBlockService();
        intersectingTimeBlocks.addAll(tbService.getIntersectingTimeBlocks(timesheetDocument.getPrincipalId(), afterCalEntry.getStart(), afterCalEntry.getEnd()));

        return intersectingTimeBlocks;
    }

    private Interval adjustShiftDates(DateTime start, DateTime end) {
        if (end.isBefore(start) || end.isEqual(start)) {
            end = end.plusDays(1);
        }
        return new Interval(start, end);
    }

    @Override
    public void processShiftDifferentialRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate) {
        List<List<TimeBlock>> shiftedBlockDays = processShift(timesheetDocument, aggregate.getDayTimeBlockList(), false);
        if (shiftedBlockDays != null) {
            aggregate.setDayTimeBlockList(shiftedBlockDays);
        }
    }

    public void processShiftDifferentialRulesForTimeBlocks(TimesheetDocument timesheetDocument, List<TimeBlock> timeBlocks, List<LeaveBlock> leaveBlocks, boolean modifyPrevious) {
        //build List of day intervals for timeblocks (payperiod + 1 day at beginning and end
        CalendarEntry calEntry = timesheetDocument.getCalendarEntry();
        Calendar cal = HrServiceLocator.getCalendarService().getCalendar(calEntry.getHrCalendarId());
        DateTimeZone zone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(timesheetDocument.getPrincipalId()));
        List<Interval> dayIntervals = TKUtils.getDaySpanForCalendarEntry(calEntry, zone);
        Interval firstInterval = dayIntervals.get(0);
        Interval lastInterval = dayIntervals.get(dayIntervals.size()-1);
        if (modifyPrevious) {
            dayIntervals.add(0, new Interval(firstInterval.getStart().minusDays(1), firstInterval.getEnd().minusDays(1)));
        }
        dayIntervals.add(new Interval(lastInterval.getStart().plusDays(1), lastInterval.getEnd().plusDays(1)));

        //build aggregate object (mainly to reuse code)
        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, leaveBlocks, calEntry, cal, true, dayIntervals);
        List<List<TimeBlock>> shiftedBlockDays = processShift(timesheetDocument, aggregate.getDayTimeBlockList(), modifyPrevious);
        if (shiftedBlockDays != null) {
            aggregate.setDayTimeBlockList(shiftedBlockDays);
        }

    }

    protected List<List<TimeBlock>> processShift(TimesheetDocument timesheetDocument, List<List<TimeBlock>> blockDays, boolean allowPriorPayPeriod) {
        String principalId = timesheetDocument.getPrincipalId();
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        DateTime periodStartDateTime = timesheetDocument.getCalendarEntry().getBeginPeriodLocalDateTime().toDateTime(zone);
        if (allowPriorPayPeriod) {
            periodStartDateTime = periodStartDateTime.minusDays(1);
        }

		Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts = getJobNumberToShiftRuleMap(timesheetDocument);

        //convert to builders
        List<List<TimeBlock.Builder>> builderBlockDays = new ArrayList<List<TimeBlock.Builder>>();
        for (List<TimeBlock> tbList : blockDays) {
            List<TimeBlock.Builder> tempList = new ArrayList<TimeBlock.Builder>();
            for (TimeBlock t : tbList) {
                tempList.add(TimeBlock.Builder.create(t));
            }
            builderBlockDays.add(tempList);
        }

        // If there are no shift differential rules, we have an early exit.
		if (jobNumberToShifts.isEmpty()) {
			return null;
		}

		// Get the last day of the previous pay period. We need this to determine
		// if there are hours from the previous pay period that will effect the
		// shift rule on the first day of the currently-being-processed pay period.
		//
        // Will be set to null if not applicable.
        boolean previousPayPeriodPrevDay = !allowPriorPayPeriod;
		Map<Long, List<TimeBlock.Builder>> jobNumberToTimeBlocksPreviousDay = allowPriorPayPeriod ? null :
                getPreviousPayPeriodLastDayJobToTimeBlockMap(timesheetDocument, jobNumberToShifts);

		// We are going to look at the time blocks grouped by Days.
        //
        // This is a very large outer loop.
		for (int pos = 0; pos < builderBlockDays.size(); pos++) {
			List<TimeBlock.Builder> blocks = builderBlockDays.get(pos); // Timeblocks for this day.
			if (blocks.isEmpty()) {
				continue; // No Time blocks, no worries.
            }
			DateTime currentDay = periodStartDateTime.plusDays(pos);
			Interval virtualDay = new Interval(currentDay, currentDay.plusDays(1));

			// Builds our JobNumber to TimeBlock for Current Day List.
            //
            // Shift Differential Rules are also grouped by Job number, this
            // provides a quick way to do the lookup / reference.
            // We don't need every time block, only the ones that will be
            // applicable to the shift rules.
			Map<Long, List<TimeBlock.Builder>> jobNumberToTimeBlocks = new HashMap<Long,List<TimeBlock.Builder>>();
			for (TimeBlock.Builder block : blocks) {
				Long jobNumber = block.getJobNumber();
				if (jobNumberToShifts.containsKey(jobNumber)) {
					List<TimeBlock.Builder> jblist = jobNumberToTimeBlocks.get(jobNumber);
					if (jblist == null) {
						jblist = new ArrayList<TimeBlock.Builder>();
						jobNumberToTimeBlocks.put(jobNumber, jblist);
					}
					jblist.add(block);
				}
			}


            // Large Outer Loop to look at applying the Shift Rules based on
            // the current JobNumber.
            //
			// This loop will handle previous day boundary time as well as the
            // current day.
            //
            // There is room for refactoring here!
			for (Map.Entry<Long, Set<ShiftDifferentialRule>> entry : jobNumberToShifts.entrySet()) {
				Set<ShiftDifferentialRule> shiftDifferentialRules = entry.getValue();
				// Obtain and sort our previous and current time blocks.
				List<TimeBlock.Builder> ruleTimeBlocksPrev = null;
				List<TimeBlock.Builder> ruleTimeBlocksCurr = jobNumberToTimeBlocks.get(entry.getKey());
				if (ruleTimeBlocksCurr != null && ruleTimeBlocksCurr.size() > 0) {
					if (jobNumberToTimeBlocksPreviousDay != null)
						ruleTimeBlocksPrev = jobNumberToTimeBlocksPreviousDay.get(entry.getKey());
					if (ruleTimeBlocksPrev != null && ruleTimeBlocksPrev.size() > 0)
						this.sortTimeBlocksInverse(ruleTimeBlocksPrev);
					this.sortTimeBlocksNatural(ruleTimeBlocksCurr);
				} else {
					// Skip to next job, there is nothing for this job
					// on this day, and because of this we don't care
					// about the previous day either.
					continue;
				}

				for (ShiftDifferentialRule rule : shiftDifferentialRules) {
					Set<String> fromEarnGroup = HrServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(rule.getFromEarnGroup(), timesheetDocument.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate());

                   /* LocalTime ruleStart = new LocalTime(rule.getBeginTime(), zone);
                    LocalTime ruleEnd = new LocalTime(rule.getEndTime(), zone);*/
                    LocalTime ruleStart = new LocalTime(rule.getBeginTime());
                    LocalTime ruleEnd = new LocalTime(rule.getEndTime());


					DateTime shiftEnd = ruleEnd.toDateTime(currentDay);
					DateTime shiftStart = ruleStart.toDateTime(currentDay);

					if (shiftEnd.isBefore(shiftStart) || shiftEnd.isEqual(shiftStart)) {
						shiftEnd = shiftEnd.plusDays(1);
                    }
					Interval shiftInterval = new Interval(shiftStart, shiftEnd);

					// Set up buckets to handle previous days time accumulations
					BigDecimal hoursBeforeVirtualDay = BigDecimal.ZERO;

					// Check current day first block to see if start time gap from virtual day start is greater than max gap
					// if so, we can skip the previous day checks.
					TimeBlock.Builder firstBlockOfCurrentDay = null;
					for (TimeBlock.Builder b : ruleTimeBlocksCurr) {
						if (timeBlockHasEarnCode(fromEarnGroup, b)) {
							firstBlockOfCurrentDay = b;
							break;
						}
					}

					// Previous Day :: We have prior block container of nonzero size, and the previous day is active.
					Interval previousDayShiftInterval = new Interval(shiftStart.minusDays(1), shiftEnd.minusDays(1));

                    // Blank initialization pointer for picking which interval to pass to applyPremium()
                    Interval evalInterval = null;
					if (ruleTimeBlocksPrev != null && ruleTimeBlocksPrev.size() > 0 && dayIsRuleActive(currentDay.minusDays(1), rule)) {
						// Simple heuristic to see if we even need to worry about
						// the Shift rule for this set of data.
						if (shiftEnd.isAfter(virtualDay.getEnd())) {
							// Compare first block of previous day with first block of current day for max gaptitude.
							TimeBlock.Builder firstBlockOfPreviousDay = null;
							for (TimeBlock.Builder b : ruleTimeBlocksPrev) {
								if (timeBlockHasEarnCode(fromEarnGroup, b)
                                        && b.getBeginDateTime().compareTo(b.getEndDateTime()) != 0) {
									firstBlockOfPreviousDay = b;
									break;
								}
							}
							// Only if we actually have at least one block.
                            // Adding Assumption: We must have both a valid current and previous block. Max Gap can not be more than a virtual day.
                            // If this assumption does not hold, additional logic will be needed to iteratively go back in time to figure out which
                            // blocks are valid.
							if ( (firstBlockOfPreviousDay != null) && (firstBlockOfCurrentDay != null)) {
								Interval previousBlockInterval = new Interval(firstBlockOfPreviousDay.getEndDateTime().withZone(zone), firstBlockOfCurrentDay.getBeginDateTime().withZone(zone));
								Duration blockGapDuration = previousBlockInterval.toDuration();
								BigDecimal bgdMinutes = TKUtils.convertMillisToMinutes(blockGapDuration.getMillis());
								// if maxGap is 0, ignore gaps and assign shift to time blocks within the hours
								if (rule.getMaxGap().compareTo(BigDecimal.ZERO) == 0 || bgdMinutes.compareTo(rule.getMaxGap()) <= 0) {
									// If we are here, we know we have at least one valid time block to pull some hours forward from.


									// These are inversely sorted.
									for (int i=0; i<ruleTimeBlocksPrev.size(); i++) {
										TimeBlock.Builder b = ruleTimeBlocksPrev.get(i);
										if (timeBlockHasEarnCode(fromEarnGroup, b)
                                                && b.getBeginDateTime().compareTo(b.getEndDateTime()) != 0) {
											Interval blockInterval = new Interval(b.getBeginDateTime().withZone(zone), b.getEndDateTime().withZone(zone));

											// Calculate Block Gap, the duration between clock outs and clock ins of adjacent time blocks.
											if (previousBlockInterval != null) {
												blockGapDuration = new Duration(b.getEndDateTime().withZone(zone), previousBlockInterval.getStart());
                                                bgdMinutes = TKUtils.convertMillisToMinutes(blockGapDuration.getMillis());
											}

											// Check Gap, if good, sum hours, if maxGap is 0, ignore gaps
											if (rule.getMaxGap().compareTo(BigDecimal.ZERO) == 0 || bgdMinutes.compareTo(rule.getMaxGap()) <= 0) {
												// Calculate Overlap and add it to hours before virtual day bucket.												
												if (blockInterval.overlaps(previousDayShiftInterval)) {
														BigDecimal hrs = TKUtils.convertMillisToHours(blockInterval.overlap(previousDayShiftInterval).toDurationMillis());
														hoursBeforeVirtualDay = hoursBeforeVirtualDay.add(hrs);
													}

											} else {
												// Time blocks are reverse sorted, we can jump out as soon as the max gap is exceeded.
												break;
											}

											previousBlockInterval = blockInterval;

										}
									}
								} else {
									// DO NOTHING!
								}
							}
						}
					}

					BigDecimal hoursToApply = BigDecimal.ZERO;
					BigDecimal hoursToApplyPrevious = BigDecimal.ZERO;
                    // If the hours before virtual day are less than or equal to
                    // min hours, we have already applied the time, so we don't
                    // set hoursToApplyPrevious
					if (hoursBeforeVirtualDay.compareTo(rule.getMinHours()) <= 0) {
						// we need to apply these hours.
						hoursToApplyPrevious = hoursBeforeVirtualDay;
					}


					//  Current Day

					TimeBlock.Builder previous = null; // Previous Time Block
					List<TimeBlock.Builder> accumulatedBlocks = new ArrayList<TimeBlock.Builder>(); // TimeBlocks we MAY or MAY NOT apply Shift Premium to.
                    List<Interval> accumulatedBlockIntervals = new ArrayList<Interval>(); // To save recompute time when checking timeblocks for application we store them as we create them.
					// Iterate over sorted list, checking time boundaries vs Shift Intervals.
					long accumulatedMillis = TKUtils.convertHoursToMillis(hoursBeforeVirtualDay);

                    boolean previousDayOnly = false; // IF the rule is not active today, but was on the previous day, we need to still look at time blocks.
                    if (!dayIsRuleActive(currentDay, rule)) {
                        if (dayIsRuleActive(currentDay.minusDays(1), rule)) {
                            previousDayOnly = true;
                        } else {
                            // Nothing to see here, move to next rule.
                            continue;
                        }

                    }

					/*
					 * We will touch each time block and accumulate time blocks that are applicable to
					 * the current rule we are on.
					 */

                    // These blocks are only used for detail application
                    // We don't want to pass along the previous pay period,
                    // because we don't want to modify the time blocks on that
                    // period. If null is passed, time will be placed on the
                    // first block of the first period if the previous period
                    // block had influence.
                    List<TimeBlock.Builder> previousBlocksFiltered = (previousPayPeriodPrevDay) ? null : filterBlocksByApplicableEarnGroup(fromEarnGroup, ruleTimeBlocksPrev);

					for (TimeBlock.Builder current : ruleTimeBlocksCurr) {
						if (!timeBlockHasEarnCode(fromEarnGroup, current)) {
                            // TODO: WorkSchedule considerations somewhere in here?
                            continue;
                        }

						Interval blockInterval = new Interval(current.getBeginDateTime().withZone(zone), current.getEndDateTime().withZone(zone));

                        if (blockInterval.getStartMillis() == blockInterval.getEndMillis()) {
                            continue;
                        }
						// Check both Intervals, since the time blocks could still
						// be applicable to the previous day.  These two intervals should
						// not have any overlap.
						if (previousDayShiftInterval.overlaps(shiftInterval)) {
							LOG.error("Interval of greater than 24 hours created in the rules processing.");
							return null;
//							throw new RuntimeException("Interval of greater than 24 hours created in the rules processing.");
						}

                        // This block of code handles cases where you have time
                        // that spills to multiple days and a shift rule that
                        // has a valid window on multiple consecutive days. Time
                        // must be applied with the correct shift interval.
						Interval overlap = previousDayShiftInterval.overlap(blockInterval);
                        Interval overlapCurrentDay = shiftInterval.overlap(blockInterval);
                        evalInterval = previousDayShiftInterval;
                        boolean overlapFromPreviousDay = true;
						if (overlap == null) {
                            if (hoursToApplyPrevious.compareTo(BigDecimal.ZERO) > 0) {
                                // we have hours from previous day, and the shift
                                // window is going to move to current day.
                                // Need to apply this now, and move window forward
                                // for current time block.
                                BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
                                this.applyAccumulatedWrapper(accumHours, evalInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocksFiltered, hoursToApplyPrevious, hoursToApply, rule, allowPriorPayPeriod, timesheetDocument.getDocumentId());
                                accumulatedMillis = 0L; // reset accumulated hours..
                                hoursToApply = BigDecimal.ZERO;
                                hoursToApplyPrevious = BigDecimal.ZERO;
                            }

                            // Because of our position in the loop, when we are at this point,
                            // we know we've passed any previous day shift intervals, so we can
                            // determine if we should skip the current day based on the boolean
                            // we set earlier.
                            if (previousDayOnly) {
                                continue;
                            }

							overlap = shiftInterval.overlap(blockInterval);
                            evalInterval = shiftInterval;
                            overlapFromPreviousDay = false;
                        }


                        // Time bucketing and application as normal:
                        //
						if (overlap != null) {
							// There IS overlap.
							if (previous != null) {
								// check if the evalInterval we are on covers the previous time block
								// if not, it means we need to apply the accumulated shift hours and start fresh on a new  interval								
								Interval previousBlockInterval = new Interval(previous.getBeginDateTime().withZone(zone), previous.getEndDateTime().withZone(zone));
								if(evalInterval.overlaps(previousBlockInterval)) {
									// only check max gap if max gap of rule is not 0
									if (rule.getMaxGap().compareTo(BigDecimal.ZERO) != 0 && exceedsMaxGap(previous, current, rule.getMaxGap())) {
										BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
                                        this.applyAccumulatedWrapper(accumHours, evalInterval, accumulatedBlockIntervals,
                                            accumulatedBlocks, previousBlocksFiltered, hoursToApplyPrevious,
                                            hoursToApply, rule, allowPriorPayPeriod, timesheetDocument.getDocumentId());
	                                    accumulatedMillis = 0L; // reset accumulated hours..
										hoursToApply = BigDecimal.ZERO;
										hoursToApplyPrevious = BigDecimal.ZERO;

                                        //didn't hit max gap for previous, but we still need to check for possible next shift
                                        Interval currentShiftOverlap = shiftInterval.overlap(blockInterval);
                                        if (currentShiftOverlap != null) {
                                            long millis = currentShiftOverlap.toDurationMillis();
                                            accumulatedMillis  += millis;
                                            hoursToApply = hoursToApply.add(TKUtils.convertMillisToHours(millis));
                                        }
									} else {
                                    //We really need a list of the shift intervals here as overlap can happen more than one
                                    //per day
                                        List<Interval> overlapIntervals = getOverlappingIntervals(blockInterval,rule, principalId);
                                        for(Interval overlapWithShift : overlapIntervals){
                                            long millis = overlapWithShift.toDurationMillis();
                                            accumulatedMillis  += millis;
                                            hoursToApply = hoursToApply.add(TKUtils.convertMillisToHours(millis));
                                            }
									    }
								} else {
									// rules from different days apply to time block on this day 
									// finish applying accumulated hours to the previous block
									BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
									this.applyAccumulatedWrapper(accumHours, previousDayShiftInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocksFiltered, hoursToApplyPrevious, hoursToApply, rule, allowPriorPayPeriod, timesheetDocument.getDocumentId());
									// start fresh with this block which has a new 
									long millis = overlap.toDurationMillis();
									accumulatedMillis  = millis;
									hoursToApply = TKUtils.convertMillisToHours(millis);
								}
							} else {								
								// get the date of previousDayShiftInterval, check is that day is active for the rule, if not, then don't accumulate the hours
								boolean previousDayActive = dayIsRuleActive(previousDayShiftInterval.getStart(), rule);
								if(!previousDayActive && overlapFromPreviousDay) {
									continue;
								} else {
									// Overlap shift at first time block.
                                    List<Interval> overlapIntervals = getOverlappingIntervals(blockInterval,rule, principalId);
                                    for(Interval overlapWithShift : overlapIntervals){
                                        long millis = overlapWithShift.toDurationMillis();
                                        accumulatedMillis  += millis;
                                        hoursToApply = hoursToApply.add(TKUtils.convertMillisToHours(millis));
                                    }
								}
							}
							accumulatedBlocks.add(current);
                            accumulatedBlockIntervals.add(blockInterval);
							previous = current; // current can still apply to next.
						} else {
							// No Overlap / Outside of Rule
							if (previous != null) {
								BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
                                this.applyAccumulatedWrapper(accumHours, evalInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocksFiltered, hoursToApplyPrevious, hoursToApply, rule, allowPriorPayPeriod, timesheetDocument.getDocumentId());
								accumulatedMillis = 0L; // reset accumulated hours..
								hoursToApply = BigDecimal.ZERO;
								hoursToApplyPrevious = BigDecimal.ZERO;
							}
						}

					}

					// All time blocks are iterated over, check for remainders.
					// Check containers for time, and apply if needed.
					BigDecimal accumHours = TKUtils.convertMillisToHours(accumulatedMillis);
                    this.applyAccumulatedWrapper(accumHours, evalInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocksFiltered, hoursToApplyPrevious, hoursToApply, rule, allowPriorPayPeriod, timesheetDocument.getDocumentId());
                }
			}
			// 	Keep track of previous as we move day by day.
			jobNumberToTimeBlocksPreviousDay = jobNumberToTimeBlocks;
            previousPayPeriodPrevDay = false;
		}

        //convert to TimeBlock
        List<List<TimeBlock>> shiftedBlockDays = new ArrayList<List<TimeBlock>>();
        for (List<TimeBlock.Builder> builderList : builderBlockDays) {
            shiftedBlockDays.add(ModelObjectUtils.<TimeBlock>buildImmutableCopy(builderList));
        }
        return shiftedBlockDays;
	}

    @Override
    public List<ShiftDifferentialRule> getShiftDifferentialRules(String userPrincipalId, String location, String hrSalGroup, String payGrade, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist) {
    	List<ShiftDifferentialRule> results = new ArrayList<ShiftDifferentialRule>();
        
    	List<ShiftDifferentialRule> shiftDifferentialRuleObjs = shiftDifferentialRuleDao.getShiftDifferentialRules(location, hrSalGroup, payGrade, fromEffdt, toEffdt, active, showHist);
    
    	for (ShiftDifferentialRule shiftDifferentialRuleObj : shiftDifferentialRuleObjs) {
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), shiftDifferentialRuleObj.getLocation());
        	
        	Map<String, String> permissionDetails = new HashMap<String, String>();
        	permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, KRADServiceLocatorWeb.getDocumentDictionaryService().getMaintenanceDocumentTypeName(ShiftDifferentialRule.class));
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), permissionDetails)
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), permissionDetails, roleQualification)) {
        		results.add(shiftDifferentialRuleObj);
        	}
    	}
    	
    	return results;
    }

    private List<TimeBlock.Builder> filterBlocksByApplicableEarnGroup(Set<String> fromEarnGroup, List<TimeBlock.Builder> blocks) {
        List<TimeBlock.Builder> filtered;

        if (blocks == null || blocks.size() == 0)
            filtered = null;
        else {
            filtered = new ArrayList<TimeBlock.Builder>();
            for (TimeBlock.Builder b : blocks) {
                if (timeBlockHasEarnCode(fromEarnGroup, b))
                    filtered.add(b);
            }
        }

        return filtered;
    }

    private void applyAccumulatedWrapper(BigDecimal accumHours,
                                         Interval evalInterval,
                                         List<Interval>accumulatedBlockIntervals,
                                         List<TimeBlock.Builder>accumulatedBlocks,
                                         List<TimeBlock.Builder> previousBlocks,
                                         BigDecimal hoursToApplyPrevious,
                                         BigDecimal hoursToApply,
                                         ShiftDifferentialRule rule,
                                         boolean allowEditToPriorPayPeriod,
                                         String currentTimesheetId) {
        if (accumHours.compareTo(rule.getMinHours()) >= 0) {
            this.applyPremium(evalInterval, accumulatedBlockIntervals, accumulatedBlocks, previousBlocks, hoursToApplyPrevious, hoursToApply, rule.getEarnCode(), rule, allowEditToPriorPayPeriod, currentTimesheetId);
        }
        accumulatedBlocks.clear();
        accumulatedBlockIntervals.clear();
    }

	private void sortTimeBlocksInverse(List<? extends TimeBlockContract> blocks) {
		Collections.sort(blocks, new Comparator<TimeBlockContract>() { // Sort the Time Blocks
			public int compare(TimeBlockContract tb1, TimeBlockContract tb2) {
				if (tb1 != null && tb2 != null)
					return -1 * tb1.getBeginDateTime().compareTo(tb2.getBeginDateTime());
				return 0;
			}
		});
	}

	private void sortTimeBlocksNatural(List<? extends TimeBlockContract> blocks) {
		Collections.sort(blocks, new Comparator<TimeBlockContract>() { // Sort the Time Blocks
			public int compare(TimeBlockContract tb1, TimeBlockContract tb2) {
				if (tb1 != null && tb2 != null)
					return tb1.getBeginDateTime().compareTo(tb2.getBeginDateTime());
				return 0;
			}
		});
	}

    /**
     *
     * @param shift The shift interval - need to examine the time block to determine how many hours are eligible per block.
     * @param blockIntervals Intervals for each block present in the blocks list. Passed here to avoid re computation.
     * @param blocks The blocks we are applying hours to.
     * @param previousBlocks If present, this is the list of time blocks from a previous "day", on which the initial hours (from previous day) should be placed.
     * @param initialHours hours accumulated from a previous boundary that need to be applied here (NOT SUBJECT TO INTERVAL)
     * @param hours hours to apply
     * @param earnCode what earn code to create time hour detail entry for.
     */
	protected void applyPremium(Interval shift, List<Interval> blockIntervals, List<TimeBlock.Builder> blocks,
                                List<TimeBlock.Builder> previousBlocks, BigDecimal initialHours, BigDecimal hours,
                                String earnCode, ShiftDifferentialRule rule, boolean allowEditToPriorPayPeriod, String currentTimesheetId) {
        //Map<Interval, Long> nextGaps = new HashMap<Interval, Long>();
        hours = hours.setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_UP);
        List<Interval> possibleShifts = new ArrayList<Interval>(3);
        if (shift != null) {
            possibleShifts.add(new Interval(shift.getStart().minusDays(1), shift.getEnd().minusDays(1)));
            possibleShifts.add(shift);
            possibleShifts.add(new Interval(shift.getStart().plusDays(1), shift.getEnd().plusDays(1)));
        }
        Map<Interval, Long> shiftOverlapMillis = new HashMap<Interval, Long>();
        Map<Interval, Interval> overlapToShift = new HashMap<Interval, Interval>();
        String principalId = StringUtils.EMPTY;
        TimeBlock.Builder firstTimeBlock = CollectionUtils.isNotEmpty(blocks) ? blocks.get(0) : null;
        if (CollectionUtils.isNotEmpty(blocks)) {
            principalId = blocks.get(0).getPrincipalId();
        }
        DateTimeZone zone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(principalId));
        List<Interval> allOverlaps = new ArrayList<Interval>();
        List<Interval> allBlockIntervals = new ArrayList<Interval>(blockIntervals);
        if (CollectionUtils.isNotEmpty(previousBlocks)
                && firstTimeBlock != null) {
            for (TimeBlock.Builder previousTb : previousBlocks) {
                //check gap, it within minimum gap, add them the block intervals
                if (rule.getMaxGap().compareTo(BigDecimal.ZERO) != 0
                        && exceedsMaxGap(previousTb, firstTimeBlock, rule.getMaxGap())) {
                    //do not add
                } else {
                    Interval prevInterval = new Interval(previousTb.getBeginDateTime().withZone(zone), previousTb.getEndDateTime().withZone(zone));
                    allBlockIntervals.add(prevInterval);
        }
            }
        }
        for (Interval interval : allBlockIntervals) {
            if (interval.getStartMillis() != interval.getEndMillis()) {
                allOverlaps.addAll(getOverlappingIntervals(interval, rule, principalId));
            }
        }
        //may need this gap information.. commented out to keep here for now.
        /*if (rule.getMaxGap().compareTo(BigDecimal.ZERO) != 0) {
            for (int i = 0; i < allOverlaps.size(); i++) {
                Long gap = 0L;
                if (i+1 < allOverlaps.size()) {
                    gap = allOverlaps.get(i+1).getStartMillis() - allOverlaps.get(i).getEndMillis();
                }
                nextGaps.put(allOverlaps.get(i), gap);
            }
        }*/
        for (Interval overlap : allOverlaps) {
            for (Interval possibleShift : possibleShifts) {
                if (possibleShift.overlaps(overlap)) {
                    overlapToShift.put(overlap, possibleShift);
                    if (!shiftOverlapMillis.containsKey(possibleShift)) {
                        shiftOverlapMillis.put(possibleShift, possibleShift.overlap(overlap).toDurationMillis());
                    } else {
                        shiftOverlapMillis.put(possibleShift, (shiftOverlapMillis.get(possibleShift) + possibleShift.overlap(overlap).toDurationMillis()));
                    }
                }
            }
        }

        for (int i=0; i<blocks.size(); i++) {
			TimeBlock.Builder b = blocks.get(i);

            // Only apply initial hours to the first timeblock.
			if (i == 0 && (initialHours.compareTo(BigDecimal.ZERO) > 0)) {
                // ONLY if they're on the same document ID, do we apply to previous,
                // otherwise we dump all on the current document.
                if (previousBlocks != null && previousBlocks.size() > 0 && (allowEditToPriorPayPeriod || previousBlocks.get(0).getDocumentId().equals(currentTimesheetId))) {
                    for (TimeBlock.Builder pb : previousBlocks) {
                        BigDecimal lunchSub = this.negativeTimeHourDetailSum(pb); // A negative number
                        initialHours = BigDecimal.ZERO.max(initialHours.add(lunchSub)); // We don't want negative premium hours!
                        if (initialHours.compareTo(BigDecimal.ZERO) <= 0) // check here now as well, we may not have anything at all to apply.
                            break;

                        //initial hours already has lunch sub
                        BigDecimal hoursToApply = initialHours.min(pb.getHours());
                        addPremiumTimeHourDetail(pb, hoursToApply, earnCode);
                        initialHours = initialHours.subtract(hoursToApply, HrConstants.MATH_CONTEXT);
                        if (initialHours.compareTo(BigDecimal.ZERO) <= 0)
                            break;
                    }
                } else {
                    addPremiumTimeHourDetail(b, initialHours, earnCode);
                }
            }

            BigDecimal lunchSub = this.negativeTimeHourDetailSum(b); // A negative number
            hours = BigDecimal.ZERO.max(hours.add(lunchSub)); // We don't want negative premium hours!

			if (hours.compareTo(BigDecimal.ZERO) > 0) {
                Interval blockInterval = blockIntervals.get(i);
                List<Interval> overlapIntervals = getOverlappingIntervals(blockInterval, rule, b.getPrincipalId());
                BigDecimal allHoursToApply = BigDecimal.ZERO;
                for (Interval overlapInterval : overlapIntervals) {
                    if (overlapInterval == null
                            || overlapInterval.getStartMillis() == overlapInterval.getEndMillis()) {
                        continue;
                    }

                    long minMillis = TKUtils.convertHoursToMillis(rule.getMinHours());
                    long overlap = overlapInterval.toDurationMillis();
                    BigDecimal hoursMax = TKUtils.convertMillisToHours(overlap); // Maximum number of possible hours applicable for this time block and shift rule
                    // Adjust this time block's hoursMax (below) by lunchSub to
                    // make sure the time applied is the correct amount per block.
                    //if (overlap >= minMillis || overlapIntervals.size() == 1) {
                    Interval overlapShift = overlapToShift.get(overlapInterval);
                    Long totalOverlapMillisInShift = shiftOverlapMillis.get(overlapShift);
                    Long lunchSubMillis = TKUtils.convertHoursToMillis(lunchSub);
                    if ((totalOverlapMillisInShift - lunchSubMillis) >= minMillis) {
                        //BigDecimal hoursToApply = hours.min(hoursMax.add(lunchSub));
                        allHoursToApply = allHoursToApply.add(hoursMax);
                    }
                }
                if (allHoursToApply.compareTo(BigDecimal.ZERO) > 0) {
                    addPremiumTimeHourDetail(b, allHoursToApply.add(lunchSub), earnCode);
                    hours = hours.subtract(allHoursToApply.add(lunchSub), HrConstants.MATH_CONTEXT);
                }
			}
		}
	}

    /**
     * This is to allow for two periods to be evaluated by the rule we probably can incorporate
     * this into core logic to allow for a List<Interval>
     * This case fixed a rule that ran from 6p - 6a and 6p - 6a and it only evaluated the 6p-6a of the start day and
     * not the previous day or a timeblock that spanned a rule from a previous day to current day then a new
     * rule started on the same day and ends next day
     * @param timeBlockOverlap
     * @param rule
     * @return
     */
    protected List<Interval> getOverlappingIntervals(Interval timeBlockOverlap, ShiftDifferentialRule rule, String principalId){
        List<Interval> overlappingIntervals = new ArrayList<Interval>();

        DateTimeZone zone = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();

        //See if a shift interval starts on the previous day and has an overlap into the next day
        DateTime previousDay = timeBlockOverlap.getStart().minusDays(1);
        if(dayIsRuleActive(previousDay,rule)){
            LocalTime ruleStart = new LocalTime(rule.getBeginTime());
            LocalTime ruleEnd = new LocalTime(rule.getEndTime());

            DateTime shiftEnd = ruleEnd.toDateTime(timeBlockOverlap.getStart());
            if (ruleEnd.isAfter(ruleStart)) {
                shiftEnd = ruleEnd.toDateTime(timeBlockOverlap.getEnd().minusDays(1));
            }
            DateTime shiftStart = ruleStart.toDateTime(previousDay);
            Interval shiftInterval = new Interval(shiftStart,shiftEnd);
            Interval overlapInterval = shiftInterval.overlap(timeBlockOverlap);
            if(overlapInterval != null){
                overlappingIntervals.add(overlapInterval);
            }
        }

        DateTime currentDay = timeBlockOverlap.getStart();
        if(dayIsRuleActive(currentDay,rule)){
            LocalTime ruleStart = new LocalTime(rule.getBeginTime());
            LocalTime ruleEnd = new LocalTime(rule.getEndTime());

            DateTime shiftEnd = null;
            if(ruleEnd.isBefore(ruleStart) || ruleEnd.isEqual(ruleStart)){
                shiftEnd = ruleEnd.toDateTime(timeBlockOverlap.getEnd().plusDays(1));
            }  else {
                shiftEnd = ruleEnd.toDateTime(timeBlockOverlap.getEnd());
            }
            DateTime shiftStart = ruleStart.toDateTime(currentDay);
            Interval shiftInterval = new Interval(shiftStart,shiftEnd);
            Interval overlapInterval = shiftInterval.overlap(timeBlockOverlap);
            if(overlapInterval != null){
                overlappingIntervals.add(overlapInterval);
            }
        }

        return overlappingIntervals;

    }

	void addPremiumTimeHourDetail(TimeBlock.Builder block, BigDecimal hours, String earnCode) {
        List<TimeHourDetail.Builder> details = block.getTimeHourDetails();
        TimeHourDetail.Builder premium = TimeHourDetail.Builder.create();
		premium.setHours(hours);
		premium.setEarnCode(earnCode);
		premium.setTkTimeBlockId(block.getTkTimeBlockId());
        if (!details.contains(premium)) {
            details.add(premium);
	    }
    }

	/**
	 * Does the difference between the previous time blocks clock out time and the
	 * current time blocks clock in time exceed the max gap. max gap is in minutes
	 *
	 * @param previous
	 * @param current
	 * @param maxGap
	 * @return
	 */
	boolean exceedsMaxGap(TimeBlockContract previous, TimeBlockContract current, BigDecimal maxGap) {
		long difference = current.getBeginDateTime().getMillis() - previous.getEndDateTime().getMillis();
		BigDecimal gapMinutes = TKUtils.convertMillisToMinutes(difference);

		return (gapMinutes.compareTo(maxGap) > 0);
	}

	public void setShiftDifferentialRuleDao(ShiftDifferentialRuleDao shiftDifferentialRuleDao) {
		this.shiftDifferentialRuleDao = shiftDifferentialRuleDao;
	}

	@Override
	public ShiftDifferentialRule getShiftDifferentialRule(String tkShiftDifferentialRuleId) {
		return this.shiftDifferentialRuleDao.findShiftDifferentialRule(tkShiftDifferentialRuleId);
	}

	@Override
	public List<ShiftDifferentialRule> getShiftDifferentalRules(String location, String hrSalGroup, String payGrade, String pyCalendarGroup, LocalDate asOfDate) {
		List<ShiftDifferentialRule> sdrs = new ArrayList<ShiftDifferentialRule>();

		// location, sal group, pay grade, calendar
	    sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, payGrade, pyCalendarGroup, asOfDate));

		// location, sal group, *, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, "%", pyCalendarGroup, asOfDate));

		// location, *, pay grade, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", payGrade, pyCalendarGroup, asOfDate));

		// location, *, *, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", "%", pyCalendarGroup, asOfDate));

		// *, sal group, pay grade, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, payGrade, pyCalendarGroup, asOfDate));

		// *, sal group, *, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, "%", pyCalendarGroup, asOfDate));

		// *, *, pay grade, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", payGrade, pyCalendarGroup, asOfDate));

		// *, *, *, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", "%", pyCalendarGroup, asOfDate));

		// location, sal group, pay grade, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, payGrade, "%", asOfDate));

		// location, sal group, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, "%", "%", asOfDate));

		// location, *, pay grade, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", payGrade, "%", asOfDate));
		
		// location, *, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", "%", "%", asOfDate));

		// *, sal group, pay grade, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, payGrade, "%", asOfDate));

		// *, sal group, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, "%", "%", asOfDate));

		// *, *, pay grade, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", payGrade, "%", asOfDate));

		// *, *, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", "%", "%", asOfDate));

		return sdrs;
	}

	private boolean dayIsRuleActive(DateTime currentDate, ShiftDifferentialRule sdr) {
		boolean active = false;

		switch (currentDate.getDayOfWeek()) {
		case DateTimeConstants.MONDAY:
			active = sdr.isMonday();
			break;
		case DateTimeConstants.TUESDAY:
			active = sdr.isTuesday();
			break;
		case DateTimeConstants.WEDNESDAY:
			active = sdr.isWednesday();
			break;
		case DateTimeConstants.THURSDAY:
			active = sdr.isThursday();
			break;
		case DateTimeConstants.FRIDAY:
			active = sdr.isFriday();
			break;
		case DateTimeConstants.SATURDAY:
			active = sdr.isSaturday();
			break;
		case DateTimeConstants.SUNDAY:
			active = sdr.isSunday();
			break;
		}

		return active;
	}

	@Override
	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules) {
		shiftDifferentialRuleDao.saveOrUpdate(shiftDifferentialRules);
	}

	@Override
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule) {
		shiftDifferentialRuleDao.saveOrUpdate(shiftDifferentialRule);
	}

    //@Override
    public void checkForNonActiveTimesheetChanges(String currentDocumetId, List<TimeBlock> extraBlocks, List<TimeBlock> newTimeBlocks) {
        Map<String, TimeBlock> nonActiveTimesheetBlocks = new HashMap<String, TimeBlock>();
        Map<String, Boolean> sendNotificationForDoc = new HashMap<String, Boolean>();
        if (currentDocumetId == null) {
            return;
        }
        for (TimeBlock tb : newTimeBlocks) {
            if (!tb.getDocumentId().equals(currentDocumetId)) {
                if (!sendNotificationForDoc.containsKey(tb.getDocumentId())) {
                    DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(tb.getDocumentId());
                    if (DocumentStatus.ENROUTE.equals(documentStatus)) {
                        List<ActionTaken> actions = KewApiServiceLocator.getWorkflowDocumentService().getActionsTaken(tb.getDocumentId());
                        boolean containsApprove = false;
                        for (ActionTaken action : actions) {
                            if (action.getActionTaken().equals(ActionType.APPROVE)) {
                                containsApprove = true;
                                break;
                            }
                        }
                        if(containsApprove) {
                            sendNotificationForDoc.put(tb.getDocumentId(), Boolean.TRUE);
                        } else {
                            sendNotificationForDoc.put(tb.getDocumentId(), Boolean.FALSE);
                        }
                    } else {
                        sendNotificationForDoc.put(tb.getDocumentId(), Boolean.FALSE);
                    }
                }
                nonActiveTimesheetBlocks.put(tb.getTkTimeBlockId(), tb);
            }
        }

        for (TimeBlock extra : extraBlocks) {
            if (sendNotificationForDoc.containsKey(extra.getDocumentId())
                    && sendNotificationForDoc.get(extra.getDocumentId())) {

                if (nonActiveTimesheetBlocks.containsKey(extra.getTkTimeBlockId())) {
                    TimeBlock nonActiveTimesheetBlock = nonActiveTimesheetBlocks.get(extra.getTkTimeBlockId());
                    if (extra.getTimeHourDetails().size() != nonActiveTimesheetBlock.getTimeHourDetails().size()) {
                        //send fyi
                        sendShiftFYI(extra.getDocumentId());
                        break;
                    }
                    for (TimeHourDetail hourDetail : extra.getTimeHourDetails()) {
                        if (!nonActiveTimesheetBlock.getTimeHourDetails().contains(hourDetail)) {
                            //send fyi
                            sendShiftFYI(extra.getDocumentId());
                            break;
                        }
                    }
                }
            }
        }
    }

    protected void sendShiftFYI(String docId) {
        String annotationTemplate = "Document was modified by a rules operation on a subsequent timesheet after it was approved.";
        Set<String> approverIds = new HashSet<String>();
        Set<String> doNotFYIAgainIds = new HashSet<String>();
        // get approver's node name
        List<ActionRequest> rootActionRequests = KewApiServiceLocator.getWorkflowDocumentService().getRootActionRequests(docId);
        for (ActionRequest actionRequest : rootActionRequests) {
            List<ActionRequest> childRequests = actionRequest.getChildRequests();
            for (ActionRequest childRequest : childRequests) {
                if (ActionRequestType.APPROVE.equals(childRequest.getActionRequested())) {
                    approverIds.add(childRequest.getPrincipalId());
                }
                if (ActionRequestType.FYI.equals(childRequest.getActionRequested())
                        && annotationTemplate.equals(childRequest.getAnnotation())
                        && !ActionRequestStatus.DONE.equals(childRequest.getStatus())) {
                    doNotFYIAgainIds.add(childRequest.getPrincipalId());
                }
            }
        }

        for (String principalId : approverIds) {
            if (!doNotFYIAgainIds.contains(principalId)) {
                DocumentActionParameters.Builder documentActionParametersBuilder = DocumentActionParameters.Builder.create(docId, principalId);
                documentActionParametersBuilder.setAnnotation(annotationTemplate);
                AdHocToPrincipal.Builder adhocToPrincipalBuilder = AdHocToPrincipal.Builder.create(ActionRequestType.fromCode(KewApiConstants.ACTION_REQUEST_FYI_REQ), null, principalId);
                adhocToPrincipalBuilder.setResponsibilityDescription("approver");
                KewApiServiceLocator.getWorkflowDocumentActionsService().adHocToPrincipal(documentActionParametersBuilder.build(), adhocToPrincipalBuilder.build());
            }
        }
    }
}
