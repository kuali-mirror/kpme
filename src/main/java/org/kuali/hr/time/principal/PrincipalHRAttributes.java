package org.kuali.hr.time.principal;

import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class PrincipalHRAttributes extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String principalId;
	private String leaveCalendar;
	private String payCalendar;
	private String leavePlan;
	private Date serviceDate;
	private boolean fmlaEligible;
	private boolean workmansCompEligible;
	private String holidayCalendarGroup;
	private String timezone;
	
	private Calendar calendar;
	private Person person;
	private LeavePlan leavePlanObj;

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

	public String getPayCalendar() {
		return payCalendar;
	}

	public void setPayCalendar(String payCalendar) {
		this.payCalendar = payCalendar;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public boolean isFmlaEligible() {
		return fmlaEligible;
	}

	public void setFmlaEligible(boolean fmlaEligible) {
		this.fmlaEligible = fmlaEligible;
	}

	public boolean isWorkmansCompEligible() {
		return workmansCompEligible;
	}

	public void setWorkmansCompEligible(boolean workmansCompEligible) {
		this.workmansCompEligible = workmansCompEligible;
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

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public LeavePlan getLeavePlanObj() {
		return leavePlanObj;
	}

	public void setLeavePlanObj(LeavePlan leavePlanObj) {
		this.leavePlanObj = leavePlanObj;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	protected String getUniqueKey() {
		return principalId + "_" + payCalendar == null ? "" : payCalendar + "_"
				+ leaveCalendar == null ? "" : leaveCalendar;
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
