package org.kuali.hr.time.dept.lunch.dao;

import java.sql.Date;

import org.kuali.hr.time.dept.lunch.DeptLunchRule;

public interface DepartmentLunchRuleDao {
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea, String principalId, Long jobNumber, Date asOfDate);
	public DeptLunchRule getDepartmentLunchRule(String tkDeptLunchRuleId);
}
