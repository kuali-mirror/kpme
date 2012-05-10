package org.kuali.hr.time.paycalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import javax.persistence.Transient;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashMap;

/**
 * This class uses java.sql.Time and java.sql.Date because for each respective component
 * we are only interested in a partial Date or Time, that is for example:
 * <p/>
 * 3:55 pm   (at any date)
 * 6/22/2010 (at any time)
 *
 * Make sure you are aware of whether or not you need a Local Relative time or
 * an absolute server time.
 *
 * Local Relative Time Methods: (Time/Date without Timezone)
 *
 * LocalDateTime : getBeginLocalDateTime()
 * LocalDateTime : getEndLocalDateTime()
 *
 * Absolute Methods:
 *
 * java.util.Date : getEndPeriodDateTime()
 * java.util.Date : getBeginPeriodDateTime()
 *
 */
public class PayCalendarEntries extends PersistableBusinessObjectBase implements Comparable<PayCalendarEntries> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String hrPyCalendarEntriesId;
    private String hrPyCalendarId;
    private String pyCalendarGroup;

    private java.util.Date beginPeriodDateTime;

    private java.util.Date endPeriodDateTime;

    @Transient
    private java.sql.Date beginPeriodDate;
    @Transient
    private java.sql.Date endPeriodDate;
    @Transient
    private Time beginPeriodTime;
    @Transient
    private Time endPeriodTime;

    private Date batchInitiateDate;
    private Time batchInitiateTime;

    //this property is for the batch job
    //that runs at the end of pay period
    private Date batchEndPayPeriodDate;
    private Time batchEndPayPeriodTime;

    private Date batchEmployeeApprovalDate;
    private Time batchEmployeeApprovalTime;

    private Date batchSupervisorApprovalDate;
    private Time batchSupervisorApprovalTime;

    private PayCalendar payCalendarObj;


    /**
     * Provides the Begin Period time without timezone information, used
     * for relative calculations.
     *
     * @return A LocalDateTime representation of the begin period date/time.
     */
    public LocalDateTime getBeginLocalDateTime() {
        return (new DateTime(this.getBeginPeriodDateTime())).toLocalDateTime();
    }

    /**
     * Provides the End Period time without timezone information, used
     * for relative calculations.
     *
     * @return A LocalDateTime representation of the end period date/time.
     */
    public LocalDateTime getEndLocalDateTime() {
        return (new DateTime(this.getEndPeriodDateTime())).toLocalDateTime();
    }

    public String getHrPyCalendarId() {
        payCalendarObj = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(this.getPyCalendarGroup());
        if (payCalendarObj != null) {
            this.setHrPyCalendarId(payCalendarObj.getHrPyCalendarId());
        }
        return hrPyCalendarId;
    }

    public void setHrPyCalendarId(String hrPyCalendarId) {
        this.hrPyCalendarId = hrPyCalendarId;
    }

    public String getPyCalendarGroup() {
        return pyCalendarGroup;
    }

    public void setPyCalendarGroup(String pyCalendarGroup) {
        this.pyCalendarGroup = pyCalendarGroup;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		toStringMap.put("beginPeriodDate", beginPeriodDate);
		toStringMap.put("endPeriodDate", endPeriodDate);

        return toStringMap;

    }

    public String getHrPyCalendarEntriesId() {
        return hrPyCalendarEntriesId;
    }

    public void setHrPyCalendarEntriesId(String hrPyCalendarEntriesId) {
        this.hrPyCalendarEntriesId = hrPyCalendarEntriesId;
    }


    public java.util.Date getBeginPeriodDateTime() {
        return beginPeriodDateTime;
    }

    public void setBeginPeriodDateTime(java.util.Date beginPeriodDateTime) {
        this.beginPeriodDateTime = beginPeriodDateTime;
        if (beginPeriodDateTime != null) {
            setBeginPeriodDate(new java.sql.Date(beginPeriodDateTime.getTime()));
            setBeginPeriodTime(new java.sql.Time(beginPeriodDateTime.getTime()));
        }
    }

    public java.util.Date getEndPeriodDateTime() {
        return endPeriodDateTime;
    }

    public void setEndPeriodDateTime(java.util.Date endPeriodDateTime) {
        this.endPeriodDateTime = endPeriodDateTime;
        if (endPeriodDateTime != null) {
            setEndPeriodDate(new java.sql.Date(endPeriodDateTime.getTime()));
            setEndPeriodTime(new java.sql.Time(endPeriodDateTime.getTime()));
        }
    }

    public java.sql.Date getBeginPeriodDate() {
    	if(beginPeriodDate == null && this.getBeginPeriodDateTime() != null) {
    		setBeginPeriodDate(new java.sql.Date(this.getBeginPeriodDateTime().getTime()));
    	}
        return beginPeriodDate;
    }

    public void setBeginPeriodDate(java.sql.Date beginPeriodDate) {
        this.beginPeriodDate = beginPeriodDate;
    }

    public java.sql.Date getEndPeriodDate() {
    	if(endPeriodDate == null && this.getEndPeriodDateTime() != null) {
    		setEndPeriodDate(new java.sql.Date(this.getEndPeriodDateTime().getTime()));
    	}
        return endPeriodDate;
    }

    public void setEndPeriodDate(java.sql.Date endPeriodDate) {
        this.endPeriodDate = endPeriodDate;
    }

    public Time getBeginPeriodTime() {
        return beginPeriodTime;
    }

    public void setBeginPeriodTime(Time beginPeriodTime) {
        this.beginPeriodTime = beginPeriodTime;
    }

    public Time getEndPeriodTime() {
        return endPeriodTime;
    }

    public void setEndPeriodTime(Time endPeriodTime) {
        this.endPeriodTime = endPeriodTime;
    }

    public Date getBatchInitiateDate() {
        return batchInitiateDate;
    }

    public void setBatchInitiateDate(Date batchInitiateDate) {
        this.batchInitiateDate = batchInitiateDate;
    }

    public Time getBatchInitiateTime() {
        return batchInitiateTime;
    }

    public void setBatchInitiateTime(Time batchInitiateTime) {
        this.batchInitiateTime = batchInitiateTime;
    }

    public Date getBatchEndPayPeriodDate() {
        return batchEndPayPeriodDate;
    }

    public void setBatchEndPayPeriodDate(Date batchEndPayPeriodDate) {
        this.batchEndPayPeriodDate = batchEndPayPeriodDate;
    }

    public Time getBatchEndPayPeriodTime() {
        return batchEndPayPeriodTime;
    }

    public void setBatchEndPayPeriodTime(Time batchEndPayPeriodTime) {
        this.batchEndPayPeriodTime = batchEndPayPeriodTime;
    }

    public Date getBatchEmployeeApprovalDate() {
        return batchEmployeeApprovalDate;
    }

    public void setBatchEmployeeApprovalDate(Date batchEmployeeApprovalDate) {
        this.batchEmployeeApprovalDate = batchEmployeeApprovalDate;
    }

    public Time getBatchEmployeeApprovalTime() {
        return batchEmployeeApprovalTime;
    }

    public void setBatchEmployeeApprovalTime(Time batchEmployeeApprovalTime) {
        this.batchEmployeeApprovalTime = batchEmployeeApprovalTime;
    }

    public Date getBatchSupervisorApprovalDate() {
        return batchSupervisorApprovalDate;
    }

    public void setBatchSupervisorApprovalDate(Date batchSupervisorApprovalDate) {
        this.batchSupervisorApprovalDate = batchSupervisorApprovalDate;
    }

    public Time getBatchSupervisorApprovalTime() {
        return batchSupervisorApprovalTime;
    }

    public void setBatchSupervisorApprovalTime(Time batchSupervisorApprovalTime) {
        this.batchSupervisorApprovalTime = batchSupervisorApprovalTime;
    }

    public PayCalendar getPayCalendarObj() {
        return payCalendarObj;
    }

    public void setPayCalendarObj(PayCalendar payCalendarObj) {
        this.payCalendarObj = payCalendarObj;
    }
    public int compareTo(PayCalendarEntries pce) {
        return this.getBeginPeriodDate().compareTo(pce.getBeginPeriodDate());
    }
}
