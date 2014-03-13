/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.krms;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.api.assignment.Assignment;

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
        List<Assignment> assignments = assignable.getAssignments();
        if (CollectionUtils.isNotEmpty(assignments)) {
            for (Assignment contract : assignments) {
                depts.add(contract.getDept());
            }
        }
        return depts;
    }

    private Set<Long> getWorkAreasFromAssignments(Assignable assignable) {
        Set<Long> was = new HashSet<Long>();
        List<Assignment> assignments = assignable.getAssignments();
        if (CollectionUtils.isNotEmpty(assignments)) {
            for (Assignment contract : assignments) {
                was.add(contract.getWorkArea());
            }
        }
        return was;
    }

    private Set<Long> getTasksFromAssignments(Assignable assignable) {
        Set<Long> tasks = new HashSet<Long>();
        List<Assignment> assignments = assignable.getAssignments();
        if (CollectionUtils.isNotEmpty(assignments)) {
            for (Assignment contract : assignments) {
                tasks.add(contract.getTask());
            }
        }
        return tasks;
    }

    private Set<Long> getJobNumbersFromAssignments(Assignable assignable) {
        Set<Long> jobs = new HashSet<Long>();
        List<Assignment> assignments = assignable.getAssignments();
        if (CollectionUtils.isNotEmpty(assignments)) {
            for (Assignment contract : assignments) {
                jobs.add(contract.getJobNumber());
            }
        }
        return jobs;
    }
}
