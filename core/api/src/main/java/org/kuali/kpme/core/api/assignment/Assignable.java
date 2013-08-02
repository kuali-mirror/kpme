package org.kuali.kpme.core.api.assignment;

import java.util.List;

public interface Assignable {
    static String ASSIGNABLE_TERM_NAME = "Assignments";
    List<? extends AssignmentContract> getAssignments();
}
