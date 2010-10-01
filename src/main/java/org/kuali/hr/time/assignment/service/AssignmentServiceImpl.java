package org.kuali.hr.time.assignment.service;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.assignment.dao.AssignmentDao;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;

public class AssignmentServiceImpl implements AssignmentService {

	private static final Logger LOG = Logger.getLogger(AssignmentServiceImpl.class);
	private AssignmentDao assignmentDao;

	public AssignmentDao getAssignmentDao() {
		return assignmentDao;
	}

	public void setAssignmentDao(AssignmentDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}

	@Override
	public List<Assignment> getAssignments(String principalId, Date asOfDate) {
		if (asOfDate == null) {
			asOfDate = TKUtils.getCurrentDate();
		}
		return assignmentDao.findAssignments(principalId, asOfDate);
	}

	@Override
	public AssignmentDescriptionKey getAssignmentDescriptionKey(String assignmentKey) {
		return new AssignmentDescriptionKey(assignmentKey);
	}
	
	@Override
	public Map<String,String> getAssignmentDescriptions(TimesheetDocument td) {
		List<Assignment> assignments = td.getAssignments();
		if(assignments.size() < 1) {
			throw new RuntimeException("No assignment on the timesheet document.");
		}
		
		Map<String,String> assignmentDescriptions = new LinkedHashMap<String,String>();
		for(Assignment assignment : assignments) {
			assignmentDescriptions.putAll(formatAssignmentDescription(assignment));
		}
		
		return assignmentDescriptions;
	}
	
	public Map<String,String> getAssignmentDescriptions(Assignment assignment) {
		if(assignment == null) {
			throw new RuntimeException("Assignment is null");
		}
		
		Map<String,String> assignmentDescriptions = new LinkedHashMap<String,String>();
		assignmentDescriptions.putAll(formatAssignmentDescription(assignment));
		
		return assignmentDescriptions;
		
	}
	
	private Map<String,String> formatAssignmentDescription(Assignment assignment) {
		Map<String,String> assignmentDescriptions = new LinkedHashMap<String,String>();
		String assignmentDescKey  = TKUtils.formatAssignmentKey(assignment.getJobNumber(), assignment.getWorkArea(), assignment.getTask());
		// TODO: do we still need to display comp rate?
		String assignmentDescValue =  assignment.getWorkAreaObj().getDescription() + " : compRate Rcd " + assignment.getJobNumber() + " " + assignment.getJob().getDept();
		assignmentDescriptions.put(assignmentDescKey, assignmentDescValue);
		
		return assignmentDescriptions;
	}

	@Override
	public Assignment getAssignment(TimesheetDocument timesheetDocument, String assignmentKey) {
		List<Assignment> assignments = timesheetDocument.getAssignments();
		AssignmentDescriptionKey desc = getAssignmentDescriptionKey(assignmentKey);
		
		for(Assignment assignment : assignments) {
			if(assignment.getJobNumber().compareTo(desc.getJobNumber()) == 0 &&
				assignment.getWorkArea().compareTo(desc.getWorkArea()) == 0 &&
				assignment.getTask().compareTo(desc.getTask()) == 0) {
					return assignment;
				}
		}
		
		LOG.warn("no matched assignment found");
		return new Assignment();
	}
}
