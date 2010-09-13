package org.kuali.hr.time.timesheet;

import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;


public class TimesheetDocument  {

	public static final String TIMESHEET_DOCUMENT_TYPE = "TimesheetDocument";
	public static final String TIMESHEET_DOCUMENT_TITLE = "TimesheetDocument";
	
	private TimesheetDocumentHeader documentHeader;
	private List<Assignment> assignments = new LinkedList<Assignment>();
	
	public TimesheetDocument(TimesheetDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}

	public TimesheetDocumentHeader getDocumentHeader() {
		return documentHeader;
	}

	public void setDocumentHeader(TimesheetDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
}
