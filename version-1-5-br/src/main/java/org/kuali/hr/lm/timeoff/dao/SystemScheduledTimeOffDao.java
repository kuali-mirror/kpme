package org.kuali.hr.lm.timeoff.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;


public interface SystemScheduledTimeOffDao {

		/**
	 * Get SystemScheduledTimeOff from id
	 * @param lmSystemScheduledTimeOffId
	 * @return SystemScheduledTimeOff
	 */
	public SystemScheduledTimeOff getSystemScheduledTimeOff(String lmSystemScheduledTimeOffId);

	public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, Date startDate, Date endDate);

	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(String leavePlan, Date startDate);
	
}
