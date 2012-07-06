package org.kuali.hr.time.workschedule.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workschedule.WorkSchedule;
import org.kuali.hr.time.workschedule.WorkScheduleEntry;
import org.kuali.hr.time.workschedule.dao.WorkScheduleDao;

public class WorkScheduleServiceImpl implements WorkScheduleService {

	private WorkScheduleDao workScheduleDao = null;

    @Override
    public WorkSchedule getWorkSchedule(Long workSchedule, Date asOfDate) {
        return workScheduleDao.getWorkSchedule(workSchedule, asOfDate);
    }

    public List<WorkScheduleEntry> getWorkSchedEntries(WorkSchedule workSchedule, java.util.Date beginDateTime, java.util.Date endDateTime){
		List<WorkScheduleEntry> lstWorkScheduleEntries = new ArrayList<WorkScheduleEntry>();
		//increment to the appropriate range based on the beginDate and endDate
		//cycle through and return an appropriate list of workschedule entries based on
		//the beginDateTime, endDatetime based in
		DateTime workScheduleEffectiveDate = new DateTime(workSchedule.getEffectiveDate());
		DateTime endWorkScheduleEffectiveDate = workScheduleEffectiveDate.plusDays(TkConstants.LENGTH_OF_WORK_SCHEDULE);

		Interval payPeriodInterval = new Interval(beginDateTime.getTime(), endDateTime.getTime());
		//create interval for work schedule effdt + TkConstants.LENGTH_OF_WORK_SCHEDULE days
		Interval workScheduleInterval = new Interval(workScheduleEffectiveDate,endWorkScheduleEffectiveDate);
		//if before pay period interval then return an empty list back
		if(payPeriodInterval.isBefore(workScheduleInterval)){
			return lstWorkScheduleEntries;
		}

		//determine the starting index of work schedule entries for the pay period
		int indexOfWorkSchedule = 0;
		Interval workSchedGap = payPeriodInterval.gap(workScheduleInterval);
		if(workSchedGap!= null){
			int days = TKUtils.convertMillisToWholeDays(workSchedGap.getEndMillis()-workSchedGap.getStartMillis());
			if(days < TkConstants.LENGTH_OF_WORK_SCHEDULE){
				indexOfWorkSchedule = days;
			}
			//determine offset
			indexOfWorkSchedule = days % TkConstants.LENGTH_OF_WORK_SCHEDULE;
		} else{
			Interval workSchedOverlap = payPeriodInterval.overlap(workScheduleInterval);
			if(workSchedOverlap != null){
				int days = TKUtils.convertMillisToWholeDays(workSchedOverlap.toDurationMillis());

				indexOfWorkSchedule = TkConstants.LENGTH_OF_WORK_SCHEDULE - days;
			}
		}

		int numberOfDaysInPayPeriod = TKUtils.convertMillisToWholeDays(payPeriodInterval.toDurationMillis());
		for(int i = 0 ;i<numberOfDaysInPayPeriod;i++){
			indexOfWorkSchedule += i;
			if(indexOfWorkSchedule >= TkConstants.LENGTH_OF_WORK_SCHEDULE){
				indexOfWorkSchedule = 0;
			}
			//this assumes sorted by index of day
			lstWorkScheduleEntries.add(workSchedule.getWorkScheduleEntries().get(indexOfWorkSchedule));
		}
		return lstWorkScheduleEntries;
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
