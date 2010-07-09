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
}
