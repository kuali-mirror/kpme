/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.rules.overtime.daily.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.time.rules.overtime.daily.DailyOvertimeRule;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface DailyOvertimeRuleService {
	/**
	 * Save of update a DailyOvertimeRule
	 * @param dailyOvertimeRule
	 */
    @CacheEvict(value={DailyOvertimeRule.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule);
	/**
	 * Save or Update a List of DailyOvertimeRules
	 * @param dailyOvertimeRules
	 */
    @CacheEvict(value={DailyOvertimeRule.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules);
	
	/**
	 * Fetch DailyOvertimeRule for the given criteria
	 * @param location
	 * @param paytype
	 * @param dept
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= DailyOvertimeRule.CACHE_NAME,
            key="'location=' + #p0" +
                    "+ '|' + 'paytype=' + #p1" +
                    "+ '|' + 'dept=' + #p2" +
                    "+ '|' + 'workArea=' + #p3" +
                    "+ '|' + 'asOfDate=' + #p4")
	public DailyOvertimeRule getDailyOvertimeRule(String location, String paytype, String dept, Long workArea, LocalDate asOfDate);
	/**
	 * Process DailyOvertimeRules for the given TkTimeBlockAggregate
	 * @param timesheetDocument
	 * @param aggregate
	 */
	public void processDailyOvertimeRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);
	/**
	 * Fetch Daily overtime rule by id
	 * @param tkDailyOvertimeRuleId
	 * @return
	 */
    @Cacheable(value= DailyOvertimeRule.CACHE_NAME, key="'tkDailyOvertimeRuleId=' + #p0")
	public DailyOvertimeRule getDailyOvertimeRule(String tkDailyOvertimeRuleId);
    
    public List<DailyOvertimeRule> getDailyOvertimeRules(String userPrincipalId, String dept, String workArea, String location, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist);

}