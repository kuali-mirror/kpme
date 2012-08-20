package org.kuali.hr.time.dept.lunch.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.springframework.cache.annotation.Cacheable;

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
    @Cacheable(value= DeptLunchRule.CACHE_NAME,
            key="'dept=' + #p0" +
                    "+ '|' + 'workArea=' + #p1" +
                    "+ '|' + 'principalId=' + #p2" +
                    "+ '|' + 'jobNumber=' + #p3" +
                    "+ '|' + 'asOfDate=' + #p4")
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
    @Cacheable(value= DeptLunchRule.CACHE_NAME, key="'tkDeptLunchRuleId=' + #p0")
	public DeptLunchRule getDepartmentLunchRule(String tkDeptLunchRuleId);
}
