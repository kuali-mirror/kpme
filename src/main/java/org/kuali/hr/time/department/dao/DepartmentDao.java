package org.kuali.hr.time.department.dao;

import org.kuali.hr.time.department.Department;

import java.sql.Date;
import java.util.List;

public interface DepartmentDao {
	public void saveOrUpdate(Department dept);
	public Department getDepartment(String department,Date asOfDate);
    public List<Department> getDepartments(String location, Date asOfDate);
    public Department getDepartment(Long tkDeptId);
}
