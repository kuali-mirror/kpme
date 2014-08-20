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
package org.kuali.kpme.core.api.util;


import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;

import java.util.*;

public class KpmeUtils {
    public static String formatAssignmentKey(String groupKeyCode, Long jobNumber, Long workArea, Long task) {
        String assignmentKey = StringUtils.EMPTY;

        String jobNumberString = ObjectUtils.toString(jobNumber, "0");
        String workAreaString = ObjectUtils.toString(workArea, "0");
        String taskString = ObjectUtils.toString(task, "0");

        if (!jobNumberString.equals("0") || !workAreaString.equals("0") || !taskString.equals("0")) {
            assignmentKey = StringUtils.join(new String[] {groupKeyCode, jobNumberString, workAreaString, taskString}, HrApiConstants.ASSIGNMENT_KEY_DELIMITER);
        }

        return assignmentKey;
    }

    public static List<Assignment> getUniqueAssignments(Map<LocalDate, List<Assignment>> history) {
        if (MapUtils.isEmpty(history)) {
            return Collections.emptyList();
        }
        Set<Assignment> allAssignments = new HashSet<Assignment>();
        for (List<Assignment> assignList : history.values()) {
            allAssignments.addAll(assignList);
        }
        return new ArrayList<Assignment>(allAssignments);
    }

    public static <T extends Comparable<? super T>> int nullSafeCompare(T s1, T s2) {
        if(s1 == null && s2 != null) { return -1;}
        if(s1 != null && s2 == null) { return 1;}
        if(s1 == null) { return 0;}
        return s1.compareTo(s2);
    }
}
