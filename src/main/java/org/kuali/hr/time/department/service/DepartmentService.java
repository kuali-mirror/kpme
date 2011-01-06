package org.kuali.hr.time.department.service;

import java.sql.Date;

import org.kuali.hr.time.department.Department;

public interface DepartmentService {

	public Department getDepartment(String department, Date asOfDate);
}
