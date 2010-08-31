package org.kuali.hr.time.timesheet.web;

import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public class TimesheetActionForm extends TkForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 6938733178369007689L;
	TimesheetDocument timesheetDocument;

	public TimesheetDocument getTimesheetDocument() {
		return timesheetDocument;
	}

	public void setTimesheetDocument(TimesheetDocument timesheetDocument) {
		this.timesheetDocument = timesheetDocument;
	}

}
