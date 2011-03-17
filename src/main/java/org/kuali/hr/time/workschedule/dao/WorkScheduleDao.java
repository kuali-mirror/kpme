package org.kuali.hr.time.workschedule.dao;

import org.kuali.hr.time.workschedule.WorkSchedule;

import java.sql.Date;
import java.util.List;

public interface WorkScheduleDao {
	public void saveOrUpdate(WorkSchedule workSchedule);
	public void saveOrUpdate(List<WorkSchedule> workSchedules);
    public WorkSchedule getWorkSchedule(Long workSchedule, Date asOfDate);
}
