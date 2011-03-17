package org.kuali.hr.time.workschedule.service;

import org.kuali.hr.time.workschedule.WorkScheduleAssignment;

import java.sql.Date;
import java.util.List;

public interface WorkScheduleAssignmentService {
    public void saveOrUpdate(WorkScheduleAssignment wsa);
    public List<WorkScheduleAssignment> getWorkScheduleAssignments(String principalId, String dept, Long workArea, Date asOfDate);
}
