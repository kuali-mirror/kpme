package org.kuali.hr.time.timesheet.web;

import java.util.List;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public class TimesheetActionForm extends TkForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 6938733178369007689L;
	TimesheetDocument timesheetDocument;

	private List<Assignment> assignments;
	private DepartmentEarnCode deptEarnCode;

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

	public DepartmentEarnCode getDeptEarnCode() {
		return deptEarnCode;
	}

	public void setDeptEarnCode(DepartmentEarnCode deptEarnCode) {
		this.deptEarnCode = deptEarnCode;
	}

}
