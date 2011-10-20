package org.kuali.hr.lm.leavecode.service;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.leavecode.dao.LeaveCodeDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.util.TkConstants;

public class LeaveCodeServiceImpl implements LeaveCodeService {

	private static final Logger LOG = Logger.getLogger(LeaveCodeServiceImpl.class);
	private LeaveCodeDao leaveCodeDao;


    public LeaveCodeDao getLeaveCodeDao() {
		return leaveCodeDao;
	}


	public void setLeaveCodeDao(LeaveCodeDao leaveCodeDao) {
		this.leaveCodeDao = leaveCodeDao;
	}


	@Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public LeaveCode getLeaveCode(Long lmLeaveCodeId) {
		return getLeaveCodeDao().getLeaveCode(lmLeaveCodeId);
	}
   

}
