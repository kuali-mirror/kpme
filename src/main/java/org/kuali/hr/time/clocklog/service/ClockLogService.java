package org.kuali.hr.time.clocklog.service;

import org.kuali.hr.time.clocklog.ClockLog;

public interface ClockLogService {

    public void saveClockAction(ClockLog clockLog);
    public ClockLog getLastClockLog(String principalId);
    
}
