package org.kuali.hr.time.clock.location.dao;

import java.sql.Date;

import org.kuali.hr.time.clock.location.ClockLocationRule;

public interface ClockLocationDao {
	public ClockLocationRule getClockLocationRule(String principalId, Date effectiveDate);
}
