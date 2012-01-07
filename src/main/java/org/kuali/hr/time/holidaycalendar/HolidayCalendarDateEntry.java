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
	private String hrHolidayCalendarDateId;
	private Date holidayDate;
	private String holidayDescr;
	private String hrHolidayCalendarId;
	private BigDecimal holidayHours;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public String getHrHolidayCalendarDateId() {
		return hrHolidayCalendarDateId;
	}


	public void setHrHolidayCalendarDateId(String hrHolidayCalendarDateId) {
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


	public String getHrHolidayCalendarId() {
		return hrHolidayCalendarId;
	}


	public void setHrHolidayCalendarId(String hrHolidayCalendarId) {
		this.hrHolidayCalendarId = hrHolidayCalendarId;
	}


	public BigDecimal getHolidayHours() {
		return holidayHours;
	}


	public void setHolidayHours(BigDecimal holidayHours) {
		this.holidayHours = holidayHours;
	}

}
