package org.kuali.hr.lm.timeoff.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface SystemScheduledTimeOffService {
    
    /**
     * Fetch SystemScheduledTimeOff by id
     * @param lmSystemScheduledTimeOffId
     * @return
     */
    public SystemScheduledTimeOff getSystemScheduledTimeOff(String lmSystemScheduledTimeOffId);
    
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, Date startDate, Date endDate);

	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(String leavePlan, Date startDate);

	Assignment getAssignmentToApplyHolidays(
			TimesheetDocument timesheetDocument, java.sql.Date payEndDate);

	BigDecimal calculateHolidayHours(Job job, Long holidayHours);
	
}
