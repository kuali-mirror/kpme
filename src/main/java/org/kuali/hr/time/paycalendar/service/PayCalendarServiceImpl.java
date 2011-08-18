package org.kuali.hr.time.paycalendar.service;

import java.util.Date;
import java.util.List;

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
    public PayCalendarEntries getPayCalendarDatesByPayEndDate(String principalId, Date payEndDate) {
        PayCalendarEntries pcd = null;

        PayCalendar payCalendar = getPayCalendar(principalId, payEndDate);
        pcd = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntriesByIdAndPeriodEndDate(payCalendar.getPayCalendarId(), payEndDate);
        pcd.setPayCalendarObj(payCalendar);

        return pcd;
    }

	@Override
	@CacheResult
	public PayCalendarEntries getCurrentPayCalendarDates(String principalId, Date currentDate) {
		PayCalendarEntries pcd = null;
        PayCalendar payCalendar = getPayCalendar(principalId, currentDate);
	    pcd = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(payCalendar.getPayCalendarId(), currentDate);
        pcd.setPayCalendarObj(payCalendar);
		return pcd;
	}

    /**
     * Helper method common to the PayCalendarEntry search methods above.
     * @param principalId Principal ID to lookup
     * @param date A date, Principal Calendars are EffDt/Timestamped, so we can any current date.
     * @return A PayCalendar
     */
    private PayCalendar getPayCalendar(String principalId, Date date) {
        PayCalendar pcal = null;

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
            PrincipalCalendar principalCalendar = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, date);
            if(principalCalendar == null){
                throw new RuntimeException("Null principal calendar for principalid "+principalId);
            }
            pcal = principalCalendar.getPayCalendar();
            if (pcal == null)
                throw new RuntimeException("Null pay calendar on principal calendar in getPayEndDate");

        }

        return pcal;
    }

	public PayCalendarEntries getPreviousPayCalendarEntry(Long tkPayCalendarId, Date beginDateCurrentPayCalendar){
		return payCalendarDao.getPreviousPayCalendarEntry(tkPayCalendarId, beginDateCurrentPayCalendar);
	}


}
