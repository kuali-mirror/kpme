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
package org.kuali.hr.time.overtime.weekly.rule;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class WeeklyOvertimeRuleGroup extends PersistableBusinessObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private List<WeeklyOvertimeRule> lstWeeklyOvertimeRules = new ArrayList<WeeklyOvertimeRule>();
	@Transient
	private Long tkWeeklyOvertimeRuleGroupId = 1L;

	public List<WeeklyOvertimeRule> getLstWeeklyOvertimeRules() {
		return lstWeeklyOvertimeRules;
	}

	public void setLstWeeklyOvertimeRules(
			List<WeeklyOvertimeRule> lstWeeklyOvertimeRules) {
		this.lstWeeklyOvertimeRules = lstWeeklyOvertimeRules;
	}

	public Long getTkWeeklyOvertimeRuleGroupId() {
		return tkWeeklyOvertimeRuleGroupId;
	}

	public void setTkWeeklyOvertimeRuleGroupId(Long tkWeeklyOvertimeRuleGroupId) {
		this.tkWeeklyOvertimeRuleGroupId = tkWeeklyOvertimeRuleGroupId;
	}
	
	
	
}
