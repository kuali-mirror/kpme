package org.kuali.hr.time.department.service;

import java.sql.Date;

import org.kuali.hr.time.department.Department;

public interface DepartmentService {
	/**
	 * Get Department as of a particular date passed in
	 * @param department
	 * @param asOfDate
	 * @return
	 */
	public Department getDepartment(String department, Date asOfDate);
}
