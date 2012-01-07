package org.kuali.hr.lm.accrual.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.accrual.AccrualCategory;


public interface AccrualCategoryDao {
	
	/**
	 * Fetch accrual category as of a particular date
	 * @param accrualCategory
	 * @param asOfDate
	 * @return
	 */
    public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate);
    public void saveOrUpdate(AccrualCategory accrualCategory);
    /**
     * Fetch accrual category by a unique id
     * @param lmAccrualCategoryId
     * @return
     */
    public AccrualCategory getAccrualCategory(String lmAccrualCategoryId);
    /**
     * Fetch list of active accrual categories
     * @param asOfDate
     * @return
     */
    public List<AccrualCategory> getActiveAccrualCategories(Date asOfDate);
    
    /**
     * 
     * @param leavePlan
     * @param asOfDate
     * @return
     */
    public List<AccrualCategory> getActiveAccrualCategories(String leavePlan, Date asOfDate);
	
}
