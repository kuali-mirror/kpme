package org.kuali.hr.time.rule;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.TkConstants;

public class TkRuleControllerServiceImpl implements TkRuleControllerService {

	public List<TkRule> getRulesForAction(String action){
		List<TkRule> rules = new ArrayList<TkRule>();
		//foreach action build a list of rules that apply
		if(StringUtils.equals(action, TkConstants.ACTIONS.CLOCK_IN)){
			
		} else {
			
		}
		return rules;
		
	}
	
	public void applyRules(TkRuleContext tkRuleContext){
		List<TkRule> rules = getRulesForAction(tkRuleContext.getAction());
		for(TkRule rule : rules){
			//rule.applyRule(tkRuleContext);
		}
	}


}
