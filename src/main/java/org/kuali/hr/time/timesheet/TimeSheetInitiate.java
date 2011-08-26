package org.kuali.hr.time.timesheet;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import java.util.LinkedHashMap;

public class TimeSheetInitiate extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkTimeSheetInitId;
	private String principalId;
	private Long hrPyCalendarEntriesId;
	private String pyCalendarGroup;
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
	
    public Long getHrPyCalendarEntriesId() {
        return hrPyCalendarEntriesId;
    }

    public void setHrPyCalendarEntriesId(Long hrPyCalendarEntriesId) {
        this.hrPyCalendarEntriesId = hrPyCalendarEntriesId;
    }
	
	public PayCalendarEntries getPayCalendarEntriesObj() {
		if(hrPyCalendarEntriesId != null) {
			setPayCalendarEntriesObj(TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(hrPyCalendarEntriesId));
		}
		return payCalendarEntriesObj;
	}

	public void setPayCalendarEntriesObj(PayCalendarEntries payCalendarEntriesObj) {
		this.payCalendarEntriesObj = payCalendarEntriesObj;
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
		if (payCalendarEntriesObj == null && this.getHrPyCalendarEntriesId() != null) {
			payCalendarEntriesObj = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(this.getHrPyCalendarEntriesId());
	    }
	    return (payCalendarEntriesObj != null) ? 
	    		payCalendarEntriesObj.getBeginPeriodDateTime().toString() + " - "+ payCalendarEntriesObj.getEndPeriodDateTime().toString() : "";
	}

}
