package org.kuali.hr.time.accrual.dao;

import java.util.Collection;
import java.util.List;

import org.kuali.hr.time.accrual.TimeOffAccrual;

public interface TimeOffAccrualDao {

	public List<TimeOffAccrual> getTimeOffAccruals(String principalId);
	
	public TimeOffAccrual getTimeOffAccrual(Long laTimeOffAccrualId);
	
	public List<TimeOffAccrual> getActiveTimeOffAccruals (String principalId, List<String> activeAccrualCategories);

}
