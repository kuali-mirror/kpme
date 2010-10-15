package org.kuali.hr.time.paycalendar;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

/**
 * This class uses java.sql.Time and java.sql.Date because for each respective component
 * we are only interested in a partial Date or Time, that is for example:
 * 
 * 3:55 pm   (at any date)
 * 6/22/2010 (at any time)
 * 
 * @author djunk
 *
 */
public class PayCalendarDates extends PersistableBusinessObjectBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long payCalendarDatesId;
    private Long payCalendarId;

    private java.util.Date beginPeriodDateTime;

    private java.util.Date endPeriodDateTime;
    
	private Date initiateDate;
	private Time initiateTime;

	//this property is for the batch job 
	//that runs at the end of pay period
	private Date endPayPeriodDate;
	private Time endPayPeriodTime;

	private Date employeeApprovalDate;
	private Time employeeApprovalTime;

	private Date supervisorApprovalDate;
	private Time supervisorApprovalTime;

    public Long getPayCalendarId() {
	return payCalendarId;
    }

    public void setPayCalendarId(Long payCalendarId) {
	this.payCalendarId = payCalendarId;
    }

  

    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
	// TODO Auto-generated method stub
	return null;
    }

    public Long getPayCalendarDatesId() {
        return payCalendarDatesId;
    }

    public void setPayCalendarDatesId(Long payCalendarDatesId) {
        this.payCalendarDatesId = payCalendarDatesId;
    }

	public Date getInitiateDate() {
		return initiateDate;
	}

	public void setInitiateDate(Date initiateDate) {
		this.initiateDate = initiateDate;
	}

	public Time getInitiateTime() {
		return initiateTime;
	}

	public void setInitiateTime(Time initiateTime) {
		this.initiateTime = initiateTime;
	}

	public Date getEndPayPeriodDate() {
		return endPayPeriodDate;
	}

	public void setEndPayPeriodDate(Date endPayPeriodDate) {
		this.endPayPeriodDate = endPayPeriodDate;
	}

	public Time getEndPayPeriodTime() {
		return endPayPeriodTime;
	}

	public void setEndPayPeriodTime(Time endPayPeriodTime) {
		this.endPayPeriodTime = endPayPeriodTime;
	}

	public Date getEmployeeApprovalDate() {
		return employeeApprovalDate;
	}

	public void setEmployeeApprovalDate(Date employeeApprovalDate) {
		this.employeeApprovalDate = employeeApprovalDate;
	}

	public Time getEmployeeApprovalTime() {
		return employeeApprovalTime;
	}

	public void setEmployeeApprovalTime(Time employeeApprovalTime) {
		this.employeeApprovalTime = employeeApprovalTime;
	}

	public Date getSupervisorApprovalDate() {
		return supervisorApprovalDate;
	}

	public void setSupervisorApprovalDate(Date supervisorApprovalDate) {
		this.supervisorApprovalDate = supervisorApprovalDate;
	}

	public Time getSupervisorApprovalTime() {
		return supervisorApprovalTime;
	}

	public void setSupervisorApprovalTime(Time supervisorApprovalTime) {
		this.supervisorApprovalTime = supervisorApprovalTime;
	}

	public java.util.Date getBeginPeriodDateTime() {
		return beginPeriodDateTime;
	}

	public void setBeginPeriodDateTime(java.util.Date beginPeriodDateTime) {
		this.beginPeriodDateTime = beginPeriodDateTime;
	}

	public java.util.Date getEndPeriodDateTime() {
		return endPeriodDateTime;
	}

	public void setEndPeriodDateTime(java.util.Date endPeriodDateTime) {
		this.endPeriodDateTime = endPeriodDateTime;
	}
}
