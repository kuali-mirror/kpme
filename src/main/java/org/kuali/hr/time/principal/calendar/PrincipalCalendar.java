package org.kuali.hr.time.principal.calendar;

import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class PrincipalCalendar extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String principalId;
	private String name;
	private String calendarGroup;
	private String holidayCalendarGroup;
	private Date effectiveDate;
	private Timestamp timestamp;
	private Boolean active;
	
	private PayCalendar payCalendar;
	private HolidayCalendar holidayCalendar;
	private Person person;

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCalendarGroup() {
		return calendarGroup;
	}

	public void setCalendarGroup(String calendarGroup) {
		this.calendarGroup = calendarGroup;
	}

	public String getHolidayCalendarGroup() {
		return holidayCalendarGroup;
	}

	public void setHolidayCalendarGroup(String holidayCalendarGroup) {
		this.holidayCalendarGroup = holidayCalendarGroup;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public PayCalendar getPayCalendar() {
		return payCalendar;
	}

	public void setPayCalendar(PayCalendar payCalendar) {
		this.payCalendar = payCalendar;
	}

	public HolidayCalendar getHolidayCalendar() {
		return holidayCalendar;
	}

	public void setHolidayCalendar(HolidayCalendar holidayCalendar) {
		this.holidayCalendar = holidayCalendar;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
