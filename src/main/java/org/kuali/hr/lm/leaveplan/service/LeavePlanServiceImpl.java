package org.kuali.hr.lm.leaveplan.service;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.leaveplan.dao.LeavePlanDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.util.TkConstants;

public class LeavePlanServiceImpl implements LeavePlanService {

	private static final Logger LOG = Logger.getLogger(LeavePlanServiceImpl.class);
	private LeavePlanDao leavePlanDao;

 
	public LeavePlanDao getLeavePlanDao() {
		return leavePlanDao;
	}


	public void setLeavePlanDao(LeavePlanDao leavePlanDao) {
		this.leavePlanDao = leavePlanDao;
	}


	@Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public LeavePlan getLeavePlan(String lmLeavePlanId) {
		return getLeavePlanDao().getLeavePlan(lmLeavePlanId);
	}
	
	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public LeavePlan getLeavePlan(String leavePlan, Date asOfDate) {
		return getLeavePlanDao().getLeavePlan(leavePlan, asOfDate);
	}
   
	@Override
	public boolean isValidLeavePlan(String leavePlan){
		boolean valid = false;
		int count = getLeavePlanDao().getNumberLeavePlan(leavePlan);
		valid = (count > 0);
		return valid;
	}
	
	@Override
	public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, Date asOfDate) {
		 return leavePlanDao.getAllActiveLeavePlan(leavePlan, asOfDate);
	 }
	@Override
	public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, Date asOfDate) {
		 return leavePlanDao.getAllInActiveLeavePlan(leavePlan, asOfDate);
	 }
}
