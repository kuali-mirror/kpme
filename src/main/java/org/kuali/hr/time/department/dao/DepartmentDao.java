package org.kuali.hr.time.department.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.department.Department;

public interface DepartmentDao {
	public void saveOrUpdate(Department dept);
	public Department getDepartment(String department,Date asOfDate);
    public List<Department> getDepartments(String location, Date asOfDate);
    public Department getDepartment(String hrDeptId);
    public List<Department> getDepartmentByLocation(String location);
}
