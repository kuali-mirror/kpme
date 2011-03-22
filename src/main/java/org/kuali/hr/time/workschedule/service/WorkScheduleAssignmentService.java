package org.kuali.hr.time.workschedule.service;

import org.kuali.hr.time.workschedule.WorkScheduleAssignment;

import java.sql.Date;
import java.util.List;

public interface WorkScheduleAssignmentService {
	/**
	 * 
	 * @param wsa
	 */
    public void saveOrUpdate(WorkScheduleAssignment wsa);
    /**
     * 
     * @param principalId
     * @param dept
     * @param workArea
     * @param asOfDate
     * @return
     */
    public List<WorkScheduleAssignment> getWorkScheduleAssignments(String principalId, String dept, Long workArea, Date asOfDate);
}
