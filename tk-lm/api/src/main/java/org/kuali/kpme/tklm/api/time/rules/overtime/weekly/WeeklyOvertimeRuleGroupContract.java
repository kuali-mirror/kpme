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
package org.kuali.kpme.tklm.api.time.rules.overtime.weekly;

import java.util.List;

import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>WeeklyOvertimeRuleGroupContract interface</p>
 *
 */
public interface WeeklyOvertimeRuleGroupContract extends PersistableBusinessObject {
	
	/**
	 * The list of WeeklyOvertimeRule objects associated with the WeeklyOvertimeRuleGroup
	 * 
	 * <p>
	 * lstWeeklyOvertimeRules of a WeeklyOvertimeRuleGroup
	 * <p>
	 * 
	 * @return lstWeeklyOvertimeRules for WeeklyOvertimeRuleGroup
	 */
	public List<? extends WeeklyOvertimeRuleContract> getLstWeeklyOvertimeRules();

	/**
	 * The primary key of a WeeklyOvertimeRuleGroup entry saved in a database
	 * 
	 * <p>
	 * tkWeeklyOvertimeRuleGroupId of a WeeklyOvertimeRuleGroup
	 * <p>
	 * 
	 * @return tkWeeklyOvertimeRuleGroupId for WeeklyOvertimeRuleGroup
	 */
	public Long getTkWeeklyOvertimeRuleGroupId();

}
