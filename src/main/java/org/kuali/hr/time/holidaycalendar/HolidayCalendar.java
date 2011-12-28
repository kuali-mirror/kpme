package org.kuali.hr.time.holidaycalendar;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class HolidayCalendar extends PersistableBusinessObjectBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hrHolidayCalendarId;
	private String holidayCalendarGroup;
	private String descr;
	private boolean active = true;
	
	private List<HolidayCalendarDateEntry> dateEntries = new ArrayList<HolidayCalendarDateEntry>();
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public String getHrHolidayCalendarId() {
		return hrHolidayCalendarId;
	}


	public void setHrHolidayCalendarId(String hrHolidayCalendarId) {
		this.hrHolidayCalendarId = hrHolidayCalendarId;
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


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


}
