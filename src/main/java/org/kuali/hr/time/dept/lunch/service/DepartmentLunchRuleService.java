package org.kuali.hr.time.dept.lunch.service;

import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.timeblock.TimeBlock;

import java.sql.Date;
import java.util.List;

public interface DepartmentLunchRuleService {
	/**
	 * Fetch department lunch rule based on criteria passed in
	 * @param dept
	 * @param workArea
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea, String principalId, Long jobNumber, Date asOfDate);
	/**
	 * Apply department lunch rule to the list of timeblocks
	 * @param timeblocks
	 */
	public void applyDepartmentLunchRule(List<TimeBlock> timeblocks);
	
	/**
	 * Fetch department lunch rule by id
	 * @param tkDeptLunchRuleId
	 * @return
	 */
	public DeptLunchRule getDepartmentLunchRule(Long tkDeptLunchRuleId);
}
