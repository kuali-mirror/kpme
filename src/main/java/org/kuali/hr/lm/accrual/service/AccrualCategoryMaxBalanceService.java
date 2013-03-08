package org.kuali.hr.lm.accrual.service;

import java.util.Map;
import java.util.Set;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.calendar.CalendarEntries;

public interface AccrualCategoryMaxBalanceService {

	public Map<String, Set<LeaveBlock>> getMaxBalanceViolations(CalendarEntries entry, String principalId);
	
}
