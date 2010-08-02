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

	public List<PayCalendarDates> getPayCalendarDates() {
		return payCalendarDates;
	}

	public void setPayCalendarDates(List<PayCalendarDates> payCalendarDates) {
		this.payCalendarDates = payCalendarDates;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}
}
