package org.kuali.hr.time.timesheet;

import java.util.LinkedHashMap;

import org.kuali.hr.time.paycalendar.PayCalendar;
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
	private String documentId;
	
	private Person principal;
	private PayCalendar payCalendarObj;
	
	private PayCalendarEntries payCalendarEntriesObj;
	
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

 
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		toStringMap.put("tkTimeSheetInitId", tkTimeSheetInitId);
		toStringMap.put("principalId", principalId);
		return toStringMap;
	}
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public PayCalendar getPayCalendarObj() {
		return payCalendarObj;
	}

	public void setPayCalendarObj(PayCalendar payCalendarObj) {
		this.payCalendarObj = payCalendarObj;
	}

	public String getBeginAndEndDateTime() {
		if (payCalendarEntriesObj == null && this.getPayCalendarEntriesId() != null) {
			payCalendarEntriesObj = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(this.getPayCalendarEntriesId());
	    }
	    return (payCalendarEntriesObj != null) ? 
	    		payCalendarEntriesObj.getBeginPeriodDateTime().toString() + " - "+ payCalendarEntriesObj.getEndPeriodDateTime().toString() : "";
	}

}
