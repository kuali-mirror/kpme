package org.kuali.hr.lm.timeoff.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.lm.timeoff.dao.SystemScheduledTimeOffDao;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;

public class SystemScheduledTimeOffServiceImpl implements SystemScheduledTimeOffService {

	private static final Logger LOG = Logger.getLogger(SystemScheduledTimeOffServiceImpl.class);
	private SystemScheduledTimeOffDao systemScheduledTimeOffDao;

	public SystemScheduledTimeOffDao getSystemScheduledTimeOffDao() {
		return systemScheduledTimeOffDao;
	}

	public void setSystemScheduledTimeOffDao(
			SystemScheduledTimeOffDao systemScheduledTimeOffDao) {
		this.systemScheduledTimeOffDao = systemScheduledTimeOffDao;
	}

	@Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public SystemScheduledTimeOff getSystemScheduledTimeOff(String lmSystemScheduledTimeOffId) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOff(lmSystemScheduledTimeOffId);
	}

	@Override
	public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, Date startDate, Date endDate) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOffForPayPeriod(leavePlan, startDate, endDate);
	}

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(
			String leavePlan, Date startDate) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOffByDate(leavePlan, startDate);
	}	
	@Override
	public Assignment getAssignmentToApplyHolidays(TimesheetDocument timesheetDocument, java.sql.Date payEndDate) {
		Job primaryJob = TkServiceLocator.getJobService().getPrimaryJob(timesheetDocument.getPrincipalId(), payEndDate);
		for(Assignment assign : timesheetDocument.getAssignments()){
			if(assign.getJobNumber().equals(primaryJob.getJobNumber())){
				return assign;
			}
		}
		return null;
	}

	@Override
	public BigDecimal calculateHolidayHours(Job job, Long holidayHours) {
		BigDecimal fte = job.getStandardHours().divide(new BigDecimal(40.0),TkConstants.BIG_DECIMAL_SCALE);
		return fte.multiply(new BigDecimal(holidayHours)).setScale(TkConstants.BIG_DECIMAL_SCALE);
	}
   
}
