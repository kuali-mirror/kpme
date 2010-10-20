package org.kuali.hr.time.clock.location.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clocklog.ClockLog;

public interface ClockLocationRuleService {
	public List<ClockLocationRule> getClockLocationRule(String dept, Long workArea,
			String principalId, Long jobNumber, Date asOfDate);
	public void processClockLocationRule(ClockLog clockLog, Date asOfDate);
}
