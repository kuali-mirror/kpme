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
package org.kuali.hr.time.calendar.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.CalendarEntryPeriodType;
import org.kuali.hr.time.calendar.dao.CalendarEntriesDao;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class CalendarEntriesServiceImpl implements CalendarEntriesService {

    private CalendarEntriesDao calendarEntriesDao;

    public void setCalendarEntriesDao(CalendarEntriesDao calendarEntriesDao) {
        this.calendarEntriesDao = calendarEntriesDao;
    }

    public CalendarEntries getCalendarEntries(String hrCalendarEntriesId) {

        return calendarEntriesDao.getCalendarEntries(hrCalendarEntriesId);
    }

    @Override
    public CalendarEntries getCalendarEntriesByIdAndPeriodEndDate(String hrCalendarId, Date endPeriodDate) {
        return calendarEntriesDao.getCalendarEntriesByIdAndPeriodEndDate(hrCalendarId, endPeriodDate);
    }

    @Override
    public CalendarEntries getCalendarEntriesByCalendarIdAndDateRange(
            String hrCalendarId, Date beginDate, Date endDate) {
        return calendarEntriesDao.getCalendarEntriesByCalendarIdAndDateRange(hrCalendarId, beginDate, endDate);
    }

    @Override
    public CalendarEntries getCurrentCalendarEntriesByCalendarId(
            String hrCalendarId, Date currentDate) {
        return calendarEntriesDao.getCurrentCalendarEntriesByCalendarId(hrCalendarId, currentDate);
    }

    @Override
    public CalendarEntries getPreviousCalendarEntriesByCalendarId(String hrCalendarId, CalendarEntries pce) {
        return calendarEntriesDao.getPreviousCalendarEntriesByCalendarId(hrCalendarId, pce);
    }

    @Override
    public CalendarEntries getNextCalendarEntriesByCalendarId(String hrCalendarId, CalendarEntries pce) {
        return calendarEntriesDao.getNextCalendarEntriesByCalendarId(hrCalendarId, pce);
    }

    public List<CalendarEntries> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate) {
        return calendarEntriesDao.getCurrentCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
    }

    @Override
    public CalendarEntries createNextCalendarEntry(CalendarEntries calendarEntries, CalendarEntryPeriodType type) {
        CalendarEntries newEntry = new CalendarEntries();
        newEntry.setCalendarName(calendarEntries.getCalendarName());
        newEntry.setHrCalendarId(calendarEntries.getHrCalendarId());
        newEntry.setCalendarObj(calendarEntries.getCalendarObj());
        
        if (type == null) {
            type = CalendarEntryPeriodType.BI_WEEKLY;
        }
        if (CalendarEntryPeriodType.WEEKLY.equals(type)
                || CalendarEntryPeriodType.BI_WEEKLY.equals(type)) {
            int weekly_multiplier = 2;
            if (CalendarEntryPeriodType.WEEKLY.equals(type)) {
                weekly_multiplier = 1;
            }
            newEntry.setBeginPeriodDateTime(DateUtils.addWeeks(calendarEntries.getBeginPeriodDateTime(), weekly_multiplier));
            newEntry.setEndPeriodDateTime(DateUtils.addWeeks(calendarEntries.getEndPeriodDateTime(), weekly_multiplier));
            newEntry.setBatchInitiateDateTime(DateUtils.addWeeks(calendarEntries.getBatchInitiateDateTime(), weekly_multiplier));
            newEntry.setBatchEndPayPeriodDateTime(DateUtils.addWeeks(calendarEntries.getBatchEndPayPeriodDateTime(), weekly_multiplier));
            newEntry.setBatchEmployeeApprovalDateTime(DateUtils.addWeeks(calendarEntries.getBatchEmployeeApprovalDateTime(), weekly_multiplier));
            newEntry.setBatchSupervisorApprovalDateTime(DateUtils.addWeeks(calendarEntries.getBatchSupervisorApprovalDateTime(), weekly_multiplier));
        } else if (CalendarEntryPeriodType.MONTHLY.equals(type)) {
            newEntry.setBeginPeriodDateTime(addMonthToDate(calendarEntries.getBeginPeriodDateTime()));
            newEntry.setEndPeriodDateTime(addMonthToDate(calendarEntries.getEndPeriodDateTime()));
            newEntry.setBatchInitiateDateTime(addMonthToDate(calendarEntries.getBatchInitiateDateTime()));
            newEntry.setBatchEndPayPeriodDateTime(addMonthToDate(calendarEntries.getBatchEndPayPeriodDateTime()));
            newEntry.setBatchEmployeeApprovalDateTime(addMonthToDate(calendarEntries.getBatchEmployeeApprovalDateTime()));
            newEntry.setBatchSupervisorApprovalDateTime(addMonthToDate(calendarEntries.getBatchSupervisorApprovalDateTime()));
        } else if (CalendarEntryPeriodType.SEMI_MONTHLY.equals(type)) {
            newEntry.setBeginPeriodDateTime(addSemiMonthToDate(calendarEntries.getBeginPeriodDateTime()));
            newEntry.setEndPeriodDateTime(addSemiMonthToDate(calendarEntries.getEndPeriodDateTime()));
            newEntry.setBatchInitiateDateTime(addSemiMonthToDate(calendarEntries.getBatchInitiateDateTime()));
            newEntry.setBatchEndPayPeriodDateTime(addSemiMonthToDate(calendarEntries.getBatchEndPayPeriodDateTime()));
            newEntry.setBatchEmployeeApprovalDateTime(addSemiMonthToDate(calendarEntries.getBatchEmployeeApprovalDateTime()));
            newEntry.setBatchSupervisorApprovalDateTime(addSemiMonthToDate(calendarEntries.getBatchSupervisorApprovalDateTime()));
        }
        KRADServiceLocator.getBusinessObjectService().save(newEntry);
        return getNextCalendarEntriesByCalendarId(calendarEntries.getHrCalendarId(), calendarEntries);
    }

    private Date addMonthToDate(Date date) {
        Calendar temp = Calendar.getInstance();
        temp.setTime(date);
        boolean lastDayOfMonth = temp.getActualMaximum(Calendar.DATE) == temp.get(Calendar.DATE);

        date = DateUtils.addMonths(date, 1);
        if (lastDayOfMonth) {
            temp.setTime(date);
            temp.set(Calendar.DATE, temp.getActualMaximum(Calendar.DATE));
            date = temp.getTime();
        }
        return date;
    }

    private Date addSemiMonthToDate(Date date) {
        //so assuming the common pairs of this are the 1st & 16th, and then 15th and the last day,
        // and 14th with the last day minus 1
        //so we'll peek at the current date and try to figure out the best guesses for addition.
        Calendar temp = Calendar.getInstance();
        temp.setTime(date);
        if (temp.getActualMaximum(Calendar.DATE) == temp.get(Calendar.DATE)) {
            //date is on the last day of the month.  Set next date to the 15th
            date = DateUtils.addMonths(date, 1);
            temp.setTime(date);
            temp.set(Calendar.DATE, 15);
        } else if (temp.get(Calendar.DATE) == 15) {
            //we are on the 15th of the month, so now lets go to the end of the month
            temp.setTime(date);
            temp.set(Calendar.DATE,  temp.getActualMaximum(Calendar.DATE));
        } else if (temp.get(Calendar.DATE) == 1) {
            //first of the month, next would be 16
            temp.setTime(date);
            temp.set(Calendar.DATE,  16);
        } else if (temp.get(Calendar.DATE) == 16) {
            //16th, so add a month and set day to '1'
            date = DateUtils.addMonths(date, 1);
            temp.setTime(date);
            temp.set(Calendar.DATE, 1);
        } else if (temp.get(Calendar.DATE) == 14) {
            //14th day, set next one to last day minus 1
            temp.setTime(date);
            temp.set(Calendar.DATE,  temp.getActualMaximum(Calendar.DATE) - 1);
        } else if (temp.getActualMaximum(Calendar.DATE) == temp.get(Calendar.DATE) - 1) {
            //date is on the second to last day of the month.  Set next date to the 14th
            date = DateUtils.addMonths(date, 1);
            temp.setTime(date);
            temp.set(Calendar.DATE, 14);
        } else {
            // so it isn't one of the common dates... i guess we'll just add 15 days...
            date = DateUtils.addDays(date, 15);
            temp.setTime(date);
        }

        return temp.getTime() ;
    }

    public List<CalendarEntries> getFutureCalendarEntries(String hrCalendarId, Date currentDate, int numberOfEntries) {
        List<CalendarEntries> calendarEntries = null;
        calendarEntries = calendarEntriesDao.getFutureCalendarEntries(hrCalendarId, currentDate, numberOfEntries);
        return calendarEntries;
    }

    public CalendarEntries getCalendarEntriesByBeginAndEndDate(Date beginPeriodDate, Date endPeriodDate) {
        return calendarEntriesDao.getCalendarEntriesByBeginAndEndDate(beginPeriodDate, endPeriodDate);
    }
    
    public List<CalendarEntries> getCalendarEntriesEndingBetweenBeginAndEndDate(String hrCalendarId, Date beginDate, Date endDate) {
        return calendarEntriesDao.getCalendarEntriesEndingBetweenBeginAndEndDate(hrCalendarId, beginDate, endDate);
    }
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarId(String hrCalendarId) {
    	return calendarEntriesDao.getAllCalendarEntriesForCalendarId(hrCalendarId);
    }
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year) {
    	return calendarEntriesDao.getAllCalendarEntriesForCalendarIdAndYear(hrCalendarId, year);
    }
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarIdUpToPlanningMonths(String hrCalendarId, String principalId) {
    	int planningMonths = ActionFormUtils.getPlanningMonthsForEmployee(principalId);
    	List<CalendarEntries> futureCalEntries = TkServiceLocator.getCalendarEntriesService().getFutureCalendarEntries(
    			hrCalendarId,TKUtils.getTimelessDate(null),planningMonths);
    	CalendarEntries futureCalEntry = null;
    	if (futureCalEntries != null && !futureCalEntries.isEmpty()) {
    		futureCalEntry = futureCalEntries.get(futureCalEntries.size() - 1);
    	}
    	Date cutOffTime = TKUtils.getTimelessDate(null);
    	if(futureCalEntry != null) {
    		cutOffTime = futureCalEntry.getEndPeriodDateTime();
    	} else {
	    	CalendarEntries currentCE = TkServiceLocator.getCalendarEntriesService().getCurrentCalendarEntriesByCalendarId(hrCalendarId, TKUtils.getCurrentDate());
	    	cutOffTime = currentCE.getEndPeriodDateTime();    	
    	}
    	return TkServiceLocator.getCalendarEntriesService().getAllCalendarEntriesForCalendarIdUpToCutOffTime(hrCalendarId, cutOffTime);
    }
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarIdUpToCutOffTime(String hrCalendarId, Date cutOffTime) {
    	return calendarEntriesDao.getAllCalendarEntriesForCalendarIdUpToCutOffTime(hrCalendarId, cutOffTime);
    }
}
