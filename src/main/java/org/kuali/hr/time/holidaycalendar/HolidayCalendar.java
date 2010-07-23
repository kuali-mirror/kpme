package org.kuali.hr.time.holidaycalendar;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class HolidayCalendar extends PersistableBusinessObjectBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long holidayCalendarId;
	private String holidayCalendarGroup;
	private String descr;
	
	private List<HolidayCalendarDateEntry> dateEntries = new ArrayList<HolidayCalendarDateEntry>();
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public Long getHolidayCalendarId() {
		return holidayCalendarId;
	}


	public void setHolidayCalendarId(Long holidayCalendarId) {
		this.holidayCalendarId = holidayCalendarId;
	}


	public String getHolidayCalendarGroup() {
		return holidayCalendarGroup;
	}


	public void setHolidayCalendarGroup(String holidayCalendarGroup) {
		this.holidayCalendarGroup = holidayCalendarGroup;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
	}


	public List<HolidayCalendarDateEntry> getDateEntries() {
		return dateEntries;
	}


	public void setDateEntries(List<HolidayCalendarDateEntry> dateEntries) {
		this.dateEntries = dateEntries;
	}

}
