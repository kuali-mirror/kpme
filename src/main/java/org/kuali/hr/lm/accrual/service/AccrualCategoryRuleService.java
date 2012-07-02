package org.kuali.hr.lm.accrual.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;

public interface AccrualCategoryRuleService {
	
    /**
     * Fetch list of active accrual category rules by given accrual category id
     * @param accrualCategoryId
     * @return
     */
    public List <AccrualCategoryRule> getActiveAccrualCategoryRules(String accrualCategoryId);
    
    /**
     * Fetch accrual category rule by unique id
     * @param lmAccrualCategoryRuleId
     * @return
     */
    public AccrualCategoryRule getAccrualCategoryRule(String lmAccrualCategoryRuleId);
    
    /**
     * Fetch the accrual category rule applies for the given date and accrualCategory
     */
    public AccrualCategoryRule getAccrualCategoryRuleForDate(AccrualCategory accrualCategory, Date currentDate, Date serviceDate);

}
