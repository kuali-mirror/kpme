package org.kuali.hr.time.accrual.dao;

import java.sql.Date;

import org.kuali.hr.time.accrual.AccrualCategory;

public interface AccrualCategoryDao {

    public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate);
    public void saveOrUpdate(AccrualCategory accrualCategory);
}
