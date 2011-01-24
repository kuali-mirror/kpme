package org.kuali.hr.time.accrual.service;

import java.sql.Date;

import org.kuali.hr.time.accrual.AccrualCategory;

public interface AccrualCategoryService {

    public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate);
    public void saveOrUpdate(AccrualCategory accrualCategory);
}
