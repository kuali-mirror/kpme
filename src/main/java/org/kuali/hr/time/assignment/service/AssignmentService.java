package org.kuali.hr.time.assignment.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;

public interface AssignmentService {

    public List<Assignment> getAssignments(String principalId, Date payPeriodEndDate);
    public List<Assignment> getAssignmentsByJobNumber(Long jobNumber, String principalId, Date payPeriodEndDate);
}
