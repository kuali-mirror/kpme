package org.kuali.hr.time.holidaycalendar;

import java.sql.Date;
import java.util.LinkedHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.rice.core.jaxb.SqlDateAdapter;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

@XmlAccessorType(value = XmlAccessType.NONE)
public class HolidayCalendarDateEntry extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private Long holidayCalendarDateId;

	@XmlJavaTypeAdapter(value=SqlDateAdapter.class, type=Date.class)
	@XmlElement
	private Date holidayDate;

	@XmlElement
	private String holidayDescr;

	@XmlElement
	private Long holidayCalendarId;

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

}
