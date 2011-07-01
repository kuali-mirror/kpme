package org.kuali.hr.time.assignment.service;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.assignment.dao.AssignmentDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		List<Assignment> assignments = new LinkedList<Assignment>();

		if (asOfDate == null) {
			asOfDate = TKUtils.getCurrentDate();
		}

		assignments = assignmentDao.findAssignments(principalId, asOfDate);

		for(Assignment assignment: assignments){
			assignment.setJob(TkServiceLocator.getJobSerivce().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getEffectiveDate()));
			assignment.setTimeCollectionRule(TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), asOfDate));
			assignment.setWorkAreaObj(TkServiceLocator.getWorkAreaService().getWorkArea(assignment.getWorkArea(), asOfDate));
			assignment.setDeptLunchRule(TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule(assignment.getJob().getDept(),
											assignment.getWorkArea(), assignment.getPrincipalId(), assignment.getJobNumber(), asOfDate));
		}

		return assignments;
	}

	@Override
	public AssignmentDescriptionKey getAssignmentDescriptionKey(String assignmentKey) {
		return new AssignmentDescriptionKey(assignmentKey);
	}

	@Override
	public Map<String,String> getAssignmentDescriptions(TimesheetDocument td, boolean clockOnlyAssignments) {
		if(td == null) {
			throw new RuntimeException("timesheet document is null.");
		}
		List<Assignment> assignments = td.getAssignments();
		if(assignments.size() < 1) {
			throw new RuntimeException("No assignment on the timesheet document.");
		}

		Map<String,String> assignmentDescriptions = new LinkedHashMap<String,String>();
		for(Assignment assignment : assignments) {
			//only add to the assignment list if they are synchronous assignments
			//or clock only assignments is false
			if(!clockOnlyAssignments || assignment.isSynchronous()){
				assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
			}
		}

		return assignmentDescriptions;
	}

	public Map<String,String> getAssignmentDescriptions(Assignment assignment) {
		if(assignment == null) {
			throw new RuntimeException("Assignment is null");
		}

		Map<String,String> assignmentDescriptions = new LinkedHashMap<String,String>();
		assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));

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

	public Assignment getAssignment(String tkAssignmentId) {
		return getAssignmentDao().getAssignment(tkAssignmentId);
	}

	public List<Assignment> getActiveAssignmentsForWorkArea(Long workArea, Date asOfDate){
		List<Assignment> assignments = assignmentDao.getActiveAssignmentsInWorkArea(workArea, asOfDate);
		for(Assignment assignment :assignments){
			assignment.setJob(TkServiceLocator.getJobSerivce().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getEffectiveDate()));
			assignment.setTimeCollectionRule(TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), asOfDate));
		}
		return assignments;
	}

	@Override
	public List<Assignment> getActiveAssignments(Date asOfDate) {
		return assignmentDao.getActiveAssignments(asOfDate);
	}

}
