package org.kuali.hr.lm.accrual.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.accrual.AccrualCategoryRule;

public interface AccrualCategoryRuleDao {
	
    public List <AccrualCategoryRule> getActiveAccrualCategoryRules(String accrualCategoryId);
    
    public AccrualCategoryRule getAccrualCategoryRule(String lmAccrualCategoryRuleId);
    
    public List <AccrualCategoryRule> getActiveRulesForAccrualCategoryId(String accrualCategoryId, Date asOfDate);
    
    public List <AccrualCategoryRule> getInActiveRulesForAccrualCategoryId(String accrualCategoryId, Date asOfDate);

}
