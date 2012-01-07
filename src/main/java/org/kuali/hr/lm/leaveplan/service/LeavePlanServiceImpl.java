package org.kuali.hr.lm.leaveplan.service;

import java.sql.Date;

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
   

}
