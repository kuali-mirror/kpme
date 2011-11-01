package org.kuali.hr.lm.timeoff.service;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.lm.timeoff.dao.SystemScheduledTimeOffDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.util.TkConstants;

public class SystemScheduledTimeOffServiceImpl implements SystemScheduledTimeOffService {

	private static final Logger LOG = Logger.getLogger(SystemScheduledTimeOffServiceImpl.class);
	private SystemScheduledTimeOffDao systemScheduledTimeOffDao;

	public SystemScheduledTimeOffDao getSystemScheduledTimeOffDao() {
		return systemScheduledTimeOffDao;
	}

	public void setSystemScheduledTimeOffDao(
			SystemScheduledTimeOffDao systemScheduledTimeOffDao) {
		this.systemScheduledTimeOffDao = systemScheduledTimeOffDao;
	}

	@Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public SystemScheduledTimeOff getSystemScheduledTimeOff(Long lmSystemScheduledTimeOffId) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOff(lmSystemScheduledTimeOffId);
	}
   
}
