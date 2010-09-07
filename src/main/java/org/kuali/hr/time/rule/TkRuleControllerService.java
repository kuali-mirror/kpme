package org.kuali.hr.time.rule;

import java.util.List;

public interface TkRuleControllerService {
	public void applyRules(TkRuleContext tkRuleContext);
	public List<TkRule> getRulesForAction(String action);
}
