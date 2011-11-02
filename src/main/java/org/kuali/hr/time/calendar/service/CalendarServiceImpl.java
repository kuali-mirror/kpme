package org.kuali.hr.time.calendar.service;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.dao.CalendarDao;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

import java.util.Date;
import java.util.List;

public class CalendarServiceImpl implements CalendarService {

	private CalendarDao calendarDao;

	public void setCalendarDao(CalendarDao calendarDao) {
		this.calendarDao = calendarDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public Calendar getPayCalendar(Long hrCalendarId) {
		return calendarDao.getPayCalendar(hrCalendarId);
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public Calendar getPayCalendarByGroup(String calendarName) {
		return calendarDao.getPayCalendarByGroup(calendarName);
	}

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public CalendarEntries getPayCalendarDatesByPayEndDate(String principalId, Date payEndDate) {
        CalendarEntries pcd = null;

        Calendar calendar = getPayCalendar(principalId, payEndDate);
        pcd = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntriesByIdAndPeriodEndDate(calendar.getHrCalendarId(), payEndDate);
        pcd.setCalendarObj(calendar);

        return pcd;
    }

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public CalendarEntries getCurrentPayCalendarDates(String principalId, Date currentDate) {
		CalendarEntries pcd = null;
        Calendar payCalendar = getPayCalendar(principalId, currentDate);
	    pcd = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(payCalendar.getHrCalendarId(), currentDate);
        pcd.setCalendarObj(payCalendar);
		return pcd;
	}

    /**
     * Helper method common to the PayCalendarEntry search methods above.
     * @param principalId Principal ID to lookup
     * @param date A date, Principal Calendars are EffDt/Timestamped, so we can any current date.
     * @return A PayCalendar
     */
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    private Calendar getPayCalendar(String principalId, Date date) {
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
            PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributesService().getPrincipalCalendar(principalId, date);
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
	public CalendarEntries getPreviousPayCalendarEntry(Long tkCalendarId, Date beginDateCurrentPayCalendar){
		return calendarDao.getPreviousPayCalendarEntry(tkCalendarId, beginDateCurrentPayCalendar);
	}


}
