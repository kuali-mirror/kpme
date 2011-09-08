package org.kuali.hr.time.accrual.service;

import org.apache.log4j.Logger;
import org.kuali.hr.time.accrual.AccrualCategory;
import org.kuali.hr.time.accrual.dao.AccrualCategoryDao;

import java.sql.Date;
import java.util.List;

public class AccrualCategoryServiceImpl implements AccrualCategoryService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(AccrualCategoryServiceImpl.class);
	private AccrualCategoryDao accrualCategoryDao;
	public AccrualCategoryServiceImpl() {
	}

	@Override
	public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate) {
		return accrualCategoryDao.getAccrualCategory(accrualCategory, asOfDate);
	}

	@Override
	public void saveOrUpdate(AccrualCategory accrualCategory) {
		accrualCategoryDao.saveOrUpdate(accrualCategory);
	}

	public AccrualCategoryDao getAccrualCategoryDao() {
		return accrualCategoryDao;
	}

	public void setAccrualCategoryDao(AccrualCategoryDao accrualCategoryDao) {
		this.accrualCategoryDao = accrualCategoryDao;
	}

	@Override
	public AccrualCategory getAccrualCategory(Long lmAccrualCategoryId) {
		return accrualCategoryDao.getAccrualCategory(lmAccrualCategoryId);
	}

	@Override	
	public List <AccrualCategory> getActiveAccrualCategories(Date asOfDate){
		return accrualCategoryDao.getActiveAccrualCategories(asOfDate);
	}
}
