package org.kuali.hr.time.accrual.service;

import java.sql.Date;

import org.kuali.hr.time.accrual.AccrualCategory;

public interface AccrualCategoryService {

	/**
	 * Get an AccrualCategory as of a particular date
	 * @param accrualCategory
	 * @param asOfDate
	 * @return
	 */
    public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate);
    /**
     * Save or Update an accrual category
     * @param accrualCategory
     */
    public void saveOrUpdate(AccrualCategory accrualCategory);
    
    public AccrualCategory getAccrualCategory(Long laAccrualCategoryId);
}
