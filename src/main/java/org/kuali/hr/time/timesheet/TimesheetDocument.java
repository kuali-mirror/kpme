package org.kuali.hr.time.timesheet;

import org.kuali.hr.time.workflow.TkDocumentHeader;


public class TimesheetDocument  {

	public static final String TIMESHEET_DOCUMENT_TYPE = "TimesheetDocument";
	public static final String TIMESHEET_DOCUMENT_TITLE = "TimesheetDocument";
	
	private TkDocumentHeader documentHeader;
	
	public TimesheetDocument(TkDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}

	public TkDocumentHeader getDocumentHeader() {
		return documentHeader;
	}

	public void setDocumentHeader(TkDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}
}
