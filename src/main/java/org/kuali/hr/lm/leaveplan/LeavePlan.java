package org.kuali.hr.lm.leaveplan;

import java.util.LinkedHashMap;

import org.kuali.hr.time.HrBusinessObject;

public class LeavePlan extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lmLeavePlanId;
	private String leavePlan;
	private String descr;
	private String calendarYearStart;
	private Boolean history;
	private String planningMonths;
	
	public String getPlanningMonths() {
		return planningMonths;
	}

	public void setPlanningMonths(String planningMonths) {
		this.planningMonths = planningMonths;
	}

	public String getLmLeavePlanId() {
		return lmLeavePlanId;
	}

	public void setLmLeavePlanId(String lmLeavePlanId) {
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
	public String getId() {
		return getLmLeavePlanId();
	}

	@Override
	public void setId(String id) {
		setLmLeavePlanId(id);
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}


}
