package org.kuali.hr.time.paycalendar;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class PayCalendar extends PersistableBusinessObjectBase {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private Long payCalendarId;
	private String calendarGroup;
	private String chart;

	private Date beginDate;
	private Time beginTime;

	private Date endDate;
	private Time endTime;

	private Date initiateDate;
	private Time initiateTime;

	private Date endPayPeriodDate;
	private Time endPayPeriodTime;

	private Date employeeApprovalDate;
	private Time employeeApprovalTime;

	private Date supervisorApprovalDate;
	private Time supervisorApprovalTime;

	private List<PayCalendarDates> payCalendarDates = new LinkedList<PayCalendarDates>();

	public PayCalendar() {

	}

	public Long getPayCalendarId() {
		return payCalendarId;
	}

	public void setPayCalendarId(Long payCalendarId) {
		this.payCalendarId = payCalendarId;
	}

	public String getCalendarGroup() {
		return calendarGroup;
	}

	public void setCalendarGroup(String calendarGroup) {
		this.calendarGroup = calendarGroup;
	}

	public String getChart() {
		return chart;
	}

	public void setChart(String chart) {
		this.chart = chart;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Time getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Time beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Date getInitiateDate() {
		return initiateDate;
	}

	public void setInitiateDate(Date initiateDate) {
		this.initiateDate = initiateDate;
	}

	public Time getInitiateTime() {
		return initiateTime;
	}

	public void setInitiateTime(Time initiateTime) {
		this.initiateTime = initiateTime;
	}

	public Date getEndPayPeriodDate() {
		return endPayPeriodDate;
	}

	public void setEndPayPeriodDate(Date endPayPeriodDate) {
		this.endPayPeriodDate = endPayPeriodDate;
	}

	public Time getEndPayPeriodTime() {
		return endPayPeriodTime;
	}

	public void setEndPayPeriodTime(Time endPayPeriodTime) {
		this.endPayPeriodTime = endPayPeriodTime;
	}

	public Date getEmployeeApprovalDate() {
		return employeeApprovalDate;
	}

	public void setEmployeeApprovalDate(Date employeeApprovalDate) {
		this.employeeApprovalDate = employeeApprovalDate;
	}

	public Time getEmployeeApprovalTime() {
		return employeeApprovalTime;
	}

	public void setEmployeeApprovalTime(Time employeeApprovalTime) {
		this.employeeApprovalTime = employeeApprovalTime;
	}

	public Date getSupervisorApprovalDate() {
		return supervisorApprovalDate;
	}

	public void setSupervisorApprovalDate(Date supervisorApprovalDate) {
		this.supervisorApprovalDate = supervisorApprovalDate;
	}

	public Time getSupervisorApprovalTime() {
		return supervisorApprovalTime;
	}

	public void setSupervisorApprovalTime(Time supervisorApprovalTime) {
		this.supervisorApprovalTime = supervisorApprovalTime;
	}

	public List<PayCalendarDates> getPayCalendarDates() {
		return payCalendarDates;
	}

	public void setPayCalendarDates(List<PayCalendarDates> payCalendarDates) {
		this.payCalendarDates = payCalendarDates;
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}
}
