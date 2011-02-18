package org.kuali.hr.time.dept.lunch.service;

import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.timeblock.TimeBlock;

import java.sql.Date;
import java.util.List;

public interface DepartmentLunchRuleService {
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea, String principalId, Long jobNumber, Date asOfDate);
	public void applyDepartmentLunchRule(List<TimeBlock> timeblocks);
}
