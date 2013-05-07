package org.kuali.kpme.core.document.calendar;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.document.CalendarDocumentHeaderContract;

public abstract class CalendarDocument implements CalendarDocumentContract,
		Serializable {

	protected CalendarDocumentHeaderContract documentHeader;
	protected List<Assignment> assignments = new LinkedList<Assignment>();
	protected CalendarEntry calendarEntry = null;
	protected LocalDate asOfDate;
	protected String calendarType;
	
	public String getDocumentId() {
		if(documentHeader != null)
			return documentHeader.getDocumentId();
		else
			return null;
	}
	
	public String getPrincipalId() {
		if(documentHeader != null)
			return documentHeader.getPrincipalId();
		else
			return null;
	}
	
	
	@Override
	public CalendarDocumentHeaderContract getDocumentHeader() {
		return documentHeader;
	}

	@Override
	public List<Assignment> getAssignments() {
		return assignments;
	}

	@Override
	public CalendarEntry getCalendarEntry() {
		return calendarEntry;
	}

	@Override
	public LocalDate getAsOfDate() {
		return asOfDate;
	}
	
    /**
     * The type of calendar this contract is associated with
     * 
     * @return
     */
    public String getCalendarType() {
    	return calendarType;
    }
    
    public void setCalendarType(String calendarType) {
    	this.calendarType = calendarType;
    }

}
