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
