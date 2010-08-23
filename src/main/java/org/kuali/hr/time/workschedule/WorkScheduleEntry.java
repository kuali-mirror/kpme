package org.kuali.hr.time.workschedule;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class WorkScheduleEntry extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long workScheduleEntryId;
	private Long workScheduleId;
	private Long calDayId;
	private Long dayOfPeriodId;
	private Long  regHours;
	private WorkSchedule workScheduleObj;
	
	
	public WorkSchedule getWorkScheduleObj() {
		return workScheduleObj;
	}


	public void setWorkScheduleObj(WorkSchedule workScheduleObj) {
		this.workScheduleObj = workScheduleObj;
	}


	public Long getDayOfPeriodId() {
		return dayOfPeriodId;
	}


	public void setDayOfPeriodId(Long dayOfPeriodId) {
		this.dayOfPeriodId = dayOfPeriodId;
	}


	public Long getRegHours() {
		return regHours;
	}


	public void setRegHours(Long regHours) {
		this.regHours = regHours;
	}
	
	public Long getWorkScheduleEntryId() {
		return workScheduleEntryId;
	}


	public void setWorkScheduleEntryId(Long workScheduleEntryId) {
		this.workScheduleEntryId = workScheduleEntryId;
	}

 
	public Long getWorkScheduleId() {
		return workScheduleId;
	}


	public void setWorkScheduleId(Long workScheduleId) {
		this.workScheduleId = workScheduleId;
	}


	public Long getCalDayId() {
		return calDayId;
	}

	public void setCalDayId(Long calDayId) {
		this.calDayId = calDayId;
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		
		toStringMap.put("workScheduleEntryId", workScheduleEntryId);
		toStringMap.put("workScheduleId", workScheduleId);
		toStringMap.put("calDayId", calDayId);
		toStringMap.put("dayOfPeriodId", dayOfPeriodId);
		toStringMap.put("regHours", regHours);		
		 
		return toStringMap;
		
	}

	 
}
