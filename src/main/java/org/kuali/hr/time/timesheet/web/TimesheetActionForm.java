package org.kuali.hr.time.timesheet.web;

import java.util.List;
import java.util.Map;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public class TimesheetActionForm extends TkForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 6938733178369007689L;
	TimesheetDocument timesheetDocument;

	private List<Assignment> assignments;
	private Map<Long,List<String>> formattedDeptEarnCodes;
	private Map<Long,List<String>>  formattedAssignments;

	public TimesheetDocument getTimesheetDocument() {
		return timesheetDocument;
	}

	public void setTimesheetDocument(TimesheetDocument timesheetDocument) {
		this.timesheetDocument = timesheetDocument;
	}

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

	public Map<Long,List<String>> getDeptEarnCodes() {
		return formattedDeptEarnCodes;
	}

	public void setDeptEarnCode(Map<Long,List<String>> formattedDeptEarnCodes) {
		this.formattedDeptEarnCodes = formattedDeptEarnCodes;
	}

	public Map<Long,List<String>>  getFormattedAssignments() {
		return formattedAssignments;
	}

	public void setFormattedAssignments(Map<Long,List<String>>  formattedAssignments) {
		this.formattedAssignments = formattedAssignments;
	}

}
