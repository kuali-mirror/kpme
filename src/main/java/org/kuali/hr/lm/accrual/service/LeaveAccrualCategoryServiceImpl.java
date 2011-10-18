package org.kuali.hr.lm.accrual.service;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.accrual.LeaveAccrualCategory;
import org.kuali.hr.lm.accrual.dao.LeaveAccrualCategoryDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.util.TkConstants;

public class LeaveAccrualCategoryServiceImpl implements LeaveAccrualCategoryService {

	private static final Logger LOG = Logger.getLogger(LeaveAccrualCategoryServiceImpl.class);
	private LeaveAccrualCategoryDao leaveAccrualCategoryDao;

	public LeaveAccrualCategoryDao getLeaveAccrualCategoryDao() {
		return leaveAccrualCategoryDao;
	}

	public void setLeaveAccrualCategoryDao(
			LeaveAccrualCategoryDao leaveAccrualCategoryDao) {
		this.leaveAccrualCategoryDao = leaveAccrualCategoryDao;
	}

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public LeaveAccrualCategory getLeaveAccrualCategory(Long lmAccrualCategoryId) {
		return getLeaveAccrualCategoryDao().getLeaveAccrualCategory(lmAccrualCategoryId);
	}
   

}
