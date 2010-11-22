package org.kuali.hr.time.workschedule;

import java.util.LinkedHashMap;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class WorkScheduleEntry extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long hrWorkScheduleEntryId;
	private Long hrWorkScheduleId;
	private String calendarGroup;



	private Long dayOfPeriod;
	private Long  regHours;
	private WorkSchedule workScheduleObj;
	private PayCalendar payCalendarObj;
	
	public PayCalendar getPayCalendarObj() {
		return payCalendarObj;
	}


	public void setPayCalendarObj(PayCalendar payCalendarObj) {
		this.payCalendarObj = payCalendarObj;
	}
	
	public WorkSchedule getWorkScheduleObj() {
		return workScheduleObj;
	}


	public void setWorkScheduleObj(WorkSchedule workScheduleObj) {
		this.workScheduleObj = workScheduleObj;
	}


	public Long getDayOfPeriod() {
		return dayOfPeriod;
	}


	public void setDayOfPeriod(Long dayOfPeriod) {
		this.dayOfPeriod = dayOfPeriod;
	}


	public Long getRegHours() {
		return regHours;
	}


	public void setRegHours(Long regHours) {
		this.regHours = regHours;
	}

	 
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		
		toStringMap.put("workScheduleEntryId", hrWorkScheduleEntryId);
		toStringMap.put("workScheduleId", hrWorkScheduleId);
		toStringMap.put("calendarGroup", calendarGroup);
		toStringMap.put("dayOfPeriod", dayOfPeriod);
		toStringMap.put("regHours", regHours);		
		 
		return toStringMap;
		
	}


	public Long getHrWorkScheduleEntryId() {
		return hrWorkScheduleEntryId;
	}


	public void setHrWorkScheduleEntryId(Long hrWorkScheduleEntryId) {
		this.hrWorkScheduleEntryId = hrWorkScheduleEntryId;
	}


	public Long getHrWorkScheduleId() {
		return hrWorkScheduleId;
	}


	public void setHrWorkScheduleId(Long hrWorkScheduleId) {
		this.hrWorkScheduleId = hrWorkScheduleId;
	}

	public String getCalendarGroup() {
		return calendarGroup;
	}


	public void setCalendarGroup(String calendarGroup) {
		this.calendarGroup = calendarGroup;
	}
}
