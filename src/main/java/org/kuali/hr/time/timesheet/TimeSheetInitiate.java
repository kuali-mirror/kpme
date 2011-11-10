package org.kuali.hr.time.timesheet;

import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
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
	private Calendar calendarObj;
	
	private CalendarEntries calendarEntriesObj;
	
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
	
	public CalendarEntries getCalendarEntriesObj() {
		if(hrPyCalendarEntriesId != null) {
			setCalendarEntriesObj(TkServiceLocator.getCalendarEntriesSerivce().getCalendarEntries(hrPyCalendarEntriesId));
		}
		return calendarEntriesObj;
	}

	public void setCalendarEntriesObj(CalendarEntries calendarEntriesObj) {
		this.calendarEntriesObj = calendarEntriesObj;
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

	public Calendar getCalendarObj() {
		return calendarObj;
	}

	public void setCalendarObj(Calendar calendarObj) {
		this.calendarObj = calendarObj;
	}

	public String getBeginAndEndDateTime() {
		if (calendarEntriesObj == null && this.getHrPyCalendarEntriesId() != null) {
			calendarEntriesObj = TkServiceLocator.getCalendarEntriesSerivce().getCalendarEntries(this.getHrPyCalendarEntriesId());
	    }
	    return (calendarEntriesObj != null) ? 
	    		calendarEntriesObj.getBeginPeriodDateTime().toString() + " - "+ calendarEntriesObj.getEndPeriodDateTime().toString() : "";
	}

}
