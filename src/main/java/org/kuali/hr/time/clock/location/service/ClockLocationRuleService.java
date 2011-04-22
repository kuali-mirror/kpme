package org.kuali.hr.time.clock.location.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clocklog.ClockLog;

public interface ClockLocationRuleService {
	/**
	 * Fetch Clock Location Rule based on criteria
	 * @param dept
	 * @param workArea
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
	public List<ClockLocationRule> getClockLocationRule(String dept, Long workArea,
			String principalId, Long jobNumber, Date asOfDate);
	/**
	 * Process clock location rule based on clock log passed in
	 * @param clockLog
	 * @param asOfDate
	 */
	public void processClockLocationRule(ClockLog clockLog, Date asOfDate);
	
	public List<ClockLocationRule> getNewerVersionClockLocationRule(String dept, Long workArea, String principalId, 
			Long jobNumber, Date asOfDate);
}
