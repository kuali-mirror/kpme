package org.kuali.hr.time.dept.earncode.dao;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;

import java.util.Date;
import java.util.List;

public interface DepartmentEarnCodeDao {

	public void saveOrUpdate(DepartmentEarnCode deptErncd);

	public void saveOrUpdate(List<DepartmentEarnCode> deptErncdList);

	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String hr_sal_group, String location, Date asOfDate);

	public DepartmentEarnCode getDepartmentEarnCode(String hrDeptEarnCodeId);
	
	public List<DepartmentEarnCode> searchDepartmentEarnCodes(String dept, String salGroup, String earnCode, String location,
			java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory);
	
	public int getDepartmentEarnCodeCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, java.sql.Date effdt,String hrDeptEarnCodeId);
	
	public int getNewerDeptEarnCodeCount(String earnCode, Date effdt);
}
