package org.kuali.hr.time.clock.location.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.clock.location.ClockLocationRule;

public interface ClockLocationDao {
	public List<ClockLocationRule> getClockLocationRule(String dept, Long workArea, String principalId, 
													Long jobNumber, Date asOfDate);
}
