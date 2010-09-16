package org.kuali.hr.time.dept.earncode.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.earncode.EarnCode;

public interface DepartmentEarnCodeDao {

	public void saveOrUpdate(DepartmentEarnCode deptErncd);

	public void saveOrUpdate(List<DepartmentEarnCode> deptErncdList);

	public List<EarnCode> getDepartmentEarnCodes(String department, String tk_sal_group, Date asOfDate);
}
