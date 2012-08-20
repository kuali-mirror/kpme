package org.kuali.hr.time.workschedule;

import java.sql.Time;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class WorkScheduleEntry extends PersistableBusinessObjectBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long hrWorkScheduleEntryId;
	private Long hrWorkSchedule;
	private Long indexOfDay;
	private Time beginTime;
	private Time endTime;

	public Long getIndexOfDay() {
		return indexOfDay;
	}


	public void setIndexOfDay(Long indexOfDay) {
		this.indexOfDay = indexOfDay;
	}


	public Time getBeginTime() {
		return beginTime;
	}


	public void setBeginTime(Time beginTime) {
		this.beginTime = beginTime;
	}


	public Time getEndTime() {
		return endTime;
	}


	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}


	public Long getHrWorkScheduleEntryId() {
		return hrWorkScheduleEntryId;
	}


	public void setHrWorkScheduleEntryId(Long hrWorkScheduleEntryId) {
		this.hrWorkScheduleEntryId = hrWorkScheduleEntryId;
	}


	public Long getHrWorkSchedule() {
		return hrWorkSchedule;
	}


	public void setHrWorkSchedule(Long hrWorkSchedule) {
		this.hrWorkSchedule = hrWorkSchedule;
	}
}
