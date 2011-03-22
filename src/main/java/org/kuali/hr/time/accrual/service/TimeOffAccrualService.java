package org.kuali.hr.time.accrual.service;

import java.util.List;
import java.util.Map;

import org.kuali.hr.time.accrual.TimeOffAccrual;

public interface TimeOffAccrualService {
	/**
	 * Get TimeOffAccrual object for a particular user
	 * @param principalId
	 * @return
	 */
	public List<TimeOffAccrual> getTimeOffAccruals(String principalId);
	/**
	 * Get a list of maps that represents a persons accrual balances
	 * @param principalId
	 * @return
	 */
	public List<Map<String, Object>> getTimeOffAccrualsCalc(String principalId);
}
