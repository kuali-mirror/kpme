package org.kuali.hr.time.assignment.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;

public interface AssignmentService {

    /**
     * Returns all Assignment objects at or beyond the specified date.  Note the date is java.sql.Date, this is to enforce
     * the lack of time component when running the comparison query.
     *
     * @param effectiveDate The minimal date we are querying against.
     */
    public List<Assignment> getAssignmentsOnOrAfter(Date effectiveDate);

    /**
     * For a given principal id, return all active and valid assignments.
     * @param principalId The principal ID we are interested in looking up.
     */
    public List<Assignment> getCurrentlyValidActiveAssignments(String principalId);

    public Assignment getAssignmentByJobNumber(Long jobNumber);
}
