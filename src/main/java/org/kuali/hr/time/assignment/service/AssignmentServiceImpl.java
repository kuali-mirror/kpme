package org.kuali.hr.time.assignment.service;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.dao.AssignmentDao;

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
	public List<Assignment> getAssignments(String principalId, Date payPeriodEndDate) {
		if (payPeriodEndDate == null) {
			payPeriodEndDate = new Date(System.currentTimeMillis());
		}
		return assignmentDao.findAssignments(principalId, payPeriodEndDate);
	}

	@Override
	public List<Assignment> getAssignmentsByJobNumber(Long jobNumber, String principalId, Date payPeriodEndDate) {
		return assignmentDao.findAssignmentsByJobNumber(jobNumber, principalId, payPeriodEndDate);
	}
}
