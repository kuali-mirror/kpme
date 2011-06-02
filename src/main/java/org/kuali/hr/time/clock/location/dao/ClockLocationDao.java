package org.kuali.hr.time.clock.location.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.clock.location.ClockLocationRule;

public interface ClockLocationDao {
	/**
	 * Get Clock location rule based on passed in criteria
	 * @param dept
	 * @param workArea
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
	public List<ClockLocationRule> getClockLocationRule(String dept, Long workArea, String principalId, 
													Long jobNumber, Date asOfDate);
	
	public List<ClockLocationRule> getNewerVersionClockLocationRule(String dept, Long workArea, String principalId, 
			Long jobNumber, Date asOfDate);
	/**
	 * Get Clock Location Rule based on id
	 * @param tkClockLocationRuleId
	 * @return
	 */
	public ClockLocationRule getClockLocationRule(Long tkClockLocationRuleId);
}
