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
package org.kuali.kpme.tklm.time.rules.overtime.weekly.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.workarea.WorkArea;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetailContract;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.flsa.FlsaDay;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRule;
import org.kuali.kpme.tklm.time.rules.overtime.weekly.dao.WeeklyOvertimeRuleDao;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.*;

public class WeeklyOvertimeRuleServiceImpl implements WeeklyOvertimeRuleService {

    protected static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WeeklyOvertimeRuleServiceImpl.class);

	private WeeklyOvertimeRuleDao weeklyOvertimeRuleDao;
    private static final ModelObjectUtils.Transformer<TimeBlock, TimeBlock.Builder> toTimeBlockBuilder =
            new ModelObjectUtils.Transformer<TimeBlock, TimeBlock.Builder>() {
                public TimeBlock.Builder transform(TimeBlock input) {
                    return TimeBlock.Builder.create(input);
                };
            };
    private static final ModelObjectUtils.Transformer<TimeBlockBo, TimeBlock> toTimeBlock =
            new ModelObjectUtils.Transformer<TimeBlockBo, TimeBlock>() {
                public TimeBlock transform(TimeBlockBo input) {
                    return TimeBlockBo.to(input);
                };
            };
    private static final ModelObjectUtils.Transformer<TimeBlock, TimeBlockBo> toTimeBlockBo =
            new ModelObjectUtils.Transformer<TimeBlock, TimeBlockBo>() {
                public TimeBlockBo transform(TimeBlock input) {
                    return TimeBlockBo.from(input);
                };
            };

	@Override
	public void processWeeklyOvertimeRule(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate) {

		LocalDate asOfDate = timesheetDocument.getDocumentHeader().getEndDateTime().toLocalDate();
		String principalId = timesheetDocument.getDocumentHeader().getPrincipalId();
		DateTime beginDate = timesheetDocument.getDocumentHeader().getBeginDateTime();
		DateTime endDate = timesheetDocument.getDocumentHeader().getEndDateTime();
		List<WeeklyOvertimeRule> weeklyOvertimeRules = getWeeklyOvertimeRules(asOfDate);

        List<List<FlsaWeek>> newFlsaWeeks = getFlsaWeeks(principalId, beginDate, endDate, aggregate);

        for (List<FlsaWeek> flsaWeekParts : newFlsaWeeks)
        {
            BigDecimal totalNewOvertimeHours = null;
            BigDecimal hoursTowardsMaxEarnCodes = null;
            for (WeeklyOvertimeRule weeklyOvertimeRule : weeklyOvertimeRules)
            {
                Set<String> maxHoursEarnCodes = HrServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(weeklyOvertimeRule.getMaxHoursEarnGroup(), asOfDate);
                Set<String> convertFromEarnCodes = HrServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(weeklyOvertimeRule.getConvertFromEarnGroup(), asOfDate);
                Set<String> applyToEarnCodes = HrServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(weeklyOvertimeRule.getApplyToEarnGroup(), asOfDate);

                LOG.info(weeklyOvertimeRule);
                LOG.info("maxHoursEarnCodes =");
                LOG.info(maxHoursEarnCodes);

                LOG.info("convertFromEarnCodes =");
                LOG.info(convertFromEarnCodes);

                LOG.info("before applying rule");
                LOG.info(newFlsaWeeks);

                /***************/

                if (hoursTowardsMaxEarnCodes == null) {
                    hoursTowardsMaxEarnCodes = getHours(flsaWeekParts, maxHoursEarnCodes);
                }
                if (totalNewOvertimeHours == null) {
                    totalNewOvertimeHours = hoursTowardsMaxEarnCodes.subtract(weeklyOvertimeRule.getMaxHours(), HrConstants.MATH_CONTEXT);
                }

                //flsaWeekParts.get(0).getFlsaDays().get(0).getAppliedTimeBlocks().get(0).
                BigDecimal thisRuleConvertableHours = getHours(flsaWeekParts, convertFromEarnCodes);

                LOG.info("maxEarnCodes");
                LOG.info(maxHoursEarnCodes);


                LOG.info("convertTo");
                LOG.info(weeklyOvertimeRule.getConvertToEarnCode());

                LOG.info("newOverTimeHours");
                LOG.info(totalNewOvertimeHours);


                BigDecimal thisRuleOvertimeHoursToApply = BigDecimal.ZERO;

                if (thisRuleConvertableHours.compareTo(BigDecimal.ZERO) > 0) {
                    thisRuleOvertimeHoursToApply = totalNewOvertimeHours;
                    if (thisRuleConvertableHours.compareTo(totalNewOvertimeHours) < 0) {
                        thisRuleOvertimeHoursToApply = thisRuleConvertableHours;
                    }
                }

                Boolean ruleRan = false;
                if ( ( totalNewOvertimeHours.compareTo(BigDecimal.ZERO) > 0) && (thisRuleOvertimeHoursToApply.compareTo(BigDecimal.ZERO) > 0)) {
                    BigDecimal afterOvertimeRuleRanHours = applyOvertimeToFlsaWeeks(flsaWeekParts, weeklyOvertimeRule, asOfDate.plusDays((7 - asOfDate.getDayOfWeek())), applyToEarnCodes, thisRuleOvertimeHoursToApply);
                    totalNewOvertimeHours = totalNewOvertimeHours.subtract(thisRuleOvertimeHoursToApply.subtract(afterOvertimeRuleRanHours));
                }
                LOG.info("ruleRan: " + (ruleRan ? "Y" : "N"));
            }
            LOG.info("after applying rule");
            LOG.info(newFlsaWeeks);
		}

        //convert weeks to list of timeblocks to push back into aggregate
        List<List<TimeBlock>> updatedBlocks = new ArrayList<List<TimeBlock>>();

        DateTime d1 = aggregate.getPayCalendarEntry().getBeginPeriodFullDateTime().minusDays(1);
        DateTime d2 = aggregate.getPayCalendarEntry().getBatchEndPayPeriodFullDateTime();

        Interval calEntryInterval = null;
        if (d2 != null)
        {
            calEntryInterval = new Interval(aggregate.getPayCalendarEntry().getBeginPeriodFullDateTime().minusDays(1), aggregate.getPayCalendarEntry().getEndPeriodFullDateTime().plusDays((7 - aggregate.getPayCalendarEntry().getBatchEndPayPeriodFullDateTime().getDayOfWeek())));
        }
        else
        {
            //calEntryInterval = new Interval(d1, aggregate.getPayCalendarEntry().getEndPeriodFullDateTime().plusDays((7 - d2.getDayOfWeek() ) ));
            calEntryInterval = new Interval(aggregate.getPayCalendarEntry().getBeginPeriodFullDateTime().minusDays(1), aggregate.getPayCalendarEntry().getEndPeriodFullDateTime());
        }


        for (List<FlsaWeek> weekParts : newFlsaWeeks) {
           for (FlsaWeek week : weekParts) {
               for (FlsaDay day : week.getFlsaDays()) {
                   if (calEntryInterval.contains(day.getFlsaDate().toDateTime())) {
                        updatedBlocks.add(day.getAppliedTimeBlocks());
                   }
               }
           }
        }

        //merge
        if (aggregate.getDayTimeBlockList().size() - updatedBlocks.size() == 2) {
            List<TimeBlock> firstDay = aggregate.getDayTimeBlockList().get(0);
            List<TimeBlock> lastDay = aggregate.getDayTimeBlockList().get(aggregate.getDayTimeBlockList().size() - 1);
            updatedBlocks.add(0, firstDay);
            updatedBlocks.add(lastDay);
        }

        aggregate.setDayTimeBlockList(updatedBlocks);
		//savePreviousNextCalendarTimeBlocks(flsaWeeks);
	}
	
	/**
	 * Get the list of all FlsaWeek lists for this period.  An FlsaWeek list is a set of partial FlsaWeeks that overlap a CalendarEntry boundary.  The total of
	 * all days in an FlsaWeek list adds up to one complete week.  This is done so WeeklyOvertimeRule calculations are done for multiple Calendar periods.
	 * 
	 * @param principalId The principal id to apply the rules to
	 * @param beginDate The begin date of the CalendarEntry
	 * @param endDate The end date of the CalendarEntry
	 * @param aggregate The aggregate of the applicable TimeBlocks
	 * 
	 * @return the list of all FlsaWeek lists for this period
	 */
	protected List<List<FlsaWeek>> getFlsaWeeks(String principalId, DateTime beginDate, DateTime endDate, TkTimeBlockAggregate aggregate) {
		List<List<FlsaWeek>> flsaWeeks = new ArrayList<List<FlsaWeek>>();
		
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
		List<FlsaWeek> currentWeeks = aggregate.getFlsaWeeks(zone, 0, false);
		
		for (ListIterator<FlsaWeek> weekIterator = currentWeeks.listIterator(); weekIterator.hasNext(); ) {
			List<FlsaWeek> flsaWeek = new ArrayList<FlsaWeek>();
			
			int index = weekIterator.nextIndex();
			FlsaWeek currentWeek = weekIterator.next();
			
			if (index == 0 && !currentWeek.isFirstWeekFull()) {
				TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(principalId, beginDate);
				if (timesheetDocumentHeader != null) {
                    TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentHeader.getDocumentId());
                    List<String> assignmentKeys = new ArrayList<String>();
                    for(Assignment assignment : timesheetDocument.getAllAssignments()) {
                        assignmentKeys.add(assignment.getAssignmentKey());
                    }

                    List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(timesheetDocumentHeader.getDocumentId());
                    List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(principalId, timesheetDocumentHeader.getBeginDateTime().toLocalDate(), timesheetDocumentHeader.getEndDateTime().toLocalDate(), assignmentKeys);
                    if (CollectionUtils.isNotEmpty(timeBlocks)) {
						CalendarEntry calendarEntry =  HrServiceLocator.getCalendarEntryService().getCalendarDatesByPayEndDate(principalId, timesheetDocumentHeader.getEndDateTime(), HrConstants.PAY_CALENDAR_TYPE);
						Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
                        TkTimeBlockAggregate previousAggregate = new TkTimeBlockAggregate(timeBlocks, leaveBlocks, calendarEntry, calendar, true);
						List<FlsaWeek> previousWeek = previousAggregate.getFlsaWeeks(zone, 0, false);
						if (CollectionUtils.isNotEmpty(previousWeek)) {
							flsaWeek.add(previousWeek.get(previousWeek.size() - 1));
						}
					}
				 }
			}
			
			flsaWeek.add(currentWeek);
			
			if (index == currentWeeks.size() - 1 && !currentWeek.isLastWeekFull()) {
				TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getNextDocumentHeader(principalId, endDate);
				if (timesheetDocumentHeader != null) {
                    TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentHeader.getDocumentId());
                    List<String> assignmentKeys = new ArrayList<String>();
                    for(Assignment assignment : timesheetDocument.getAllAssignments()) {
                        assignmentKeys.add(assignment.getAssignmentKey());
                    }
					List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(timesheetDocumentHeader.getDocumentId());
                    List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(principalId, timesheetDocumentHeader.getBeginDateTime().toLocalDate(), timesheetDocumentHeader.getEndDateTime().toLocalDate(), assignmentKeys);

					if (CollectionUtils.isNotEmpty(timeBlocks)) {
						CalendarEntry calendarEntry =  HrServiceLocator.getCalendarEntryService().getCalendarDatesByPayEndDate(principalId, timesheetDocumentHeader.getEndDateTime(), HrConstants.PAY_CALENDAR_TYPE);
                        Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
						TkTimeBlockAggregate nextAggregate = new TkTimeBlockAggregate(timeBlocks, leaveBlocks, calendarEntry, calendar, true);
						List<FlsaWeek> nextWeek = nextAggregate.getFlsaWeeks(zone, 0 , false);
						if (CollectionUtils.isNotEmpty(nextWeek)) {
							flsaWeek.add(nextWeek.get(0));
						}
					}
				 }
			}
			
			flsaWeeks.add(flsaWeek);
		}

        List<List<FlsaWeek>> newFlsaWeeks = new ArrayList<List<FlsaWeek>>();

        for (List<FlsaWeek> thisWeek : flsaWeeks)
        {
            if (thisWeek.size() == 2)
            {
                List<FlsaWeek> tmpList = new ArrayList<FlsaWeek>();

                if ( (thisWeek.get(0).getFlsaDays().size() + thisWeek.get(1).getFlsaDays().size()) > 7)
                {
                    FlsaWeek tmpWeek = new FlsaWeek(thisWeek.get(0).getFlsaDayConstant(), thisWeek.get(0).getFlsaTime(), thisWeek.get(0).getPayPeriodBeginTime());
                    for (FlsaDay tmpDay : thisWeek.get(0).getFlsaDays()) {
                        if (tmpDay.getFlsaDate().toDateTime().isBefore(endDate.toDateTime()))
                        {
                            tmpWeek.addFlsaDay(tmpDay);
                        }
                    }
                    tmpList.add(tmpWeek);
                    tmpList.add(thisWeek.get(1));
                    newFlsaWeeks.add(tmpList);
                }
                else
                {
                    newFlsaWeeks.add(thisWeek);
                }
            }
            else
            {
                newFlsaWeeks.add(thisWeek);
            }
        }

		return newFlsaWeeks;
	}
	
	/**
	 * Get the hours worked for the given FlsaWeeks with EarnCodes within maxHoursEarnCodes.
	 * 
	 * @param flsaWeeks The FlsaWeeks to get the hours from
	 * @param maxHoursEarnCodes The EarnCodes used to determine what is applicable as a max hour
	 * 
	 * @return the maximum worked hours for the given FlsaWeeks
	 */
	protected BigDecimal getHours(List<FlsaWeek> flsaWeeks, Set<String> maxHoursEarnCodes) {
		BigDecimal maxHours = BigDecimal.ZERO;
		
		for (FlsaWeek flsaWeek : flsaWeeks) {
			for (FlsaDay flsaDay : flsaWeek.getFlsaDays()) {
				for (TimeBlock timeBlock : flsaDay.getAppliedTimeBlocks()) {
                    Boolean validTimeBlock = false;
					for (TimeHourDetail timeHourDetail : timeBlock.getTimeHourDetails()) {
						if (maxHoursEarnCodes.contains(timeHourDetail.getEarnCode()) || validTimeBlock) {
                            validTimeBlock = true;
							maxHours = maxHours.add(timeHourDetail.getHours(), HrConstants.MATH_CONTEXT);
						}

					}
				}
                for (LeaveBlock leaveBlock : flsaDay.getAppliedLeaveBlocks()) {
                    if (maxHoursEarnCodes.contains(leaveBlock.getEarnCode())) {
                        maxHours = maxHours.add(leaveBlock.getLeaveAmount().negate());
                    }
                }
			}
		}
		
		return maxHours;
	}
	
	/**
	 * Returns the overtime EarnCode.  Defaults to the EarnCode on the WeeklyOvertimeRule but overrides with the WorkArea default overtime EarnCode and 
	 * the TimeBlock overtime preference EarnCode in that order if they exist.
	 * 
	 * @param weeklyOvertimeRule The WeeklyOvertimeRule to use when calculating the overtime EarnCode
	 * @param timeBlock The TimeBlock to use when calculating the overtime EarnCode
	 * @param asOfDate The effectiveDate of the WorkArea
	 * 
	 * @return the overtime EarnCode
	 */
	protected String getOvertimeEarnCode(WeeklyOvertimeRule weeklyOvertimeRule, TimeBlockContract timeBlock, LocalDate asOfDate) {

        String overtimeEarnCode = weeklyOvertimeRule.getConvertToEarnCode();
        LOG.info("weekly overtime rule code is " + overtimeEarnCode);

        // KPME-2554 use time block end date instead of passed in asOfDate
        WorkArea workArea = HrServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getWorkArea(), timeBlock.getEndDateTime().toLocalDate());
		if ( StringUtils.isNotBlank(workArea.getDefaultOvertimeEarnCode()) && (!weeklyOvertimeRule.isOverrideWorkAreaDefaultOvertime()) ) {
			overtimeEarnCode = workArea.getDefaultOvertimeEarnCode();
            LOG.info("weekly overtime rule code is from work area -- " + overtimeEarnCode);
		}
		
        if (StringUtils.isNotEmpty(timeBlock.getOvertimePref())) {
        	overtimeEarnCode = timeBlock.getOvertimePref();
            LOG.info("weekly overtime rule code is from timeblock.getOvertimePref -- " + overtimeEarnCode);
        }
        
        return overtimeEarnCode;
	}
	
	/**
	 * Applies overtime to the given FlsaWeeks.
	 * 
	 * @param flsaWeeks The FlsaWeeks to apply the overtime to
	 * @param weeklyOvertimeRule The WeeklyOvertimeRule in effective
	 * @param asOfDate The effectiveDate of the WorkArea
	 * @param convertFromEarnCodes The EarnCodes to convert to overtime
	 * @param overtimeHours The number of overtime hours to apply
	 */
	protected BigDecimal applyOvertimeToFlsaWeeks(List<FlsaWeek> flsaWeeks, WeeklyOvertimeRule weeklyOvertimeRule, LocalDate asOfDate, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
		List<FlsaDay> flsaDays = getFlsaDays(flsaWeeks);


        BigDecimal otHoursLeft = applyPositiveOvertimeToFlsaWeek(flsaDays, weeklyOvertimeRule, asOfDate, convertFromEarnCodes, overtimeHours);
/*
		if (overtimeHours.compareTo(BigDecimal.ZERO) > 0) {
            LOG.info("applying positive" + overtimeHours);
			applyPositiveOvertimeToFlsaWeek(flsaDays, weeklyOvertimeRule, asOfDate, convertFromEarnCodes, overtimeHours);
		} else if (overtimeHours.compareTo(BigDecimal.ZERO) < 0) {
            LOG.info("applying negative" + overtimeHours);
            applyPositiveOvertimeToFlsaWeek(flsaDays, weeklyOvertimeRule, asOfDate, convertFromEarnCodes, overtimeHours);

		}
        else if (false)
        {
            applyNegativeOvertimeToFlsaWeek(flsaDays, weeklyOvertimeRule, asOfDate, convertFromEarnCodes, overtimeHours);
        }
        */
		removeEmptyOvertime(flsaDays, weeklyOvertimeRule, asOfDate);
        return otHoursLeft;
	}
	
	/**
	 * Gets the list of FlsaDays in the given FlsaWeek.
	 * 
	 * @param flsaWeeks The FlsaWeek to fetch the FlsaDays from
	 * 
	 * @return the list of FlsaDays in the given FlsaWeek
	 */
	protected List<FlsaDay> getFlsaDays(List<FlsaWeek> flsaWeeks) {
		List<FlsaDay> flsaDays = new ArrayList<FlsaDay>();
		
		for (FlsaWeek flsaWeek : flsaWeeks) {
			flsaDays.addAll(flsaWeek.getFlsaDays());
		}
		
		return flsaDays;
	}
	
	/**
	 * Applies positive overtime to the given FlsaDays.
	 * 
	 * @param flsaDays The FlsaDays to apply the overtime to
	 * @param weeklyOvertimeRule The WeeklyOvertimeRule in effective
	 * @param asOfDate The effectiveDate of the WorkArea
	 * @param convertFromEarnCodes The EarnCodes to convert to overtime
	 * @param overtimeHours The number of overtime hours to apply
	 */
	protected BigDecimal applyPositiveOvertimeToFlsaWeek(List<FlsaDay> flsaDays, WeeklyOvertimeRule weeklyOvertimeRule, LocalDate asOfDate, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
		for (ListIterator<FlsaDay> dayIterator = flsaDays.listIterator(flsaDays.size()); dayIterator.hasPrevious(); ) {
			FlsaDay flsaDay = dayIterator.previous();
			
			List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>(flsaDay.getAppliedTimeBlocks());
            //timeBlocks.addAll(flsaDay.get)

//            flsaDay.getAppliedLeaveBlocks()
			Collections.sort(timeBlocks, new Comparator<TimeBlock>() {
				public int compare(TimeBlock timeBlock1, TimeBlock timeBlock2) {
					return ObjectUtils.compare(timeBlock1.getBeginDateTime(), timeBlock2.getBeginDateTime());
				}
			});

            List<TimeBlockBo> bos = ModelObjectUtils.transform(timeBlocks, toTimeBlockBo);
			for (ListIterator<TimeBlockBo> timeBlockIterator = bos.listIterator(bos.size()); timeBlockIterator.hasPrevious(); ) {
				TimeBlockBo timeBlock = timeBlockIterator.previous();
				String overtimeEarnCode = getOvertimeEarnCode(weeklyOvertimeRule, timeBlock, asOfDate);
                LOG.info("applying " + overtimeHours + "hours FROM earnCodes : " + convertFromEarnCodes + " to overTime earnCode " + overtimeEarnCode);


				overtimeHours = applyPositiveOvertimeOnTimeBlock(timeBlock, overtimeEarnCode, convertFromEarnCodes, overtimeHours);
                LOG.info("after this run, OT hours = " + overtimeHours);
			}
			flsaDay.setAppliedTimeBlocks(ModelObjectUtils.transform(bos, toTimeBlock));
			flsaDay.remapTimeHourDetails();
		}
        return overtimeHours;
	}
	
	/**
	 * Applies negative overtime to the given FlsaDays.
	 * 
	 * @param flsaDays The FlsaDays to apply the overtime to
	 * @param weeklyOvertimeRule The WeeklyOvertimeRule in effective
	 * @param asOfDate The effectiveDate of the WorkArea
	 * @param convertFromEarnCodes The EarnCodes to convert to overtime
	 * @param overtimeHours The number of overtime hours to apply
	 */
	protected void applyNegativeOvertimeToFlsaWeek(List<FlsaDay> flsaDays, WeeklyOvertimeRule weeklyOvertimeRule, LocalDate asOfDate, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
		for (ListIterator<FlsaDay> dayIterator = flsaDays.listIterator(); dayIterator.hasNext(); ) {
			FlsaDay flsaDay = dayIterator.next();
			
			List<TimeBlock> timeBlocks = new ArrayList(flsaDay.getAppliedTimeBlocks());
			Collections.sort(timeBlocks, new Comparator<TimeBlock>() {
				public int compare(TimeBlock timeBlock1, TimeBlock timeBlock2) {
					return ObjectUtils.compare(timeBlock1.getBeginDateTime(), timeBlock2.getBeginDateTime());
				}
			});
            List<TimeBlockBo> bos = ModelObjectUtils.transform(timeBlocks, toTimeBlockBo);
			for (ListIterator<TimeBlockBo> timeBlockIterator = bos.listIterator(); timeBlockIterator.hasNext(); ) {
				TimeBlockBo timeBlock = timeBlockIterator.next();
				String overtimeEarnCode = getOvertimeEarnCode(weeklyOvertimeRule, timeBlock, asOfDate);
				overtimeHours = applyNegativeOvertimeOnTimeBlock(timeBlock, overtimeEarnCode, convertFromEarnCodes, overtimeHours);
			}
            flsaDay.setAppliedTimeBlocks(ModelObjectUtils.transform(bos, toTimeBlock));
			flsaDay.remapTimeHourDetails();
		}
	}
	
	protected void removeEmptyOvertime(List<FlsaDay> flsaDays, WeeklyOvertimeRule weeklyOvertimeRule, LocalDate asOfDate) {
		for (FlsaDay flsaDay : flsaDays ) {
			List<TimeBlockBo> timeBlocks = ModelObjectUtils.transform(flsaDay.getAppliedTimeBlocks(), toTimeBlockBo);
			for (TimeBlockBo timeBlock : timeBlocks ) {
				String overtimeEarnCode = getOvertimeEarnCode(weeklyOvertimeRule, timeBlock, asOfDate);

				List<TimeHourDetailBo> timeHourDetails = timeBlock.getTimeHourDetails();

                List<TimeHourDetailBo> newTimeHourDetails = new ArrayList<TimeHourDetailBo>();
                for (TimeHourDetailBo detail : timeHourDetails)
                {
                    if ( (detail.getHours().compareTo(BigDecimal.ZERO) != 0) || StringUtils.equals(detail.getEarnCode(), timeBlock.getEarnCode()) )
                    {
                        newTimeHourDetails.add(detail);
                    }
                }

                timeBlock.setTimeHourDetails(newTimeHourDetails);
			}
            flsaDay.setAppliedTimeBlocks(ModelObjectUtils.transform(timeBlocks, toTimeBlock) );
		}
	}

    protected BigDecimal applyPositiveOvertimeOnTimeBlockOld(TimeBlockBo timeBlock, String overtimeEarnCode, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
        BigDecimal applied = BigDecimal.ZERO;
        List<TimeHourDetailBo> timeHourDetails = timeBlock.getTimeHourDetails();
        List<TimeHourDetailBo> newTimeHourDetails = new ArrayList<TimeHourDetailBo>();

        LOG.info(  "INITIAL block; " + timeBlock.getTkTimeBlockId() + "overtimeHours = " + overtimeHours +  " overall timeblock hours = " + timeBlock.getHours() + " earnCode = " + timeBlock.getEarnCode()
                + " OT = " + overtimeEarnCode + " convertFrom = " + convertFromEarnCodes  + " startDate = " + timeBlock.getBeginDateTime() );
        for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
            LOG.info("EC = " + timeHourDetail.getEarnCode() + " hours = " + timeHourDetail.getHours() + " OT = " + overtimeEarnCode + " convertFrom = " + convertFromEarnCodes);
        }

        //LOG.info("before timeBlock hours = " + timeBlock.getHours() + "OT Hours = " + overtimeHours + " timeHourDetail Size = " + timeHourDetails.size());

        for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
            if (convertFromEarnCodes.contains(timeHourDetail.getEarnCode())) {
                if (timeHourDetail.getHours().compareTo(overtimeHours) >= 0) {
                    applied = overtimeHours;
                } else {
                    applied = timeHourDetail.getHours();
                }

                EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(overtimeEarnCode, timeBlock.getEndDateTime().toLocalDate());
                BigDecimal hours = earnCodeObj.getInflateFactor().multiply(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);

                TimeHourDetailBo overtimeTimeHourDetail = getTimeHourDetailByEarnCode(timeHourDetails, Collections.singletonList(overtimeEarnCode));
                if (overtimeTimeHourDetail != null) {
                    overtimeTimeHourDetail.setHours(overtimeTimeHourDetail.getHours().add(hours, HrConstants.MATH_CONTEXT));
                    //overtimeTimeHourDetail.setHours(hours);
                } else {
                    TimeHourDetailBo newTimeHourDetail = new TimeHourDetailBo();
                    newTimeHourDetail.setTkTimeBlockId(timeBlock.getTkTimeBlockId());
                    newTimeHourDetail.setEarnCode(overtimeEarnCode);
                    newTimeHourDetail.setHours(hours);

                    newTimeHourDetails.add(newTimeHourDetail);
                }

                timeHourDetail.setHours(timeHourDetail.getHours().subtract(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
            }
        }

        for (TimeHourDetailBo newTimeHourDetail : newTimeHourDetails) {
            timeBlock.addTimeHourDetail(newTimeHourDetail);
        }

        LOG.info("END block; " + timeBlock.getTkTimeBlockId() + " overall timeblock hours = " + timeBlock.getHours() + " earnCode = " + timeBlock.getEarnCode());
        for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
            LOG.info("EC = " + timeHourDetail.getEarnCode() + " hours = " + timeHourDetail.getHours() + " OT = " + overtimeEarnCode + " convertFrom = " + convertFromEarnCodes);
        }

        return overtimeHours.subtract(applied);
    }


	/**
	 * Applies overtime additions to the indicated TimeBlock.
	 *
	 * @param timeBlock The time block to apply the overtime on.
	 * @param overtimeEarnCode The overtime earn code to apply overtime to.
	 * @param convertFromEarnCodes The other earn codes on the time block.
	 * @param overtimeHours The overtime hours to apply.
	 *
	 * @return the amount of overtime hours remaining to be applied.
	 */
    protected BigDecimal applyPositiveOvertimeOnTimeBlock(TimeBlockBo timeBlock, String overtimeEarnCode, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
        BigDecimal applied = BigDecimal.ZERO;
        List<TimeHourDetailBo> timeHourDetails = timeBlock.getTimeHourDetails();
        List<TimeHourDetailBo> newTimeHourDetails = new ArrayList<TimeHourDetailBo>();

        LOG.info(  "INITIAL block; " + timeBlock.getTkTimeBlockId() + "overtimeHours = " + overtimeHours +  " overall timeblock hours = " + timeBlock.getHours() + " earnCode = " + timeBlock.getEarnCode()
                + " OT = " + overtimeEarnCode + " convertFrom = " + convertFromEarnCodes  + " startDate = " + timeBlock.getBeginDateTime() );
        for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
            LOG.info("EC = " + timeHourDetail.getEarnCode() + " hours = " + timeHourDetail.getHours() );
        }


        EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(overtimeEarnCode, timeBlock.getEndDateTime().toLocalDate());

        BigDecimal toApply = BigDecimal.ZERO;
        toApply = toApply.add(timeBlock.getHours());


        BigDecimal otApplied = BigDecimal.ZERO;

        BigDecimal newOtActual = BigDecimal.ZERO;

        BigDecimal maxOtToConvert = BigDecimal.ZERO;
        BigDecimal existingOtActualHours = BigDecimal.ZERO;
        BigDecimal newOt = BigDecimal.ZERO;

        TimeHourDetailBo overtimeTimeHourDetail = null;

        List<TimeHourDetailBo> toConvert = new ArrayList<TimeHourDetailBo>();

        BigDecimal regularHours = BigDecimal.ZERO;
        for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
            if (convertFromEarnCodes.contains(timeHourDetail.getEarnCode())) {
                maxOtToConvert = maxOtToConvert.add(timeHourDetail.getHours());
                toConvert.add(timeHourDetail);
                regularHours = regularHours.add(timeHourDetail.getHours());
            }
            else if (timeHourDetail.getEarnCode().equals(overtimeEarnCode))
            {
                overtimeTimeHourDetail = timeHourDetail;
            }
            else
            {
                regularHours = regularHours.add(timeHourDetail.getHours());
            }
        }

        LOG.info("regularHours = " + regularHours + " maxOtToConvert = " + maxOtToConvert + " overtimeHours = " + overtimeHours);


        //this is the actual hours of overtime worked before any multiplying factor.
        existingOtActualHours = timeBlock.getHours().subtract(regularHours);

        LOG.info("actual OT Hours =  " + existingOtActualHours);
        if (overtimeHours.compareTo(BigDecimal.ZERO) <= 0)
        {
/*
            if (existingOtActualHours.compareTo(BigDecimal.ZERO) == 0) {
                return  BigDecimal.ZERO;
            }
            else
            {
            */
            LOG.info("actual OT hours neq zero");

            if (existingOtActualHours.compareTo(BigDecimal.ZERO) != 0) {
                //handle this case Friday morning
                if (toConvert.size() > 0)
                {
                    toConvert.get(0).setHours(toConvert.get(0).getHours().add(existingOtActualHours));
                }
                else
                {
                    TimeHourDetailBo newTimeHourDetail = new TimeHourDetailBo();
                    newTimeHourDetail.setTkTimeBlockId(timeBlock.getTkTimeBlockId());
                    newTimeHourDetail.setEarnCode(convertFromEarnCodes.iterator().next());
                    newTimeHourDetail.setHours(existingOtActualHours);
                    timeBlock.addTimeHourDetail(newTimeHourDetail);

                    //Friday
                    //timeHourDetails.get(0).setHours(existingOtActualHours);
                }
            }
            newOtActual = BigDecimal.ZERO;       //could this be a problem??
            newOt = BigDecimal.ZERO;
        }
        else if (overtimeHours.compareTo(existingOtActualHours) <= 0) // new overtime is less than old overtime
        {
            //subtract from existing OT until existing OT is zero, if necessary add to toConvert List until the difference is ZERO
            //newOtToAdd = existingOt
            BigDecimal newOtToRemove = existingOtActualHours.subtract(overtimeHours);

            newOtActual = overtimeHours;
            newOt = earnCodeObj.getInflateFactor().multiply(overtimeHours, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);

            if (toConvert.size() > 0)
            {
                toConvert.get(0).setHours(toConvert.get(0).getHours().add(newOtToRemove));
            }
            else
            {
//                LOG.debug("hitting problem case");
//                timeHourDetails.get(0).setHours(timeHourDetails.get(0).getHours().add(newOtToRemove));

                TimeHourDetailBo newTimeHourDetail = new TimeHourDetailBo();
                newTimeHourDetail.setTkTimeBlockId(timeBlock.getTkTimeBlockId());
//                newTimeHourDetail.setEarnCode(timeBlock.getEarnCode());
                newTimeHourDetail.setEarnCode(convertFromEarnCodes.iterator().next());
                newTimeHourDetail.setHours(newOtToRemove);
                timeBlock.addTimeHourDetail(newTimeHourDetail);
            }


            LOG.info("overtimeHours <= existingOtActualHours: " + overtimeHours + " vs " + existingOtActualHours + "newOtActual = " + newOtActual + " newOt = " + newOt);
        }
        else if (overtimeHours.compareTo(existingOtActualHours) > 0) //new overtime is greater than old overtime
        {
            //add difference to OT, up to maxOtToConvert.  remove from toConvert as necessary
            BigDecimal newOtToAdd = overtimeHours.subtract(existingOtActualHours);
            BigDecimal toReduce = newOtToAdd;
            if (newOtToAdd.compareTo(maxOtToConvert) >= 0) {
                newOtToAdd = maxOtToConvert;
            }

            LOG.info("newOtToAdd: " + newOtToAdd );

            for (TimeHourDetailBo timeHourDetailBo : toConvert) {
                BigDecimal existingHours = timeHourDetailBo.getHours();
                if (toReduce.compareTo(existingHours) >= 0)
                {
                    timeHourDetailBo.setHours(BigDecimal.ZERO);
                    toReduce = toReduce.subtract(existingHours);
                }
                else if (toReduce.compareTo(BigDecimal.ZERO) > 0)
                {
                    timeHourDetailBo.setHours(timeHourDetailBo.getHours().subtract(toReduce));
                    toReduce = toReduce.subtract(toReduce);
                }
            }

            newOtActual = existingOtActualHours.add(newOtToAdd);
            newOt = earnCodeObj.getInflateFactor().multiply(newOtActual, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);

            LOG.info("overtimeHours > existingOtActualHours: " + overtimeHours + " vs " + existingOtActualHours + "newOtActual = " + newOtActual + " newOt = " + newOt);
        }


        if (overtimeTimeHourDetail != null) {
            overtimeTimeHourDetail.setHours(newOt);
        } else {
            TimeHourDetailBo newTimeHourDetail = new TimeHourDetailBo();
            newTimeHourDetail.setTkTimeBlockId(timeBlock.getTkTimeBlockId());
            newTimeHourDetail.setEarnCode(overtimeEarnCode);
            newTimeHourDetail.setHours(newOt);
            timeBlock.addTimeHourDetail(newTimeHourDetail);
        }

        LOG.info("COMPLETE block; " + timeBlock.getTkTimeBlockId() + " overall timeblock hours = " + timeBlock.getHours() + " earnCode = " + timeBlock.getEarnCode() + " OT = " + overtimeEarnCode + " convertFrom = " + convertFromEarnCodes );
        for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
            LOG.info("EC = " + timeHourDetail.getEarnCode() + " hours = " + timeHourDetail.getHours() );
        }

        LOG.info("returning   " + overtimeHours.subtract(newOtActual));

        return overtimeHours.subtract(newOtActual);
/*
        if (changeMade)
        {
        }
        else
        {
            return overtimeHours;
        }
        */
    }




	protected BigDecimal applyPositiveOvertimeOnTimeBlockNew(TimeBlockBo timeBlock, String overtimeEarnCode, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
		BigDecimal applied = BigDecimal.ZERO;
		List<TimeHourDetailBo> timeHourDetails = timeBlock.getTimeHourDetails();
		List<TimeHourDetailBo> newTimeHourDetails = new ArrayList<TimeHourDetailBo>();

        LOG.info(  "INITIAL block; " + timeBlock.getTkTimeBlockId() + "overtimeHours = " + overtimeHours +  " overall timeblock hours = " + timeBlock.getHours() + " earnCode = " + timeBlock.getEarnCode()
                    + " OT = " + overtimeEarnCode + " convertFrom = " + convertFromEarnCodes  + " startDate = " + timeBlock.getBeginDateTime() );
        for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
            LOG.info("EC = " + timeHourDetail.getEarnCode() + " hours = " + timeHourDetail.getHours() );
        }


        Boolean timeBlockHasOt = false;
        for (TimeHourDetailBo tmpDetail : timeBlock.getTimeHourDetails() )
        {
            if (tmpDetail.getEarnCode().equals(overtimeEarnCode))
            {
                timeBlockHasOt = true;
            }
        }




        Boolean changeMade = false;

        BigDecimal toApply = BigDecimal.ZERO;
        toApply = toApply.add(timeBlock.getHours());


        BigDecimal otApplied = BigDecimal.ZERO;

		for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
			if (convertFromEarnCodes.contains(timeHourDetail.getEarnCode())) {

                changeMade = true;
                if (overtimeHours.compareTo(timeHourDetail.getHours()) >= 0)
                {
                    applied = timeHourDetail.getHours();
                }
                else {
                    applied = overtimeHours;
                }


				EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(overtimeEarnCode, timeBlock.getEndDateTime().toLocalDate());
				BigDecimal hours = earnCodeObj.getInflateFactor().multiply(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);

				TimeHourDetailBo overtimeTimeHourDetail = getTimeHourDetailByEarnCode(timeHourDetails, Collections.singletonList(overtimeEarnCode));
				if (overtimeTimeHourDetail != null) {
                    overtimeTimeHourDetail.setHours(hours);
                    toApply = toApply.subtract(hours);
                    otApplied = otApplied.add(hours);
				} else {
					TimeHourDetailBo newTimeHourDetail = new TimeHourDetailBo();
					newTimeHourDetail.setTkTimeBlockId(timeBlock.getTkTimeBlockId());
					newTimeHourDetail.setEarnCode(overtimeEarnCode);
					newTimeHourDetail.setHours(hours);

					newTimeHourDetails.add(newTimeHourDetail);
                    toApply = toApply.subtract(hours);

                    otApplied = otApplied.add(hours);
				}

//                timeHourDetail.setHours(timeBlock.getHours().subtract(hours));
/*
                BigDecimal newHours = null;
                if (toApply.compareTo(timeHourDetail.getHours()) >= 0 )
                {
                }
                else {
                    newHours = toApply.setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
                }
*/
                BigDecimal newHours = toApply;
                if (newHours.compareTo(timeHourDetail.getHours()) >= 0)
                {
                    newHours = timeHourDetail.getHours();
                }
                timeHourDetail.setHours(newHours);
                toApply = toApply.subtract(newHours);


                //timeHourDetail.setHours(timeHourDetail.getHours().subtract(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP)   );

                /*
                if (timeBlockHasOt)
                {
                    timeHourDetail.setHours(applied.setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
                }
                else
                {
                    timeHourDetail.setHours(timeHourDetail.getHours().subtract(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
                }
                */
			}
            else if (!timeHourDetail.getEarnCode().equals(overtimeEarnCode))
            {
                toApply = toApply.subtract(timeHourDetail.getHours());
            }
		}

		for (TimeHourDetailBo newTimeHourDetail : newTimeHourDetails) {
			timeBlock.addTimeHourDetail(newTimeHourDetail);
		}
        LOG.info("COMPLETE block; " + timeBlock.getTkTimeBlockId() + " overall timeblock hours = " + timeBlock.getHours() + " earnCode = " + timeBlock.getEarnCode() + " OT = " + overtimeEarnCode + " convertFrom = " + convertFromEarnCodes );
        for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
            LOG.info("EC = " + timeHourDetail.getEarnCode() + " hours = " + timeHourDetail.getHours() );
        }

        LOG.info("OT applied = " + otApplied);
        return overtimeHours.subtract(otApplied);
/*
        if (changeMade)
        {
        }
        else
        {
            return overtimeHours;
        }
        */
	}
	
	/**
	 * Applies overtime subtractions on the indicated TimeBlock.
	 *
	 * @param timeBlock The time block to reset the overtime on.
	 * @param overtimeEarnCode The overtime earn code to apply overtime to.
	 * @param convertFromEarnCodes The other earn codes on the time block.
	 * @param overtimeHours The overtime hours to apply.
	 *
	 * @return the amount of overtime hours remaining to be applied.
	 */
    protected BigDecimal applyNegativeOvertimeOnTimeBlock(TimeBlockBo timeBlock, String overtimeEarnCode, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
        BigDecimal applied = BigDecimal.ZERO;
        List<TimeHourDetailBo> timeHourDetails = timeBlock.getTimeHourDetails();
        List<TimeHourDetailBo> newTimeHourDetails = new ArrayList<TimeHourDetailBo>();

        for (TimeHourDetailBo timeHourDetail : timeHourDetails) {
            if (convertFromEarnCodes.contains(timeHourDetail.getEarnCode())) {
                TimeHourDetailBo overtimeTimeHourDetail = getTimeHourDetailByEarnCode(timeHourDetails, Collections.singletonList(overtimeEarnCode));

                if (overtimeTimeHourDetail != null) { //if overtime already exists
                    TimeHourDetail.Builder overtimeTimeHourDetailBuilder = TimeHourDetail.Builder.create(overtimeTimeHourDetail);
                    applied = overtimeTimeHourDetail.getHours().add(overtimeHours, HrConstants.MATH_CONTEXT); // add the negative number of hours of overtime to the existing OT hours
                    if (applied.compareTo(BigDecimal.ZERO) >= 0) {  //if the OT hours is still greater than or equal to zero, set 'applied' to this new negative number
                        applied = overtimeHours;
                    } else {
                        //new OT value is now less than zero
                        applied = overtimeTimeHourDetail.getHours().negate(); //OT is now less than zer
                    }

                    EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(overtimeEarnCode, timeBlock.getEndDateTime().toLocalDate());
                    BigDecimal hours = earnCodeObj.getInflateFactor().multiply(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN);

                    overtimeTimeHourDetailBuilder.setHours(overtimeTimeHourDetail.getHours().add(hours, HrConstants.MATH_CONTEXT));

                    timeHourDetail.setHours(timeHourDetail.getHours().subtract(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
                }
            }
            newTimeHourDetails.add(timeHourDetail);
        }
        return overtimeHours.subtract(applied);
    }
	
	protected <T extends TimeHourDetailContract> T getTimeHourDetailByEarnCode(List<T> timeHourDetails, Collection<String> earnCodes) {
		T result = null;
		
		for (T timeHourDetail : timeHourDetails) {
			if (earnCodes.contains(timeHourDetail.getEarnCode())) {
				result = timeHourDetail;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Saves the TimeBlocks from both the previous and next CalendarEntry, since these are not saved automatically by calling methods.
	 * 
	 * @param flsaWeeks The list of FlsaWeek lists
	 */
	protected void savePreviousNextCalendarTimeBlocks(List<List<FlsaWeek>> flsaWeeks) {
		for (ListIterator<List<FlsaWeek>> weeksIterator = flsaWeeks.listIterator(); weeksIterator.hasNext(); ) {
			int index = weeksIterator.nextIndex();
			List<FlsaWeek> currentWeekParts = weeksIterator.next();
			
			if (index == 0 && currentWeekParts.size() > 1) {
				FlsaWeek previousFlsaWeek = currentWeekParts.get(0);
				for (FlsaDay flsaDay : previousFlsaWeek.getFlsaDays()) {
					KRADServiceLocator.getBusinessObjectService().save(ModelObjectUtils.transform(flsaDay.getAppliedTimeBlocks(), toTimeBlockBo));
                    //TkServiceLocator.getTimeBlockService().saveOrUpdateTimeBlocks(flsaDay.getAppliedTimeBlocks());
				}
			}
				
			if (index == flsaWeeks.size() - 1 && currentWeekParts.size() > 1) {
				FlsaWeek nextFlsaWeek = currentWeekParts.get(currentWeekParts.size() - 1);
				for (FlsaDay flsaDay : nextFlsaWeek.getFlsaDays()) {
					KRADServiceLocator.getBusinessObjectService().save(ModelObjectUtils.transform(flsaDay.getAppliedTimeBlocks(), toTimeBlockBo));
                    //TkServiceLocator.getTimeBlockService().saveOrUpdateTimeBlocks(flsaDay.getAppliedTimeBlocks());
				}
			}
		}
	}

	@Override
	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(LocalDate asOfDate) {
		return weeklyOvertimeRuleDao.findWeeklyOvertimeRules(asOfDate);
	}

	@Override
	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule) {
		weeklyOvertimeRuleDao.saveOrUpdate(weeklyOvertimeRule);
	}

	@Override
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules) {
		weeklyOvertimeRuleDao.saveOrUpdate(weeklyOvertimeRules);
	}

	public void setWeeklyOvertimeRuleDao(WeeklyOvertimeRuleDao weeklyOvertimeRuleDao) {
		this.weeklyOvertimeRuleDao = weeklyOvertimeRuleDao;
	}

	@Override
	public WeeklyOvertimeRule getWeeklyOvertimeRule(String tkWeeklyOvertimeRuleId) {
		return weeklyOvertimeRuleDao.getWeeklyOvertimeRule(tkWeeklyOvertimeRuleId);
	}

}
