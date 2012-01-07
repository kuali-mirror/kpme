package org.kuali.hr.time.calendar.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.dao.CalendarDao;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class CalendarServiceImpl implements CalendarService {

	private CalendarDao calendarDao;

	public void setCalendarDao(CalendarDao calendarDao) {
		this.calendarDao = calendarDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public Calendar getCalendar(String hrCalendarId) {
		return calendarDao.getCalendar(hrCalendarId);
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public Calendar getCalendarByGroup(String calendarName) {
		return calendarDao.getCalendarByGroup(calendarName);
	}

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public CalendarEntries getCalendarDatesByPayEndDate(String principalId, Date payEndDate) {
        CalendarEntries pcd = null;

        Calendar calendar = getCalendar(principalId, payEndDate);
        pcd = TkServiceLocator.getCalendarEntriesSerivce().getCalendarEntriesByIdAndPeriodEndDate(calendar.getHrCalendarId(), payEndDate);
        pcd.setCalendarObj(calendar);

        return pcd;
    }

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public CalendarEntries getCurrentCalendarDates(String principalId, Date currentDate) {
		CalendarEntries pcd = null;
        Calendar calendar = getCalendar(principalId, currentDate);
	    pcd = TkServiceLocator.getCalendarEntriesSerivce().getCurrentCalendarEntriesByCalendarId(calendar.getHrCalendarId(), currentDate);
        pcd.setCalendarObj(calendar);
		return pcd;
	}

    /**
     * Helper method common to the CalendarEntry search methods above.
     * @param principalId Principal ID to lookup
     * @param date A date, Principal Calendars are EffDt/Timestamped, so we can any current date.
     * @return A Calendar
     */
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    private Calendar getCalendar(String principalId, Date date) {
        Calendar pcal = null;

        List<Job> currentJobs = TkServiceLocator.getJobSerivce().getJobs(principalId, date);
        if(currentJobs.size() < 1){
            throw new RuntimeException("No jobs found for principal id "+principalId);
        }
        Job job = currentJobs.get(0);

        if (principalId == null || job == null) {
            throw new RuntimeException("Null parameters passed to getPayEndDate");
        } else {
            PayType payType = job.getPayTypeObj();
            if (payType == null)
                throw new RuntimeException("Null pay type on Job in getPayEndDate");
            PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, date);
            if(principalCalendar == null){
                throw new RuntimeException("Null principal calendar for principalid "+principalId);
            }
            pcal = principalCalendar.getCalendar();
            if (pcal == null)
                throw new RuntimeException("Null pay calendar on principal calendar in getPayEndDate");

        }

        return pcal;
    }
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public CalendarEntries getPreviousCalendarEntry(String tkCalendarId, Date beginDateCurrentCalendar){
		return calendarDao.getPreviousCalendarEntry(tkCalendarId, beginDateCurrentCalendar);
	}


}
