package org.kuali.hr.time.holidaycalendar;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class HolidayCalendarDateEntry extends PersistableBusinessObjectBase {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long holidayCalendarDateId;
	private Date holidayDate;
	private String holidayDescr;
	private Long holidayCalendarId;
	private BigDecimal holidayHours;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public Long getHolidayCalendarDateId() {
		return holidayCalendarDateId;
	}


	public void setHolidayCalendarDateId(Long holidayCalendarDateId) {
		this.holidayCalendarDateId = holidayCalendarDateId;
	}


	public Date getHolidayDate() {
		return holidayDate;
	}


	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}


	public String getHolidayDescr() {
		return holidayDescr;
	}


	public void setHolidayDescr(String holidayDescr) {
		this.holidayDescr = holidayDescr;
	}


	public Long getHolidayCalendarId() {
		return holidayCalendarId;
	}


	public void setHolidayCalendarId(Long holidayCalendarId) {
		this.holidayCalendarId = holidayCalendarId;
	}


	public BigDecimal getHolidayHours() {
		return holidayHours;
	}


	public void setHolidayHours(BigDecimal holidayHours) {
		this.holidayHours = holidayHours;
	}

}
