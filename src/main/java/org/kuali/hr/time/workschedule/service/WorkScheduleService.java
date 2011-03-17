package org.kuali.hr.time.workschedule.service;

import org.kuali.hr.time.workschedule.WorkSchedule;
import org.kuali.hr.time.workschedule.WorkScheduleEntry;

import java.sql.Date;
import java.util.List;

public interface WorkScheduleService {

	public void saveOrUpdate(WorkSchedule workSchedule);

	public void saveOrUpdate(List<WorkSchedule> workSchedules);

    public WorkSchedule getWorkSchedule(Long workScheduleId, Date asOfDate);

    /**
     * TODO - ALLEN - COMMENT THIS so we know what this does.
     *
     */
	public List<WorkScheduleEntry> getWorkSchedEntries(WorkSchedule workSchedule, java.util.Date beginDateTime, java.util.Date endDateTime);

}
