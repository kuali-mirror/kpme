package org.kuali.hr.time.dept.lunch.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.timeblock.TimeBlock;

public interface DepartmentLunchRuleService {
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea, String principalId, Long jobNumber, Date asOfDate);
	public List<TimeBlock> applyDepartmentLunchRule(List<TimeBlock> timeblocks);
}
