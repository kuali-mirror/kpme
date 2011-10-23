package org.kuali.hr.lm.leaveplan;

import java.util.LinkedHashMap;

import org.kuali.hr.time.HrBusinessObject;

public class LeavePlan extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long lmLeavePlanId;
	private String leavePlan;
	private String descr;
	private String calendarYearStart;
	private Boolean history;
	
	public Long getLmLeavePlanId() {
		return lmLeavePlanId;
	}

	public void setLmLeavePlanId(Long lmLeavePlanId) {
		this.lmLeavePlanId = lmLeavePlanId;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCalendarYearStart() {
		return calendarYearStart;
	}

	public void setCalendarYearStart(String calendarYearStart) {
		this.calendarYearStart = calendarYearStart;
	}
	
	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	@Override
	protected String getUniqueKey() {
		return leavePlan;
	}

	@Override
	public Long getId() {
		return getLmLeavePlanId();
	}

	@Override
	public void setId(Long id) {
		setLmLeavePlanId(id);
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

}
