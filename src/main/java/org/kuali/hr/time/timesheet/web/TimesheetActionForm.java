package org.kuali.hr.time.timesheet.web;

import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TimesheetActionForm extends TkForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 6938733178369007689L;
	private TimesheetDocument timesheetDocument;
	private String documentId;


	/**Job Number -> Formatted department earn codes  */
	private Map<Long,String> earnCodeDescriptions;
	/** String (concat(job number, work_area, task)) -> Formatted Assignment Descriptions */
	private Map<String,String>  assignmentDescriptions;
	private PayCalendarEntries payCalendarDates;
	private String selectedAssignment;
	private String selectedEarnCode;

	private String calNav;

	private java.util.Date beginPeriodDateTime;
	private java.util.Date endPeriodDateTime;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.documentId = "";
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

	public PayCalendarEntries getPayCalendarDates() {
		return payCalendarDates;
	}

	public void setPayCalendarDates(PayCalendarEntries payCalendarDates) {
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

}
