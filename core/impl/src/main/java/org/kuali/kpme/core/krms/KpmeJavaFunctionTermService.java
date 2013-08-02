package org.kuali.kpme.core.krms;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.krms.KpmeKrmsJavaFunctionTermServiceBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class KpmeJavaFunctionTermService extends KpmeKrmsJavaFunctionTermServiceBase {

    public Boolean containsDept(Assignable assignable, String deptartment) {

        return getDepartmentsFromAssignments(assignable).contains(deptartment);
    }

    public Boolean containsWorkArea(Assignable assignable, Long workArea) {
        return getWorkAreasFromAssignments(assignable).contains(workArea);
    }

    public Boolean containsJobNumber(Assignable assignable, Long jobNumber) {
        return getJobNumbersFromAssignments(assignable).contains(jobNumber);
    }

    public Boolean containsTask(Assignable assignable, Long task) {
        return getTasksFromAssignments(assignable).contains(task);
    }

    private Set<String> getDepartmentsFromAssignments(Assignable assignable) {
        Set<String> depts = new HashSet<String>();
        List<? extends AssignmentContract> assignments = assignable.getAssignments();
        if (CollectionUtils.isNotEmpty(assignments)) {
            for (AssignmentContract contract : assignments) {
                depts.add(contract.getDept());
            }
        }
        return depts;
    }

    private Set<Long> getWorkAreasFromAssignments(Assignable assignable) {
        Set<Long> was = new HashSet<Long>();
        List<? extends AssignmentContract> assignments = assignable.getAssignments();
        if (CollectionUtils.isNotEmpty(assignments)) {
            for (AssignmentContract contract : assignments) {
                was.add(contract.getWorkArea());
            }
        }
        return was;
    }

    private Set<Long> getTasksFromAssignments(Assignable assignable) {
        Set<Long> tasks = new HashSet<Long>();
        List<? extends AssignmentContract> assignments = assignable.getAssignments();
        if (CollectionUtils.isNotEmpty(assignments)) {
            for (AssignmentContract contract : assignments) {
                tasks.add(contract.getTask());
            }
        }
        return tasks;
    }

    private Set<Long> getJobNumbersFromAssignments(Assignable assignable) {
        Set<Long> jobs = new HashSet<Long>();
        List<? extends AssignmentContract> assignments = assignable.getAssignments();
        if (CollectionUtils.isNotEmpty(assignments)) {
            for (AssignmentContract contract : assignments) {
                jobs.add(contract.getJobNumber());
            }
        }
        return jobs;
    }
}
