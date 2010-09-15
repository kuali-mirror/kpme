package org.kuali.hr.time.timesheet.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public class TimesheetActionForm extends TkForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 6938733178369007689L;
	TimesheetDocument timesheetDocument;
	private List<EarnCode> earnCodes = new LinkedList<EarnCode>();
	
	
	/**Job Number -> Formatted department earn codes  */
	private Map<Long,List<String>> formattedDeptEarnCodes;
	/** String (concat(job number, work_area, task)) -> Formatted Assignment Descriptions */
	private Map<String,List<String>>  assignmentDescriptions;
	
	
	public TimesheetDocument getTimesheetDocument() {
		return timesheetDocument;
	}

	public void setTimesheetDocument(TimesheetDocument timesheetDocument) {
		this.timesheetDocument = timesheetDocument;
	}

	public Map<Long,List<String>> getDeptEarnCodes() {
		return formattedDeptEarnCodes;
	}

	public void setDeptEarnCode(Map<Long,List<String>> formattedDeptEarnCodes) {
		this.formattedDeptEarnCodes = formattedDeptEarnCodes;
	}

	public Map<String,List<String>>  getAssignmentDescriptions() {
		return assignmentDescriptions;
	}

	public void setAssignmentDescriptions(Map<String,List<String>>  formattedAssignments) {
		this.assignmentDescriptions = formattedAssignments;
	}

	public List<EarnCode> getEarnCodes() {
		return earnCodes;
	}

	public void setEarnCodes(List<EarnCode> earnCodes) {
		this.earnCodes = earnCodes;
	}

}
