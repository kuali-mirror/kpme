package org.kuali.hr.lm.accrual.service;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.dao.AccrualCategoryDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.util.TkConstants;

public class AccrualCategoryServiceImpl implements AccrualCategoryService {

	private static final Logger LOG = Logger.getLogger(AccrualCategoryServiceImpl.class);
	private AccrualCategoryDao accrualCategoryDao;
	public AccrualCategoryServiceImpl() {
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
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
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public AccrualCategory getAccrualCategory(Long lmAccrualCategoryId) {
		return accrualCategoryDao.getAccrualCategory(lmAccrualCategoryId);
	}

	@Override	
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public List <AccrualCategory> getActiveAccrualCategories(Date asOfDate){
		return accrualCategoryDao.getActiveAccrualCategories(asOfDate);
	}
   

}
