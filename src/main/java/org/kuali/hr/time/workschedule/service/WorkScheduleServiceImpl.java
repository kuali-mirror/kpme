package org.kuali.hr.time.workschedule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.workschedule.WorkSchedule;
import org.kuali.hr.time.workschedule.dao.WorkScheduleDao;

public class WorkScheduleServiceImpl implements WorkScheduleService {
	
	private WorkScheduleDao workScheduleDao = null;

	@Override
	public List<WorkSchedule> getWorkSchedules(String principalId, String department, Long workArea, Date asOfDate) {
		List<WorkSchedule> list = null;
		// wild-cards, 2^3 again ...
		
		// principal, dept, workArea
		list = workScheduleDao.findWorkSchedules(principalId, department, workArea, asOfDate);
		
		// principal, dept, -1
		if (list.isEmpty())
			list = workScheduleDao.findWorkSchedules(principalId, department, -1L, asOfDate);
		
		// principal, *, workArea
		if (list.isEmpty())
			list = workScheduleDao.findWorkSchedules(principalId, "*", workArea, asOfDate);
		
		// principal, *, -1
		if (list.isEmpty())
			list = workScheduleDao.findWorkSchedules(principalId, "*", -1L, asOfDate);
		
		// *, dept, workArea
		if (list.isEmpty())
			list = workScheduleDao.findWorkSchedules("*", department, workArea, asOfDate);
		
		// *, dept, -1
		if (list.isEmpty())
			list = workScheduleDao.findWorkSchedules("*", department, -1L, asOfDate);
		
		// *, *, workArea
		if (list.isEmpty())
			list = workScheduleDao.findWorkSchedules("*", "*", workArea, asOfDate);
		
		// *, *, -1
		if (list.isEmpty())
			list = workScheduleDao.findWorkSchedules("*", "*", -1L, asOfDate);
		
		return list;
	}

	@Override
	public void saveOrUpdate(WorkSchedule workSchedule) {
		workScheduleDao.saveOrUpdate(workSchedule);
	}

	@Override
	public void saveOrUpdate(List<WorkSchedule> workSchedules) {
		workScheduleDao.saveOrUpdate(workSchedules);
	}

	public void setWorkScheduleDao(WorkScheduleDao workScheduleDao) {
		this.workScheduleDao = workScheduleDao;
	}

}
