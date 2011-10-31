package org.kuali.hr.time.timesheet.web;

import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TimesheetActionForm extends TkForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 6938733178369007689L;
	private TimesheetDocument timesheetDocument;


	/**Job Number -> Formatted department earn codes  */
	private Map<Long,String> earnCodeDescriptions;
	/** String (concat(job number, work_area, task)) -> Formatted Assignment Descriptions */
	private Map<String,String>  assignmentDescriptions;
	private CalendarEntries payCalendarDates;
	private String selectedAssignment;
	private String selectedEarnCode;

	private String calNav;
	private String documentId;

	private java.util.Date beginPeriodDateTime;
	private java.util.Date endPeriodDateTime;

    private String prevDocumentId;
    private String nextDocumentId;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//setDocumentId("");
	}

	public TimesheetDocument getTimesheetDocument() {
		return timesheetDocument;
	}

	public void setTimesheetDocument(TimesheetDocument timesheetDocument) {
		this.timesheetDocument = timesheetDocument;
	}

	public Map<Long,String> getEarnCodeDescriptions() {
		return earnCodeDescriptions;
	}

	public void setEarnCodeDescriptions(Map<Long,String> earnCodeDescriptions) {
		this.earnCodeDescriptions = earnCodeDescriptions;
	}

	public Map<String,String>  getAssignmentDescriptions() {
		return assignmentDescriptions;
	}

	public void setAssignmentDescriptions(Map<String,String>  assignmentDescriptions) {
		this.assignmentDescriptions = assignmentDescriptions;
	}

	public String getSelectedAssignment() {
		return selectedAssignment;
	}

	public void setSelectedAssignment(String selectedAssignment) {
		this.selectedAssignment = selectedAssignment;
	}

	public String getSelectedEarnCode() {
		return selectedEarnCode;
	}

	public void setSelectedEarnCode(String selectedEarnCode) {
		this.selectedEarnCode = selectedEarnCode;
	}

	public CalendarEntries getPayCalendarDates() {
		return payCalendarDates;
	}

	public void setPayCalendarDates(CalendarEntries payCalendarDates) {
		this.payCalendarDates = payCalendarDates;
	}

	public String getCalNav() {
		return calNav;
	}

	public void setCalNav(String calNav) {
		this.calNav = calNav;
	}

	public java.util.Date getBeginPeriodDateTime() {
		return getPayCalendarDates().getBeginPeriodDateTime();
	}

	public java.util.Date getEndPeriodDateTime() {
		return getPayCalendarDates().getEndPeriodDateTime();
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

    public String getPrevDocumentId() {
        return prevDocumentId;
    }

    public void setPrevDocumentId(String prevDocumentId) {
        this.prevDocumentId = prevDocumentId;
    }

    public String getNextDocumentId() {
        return nextDocumentId;
    }

    public void setNextDocumentId(String nextDocumentId) {
        this.nextDocumentId = nextDocumentId;
    }
}
