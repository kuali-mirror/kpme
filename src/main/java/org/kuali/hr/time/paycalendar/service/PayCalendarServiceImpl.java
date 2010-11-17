package org.kuali.hr.time.paycalendar.service;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.paycalendar.dao.PayCalendarDao;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class PayCalendarServiceImpl implements PayCalendarService {

	private PayCalendarDao payCalendarDao;

	@Override
	public void saveOrUpdate(PayType payCalendar) {
		payCalendarDao.saveOrUpdate(payCalendar);
	}

	@Override
	public void saveOrUpdate(List<PayType> payCalendarList) {
		payCalendarDao.saveOrUpdate(payCalendarList);
	}

	public void setPayCalendarDao(PayCalendarDao payCalendarDao) {
		this.payCalendarDao = payCalendarDao;
	}

	@Override
	public PayCalendar getPayCalendar(Long payCalendarId) {
		return payCalendarDao.getPayCalendar(payCalendarId);
	}

	@Override
	public PayCalendar getPayCalendarByGroup(String calendarGroup) {
		return payCalendarDao.getPayCalendarByGroup(calendarGroup);
	}
	
	@Override
	public PayCalendarDates getCurrentPayCalendarDates(String principalId, Date currentDate) {
		PayCalendarDates pcd = null;
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
			PayCalendar payCalendar = payType.getPayCalendar();
			if (payCalendar == null)
				throw new RuntimeException("Null pay calendar on payType in getPayEndDate");
			List<PayCalendarDates> dates = payCalendar.getPayCalendarDates();
			for (PayCalendarDates pcdate : dates) { 
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
