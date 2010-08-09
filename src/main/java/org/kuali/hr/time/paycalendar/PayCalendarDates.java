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

    private Date beginPeriodDate;
    private Time beginPeriodTime;

    private Date endPeriodDate;
    private Time endPeriodTime;
    
	private Date initiateDate;
	private Time initiateTime;

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

    public Date getBeginPeriodDate() {
        return beginPeriodDate;
    }

    public void setBeginPeriodDate(Date beginPeriodDate) {
        this.beginPeriodDate = beginPeriodDate;
    }

    public Time getBeginPeriodTime() {
        return beginPeriodTime;
    }

    public void setBeginPeriodTime(Time beginPeriodTime) {
        this.beginPeriodTime = beginPeriodTime;
    }

    public Date getEndPeriodDate() {
        return endPeriodDate;
    }

    public void setEndPeriodDate(Date endPeriodDate) {
        this.endPeriodDate = endPeriodDate;
    }

    public Time getEndPeriodTime() {
        return endPeriodTime;
    }

    public void setEndPeriodTime(Time endPeriodTime) {
        this.endPeriodTime = endPeriodTime;
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
}
