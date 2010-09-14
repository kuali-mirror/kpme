package org.kuali.hr.time.dept.earncode.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;

public interface DepartmentEarnCodeDao {

	public void saveOrUpdate(DepartmentEarnCode deptErncd);

	public void saveOrUpdate(List<DepartmentEarnCode> deptErncdList);

	public List<DepartmentEarnCode> getDepartmentEarnCodeList(String dept, String salGroup, boolean employee, boolean approver, 
																boolean orgAdmin, Date payEndDate);

}
