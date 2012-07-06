package org.kuali.hr.time.workschedule.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.workschedule.WorkScheduleAssignment;

public interface WorkScheduleAssignmentDao {
    public void saveOrUpdate(WorkScheduleAssignment wsa);

    public List<WorkScheduleAssignment> getWorkScheduleAssignments(String principalId, String dept, Long workArea, Date asOfDate);
}
