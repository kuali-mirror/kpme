package org.kuali.hr.lm.employeeoverride.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.employeeoverride.EmployeeOverride;

public interface EmployeeOverrideService {
	public List<EmployeeOverride> getEmployeeOverrides(String principalId, Date asOfDate);
	
	public EmployeeOverride getEmployeeOverride(String lmEmployeeOverrideId);
}
