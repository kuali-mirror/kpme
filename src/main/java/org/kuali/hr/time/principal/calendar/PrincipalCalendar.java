package org.kuali.hr.time.principal.calendar;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class PrincipalCalendar extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String principalId;
	private String leaveCalendar;
	private String pyCalendarGroup;
	private String holidayCalendarGroup;
	private String timezone;
	
	private PayCalendar payCalendar;
	private HolidayCalendar holidayCalendar;
	private Person person;

	@SuppressWarnings("rawtypes")
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
		person = KIMServiceLocator.getPersonService().getPerson(this.principalId);
	}

	public String getName() {
		 if (person == null) {
	            person = KIMServiceLocator.getPersonService().getPerson(this.principalId);
	    }
	    return (person != null) ? person.getName() : "";
	}

	public String getPyCalendarGroup() {
		return pyCalendarGroup;
	}

	public void setPyCalendarGroup(String pyCalendarGroup) {
		this.pyCalendarGroup = pyCalendarGroup;
	}

	public String getHolidayCalendarGroup() {
		return holidayCalendarGroup;
	}

	public void setHolidayCalendarGroup(String holidayCalendarGroup) {
		this.holidayCalendarGroup = holidayCalendarGroup;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
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

	@Override
	protected String getUniqueKey() {
		return principalId + "_" + pyCalendarGroup + "_" + holidayCalendarGroup;
	}

	@Override
	public Long getId() {
		return 1L;
	}

	@Override
	public void setId(Long id) {
	}

	public String getLeaveCalendar() {
		return leaveCalendar;
	}

	public void setLeaveCalendar(String leaveCalendar) {
		this.leaveCalendar = leaveCalendar;
	}
}
