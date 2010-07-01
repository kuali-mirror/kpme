package org.kuali.hr.time.clocklog.service;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.clocklog.dao.ClockLogDao;

public class ClockLogServiceImpl implements ClockLogService {

    private ClockLogDao clockLogDao;
    
    public ClockLogServiceImpl() {
    }
    
    @Override
    public void saveClockAction(ClockLog clockLog) {
	clockLogDao.saveOrUpdate(clockLog);
    }

    public void setClockLogDao(ClockLogDao clockLogDao) {
        this.clockLogDao = clockLogDao;
    }

    @Override
    public ClockLog getLastClockLog(String principalId) {
	return clockLogDao.getLastClockLog(principalId);
    }
    
    

}
