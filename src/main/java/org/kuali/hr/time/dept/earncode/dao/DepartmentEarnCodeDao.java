package org.kuali.hr.time.dept.earncode.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;

public interface DepartmentEarnCodeDao {

	public void saveOrUpdate(DepartmentEarnCode deptErncd);

	public void saveOrUpdate(List<DepartmentEarnCode> deptErncdList);

	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String tk_sal_group, String location, Date asOfDate);

	public DepartmentEarnCode getDepartmentEarnCode(Long tkDeptEarnCodeId);
}
