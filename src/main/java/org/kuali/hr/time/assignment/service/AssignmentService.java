package org.kuali.hr.time.assignment.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface AssignmentService {

    public List<Assignment> getAssignments(String principalId, Date asOfDate);
    public Assignment getAssignment(TimesheetDocument timesheetDocument, String assignmentKey);
    public AssignmentDescriptionKey getAssignmentDescriptionKey(String assignmentDesc);
    public Map<String,String> getAssignmentDescriptions(TimesheetDocument td, boolean clockOnlyAssignments);
	public Map<String,String> getAssignmentDescriptions(Assignment assignment);
}
