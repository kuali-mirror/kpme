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
package org.kuali.hr.lm.leaveplan.service;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.leaveplan.dao.LeavePlanDao;
import org.kuali.hr.time.calendar.CalendarEntries;

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
	public LeavePlan getLeavePlan(String leavePlan, Date asOfDate) {
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
	public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, Date asOfDate) {
		 return leavePlanDao.getAllActiveLeavePlan(leavePlan, asOfDate);
	 }
	@Override
	public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, Date asOfDate) {
		 return leavePlanDao.getAllInActiveLeavePlan(leavePlan, asOfDate);
	 }

    @Override
    public List<LeavePlan> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths, Date fromEffdt, Date toEffdt, String active, String showHistory) {
        return leavePlanDao.getLeavePlans(leavePlan, calendarYearStart, descr, planningMonths, fromEffdt, toEffdt, active, showHistory);
    }
    
    @Override
	public boolean isFirstCalendarPeriodOfLeavePlan(CalendarEntries calendarEntry, String leavePlan, Date asOfDate) {
		LeavePlan leavePlanObj = getLeavePlan(leavePlan, asOfDate);
		
		DateTime calendarEntryEndDate = new DateTime(calendarEntry.getBeginPeriodDate());
		
		int calendarYearStartMonth = Integer.valueOf(leavePlanObj.getCalendarYearStartMonth());
		int calendarYearStartDay = Integer.valueOf(leavePlanObj.getCalendarYearStartDayOfMonth());
		int calendarEntryEndDateMonth = calendarEntryEndDate.getMonthOfYear();
		int calendarEntryEndDateDay = calendarEntryEndDate.getDayOfMonth();
		
		return (calendarYearStartMonth == calendarEntryEndDateMonth) && (calendarYearStartDay == calendarEntryEndDateDay);
	}
    
    @Override
	public boolean isLastCalendarPeriodOfLeavePlan(CalendarEntries calendarEntry, String leavePlan, Date asOfDate) {
		LeavePlan leavePlanObj = getLeavePlan(leavePlan, asOfDate);
		
		DateTime calendarEntryEndDate = new DateTime(calendarEntry.getEndPeriodDate());
		
		int calendarYearStartMonth = Integer.valueOf(leavePlanObj.getCalendarYearStartMonth());
		int calendarYearStartDay = Integer.valueOf(leavePlanObj.getCalendarYearStartDayOfMonth());
		int calendarEntryEndDateMonth = calendarEntryEndDate.getMonthOfYear();
		int calendarEntryEndDateDay = calendarEntryEndDate.getDayOfMonth();
		
		return (calendarYearStartMonth == calendarEntryEndDateMonth) && (calendarYearStartDay == calendarEntryEndDateDay);
	}
	
}