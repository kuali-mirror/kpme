package org.kuali.hr.lm.employeeoverride.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.employeeoverride.dao.EmployeeOverrideDao;

public class EmployeeOverrideServiceImpl implements EmployeeOverrideService {
	
	private EmployeeOverrideDao employeeOverrideDao;

	@Override
	public List<EmployeeOverride> getEmployeeOverrides(String principalId, Date asOfDate) {
		return employeeOverrideDao.getEmployeeOverrides(principalId, asOfDate);
	}

	@Override
	public EmployeeOverride getEmployeeOverride(String lmEmployeeOverrideId) {
		return employeeOverrideDao.getEmployeeOverride(lmEmployeeOverrideId);
	}

	public EmployeeOverrideDao getEmployeeOverrideDao() {
		return employeeOverrideDao;
	}

	public void setEmployeeOverrideDao(EmployeeOverrideDao employeeOverrideDao) {
		this.employeeOverrideDao = employeeOverrideDao;
	}

}
