package org.kuali.hr.time.clocklog.service;

import org.kuali.hr.time.clock.web.ClockActionForm;
import org.kuali.hr.time.clocklog.ClockLog;

public interface ClockLogService {

    public void saveClockLog(ClockLog clockLog);
    public ClockLog getLastClockLog(String principalId);
	public ClockLog saveClockAction(ClockActionForm form);
    
}
