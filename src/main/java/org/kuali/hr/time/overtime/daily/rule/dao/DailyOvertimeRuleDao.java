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
package org.kuali.hr.time.overtime.daily.rule.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;

public interface DailyOvertimeRuleDao {

	/**
	 * Given a Department, Work Area, Task and Effective Date, provides the
	 * daily overtime rule that applies.
	 * 
	 * @return
	 */
	public DailyOvertimeRule findDailyOvertimeRule(String location, String paytype, String dept, Long workArea, Date asOfDate);
	
	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule);
	
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules);
	
	public DailyOvertimeRule getDailyOvertimeRule(String tkDailyOvertimeRuleId);
	
	public List<DailyOvertimeRule> getDailyOvertimeRules(String dept, String workArea, String location, Date fromEffdt, Date toEffdt, String active, String showHist);
}
