package org.kuali.hr.time.holidaycalendar;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashMap;

public class HolidayCalendarDateEntry extends PersistableBusinessObjectBase {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long hrHolidayCalendarDateId;
	private Date holidayDate;
	private String holidayDescr;
	private Long hrHolidayCalendarId;
	private BigDecimal holidayHours;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public Long getHrHolidayCalendarDateId() {
		return hrHolidayCalendarDateId;
	}


	public void setHrHolidayCalendarDateId(Long hrHolidayCalendarDateId) {
		this.hrHolidayCalendarDateId = hrHolidayCalendarDateId;
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


	public Long getHrHolidayCalendarId() {
		return hrHolidayCalendarId;
	}


	public void setHrHolidayCalendarId(Long hrHolidayCalendarId) {
		this.hrHolidayCalendarId = hrHolidayCalendarId;
	}


	public BigDecimal getHolidayHours() {
		return holidayHours;
	}


	public void setHolidayHours(BigDecimal holidayHours) {
		this.holidayHours = holidayHours;
	}

}
