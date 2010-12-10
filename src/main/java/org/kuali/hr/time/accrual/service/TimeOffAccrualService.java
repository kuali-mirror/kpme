package org.kuali.hr.time.accrual.service;

import java.util.List;
import java.util.Map;

import org.kuali.hr.time.accrual.TimeOffAccrual;

public interface TimeOffAccrualService {
	
	public List<TimeOffAccrual> getTimeOffAccruals(String principalId);
	public List<Map<String, Object>> getTimeOffAccrualsCalc(String principalId);
}
