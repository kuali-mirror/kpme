package org.kuali.hr.lm.timeoff.service;

import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;

public interface SystemScheduledTimeOffService {
    
    /**
     * Fetch SystemScheduledTimeOff by id
     * @param lmSystemScheduledTimeOffId
     * @return
     */
    public SystemScheduledTimeOff getSystemScheduledTimeOff(Long lmSystemScheduledTimeOffId);
}
