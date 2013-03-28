package org.kuali.hr.pm.salarygroup.service;

import org.kuali.hr.pm.salarygroup.SalaryGroup;

public interface SalaryGroupService {
	/**
	 * Retrieve the Salary Group with given id
	 * @param pmSalaryGroupId
	 * @return
	 */
	public SalaryGroup getSalaryGroupById(String pmSalaryGroupId);
}
