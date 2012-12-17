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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTimeZone;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.overtime.weekly.rule.dao.WeeklyOvertimeRuleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class WeeklyOvertimeRuleServiceImpl implements WeeklyOvertimeRuleService {

	private WeeklyOvertimeRuleDao weeklyOvertimeRuleDao;

	@Override
	public void processWeeklyOvertimeRule(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate) {
		Date asOfDate = TKUtils.getTimelessDate(timesheetDocument.getDocumentHeader().getEndDate());
		String principalId = timesheetDocument.getDocumentHeader().getPrincipalId();
		java.util.Date beginDate = timesheetDocument.getDocumentHeader().getBeginDate();
		java.util.Date endDate = timesheetDocument.getDocumentHeader().getEndDate();
		List<WeeklyOvertimeRule> weeklyOvertimeRules = getWeeklyOvertimeRules(asOfDate);

		List<List<FlsaWeek>> flsaWeeks = getFlsaWeeks(principalId, beginDate, endDate, aggregate);

		for (WeeklyOvertimeRule weeklyOvertimeRule : weeklyOvertimeRules) {
			Set<String> maxHoursEarnCodes = TkServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(weeklyOvertimeRule.getMaxHoursEarnGroup(), asOfDate);
			Set<String> convertFromEarnCodes = TkServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(weeklyOvertimeRule.getConvertFromEarnGroup(), asOfDate);

			for (List<FlsaWeek> flsaWeekParts : flsaWeeks) {
				BigDecimal existingMaxHours = getMaxHours(flsaWeekParts, maxHoursEarnCodes);
				BigDecimal newOvertimeHours = existingMaxHours.subtract(weeklyOvertimeRule.getMaxHours(), TkConstants.MATH_CONTEXT);
				
				if (newOvertimeHours.compareTo(BigDecimal.ZERO) > 0) {
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
	protected List<List<FlsaWeek>> getFlsaWeeks(String principalId, java.util.Date beginDate, java.util.Date endDate, TkTimeBlockAggregate aggregate) {
		List<List<FlsaWeek>> flsaWeeks = new ArrayList<List<FlsaWeek>>();
		
        DateTimeZone zone = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
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
						CalendarEntries calendarEntry = TkServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(principalId, timesheetDocumentHeader.getEndDate(), TkConstants.PAY_CALENDAR_TYPE);
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
						CalendarEntries calendarEntry = TkServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(principalId, timesheetDocumentHeader.getEndDate(), TkConstants.PAY_CALENDAR_TYPE);
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
				for (String earnCode : flsaDay.getEarnCodeToHours().keySet()) {
					if (maxHoursEarnCodes.contains(earnCode)) {
						maxHours = maxHours.add(flsaDay.getEarnCodeToHours().get(earnCode), TkConstants.MATH_CONTEXT);
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
	protected String getOvertimeEarnCode(WeeklyOvertimeRule weeklyOvertimeRule, TimeBlock timeBlock, Date asOfDate) {
        String overtimeEarnCode = weeklyOvertimeRule.getConvertToEarnCode();
        
        WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getWorkArea(), asOfDate);
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
	protected void applyOvertimeToFlsaWeeks(List<FlsaWeek> flsaWeeks, WeeklyOvertimeRule weeklyOvertimeRule, Date asOfDate, Set<String> convertFromEarnCodes, BigDecimal overtimeHours) {
		List<FlsaDay> flsaDays = getFlsaDays(flsaWeeks);
		
		for (ListIterator<FlsaDay> dayIterator = flsaDays.listIterator(flsaDays.size()); dayIterator.hasPrevious(); ) {
			FlsaDay flsaDay = dayIterator.previous();
			
			List<TimeBlock> timeBlocks = flsaDay.getAppliedTimeBlocks();
			Collections.sort(timeBlocks, new Comparator<TimeBlock>() {
				public int compare(TimeBlock timeBlock1, TimeBlock timeBlock2) {
					return ObjectUtils.compare(timeBlock1.getBeginTimestamp(), timeBlock2.getBeginTimestamp());
				}
			});

			boolean overtimeApplied = false;
			for (ListIterator<TimeBlock> timeBlockIterator = timeBlocks.listIterator(timeBlocks.size()); timeBlockIterator.hasPrevious(); ) {
				TimeBlock timeBlock = timeBlockIterator.previous();
				if (overtimeHours.compareTo(BigDecimal.ZERO) > 0) {
					String overtimeEarnCode = getOvertimeEarnCode(weeklyOvertimeRule, timeBlock, asOfDate);
					overtimeHours = applyOvertimeToTimeBlock(timeBlock, overtimeEarnCode, convertFromEarnCodes, overtimeHours);
					overtimeApplied = true;
				}
			}
			if (overtimeApplied) {
				flsaDay.remapTimeHourDetails();
			}
		}
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
	 * Method to apply (if applicable) overtime additions to the indiciated TimeBlock.  TimeBlock
	 * earn code is checked against the convertFromEarnCodes Set.
	 *
	 * @param block
	 * @param otEarnCode
	 * @param convertFromEarnCodes
	 * @param otHours
	 *
	 * @return The amount of overtime hours remaining to be applied.  (BigDecimal is immutable)
	 */
	protected BigDecimal applyOvertimeToTimeBlock(TimeBlock block, String otEarnCode, Set<String> convertFromEarnCodes, BigDecimal otHours) {
		BigDecimal applied = BigDecimal.ZERO;
		List<TimeHourDetail> details = block.getTimeHourDetails();
		List<TimeHourDetail> addDetails = new LinkedList<TimeHourDetail>();
		for (TimeHourDetail detail : details) {
			// Apply
			String thdEarnCode = detail.getEarnCode();
			if (convertFromEarnCodes.contains(thdEarnCode)) {
				// n = detailHours - otHours
				BigDecimal n = detail.getHours().subtract(otHours, TkConstants.MATH_CONTEXT);
				// n >= 0 (meaning there are greater than or equal amount of Detail hours vs. OT hours, so apply all OT hours here)
				// n < = (meaning there were more OT hours than Detail hours, so apply only the # of hours in detail and update applied.
				if (n.compareTo(BigDecimal.ZERO) >= 0) {
					// if
					applied = otHours;
				} else {
					applied = detail.getHours();
				}

				// Make a new TimeHourDetail with the otEarnCode with "applied" hours
				TimeHourDetail timeHourDetail = new TimeHourDetail();


				EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(otEarnCode, block.getEndDate());
				BigDecimal hrs = earnCodeObj.getInflateFactor().multiply(applied, TkConstants.MATH_CONTEXT).setScale(TkConstants.BIG_DECIMAL_SCALE,BigDecimal.ROUND_HALF_UP);
				timeHourDetail.setEarnCode(otEarnCode);
				timeHourDetail.setHours(hrs);
				timeHourDetail.setTkTimeBlockId(block.getTkTimeBlockId());

				// Decrement existing matched FROM earn code.
				detail.setHours(detail.getHours().subtract(applied, TkConstants.MATH_CONTEXT).setScale(TkConstants.BIG_DECIMAL_SCALE,BigDecimal.ROUND_HALF_UP));
				addDetails.add(timeHourDetail);
			}
		}

		// If we have new time Hour details to add to the time block, add them.
		// We are modifying the data in the list in place, the caller into this service
		// will handle the diff / persist.
		if (addDetails.size() > 0) {
			details.addAll(addDetails);
			block.setTimeHourDetails(details);
		}
		

		return otHours.subtract(applied);
	}
	
	/**
	 * Saves the TimeBlocks from both the previous and next CalendarEntries, since these are not saved automatically by calling methods.
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
	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(Date asOfDate) {
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