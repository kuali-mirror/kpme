package org.kuali.hr.time.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;

@SuppressWarnings("unchecked")
public class TkRuleControllerServiceImpl implements TkRuleControllerService {
	private static final Map<String,List<Class>> actionToRuleMap = new HashMap<String,List<Class>>();
	static{
		//Add rules for clockin
		final List<Class> clockIn = new ArrayList<Class>();
		clockIn.add(GracePeriodRule.class);
		actionToRuleMap.put("clockIn", clockIn);
		//Add rules for clockout
		final List<Class> clockOut = new ArrayList<Class>();
		clockOut.add(GracePeriodRule.class);
		actionToRuleMap.put("clockOut", clockOut);
	}

	@Override
	public List<TkRule> getRulesForAction(String action) {
		List<Class> ruleClasses =  actionToRuleMap.get(action);
		List<TkRule> lstRules = new ArrayList<TkRule>();
		for(Class ruleClass : ruleClasses){
			try {
				lstRules.add((TkRule)ruleClass.newInstance());
			} catch (Exception e){
				throw new RuntimeException(e);
			}
		}
		return lstRules;
	}
	
	public void applyRules(TkRuleContext tkRuleContext){
		List<TkRule> rules = getRulesForAction(tkRuleContext.getAction());
		for(TkRule rule : rules){
			rule.applyRule(tkRuleContext);
		}
	}


}
