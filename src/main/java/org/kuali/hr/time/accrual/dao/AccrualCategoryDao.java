package org.kuali.hr.time.accrual.dao;

import org.kuali.hr.time.accrual.AccrualCategory;

import java.sql.Date;
import java.util.List;

public interface AccrualCategoryDao {

    public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate);
    public void saveOrUpdate(AccrualCategory accrualCategory);
    public AccrualCategory getAccrualCategory(Long lmAccrualCategoryId);
    public List<AccrualCategory> getActiveAccrualCategories(Date asOfDate);
}
