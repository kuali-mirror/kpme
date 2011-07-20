package org.kuali.hr.time.timesheet;

import java.util.LinkedHashMap;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TimeSheetInitiate extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkTimeSheetInitId;
	private String principalId;
	private Long payCalendarEntriesId;
	private String calendarGroup;
	
	private Person principal;
	private PayCalendarEntries payCalendarEntriesObj;
	private java.sql.Date endPeriodDate;
	
	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	
	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	
	public Long getTkTimeSheetInitId() {
		return tkTimeSheetInitId;
	}

	public void setTkTimeSheetInitId(Long tkTimeSheetInitId) {
		this.tkTimeSheetInitId = tkTimeSheetInitId;
	}
	
    public Long getPayCalendarEntriesId() {
        return payCalendarEntriesId;
    }

    public void setPayCalendarEntriesId(Long payCalendarEntriesId) {
        this.payCalendarEntriesId = payCalendarEntriesId;
    }
	
	public PayCalendarEntries getPayCalendarEntriesObj() {
		if(payCalendarEntriesId != null) {
			setPayCalendarEntriesObj(TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(payCalendarEntriesId));
		}
		return payCalendarEntriesObj;
	}

	public void setPayCalendarEntriesObj(PayCalendarEntries payCalendarEntriesObj) {
		this.payCalendarEntriesObj = payCalendarEntriesObj;
	}

    public String getCalendarGroup() {
        return calendarGroup;
    }

    public void setCalendarGroup(String calendarGroup) {
        this.calendarGroup = calendarGroup;
    }
    
    public java.sql.Date getEndPeriodDate() {
		if(endPeriodDate == null && this.getPayCalendarEntriesObj() != null){
			if(this.getPayCalendarEntriesObj().getEndPeriodDate() != null) {
				setEndPeriodDate(this.getPayCalendarEntriesObj().getEndPeriodDate());
			}
		}
    	return endPeriodDate;
	}
    
    public void setEndPeriodDate(java.sql.Date endPeriodDate) {
        this.endPeriodDate = endPeriodDate;
    }
 
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		toStringMap.put("tkTimeSheetInitId", tkTimeSheetInitId);
		toStringMap.put("principalId", principalId);
		return toStringMap;
	}

	public java.util.Date getEndPeriodDateTime() {
		return this.getEndPeriodDate();
	}

}
