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
package org.kuali.kpme.core.leaveplan.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.leaveplan.service.LeavePlanService;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.leaveplan.LeavePlan;
import org.kuali.kpme.core.leaveplan.dao.LeavePlanDao;

public class LeavePlanServiceImpl implements LeavePlanService {

	private LeavePlanDao leavePlanDao;
 
	public LeavePlanDao getLeavePlanDao() {
		return leavePlanDao;
	}


	public void setLeavePlanDao(LeavePlanDao leavePlanDao) {
		this.leavePlanDao = leavePlanDao;
	}


	@Override
	public LeavePlan getLeavePlan(String lmLeavePlanId) {
		return getLeavePlanDao().getLeavePlan(lmLeavePlanId);
	}
	
	@Override
	public LeavePlan getLeavePlan(String leavePlan, LocalDate asOfDate) {
		return getLeavePlanDao().getLeavePlan(leavePlan, asOfDate);
	}
   
	@Override
	public boolean isValidLeavePlan(String leavePlan){
		boolean valid = false;
		int count = getLeavePlanDao().getNumberLeavePlan(leavePlan);
		valid = (count > 0);
		return valid;
	}
	
	@Override
	public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, LocalDate asOfDate) {
		 return leavePlanDao.getAllActiveLeavePlan(leavePlan, asOfDate);
	 }
	@Override
	public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, LocalDate asOfDate) {
		 return leavePlanDao.getAllInActiveLeavePlan(leavePlan, asOfDate);
	 }

    @Override
    public List<LeavePlan> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        return leavePlanDao.getLeavePlans(leavePlan, calendarYearStart, descr, planningMonths, fromEffdt, toEffdt, active, showHistory);
    }
    
    @Override
	public boolean isFirstCalendarPeriodOfLeavePlan(CalendarEntryContract calendarEntry, String leavePlan, LocalDate asOfDate) {
		boolean isFirstCalendarPeriodOfLeavePlan = false;
    	
    	LeavePlan leavePlanObj = getLeavePlan(leavePlan, asOfDate);
		
    	if (leavePlanObj != null) {
			DateTime calendarEntryEndDate = calendarEntry.getBeginPeriodFullDateTime();
			
			int calendarYearStartMonth = Integer.valueOf(leavePlanObj.getCalendarYearStartMonth());
			int calendarYearStartDay = Integer.valueOf(leavePlanObj.getCalendarYearStartDayOfMonth());
			int calendarEntryEndDateMonth = calendarEntryEndDate.getMonthOfYear();
			int calendarEntryEndDateDay = calendarEntryEndDate.getDayOfMonth();
			
			isFirstCalendarPeriodOfLeavePlan = (calendarYearStartMonth == calendarEntryEndDateMonth) && (calendarYearStartDay == calendarEntryEndDateDay);
    	}
    	
    	return isFirstCalendarPeriodOfLeavePlan;
	}
    
    @Override
	public boolean isLastCalendarPeriodOfLeavePlan(CalendarEntryContract calendarEntry, String leavePlan, LocalDate asOfDate) {
    	boolean isLastCalendarPeriodOfLeavePlan = false;
    	
    	LeavePlan leavePlanObj = getLeavePlan(leavePlan, asOfDate);
		
    	if (leavePlanObj != null) {
			DateTime calendarEntryEndDate = calendarEntry.getEndPeriodFullDateTime();
			
			int calendarYearStartMonth = Integer.valueOf(leavePlanObj.getCalendarYearStartMonth());
			int calendarYearStartDay = Integer.valueOf(leavePlanObj.getCalendarYearStartDayOfMonth());
			int calendarEntryEndDateMonth = calendarEntryEndDate.getMonthOfYear();
			int calendarEntryEndDateDay = calendarEntryEndDate.getDayOfMonth();
			
			isLastCalendarPeriodOfLeavePlan = (calendarYearStartMonth == calendarEntryEndDateMonth) && (calendarYearStartDay == calendarEntryEndDateDay);
    	}
    	
    	return isLastCalendarPeriodOfLeavePlan;
	}

    @Override
    public DateTime getFirstDayOfLeavePlan(String leavePlan, LocalDate asOfDate) {
    	//The only thing this method does is tack on the year of the supplied asOfDate to the calendar year start date.
        LeavePlan lp = getLeavePlan(leavePlan, asOfDate);

        int priorYearCutOffMonth = Integer.parseInt(lp.getCalendarYearStartMonth());
        int priorYearCutOffDay = Integer.parseInt(lp.getCalendarYearStartDayOfMonth());

        LocalDate cutOffDate = asOfDate.withMonthOfYear(priorYearCutOffMonth).withDayOfMonth(priorYearCutOffDay);
        if (asOfDate.isBefore(cutOffDate)) {
            cutOffDate = cutOffDate.minusYears(1);
        }
        return cutOffDate.toDateTimeAtStartOfDay();
    }

    @Override
    public DateTime getRolloverDayOfLeavePlan(String leavePlan, LocalDate asOfDate) {
        LeavePlan lp = getLeavePlan(leavePlan, asOfDate);

        int priorYearCutOffMonth = Integer.parseInt(lp.getCalendarYearStartMonth());
        int priorYearCutOffDay = Integer.parseInt(lp.getCalendarYearStartDayOfMonth());

        LocalDate cutOffDate = asOfDate.withMonthOfYear(priorYearCutOffMonth).withDayOfMonth(priorYearCutOffDay);
        if (asOfDate.isAfter(cutOffDate)
        		|| asOfDate.equals(cutOffDate)) {
            cutOffDate = cutOffDate.plusYears(1);
        }
        return cutOffDate.toDateTimeAtStartOfDay();
    }


	@Override
	public List<LeavePlan> getLeavePlansNeedsCarryOverScheduled(int thresholdDays,
                                                                LocalDate asOfDate) {
		return leavePlanDao.getLeavePlansNeedsScheduled(thresholdDays, asOfDate);
	}
	
}