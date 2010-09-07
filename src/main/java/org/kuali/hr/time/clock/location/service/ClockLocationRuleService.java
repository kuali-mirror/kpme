package org.kuali.hr.time.clock.location.service;

import java.sql.Date;

import org.kuali.hr.time.clock.location.ClockLocationRule;

public interface ClockLocationRuleService {
	public ClockLocationRule getClockLocationRule(String principalId, Date effectiveDate);
}
