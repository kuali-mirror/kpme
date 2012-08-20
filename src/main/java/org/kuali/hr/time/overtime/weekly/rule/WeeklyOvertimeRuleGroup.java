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
