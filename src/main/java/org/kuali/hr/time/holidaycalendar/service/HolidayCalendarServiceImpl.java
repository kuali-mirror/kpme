package org.kuali.hr.time.holidaycalendar.service;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.hr.time.holidaycalendar.dao.HolidayCalendarDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class HolidayCalendarServiceImpl implements HolidayCalendarService {
	private HolidayCalendarDao holidayCalendarDao;
	
	
	@Override
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup) {
		return holidayCalendarDao.getHolidayCalendarByGroup(holidayCalendarGroup);
	}


	public HolidayCalendarDao getHolidayCalendarDao() {
		return holidayCalendarDao;
	}


	public void setHolidayCalendarDao(HolidayCalendarDao holidayCalendarDao) {
		this.holidayCalendarDao = holidayCalendarDao;
	}


	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(
			Long hrHolidayCalendarId, Date startDate, Date endDate) {
		return holidayCalendarDao.getHolidayCalendarDateEntriesForPayPeriod(hrHolidayCalendarId, startDate, endDate);
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public HolidayCalendarDateEntry getHolidayCalendarDateEntryByDate(Long hrHolidayCalendarId, Date startDate) {
		return holidayCalendarDao.getHolidayCalendarDateEntryByDate(hrHolidayCalendarId, startDate);
	}

	@Override
	public Assignment getAssignmentToApplyHolidays(TimesheetDocument timesheetDocument, java.sql.Date payEndDate) {
		Job primaryJob = TkServiceLocator.getJobSerivce().getPrimaryJob(timesheetDocument.getPrincipalId(), payEndDate);
		for(Assignment assign : timesheetDocument.getAssignments()){
			if(assign.getJobNumber().equals(primaryJob.getJobNumber())){
				return assign;
			}
		}
		return null;
	}


	@Override
	public BigDecimal calculateHolidayHours(Job job, BigDecimal holidayHours) {
		BigDecimal fte = job.getStandardHours().divide(new BigDecimal(40.0),TkConstants.BIG_DECIMAL_SCALE);
		return fte.multiply(holidayHours).setScale(TkConstants.BIG_DECIMAL_SCALE);
	}

}
