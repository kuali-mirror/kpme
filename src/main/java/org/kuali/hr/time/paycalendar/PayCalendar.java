package org.kuali.hr.time.paycalendar;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTimeConstants;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class PayCalendar extends PersistableBusinessObjectBase {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private Long payCalendarId;
	private String calendarGroup;

	private String flsaBeginDay;
	private Time flsaBeginTime;
	private int flsaBeginDayConstant = -1;
	
	private List<PayCalendarEntries> payCalendarEntries = new ArrayList<PayCalendarEntries>();

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

	public List<PayCalendarEntries> getPayCalendarEntries() {
		return payCalendarEntries;
	}

	public void setPayCalendarEntries(List<PayCalendarEntries> payCalendarEntries) {
		this.payCalendarEntries = payCalendarEntries;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}

	public String getFlsaBeginDay() {
		return flsaBeginDay;
	}

	public void setFlsaBeginDay(String flsaBeginDay) {
		this.flsaBeginDay = flsaBeginDay;
		this.setFlsaBeinDayConstant(flsaBeginDay);
	}
	
	/**
	 * This method sets a constant matching those listed in
	 * org.joda.time.DateTimeConstants for day comparisons.
	 * 
	 * Currently this is 'hard-coded' to be English specific, it would 
	 * be trivial to change and support more than one language/day naming 
	 * convention.
	 *  
	 * @param flsaBeginDay
	 */
	private void setFlsaBeinDayConstant(String day) {
		if (!StringUtils.isEmpty(day)) {
			day = day.toLowerCase().trim();
			
			if (day.startsWith("m")) {
				this.flsaBeginDayConstant = DateTimeConstants.MONDAY;
			} else if (day.startsWith("tu")) {
				this.flsaBeginDayConstant = DateTimeConstants.TUESDAY;
			} else if (day.startsWith("w")) {
				this.flsaBeginDayConstant = DateTimeConstants.WEDNESDAY;
			} else if (day.startsWith("th")) {
				this.flsaBeginDayConstant = DateTimeConstants.THURSDAY;
			} else if (day.startsWith("f")) {
				this.flsaBeginDayConstant = DateTimeConstants.FRIDAY;
			} else if (day.startsWith("sa")) {
				this.flsaBeginDayConstant = DateTimeConstants.SATURDAY;
			} else if (day.startsWith("su")) {
				this.flsaBeginDayConstant = DateTimeConstants.SUNDAY;
			}
		}		
	}

	public Time getFlsaBeginTime() {
		return flsaBeginTime;
	}

	public void setFlsaBeginTime(Time flsaBeginTime) {
		this.flsaBeginTime = flsaBeginTime;
	}

	/**
	 * org.joda.time.DateTimeConstants.MONDAY 
	 * ...
	 * org.joda.time.DateTimeConstants.SUNDAY
	 * 
	 * @return an int representing the FLSA start day, sourced from
	 * org.joda.time.DateTimeConstants in the interval [1,7].
	 */
	public int getFlsaBeginDayConstant() {
		if (flsaBeginDayConstant < 0) {
			this.setFlsaBeinDayConstant(this.getFlsaBeginDay());
		}
		return flsaBeginDayConstant;
	}
}
