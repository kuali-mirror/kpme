package org.kuali.hr.time.department.service;

import java.sql.Date;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.department.dao.DepartmentDao;

public class DepartmentServiceImpl implements DepartmentService {
	
	private DepartmentDao departmentDao;

	@Override
	public Department getDepartment(String department, Date asOfDate) {
		return departmentDao.getDepartment(department, asOfDate);
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

}
