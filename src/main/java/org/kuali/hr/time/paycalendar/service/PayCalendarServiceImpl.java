package org.kuali.hr.time.paycalendar.service;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.paycalendar.dao.PayCalendarDao;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class PayCalendarServiceImpl implements PayCalendarService {

	private PayCalendarDao payCalendarDao;
	
	public void setPayCalendarDao(PayCalendarDao payCalendarDao) {
		this.payCalendarDao = payCalendarDao;
	}

	@Override
	public PayCalendar getPayCalendar(Long payCalendarId) {
		return payCalendarDao.getPayCalendar(payCalendarId);
	}

	@Override
	@CacheResult
	public PayCalendar getPayCalendarByGroup(String calendarGroup) {
		return payCalendarDao.getPayCalendarByGroup(calendarGroup);
	}
	
	@Override
	@CacheResult
	public PayCalendarEntries getCurrentPayCalendarDates(String principalId, Date currentDate) {
		PayCalendarEntries pcd = null;
		DateTime currentTime = new DateTime(currentDate); 
		
		List<Job> currentJobs = TkServiceLocator.getJobSerivce().getJobs(principalId, currentDate);
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
			PrincipalCalendar principalCalendar = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, currentDate);
			if(principalCalendar == null){
				throw new RuntimeException("Null principal calendar for principalid "+principalId);
			}
			PayCalendar payCalendar = principalCalendar.getPayCalendar();
			if (payCalendar == null)
				throw new RuntimeException("Null pay calendar on principal calendar in getPayEndDate");
			List<PayCalendarEntries> dates = payCalendar.getPayCalendarEntries();
			for (PayCalendarEntries pcdate : dates) { 
				DateTime beginDate = new DateTime(pcdate.getBeginPeriodDateTime());					
				DateTime endDate = new DateTime(pcdate.getEndPeriodDateTime());
				
				Interval range = new Interval(beginDate, endDate);
				// For a given principal_id + job_number combination, it is given that there 
				// will be no overlapping PayCalendarDates, this way we know that if our 
				// date fits within the range any given pay calendar date, we have the 
				// correct PayCalendarDate.
				if (range.contains(currentTime)) {
					pcd = pcdate;
					break;
				}				
			}
		}
		
		return pcd;
	}
	

}
