package org.kuali.hr.lm.accrual.dao;

import java.util.List;

import org.kuali.hr.lm.accrual.AccrualCategoryRule;

public interface AccrualCategoryRuleDao {
	
    public List <AccrualCategoryRule> getActiveAccrualCategoryRules(String accrualCategoryId);
    
    public AccrualCategoryRule getAccrualCategoryRule(String lmAccrualCategoryRuleId);

}
