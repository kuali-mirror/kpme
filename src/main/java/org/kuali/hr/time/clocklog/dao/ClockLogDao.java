package org.kuali.hr.time.clocklog.dao;

import java.util.List;

import org.kuali.hr.time.clocklog.ClockLog;

public interface ClockLogDao {

    public void saveOrUpdate(ClockLog clockLog);
    public void saveOrUpdate(List<ClockLog> clockLogList);
    public ClockLog getLastClockLog(String principalId);
}
