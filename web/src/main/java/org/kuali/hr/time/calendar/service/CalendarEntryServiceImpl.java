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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.calendar.CalendarEntryPeriodType;
import org.kuali.hr.time.calendar.dao.CalendarEntryDao;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class CalendarEntryServiceImpl implements CalendarEntryService {

    private CalendarEntryDao calendarEntryDao;

    public void setCalendarEntryDao(CalendarEntryDao calendarEntryDao) {
        this.calendarEntryDao = calendarEntryDao;
    }

    public CalendarEntry getCalendarEntry(String hrCalendarEntryId) {

        return calendarEntryDao.getCalendarEntry(hrCalendarEntryId);
    }

    @Override
    public CalendarEntry getCalendarEntryByIdAndPeriodEndDate(String hrCalendarId, DateTime endPeriodDate) {
        return calendarEntryDao.getCalendarEntryByIdAndPeriodEndDate(hrCalendarId, endPeriodDate);
    }

    @Override
    public CalendarEntry getCalendarEntryByCalendarIdAndDateRange(
            String hrCalendarId, DateTime beginDate, DateTime endDate) {
        return calendarEntryDao.getCalendarEntryByCalendarIdAndDateRange(hrCalendarId, beginDate, endDate);
    }

    @Override
    public CalendarEntry getCurrentCalendarEntryByCalendarId(
            String hrCalendarId, DateTime currentDate) {
        return calendarEntryDao.getCurrentCalendarEntryByCalendarId(hrCalendarId, currentDate);
    }

    @Override
    public CalendarEntry getPreviousCalendarEntryByCalendarId(String hrCalendarId, CalendarEntry pce) {
        return calendarEntryDao.getPreviousCalendarEntryByCalendarId(hrCalendarId, pce);
    }

    @Override
    public CalendarEntry getNextCalendarEntryByCalendarId(String hrCalendarId, CalendarEntry pce) {
        return calendarEntryDao.getNextCalendarEntryByCalendarId(hrCalendarId, pce);
    }

    @Override
    public List<CalendarEntry> getCurrentCalendarEntriesNeedsScheduled(int thresholdDays, DateTime asOfDate) {
        return calendarEntryDao.getCurrentCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
    }

    @Override
    public CalendarEntry createNextCalendarEntry(CalendarEntry calendarEntry, CalendarEntryPeriodType type) {
        CalendarEntry newEntry = new CalendarEntry();
        newEntry.setCalendarName(calendarEntry.getCalendarName());
        newEntry.setHrCalendarId(calendarEntry.getHrCalendarId());
        newEntry.setCalendarObj(calendarEntry.getCalendarObj());
        
        if (type == null) {
            type = CalendarEntryPeriodType.BI_WEEKLY;
        }
        if (CalendarEntryPeriodType.WEEKLY.equals(type)
                || CalendarEntryPeriodType.BI_WEEKLY.equals(type)) {
            int weekly_multiplier = 2;
            if (CalendarEntryPeriodType.WEEKLY.equals(type)) {
                weekly_multiplier = 1;
            }
            newEntry.setBeginPeriodDateTime(DateUtils.addWeeks(calendarEntry.getBeginPeriodDateTime(), weekly_multiplier));
            newEntry.setEndPeriodDateTime(DateUtils.addWeeks(calendarEntry.getEndPeriodDateTime(), weekly_multiplier));
            newEntry.setBatchInitiateDateTime(DateUtils.addWeeks(calendarEntry.getBatchInitiateDateTime(), weekly_multiplier));
            newEntry.setBatchEndPayPeriodDateTime(DateUtils.addWeeks(calendarEntry.getBatchEndPayPeriodDateTime(), weekly_multiplier));
            newEntry.setBatchEmployeeApprovalDateTime(DateUtils.addWeeks(calendarEntry.getBatchEmployeeApprovalDateTime(), weekly_multiplier));
            newEntry.setBatchSupervisorApprovalDateTime(DateUtils.addWeeks(calendarEntry.getBatchSupervisorApprovalDateTime(), weekly_multiplier));
        } else if (CalendarEntryPeriodType.MONTHLY.equals(type)) {
            newEntry.setBeginPeriodDateTime(addMonthToDate(calendarEntry.getBeginPeriodDateTime()));
            newEntry.setEndPeriodDateTime(addMonthToDate(calendarEntry.getEndPeriodDateTime()));
            newEntry.setBatchInitiateDateTime(addMonthToDate(calendarEntry.getBatchInitiateDateTime()));
            newEntry.setBatchEndPayPeriodDateTime(addMonthToDate(calendarEntry.getBatchEndPayPeriodDateTime()));
            newEntry.setBatchEmployeeApprovalDateTime(addMonthToDate(calendarEntry.getBatchEmployeeApprovalDateTime()));
            newEntry.setBatchSupervisorApprovalDateTime(addMonthToDate(calendarEntry.getBatchSupervisorApprovalDateTime()));
        } else if (CalendarEntryPeriodType.SEMI_MONTHLY.equals(type)) {
            newEntry.setBeginPeriodDateTime(addSemiMonthToDate(calendarEntry.getBeginPeriodDateTime()));
            newEntry.setEndPeriodDateTime(addSemiMonthToDate(calendarEntry.getEndPeriodDateTime()));
            newEntry.setBatchInitiateDateTime(addSemiMonthToDate(calendarEntry.getBatchInitiateDateTime()));
            newEntry.setBatchEndPayPeriodDateTime(addSemiMonthToDate(calendarEntry.getBatchEndPayPeriodDateTime()));
            newEntry.setBatchEmployeeApprovalDateTime(addSemiMonthToDate(calendarEntry.getBatchEmployeeApprovalDateTime()));
            newEntry.setBatchSupervisorApprovalDateTime(addSemiMonthToDate(calendarEntry.getBatchSupervisorApprovalDateTime()));
        }
        KRADServiceLocator.getBusinessObjectService().save(newEntry);
        return getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
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

    @Override
    public List<CalendarEntry> getFutureCalendarEntries(String hrCalendarId, DateTime currentDate, int numberOfEntries) {
        return calendarEntryDao.getFutureCalendarEntries(hrCalendarId, currentDate, numberOfEntries);
    }

    @Override
    public CalendarEntry getCalendarEntryByBeginAndEndDate(DateTime beginPeriodDate, DateTime endPeriodDate) {
        return calendarEntryDao.getCalendarEntryByBeginAndEndDate(beginPeriodDate, endPeriodDate);
    }
    
    @Override
    public List<CalendarEntry> getCalendarEntriesEndingBetweenBeginAndEndDate(String hrCalendarId, DateTime beginDate, DateTime endDate) {
        return calendarEntryDao.getCalendarEntriesEndingBetweenBeginAndEndDate(hrCalendarId, beginDate, endDate);
    }
    
    @Override
    public List<CalendarEntry> getAllCalendarEntriesForCalendarId(String hrCalendarId) {
    	return calendarEntryDao.getAllCalendarEntriesForCalendarId(hrCalendarId);
    }
    
    @Override
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year) {
    	return calendarEntryDao.getAllCalendarEntriesForCalendarIdAndYear(hrCalendarId, year);
    }
    
    @Override
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdUpToPlanningMonths(String hrCalendarId, String principalId) {
    	int planningMonths = ActionFormUtils.getPlanningMonthsForEmployee(principalId);
    	List<CalendarEntry> futureCalEntries = getFutureCalendarEntries(hrCalendarId, new LocalDate().toDateTimeAtStartOfDay(), planningMonths);
    	CalendarEntry futureCalEntry = null;
    	if (futureCalEntries != null && !futureCalEntries.isEmpty()) {
    		futureCalEntry = futureCalEntries.get(futureCalEntries.size() - 1);
    	}
    	Date cutOffTime;
    	if(futureCalEntry != null) {
    		cutOffTime = futureCalEntry.getEndPeriodDateTime();
    	} else {
	    	CalendarEntry currentCE = getCurrentCalendarEntryByCalendarId(hrCalendarId, new LocalDate().toDateTimeAtStartOfDay());
	    	cutOffTime = currentCE.getEndPeriodDateTime();    	
    	}
    	return getAllCalendarEntriesForCalendarIdUpToCutOffTime(hrCalendarId, new DateTime(cutOffTime));
    }
    
    @Override
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdUpToCutOffTime(String hrCalendarId, DateTime cutOffTime) {
    	return calendarEntryDao.getAllCalendarEntriesForCalendarIdUpToCutOffTime(hrCalendarId, cutOffTime);
    }
}
