package org.kuali.hr.lm.accrual.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.springframework.cache.annotation.Cacheable;

public interface AccrualCategoryService {
    
	/**
	 * Get an AccrualCategory as of a particular date
	 * @param accrualCategory
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= AccrualCategory.CACHE_NAME, key="'accrualCategory=' + #p0 + '|' + 'asOfDate=' + #p1")
    public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate);
    /**
     * Save or Update an accrual category
     * @param accrualCategory
     */
   // public void saveOrUpdate(AccrualCategory accrualCategory);
    
    /**
     * Fetch accrual category by unique id
     * @param lmAccrualCategoryId
     * @return
     */
    @Cacheable(value= AccrualCategory.CACHE_NAME, key="'accrualCategoryId=' + #p0")
    public AccrualCategory getAccrualCategory(String lmAccrualCategoryId);
    
    /**
     * Fetch list of active accrual categories as of a particular date
     * @param asOfDate
     * @return
     */
    @Cacheable(value= AccrualCategory.CACHE_NAME, key="'asOfDate=' + #p0")
    public List <AccrualCategory> getActiveAccrualCategories(Date asOfDate);
    
    /**
     * Fetch list of active accrual categories with given leavePlan and date
     * @param leavePlan
     * @param asOfDate
     * @return List <AccrualCategory>
     */
   public List <AccrualCategory> getActiveAccrualCategoriesForLeavePlan(String leavePlan, Date asOfDate);
     
    public List <AccrualCategory> getActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, Date asOfDate);

    
    public List <AccrualCategory> getInActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, Date asOfDate);
}
