package org.kuali.hr.time.workschedule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.workschedule.WorkSchedule;
import org.kuali.hr.time.workschedule.WorkScheduleEntry;

public interface WorkScheduleService {
	
	public void saveOrUpdate(WorkSchedule workSchedule);
	
	public void saveOrUpdate(List<WorkSchedule> workSchedules);
	
	public List<WorkSchedule> getWorkSchedules(String principalId, String department, Long workArea, Date asOfDate);
	
	public List<WorkScheduleEntry> getWorkSchedEntries(WorkSchedule workSchedule, java.util.Date beginDateTime, java.util.Date endDateTime);

}
