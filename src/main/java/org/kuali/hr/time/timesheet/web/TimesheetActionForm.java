package org.kuali.hr.time.timesheet.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private Map<Long,String> earnCodeDescriptions;
	/** String (concat(job number, work_area, task)) -> Formatted Assignment Descriptions */
	private Map<String,String>  assignmentDescriptions;
	
	
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

	public void setAssignmentDescriptions(Map<String,String>  formattedAssignments) {
		this.assignmentDescriptions = formattedAssignments;
	}

	public List<EarnCode> getEarnCodes() {
		return earnCodes;
	}

	public void setEarnCodes(List<EarnCode> earnCodes) {
		this.earnCodes = earnCodes;
	}

}
