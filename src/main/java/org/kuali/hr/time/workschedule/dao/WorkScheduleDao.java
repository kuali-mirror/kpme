package org.kuali.hr.time.workschedule.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.workschedule.WorkSchedule;

public interface WorkScheduleDao {
	public void saveOrUpdate(WorkSchedule workSchedule);
	public void saveOrUpdate(List<WorkSchedule> workSchedules);
    public WorkSchedule getWorkSchedule(Long workSchedule, Date asOfDate);
}
