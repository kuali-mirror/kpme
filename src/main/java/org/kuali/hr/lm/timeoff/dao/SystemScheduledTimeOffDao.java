package org.kuali.hr.lm.timeoff.dao;

import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;


public interface SystemScheduledTimeOffDao {

		/**
	 * Get SystemScheduledTimeOff from id
	 * @param lmSystemScheduledTimeOffId
	 * @return SystemScheduledTimeOff
	 */
	public SystemScheduledTimeOff getSystemScheduledTimeOff(Long lmSystemScheduledTimeOffId);
	
}
