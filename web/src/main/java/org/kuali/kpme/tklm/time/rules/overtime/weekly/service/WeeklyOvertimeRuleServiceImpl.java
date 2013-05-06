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
package org.kuali.kpme.tklm.time.rules.overtime.weekly.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.time.flsa.FlsaDay;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRule;
import org.kuali.kpme.tklm.time.rules.overtime.weekly.dao.WeeklyOvertimeRuleDao;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.TimeHourDetail;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class WeeklyOvertimeRuleServiceImpl implements WeeklyOvertimeRuleService {

	private WeeklyOvertimeRuleDao weeklyOvertimeRuleDao;

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
		List<FlsaWeek> currentWeeks = aggregate.getFlsaWeeks(zone);
		
		for (ListIterator<FlsaWeek> weekIterator = currentWeeks.listIterator(); weekIterator.hasNext(); ) {
			List<FlsaWeek> flsaWeek = new ArrayList<FlsaWeek>();
			
			int index = weekIterator.nextIndex();
			FlsaWeek currentWeek = weekIterator.next();
			
			if (index == 0 && !currentWeek.isFirstWeekFull()) {
				TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(principalId, beginDate);
				if (timesheetDocumentHeader != null) { 
					List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(timesheetDocumentHeader.getDocumentId());
					if (CollectionUtils.isNotEmpty(timeBlocks)) {
						CalendarEntry calendarEntry = HrServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(principalId, timesheetDocumentHeader.getEndDateTime(), HrConstants.PAY_CALENDAR_TYPE);
						TkTimeBlockAggregate previousAggregate = new TkTimeBlockAggregate(timeBlocks, calendarEntry, calendarEntry.getCalendarObj(), true);
						List<FlsaWeek> previousWeek = previousAggregate.getFlsaWeeks(zone);
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
					List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(timesheetDocumentHeader.getDocumentId());
					if (CollectionUtils.isNotEmpty(timeBlocks)) {
						CalendarEntry calendarEntry = HrServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(principalId, timesheetDocumentHeader.getEndDateTime(), HrConstants.PAY_CALENDAR_TYPE);
						TkTimeBlockAggregate nextAggregate = new TkTimeBlockAggregate(timeBlocks, calendarEntry, calendarEntry.getCalendarObj(), true);
						List<FlsaWeek> nextWeek = nextAggregate.getFlsaWeeks(zone);
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
	protected String getOvertimeEarnCode(WeeklyOvertimeRule weeklyOvertimeRule, TimeBlock timeBlock, LocalDate asOfDate) {
        String overtimeEarnCode = weeklyOvertimeRule.getConvertToEarnCode();
        
        WorkArea workArea = HrServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getWorkArea(), asOfDate);
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
			
			List<TimeBlock> timeBlocks = flsaDay.getAppliedTimeBlocks();
			Collections.sort(timeBlocks, new Comparator<TimeBlock>() {
				public int compare(TimeBlock timeBlock1, TimeBlock timeBlock2) {
					return ObjectUtils.compare(timeBlock1.getBeginTimestamp(), timeBlock2.getBeginTimestamp());
				}
			});

			for (ListIterator<TimeBlock> timeBlockIterator = timeBlocks.listIterator(timeBlocks.size()); timeBlockIterator.hasPrevious(); ) {
				TimeBlock timeBlock = timeBlockIterator.previous();
				String overtimeEarnCode = getOvertimeEarnCode(weeklyOvertimeRule, timeBlock, asOfDate);
				overtimeHours = applyPositiveOvertimeOnTimeBlock(timeBlock, overtimeEarnCode, convertFromEarnCodes, overtimeHours);
			}
			
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
			
			List<TimeBlock> timeBlocks = flsaDay.getAppliedTimeBlocks();
			Collections.sort(timeBlocks, new Comparator<TimeBlock>() {
				public int compare(TimeBlock timeBlock1, TimeBlock timeBlock2) {
					return ObjectUtils.compare(timeBlock1.getBeginTimestamp(), timeBlock2.getBeginTimestamp());
				}
			});

			for (ListIterator<TimeBlock> timeBlockIterator = timeBlocks.listIterator(); timeBlockIterator.hasNext(); ) {
				TimeBlock timeBlock = timeBlockIterator.next();
				String overtimeEarnCode = getOvertimeEarnCode(weeklyOvertimeRule, timeBlock, asOfDate);
				overtimeHours = applyNegativeOvertimeOnTimeBlock(timeBlock, overtimeEarnCode, convertFromEarnCodes, overtimeHours);
			}
			
			flsaDay.remapTimeHourDetails();
		}
	}
	
	protected void removeEmptyOvertime(List<FlsaDay> flsaDays, WeeklyOvertimeRule weeklyOvertimeRule, LocalDate asOfDate) {
		for (ListIterator<FlsaDay> dayIterator = flsaDays.listIterator(); dayIterator.hasNext(); ) {
			FlsaDay flsaDay = dayIterator.next();
			
			List<TimeBlock> timeBlocks = flsaDay.getAppliedTimeBlocks();
			for (ListIterator<TimeBlock> timeBlockIterator = timeBlocks.listIterator(); timeBlockIterator.hasNext(); ) {
				TimeBlock timeBlock = timeBlockIterator.next();
				String overtimeEarnCode = getOvertimeEarnCode(weeklyOvertimeRule, timeBlock, asOfDate);

				List<TimeHourDetail> timeHourDetails = timeBlock.getTimeHourDetails();
				List<TimeHourDetail> oldTimeHourDetails = new ArrayList<TimeHourDetail>();

				TimeHourDetail overtimeTimeHourDetail = getTimeHourDetailByEarnCode(timeHourDetails, Collections.singletonList(overtimeEarnCode));
				if (overtimeTimeHourDetail != null) {
					if (overtimeTimeHourDetail.getHours().compareTo(BigDecimal.ZERO) == 0) {
						oldTimeHourDetails.add(overtimeTimeHourDetail);
					}
				}
				
				for (TimeHourDetail oldTimeHourDetail : oldTimeHourDetails) {
					timeBlock.removeTimeHourDetail(oldTimeHourDetail);
				}
			}
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
	protected BigDecimal applyPositiveOvertimeOnTimeBlock(TimeBlock timeBlock, String overtimeEarnCode, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
		BigDecimal applied = BigDecimal.ZERO;
		List<TimeHourDetail> timeHourDetails = timeBlock.getTimeHourDetails();
		List<TimeHourDetail> newTimeHourDetails = new ArrayList<TimeHourDetail>();
		
		for (TimeHourDetail timeHourDetail : timeHourDetails) {
			if (convertFromEarnCodes.contains(timeHourDetail.getEarnCode())) {
				if (timeHourDetail.getHours().compareTo(overtimeHours) >= 0) {
					applied = overtimeHours;
				} else {
					applied = timeHourDetail.getHours();
				}
				
				EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(overtimeEarnCode, timeBlock.getEndDateTime().toLocalDate());
				BigDecimal hours = earnCodeObj.getInflateFactor().multiply(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
				
				TimeHourDetail overtimeTimeHourDetail = getTimeHourDetailByEarnCode(timeHourDetails, Collections.singletonList(overtimeEarnCode));
				if (overtimeTimeHourDetail != null) {
					overtimeTimeHourDetail.setHours(overtimeTimeHourDetail.getHours().add(hours, HrConstants.MATH_CONTEXT));
				} else {
					TimeHourDetail newTimeHourDetail = new TimeHourDetail();
					newTimeHourDetail.setTkTimeBlockId(timeBlock.getTkTimeBlockId());
					newTimeHourDetail.setEarnCode(overtimeEarnCode);
					newTimeHourDetail.setHours(hours);
		
					newTimeHourDetails.add(newTimeHourDetail);
				}
				
				timeHourDetail.setHours(timeHourDetail.getHours().subtract(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
			}
		}
		
		for (TimeHourDetail newTimeHourDetail : newTimeHourDetails) {
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
	protected BigDecimal applyNegativeOvertimeOnTimeBlock(TimeBlock timeBlock, String overtimeEarnCode, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
		BigDecimal applied = BigDecimal.ZERO;
		List<TimeHourDetail> timeHourDetails = timeBlock.getTimeHourDetails();
		
		for (TimeHourDetail timeHourDetail : timeHourDetails) {
			if (convertFromEarnCodes.contains(timeHourDetail.getEarnCode())) {
				TimeHourDetail overtimeTimeHourDetail = getTimeHourDetailByEarnCode(timeHourDetails, Collections.singletonList(overtimeEarnCode));
			
				if (overtimeTimeHourDetail != null) {
					applied = overtimeTimeHourDetail.getHours().add(overtimeHours, HrConstants.MATH_CONTEXT);
					if (applied.compareTo(BigDecimal.ZERO) >= 0) {
						applied = overtimeHours;
					} else {
						applied = overtimeTimeHourDetail.getHours().negate();
					}
					
					EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(overtimeEarnCode, timeBlock.getEndDateTime().toLocalDate());
					BigDecimal hours = earnCodeObj.getInflateFactor().multiply(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN);
					
					overtimeTimeHourDetail.setHours(overtimeTimeHourDetail.getHours().add(hours, HrConstants.MATH_CONTEXT));
					
					timeHourDetail.setHours(timeHourDetail.getHours().subtract(applied, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		
		return overtimeHours.subtract(applied);
	}
	
	protected TimeHourDetail getTimeHourDetailByEarnCode(List<TimeHourDetail> timeHourDetails, Collection<String> earnCodes) {
		TimeHourDetail result = null;
		
		for (TimeHourDetail timeHourDetail : timeHourDetails) {
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
					KRADServiceLocator.getBusinessObjectService().save(flsaDay.getAppliedTimeBlocks());
				}
			}
				
			if (index == flsaWeeks.size() - 1 && currentWeekParts.size() > 1) {
				FlsaWeek nextFlsaWeek = currentWeekParts.get(currentWeekParts.size() - 1);
				for (FlsaDay flsaDay : nextFlsaWeek.getFlsaDays()) {
					KRADServiceLocator.getBusinessObjectService().save(flsaDay.getAppliedTimeBlocks());
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