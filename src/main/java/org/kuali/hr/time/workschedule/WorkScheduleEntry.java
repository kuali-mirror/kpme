package org.kuali.hr.time.workschedule;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class WorkScheduleEntry extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long hrWorkScheduleEntryId;
	private Long hrWorkScheduleId;
	private Long calDayId;
	private Integer dayOfPeriodId;
	private Integer regHours;
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
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

	public Long getCalDayId() {
		return calDayId;
	}

	public void setCalDayId(Long calDayId) {
		this.calDayId = calDayId;
	}

	public Integer getDayOfPeriodId() {
		return dayOfPeriodId;
	}

	public void setDayOfPeriodId(Integer dayOfPeriodId) {
		this.dayOfPeriodId = dayOfPeriodId;
	}

	public Integer getRegHours() {
		return regHours;
	}

	public void setRegHours(Integer regHours) {
		this.regHours = regHours;
	}

}
