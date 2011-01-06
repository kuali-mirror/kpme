package org.kuali.hr.time.department.dao;

import java.sql.Date;

import org.kuali.hr.time.department.Department;

public interface DepartmentDao {
	public void saveOrUpdate(Department dept);
	public Department getDepartment(String department,Date asOfDate);
}
