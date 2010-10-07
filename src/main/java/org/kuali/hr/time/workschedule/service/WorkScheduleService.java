package org.kuali.hr.time.workschedule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.workschedule.WorkSchedule;

public interface WorkScheduleService {
	
	public void saveOrUpdate(WorkSchedule workSchedule);
	
	public void saveOrUpdate(List<WorkSchedule> workSchedules);
	
	public List<WorkSchedule> getWorkSchedules(String principalId, String department, Long workArea, Date asOfDate);

}
