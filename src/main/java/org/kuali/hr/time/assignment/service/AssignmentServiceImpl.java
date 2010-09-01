package org.kuali.hr.time.assignment.service;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.dao.AssignmentDao;

public class AssignmentServiceImpl implements AssignmentService {
    private static final Logger LOG = Logger.getLogger(AssignmentServiceImpl.class);
    private AssignmentDao assignmentDao;

    @Override
    public List<Assignment> getAssignmentsOnOrAfter(Date effectiveDate) {
	return assignmentDao.findAssignmentsOnOrAfter(effectiveDate);
    }

    @Override
    public List<Assignment> getCurrentlyValidActiveAssignments(String principalId) {
	List<Assignment> assignments;
	LOG.debug("Locating assignments for principal id: " + principalId);

	assignments = assignmentDao.findCurrentlyValidActiveAssignments(principalId);
	LOG.debug("Found " + assignments.size() + " assignments for principal id: " + principalId);

	return assignments;
    }

    public AssignmentDao getAssignmentDao() {
	return assignmentDao;
    }

    public void setAssignmentDao(AssignmentDao assignmentDao) {
	this.assignmentDao = assignmentDao;
    }

	public Assignment getAssignmentByJobNumber(Long jobNumber) {
		return assignmentDao.findAssignmentByJobNumber(jobNumber);
	}
}
