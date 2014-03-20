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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class WeeklyOvertimeRuleServiceImpl implements WeeklyOvertimeRuleService {

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

		List<List<FlsaWeek>> flsaWeeks = getFlsaWeeks(principalId, beginDate, endDate, aggregate);

		for (WeeklyOvertimeRule weeklyOvertimeRule : weeklyOvertimeRules) {
			Set<String> maxHoursEarnCodes = HrServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(weeklyOvertimeRule.getMaxHoursEarnGroup(), asOfDate);
			Set<String> convertFromEarnCodes = HrServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(weeklyOvertimeRule.getConvertFromEarnGroup(), asOfDate);

			for (List<FlsaWeek> flsaWeekParts : flsaWeeks) {
				BigDecimal existingMaxHours = getMaxHours(flsaWeekParts, maxHoursEarnCodes);
				BigDecimal newOvertimeHours = existingMaxHours.subtract(weeklyOvertimeRule.getMaxHours(), HrConstants.MATH_CONTEXT);
				
				if (newOvertimeHours.compareTo(BigDecimal.ZERO) != 0) {
					applyOvertimeToFlsaWeeks(flsaWeekParts, weeklyOvertimeRule, asOfDate, convertFromEarnCodes, newOvertimeHours);
				}
			}
		}

        //convert weeks to list of timeblocks to push back into aggregate
        List<List<TimeBlock>> updatedBlocks = new ArrayList<List<TimeBlock>>();
        Interval calEntryInterval = new Interval(aggregate.getPayCalendarEntry().getBeginPeriodFullDateTime(), aggregate.getPayCalendarEntry().getEndPeriodFullDateTime());
        for (List<FlsaWeek> weekParts : flsaWeeks) {
           for (FlsaWeek week : weekParts) {
               for (FlsaDay day : week.getFlsaDays()) {
                   if (calEntryInterval.contains(day.getFlsaDate().toDateTime())) {
                        updatedBlocks.add(day.getAppliedTimeBlocks());
                   }
               }
           }

        }
        aggregate.setDayTimeBlockList(updatedBlocks);
		savePreviousNextCalendarTimeBlocks(flsaWeeks);
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
		
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
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
                    for(Assignment assignment : timesheetDocument.getAssignments()) {
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
                    for(Assignment assignment : timesheetDocument.getAssignments()) {
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
		
		return flsaWeeks;
	}
	
	/**
	 * Get the maximum worked hours for the given FlsaWeeks.
	 * 
	 * @param flsaWeeks The FlsaWeeks to get the hours from
	 * @param maxHoursEarnCodes The EarnCodes used to determine what is applicable as a max hour
	 * 
	 * @return the maximum worked hours for the given FlsaWeeks
	 */
	protected BigDecimal getMaxHours(List<FlsaWeek> flsaWeeks, Set<String> maxHoursEarnCodes) {
		BigDecimal maxHours = BigDecimal.ZERO;
		
		for (FlsaWeek flsaWeek : flsaWeeks) {
			for (FlsaDay flsaDay : flsaWeek.getFlsaDays()) {
				for (TimeBlock timeBlock : flsaDay.getAppliedTimeBlocks()) {
					for (TimeHourDetail timeHourDetail : timeBlock.getTimeHourDetails()) {
						if (maxHoursEarnCodes.contains(timeHourDetail.getEarnCode())) {
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

        // KPME-2554 use time block end date instead of passed in asOfDate
        WorkArea workArea = HrServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getWorkArea(), timeBlock.getEndDateTime().toLocalDate());
		if (StringUtils.isNotBlank(workArea.getDefaultOvertimeEarnCode())){
			overtimeEarnCode = workArea.getDefaultOvertimeEarnCode();
		}
		
        if (StringUtils.isNotEmpty(timeBlock.getOvertimePref())) {
        	overtimeEarnCode = timeBlock.getOvertimePref();
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
	protected void applyOvertimeToFlsaWeeks(List<FlsaWeek> flsaWeeks, WeeklyOvertimeRule weeklyOvertimeRule, LocalDate asOfDate, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
		List<FlsaDay> flsaDays = getFlsaDays(flsaWeeks);
		
		if (overtimeHours.compareTo(BigDecimal.ZERO) > 0) {
			applyPositiveOvertimeToFlsaWeek(flsaDays, weeklyOvertimeRule, asOfDate, convertFromEarnCodes, overtimeHours);
		} else if (overtimeHours.compareTo(BigDecimal.ZERO) < 0) {
			applyNegativeOvertimeToFlsaWeek(flsaDays, weeklyOvertimeRule, asOfDate, convertFromEarnCodes, overtimeHours);
		}
		
		removeEmptyOvertime(flsaDays, weeklyOvertimeRule, asOfDate);
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
	protected void applyPositiveOvertimeToFlsaWeek(List<FlsaDay> flsaDays, WeeklyOvertimeRule weeklyOvertimeRule, LocalDate asOfDate, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
		for (ListIterator<FlsaDay> dayIterator = flsaDays.listIterator(flsaDays.size()); dayIterator.hasPrevious(); ) {
			FlsaDay flsaDay = dayIterator.previous();
			
			List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>(flsaDay.getAppliedTimeBlocks());
			Collections.sort(timeBlocks, new Comparator<TimeBlock>() {
				public int compare(TimeBlock timeBlock1, TimeBlock timeBlock2) {
					return ObjectUtils.compare(timeBlock1.getBeginDateTime(), timeBlock2.getBeginDateTime());
				}
			});

            List<TimeBlockBo> bos = ModelObjectUtils.transform(timeBlocks, toTimeBlockBo);
			for (ListIterator<TimeBlockBo> timeBlockIterator = bos.listIterator(bos.size()); timeBlockIterator.hasPrevious(); ) {
				TimeBlockBo timeBlock = timeBlockIterator.previous();
				String overtimeEarnCode = getOvertimeEarnCode(weeklyOvertimeRule, timeBlock, asOfDate);
				overtimeHours = applyPositiveOvertimeOnTimeBlock(timeBlock, overtimeEarnCode, convertFromEarnCodes, overtimeHours);
			}
			flsaDay.setAppliedTimeBlocks(ModelObjectUtils.transform(bos, toTimeBlock));
			flsaDay.remapTimeHourDetails();
		}
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
			List<TimeBlock.Builder> timeBlocks = ModelObjectUtils.transform(flsaDay.getAppliedTimeBlocks(), toTimeBlockBuilder);
			for (TimeBlock.Builder timeBlock : timeBlocks ) {
				String overtimeEarnCode = getOvertimeEarnCode(weeklyOvertimeRule, timeBlock.build(), asOfDate);

				List<TimeHourDetail> timeHourDetails = timeBlock.build().getTimeHourDetails();

				TimeHourDetail overtimeTimeHourDetail = getTimeHourDetailByEarnCode(timeHourDetails, Collections.singletonList(overtimeEarnCode));
				if (overtimeTimeHourDetail != null) {
					if (overtimeTimeHourDetail.getHours().compareTo(BigDecimal.ZERO) == 0) {
                        timeBlock.getTimeHourDetails().remove(TimeHourDetail.Builder.create(overtimeTimeHourDetail));
					}
				}
				
			}
            flsaDay.setAppliedTimeBlocks(ModelObjectUtils.<TimeBlock>buildImmutableCopy(timeBlocks));
		}
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
		
		return overtimeHours.subtract(applied);
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

				if (overtimeTimeHourDetail != null) {
                    TimeHourDetail.Builder overtimeTimeHourDetailBuilder = TimeHourDetail.Builder.create(overtimeTimeHourDetail);
					applied = overtimeTimeHourDetail.getHours().add(overtimeHours, HrConstants.MATH_CONTEXT);
					if (applied.compareTo(BigDecimal.ZERO) >= 0) {
						applied = overtimeHours;
					} else {
						applied = overtimeTimeHourDetail.getHours().negate();
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
