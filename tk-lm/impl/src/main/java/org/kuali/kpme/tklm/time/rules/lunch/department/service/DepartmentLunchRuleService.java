/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.time.rules.lunch.department.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule;
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
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea, String principalId, Long jobNumber, LocalDate asOfDate);
	
    /**
     * Fetch department lunch rule based on criteria passed in, using literals for wildcards.  Will not go through logic to
     * determine best match, but will match exactly on what is passed in.
     * @param dept
     * @param workArea
     * @param principalId
     * @param jobNumber
     * @param asOfDate
     * @return
     */
	@Cacheable(value= DeptLunchRule.CACHE_NAME,
			key="'{getEarnCodesForLeaveAndTime}'" +
					"+ 'dept=' + #p0" +
					"+ '|' + 'workArea=' + #p1" +
					"+ '|' + 'principalId=' + #p2" +
					"+ '|' + 'jobNumber=' + #p3" +
					"+ '|' + 'asOfDate=' + #p4")
	public DeptLunchRule getDepartmentLunchRuleNoWildCards(String dept, Long workArea, String principalId, Long jobNumber, LocalDate asOfDate);
	 	
    /**
	 * Apply department lunch rule to the list of timeblocks
	 * @param timeblocks
	 */
	public List<TimeBlock> applyDepartmentLunchRule(List<TimeBlock> timeblocks);
	
	/**
	 * Fetch department lunch rule by id
	 * @param tkDeptLunchRuleId
	 * @return
	 */
    @Cacheable(value= DeptLunchRule.CACHE_NAME, key="'tkDeptLunchRuleId=' + #p0")
	public DeptLunchRule getDepartmentLunchRule(String tkDeptLunchRuleId);

    List<DeptLunchRule> getDepartmentLunchRules(String userPrincipalId, String dept, String workArea, String principalId, String jobNumber, String groupKeyCode,
    											LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
}
