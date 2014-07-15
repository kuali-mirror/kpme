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
package org.kuali.kpme.core.api.assignment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.util.HrApiConstants;
import org.kuali.kpme.core.api.util.KpmeUtils;

public class AssignmentDescriptionKey {

	private String groupKeyCode;
    private Long jobNumber;
	private Long workArea;
	private Long task;
	
	public static AssignmentDescriptionKey get(String assignmentDescriptionKeyString) {
		//AssignmentDescriptionKey assignmentDescriptionKey = new AssignmentDescriptionKey();
		
		Pattern keyPattern = Pattern.compile(".*?_\\d{1,}_\\d{1,}_\\d{1,}");
		Matcher match = keyPattern.matcher(assignmentDescriptionKeyString);
		if (match.matches()) {
			String[] key = StringUtils.split(assignmentDescriptionKeyString, HrApiConstants.ASSIGNMENT_KEY_DELIMITER);
            String groupKeyCode = key[0];
			Long jobNumber = Long.parseLong(key[1]);
			Long workArea = Long.parseLong(key[2]);
			Long task = Long.parseLong(key[3]);
            return new AssignmentDescriptionKey(groupKeyCode, jobNumber, workArea, task);
		}
		
		return new AssignmentDescriptionKey();
	}
	
	public AssignmentDescriptionKey() {
	}

    public AssignmentDescriptionKey(AssignmentContract assignment) {
        if (assignment != null) {
            this.groupKeyCode = assignment.getGroupKeyCode();
            this.jobNumber = assignment.getJobNumber();
            this.workArea = assignment.getWorkArea();
            this.task = assignment.getTask();
        }
    }

	public AssignmentDescriptionKey(String GroupKeyCode, Long jobNumer, Long workArea, Long task) {
        this.groupKeyCode = GroupKeyCode;
		this.jobNumber = jobNumer;
		this.workArea = workArea;
		this.task = task;
	}

    public String getGroupKeyCode() {
        return groupKeyCode;
    }
	public Long getJobNumber() {
		return jobNumber;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public Long getTask() {
		return task;
	}

    public String toAssignmentKeyString() {
        return KpmeUtils.formatAssignmentKey(groupKeyCode, jobNumber, workArea, task);
    }

    public static String getAssignmentKeyString(AssignmentContract a) {
    	return KpmeUtils.formatAssignmentKey(a.getGroupKeyCode(), a.getJobNumber(), a.getWorkArea(), a.getTask());
    }
    
    @Override
    public String toString() {
        return "groupKeyCode: " + groupKeyCode + "; jobNumber: " + jobNumber + "; workArea: " + workArea + "; task: " + task;
    }
    
}