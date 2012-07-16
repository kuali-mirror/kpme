package org.kuali.hr.time.calendar.service;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.CalendarEntryPeriodType;
import org.kuali.hr.time.calendar.dao.CalendarEntriesDao;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.util.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarEntriesServiceImpl implements CalendarEntriesService {

    private CalendarEntriesDao calendarEntriesDao;

    public void setCalendarEntriesDao(CalendarEntriesDao calendarEntriesDao) {
        this.calendarEntriesDao = calendarEntriesDao;
    }

    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public CalendarEntries getCalendarEntries(String hrCalendarEntriesId) {

        return calendarEntriesDao.getCalendarEntries(hrCalendarEntriesId);
    }

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public CalendarEntries getCalendarEntriesByIdAndPeriodEndDate(String hrCalendarId, Date endPeriodDate) {
        return calendarEntriesDao.getCalendarEntriesByIdAndPeriodEndDate(hrCalendarId, endPeriodDate);
    }

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
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

    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public List<CalendarEntries> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate) {
        return calendarEntriesDao.getCurrentCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
    }

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public CalendarEntries createNextCalendarEntry(CalendarEntries calendarEntries, CalendarEntryPeriodType type) {
        CalendarEntries newEntry = new CalendarEntries();
        newEntry.setCalendarName(calendarEntries.getCalendarName());
        newEntry.setHrCalendarId(calendarEntries.getHrCalendarId());
        newEntry.setCalendarObj(calendarEntries.getCalendarObj());
        newEntry.setBatchInitiateTime(calendarEntries.getBatchInitiateTime());
        newEntry.setBatchEndPayPeriodTime(calendarEntries.getBatchEndPayPeriodTime());
        newEntry.setBatchEmployeeApprovalTime(calendarEntries.getBatchEmployeeApprovalTime());
        newEntry.setBatchSupervisorApprovalTime(calendarEntries.getBatchSupervisorApprovalTime());

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
            newEntry.setBatchInitiateDate(new java.sql.Date(DateUtils.addWeeks(calendarEntries.getBatchInitiateDate(), weekly_multiplier).getTime()));
            newEntry.setBatchEndPayPeriodDate(new java.sql.Date(DateUtils.addWeeks(calendarEntries.getBatchEndPayPeriodDate(), weekly_multiplier).getTime()));
            newEntry.setBatchEmployeeApprovalDate(new java.sql.Date(DateUtils.addWeeks(calendarEntries.getBatchEmployeeApprovalDate(), weekly_multiplier).getTime()));
            newEntry.setBatchSupervisorApprovalDate(new java.sql.Date(DateUtils.addWeeks(calendarEntries.getBatchSupervisorApprovalDate(), weekly_multiplier).getTime()));
        } else if (CalendarEntryPeriodType.MONTHLY.equals(type)) {
            newEntry.setBeginPeriodDateTime(addMonthToDate(calendarEntries.getBeginPeriodDateTime()));
            newEntry.setEndPeriodDateTime(addMonthToDate(calendarEntries.getEndPeriodDateTime()));
            newEntry.setBatchInitiateDate(new java.sql.Date(addMonthToDate(calendarEntries.getBatchInitiateDate()).getTime()));
            newEntry.setBatchEndPayPeriodDate(new java.sql.Date(addMonthToDate(calendarEntries.getBatchEndPayPeriodDate()).getTime()));
            newEntry.setBatchEmployeeApprovalDate(new java.sql.Date(addMonthToDate(calendarEntries.getBatchEmployeeApprovalDate()).getTime()));
            newEntry.setBatchSupervisorApprovalDate(new java.sql.Date(addMonthToDate(calendarEntries.getBatchSupervisorApprovalDate()).getTime()));
        } else if (CalendarEntryPeriodType.SEMI_MONTHLY.equals(type)) {
            newEntry.setBeginPeriodDateTime(addSemiMonthToDate(calendarEntries.getBeginPeriodDateTime()));
            newEntry.setEndPeriodDateTime(addSemiMonthToDate(calendarEntries.getEndPeriodDateTime()));
            newEntry.setBatchInitiateDate(new java.sql.Date(addSemiMonthToDate(calendarEntries.getBatchInitiateDate()).getTime()));
            newEntry.setBatchEndPayPeriodDate(new java.sql.Date(addSemiMonthToDate(calendarEntries.getBatchEndPayPeriodDate()).getTime()));
            newEntry.setBatchEmployeeApprovalDate(new java.sql.Date(addSemiMonthToDate(calendarEntries.getBatchEmployeeApprovalDate()).getTime()));
            newEntry.setBatchSupervisorApprovalDate(new java.sql.Date(addSemiMonthToDate(calendarEntries.getBatchSupervisorApprovalDate()).getTime()));
        }
        calendarEntriesDao.saveOrUpdate(newEntry);
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
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarId(String hrCalendarId) {
    	return calendarEntriesDao.getAllCalendarEntriesForCalendarId(hrCalendarId);
    }
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year) {
    	return calendarEntriesDao.getAllCalendarEntriesForCalendarIdAndYear(hrCalendarId, year);
    }
}
