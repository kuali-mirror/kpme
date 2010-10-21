package org.kuali.hr.time.dept.lunch.service;

import java.sql.Date;

import org.kuali.hr.time.dept.lunch.DeptLunchRule;

public interface DepartmentLunchRuleService {
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea, String principalId, Long jobNumber, Date asOfDate);
}
