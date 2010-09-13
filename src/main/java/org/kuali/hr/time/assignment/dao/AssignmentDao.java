package org.kuali.hr.time.assignment.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;

public interface AssignmentDao {

	/**
	 * Returns all assignments for the provided principalId that are valid as of
	 * the specified payPeriodEndDate.
	 *
	 * @param principalId
	 * @param payPeriodEndDate
	 * @return
	 */
	public List<Assignment> findAssignments(String principalId, Date payPeriodEndDate);

	public List<Assignment> findAssignmentsByJobNumber(Long jobNumber, String principalId, Date payPeriodEndDate);

	public void saveOrUpdate(Assignment assignment);

	public void saveOrUpdate(List<Assignment> assignments);

	public void delete(Assignment assignment);

	public void deleteAllAssignments();
}
