package org.kuali.hr.time.assignment.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;

public interface AssignmentDao {

    /**
     * Provides a list of Assignment objects on or after the date specified.
     *
     * @param date A java.sql.Date object, sql date is used to enforce lack of time component.
     * @return
     */
    public List<Assignment> findAssignmentsOnOrAfter(Date date);
    public List<Assignment> findAllAssignments();

    /**
     * Returns a list of the currently valid active assignments for the provided principal ID.
     * @param principalId
     * @return
     */
    public List<Assignment> findCurrentlyValidActiveAssignments(String principalId);

    public void saveOrUpdate(Assignment assignment);
    public void saveOrUpdate(List<Assignment> assignments);

    public void delete(Assignment assignment);
    public void deleteAllAssignments();
	public Assignment findAssignmentByJobNumber(Long jobNumber);

}
