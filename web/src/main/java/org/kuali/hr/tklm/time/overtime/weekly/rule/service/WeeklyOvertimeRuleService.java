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
package org.kuali.hr.tklm.time.overtime.weekly.rule.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.tklm.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.util.TkTimeBlockAggregate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface WeeklyOvertimeRuleService {
	/**
	 * Save or Update a given WeeklyOvertimeRule
	 * @param weeklyOvertimeRule
	 */
    @CacheEvict(value={WeeklyOvertimeRule.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule);
	/**
	 * Save or Update a List of WeeklyOvertimeRules
	 * @param weeklyOvertimeRules
	 */
    @CacheEvict(value={WeeklyOvertimeRule.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules);
	/**
	 * Fetch a List of WeeklyOvertimeRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= WeeklyOvertimeRule.CACHE_NAME, key="'asOfDate=' + #p1")
	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(LocalDate asOfDate);
	/**
	 * Process weekly overtime rules for a given TkTimeBlockAggregate
	 * @param timesheetDocument
	 * @param aggregate
	 */
	public void processWeeklyOvertimeRule(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);
	/**
	 * Fetch Weekly overtime rule by id
	 * @param tkWeeklyOvertimeRuleId
	 * @return
	 */
    @Cacheable(value= WeeklyOvertimeRule.CACHE_NAME, key="'tkWeeklyOvertimeRuleId=' + #p0")
	public WeeklyOvertimeRule getWeeklyOvertimeRule(String tkWeeklyOvertimeRuleId);
}
