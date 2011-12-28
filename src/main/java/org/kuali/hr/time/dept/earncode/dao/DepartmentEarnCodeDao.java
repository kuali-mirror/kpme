package org.kuali.hr.time.dept.earncode.dao;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;

import java.util.Date;
import java.util.List;

public interface DepartmentEarnCodeDao {

	public void saveOrUpdate(DepartmentEarnCode deptErncd);

	public void saveOrUpdate(List<DepartmentEarnCode> deptErncdList);

	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String hr_sal_group, String location, Date asOfDate);

	public DepartmentEarnCode getDepartmentEarnCode(String hrDeptEarnCodeId);
}
