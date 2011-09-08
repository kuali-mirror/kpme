package org.kuali.hr.time.accrual.service;

import org.kuali.hr.time.accrual.AccrualCategory;

import java.sql.Date;
import java.util.List;

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
    
    public AccrualCategory getAccrualCategory(Long lmAccrualCategoryId);
    
    public List <AccrualCategory> getActiveAccrualCategories(Date asOfDate);
}
