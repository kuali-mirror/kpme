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


import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class KpmeUtils {
    public static String formatAssignmentKey(Long jobNumber, Long workArea, Long task) {
        String assignmentKey = StringUtils.EMPTY;

        String jobNumberString = ObjectUtils.toString(jobNumber, "0");
        String workAreaString = ObjectUtils.toString(workArea, "0");
        String taskString = ObjectUtils.toString(task, "0");

        if (!jobNumberString.equals("0") || !workAreaString.equals("0") || !taskString.equals("0")) {
            assignmentKey = StringUtils.join(new String[] {jobNumberString, workAreaString, taskString}, HrApiConstants.ASSIGNMENT_KEY_DELIMITER);
        }

        return assignmentKey;
    }
}
