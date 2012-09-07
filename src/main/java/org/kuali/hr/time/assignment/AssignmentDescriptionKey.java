package org.kuali.hr.time.assignment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.kuali.hr.time.util.TkConstants;

public class AssignmentDescriptionKey {
	private static final Logger LOG = Logger.getLogger(AssignmentDescriptionKey.class);

	private Long jobNumber;
	private Long workArea;
	private Long task;

    public AssignmentDescriptionKey() {

    }

    // djunk - added this constructor since we are essentially working on units
    // of assignments and their keys
    public AssignmentDescriptionKey(Assignment a) {
        if (a != null) {
            this.jobNumber = a.getJobNumber();
            this.workArea = a.getWorkArea();
            this.task = a.getTask();
        }
    }

	public AssignmentDescriptionKey(String jobNumer, String workArea, String task) {
		this.jobNumber = Long.parseLong(jobNumer);
		this.workArea = Long.parseLong(workArea);
		this.task = Long.parseLong(task);
	}
	public AssignmentDescriptionKey(Long jobNumer, Long workArea, Long task) {
		this.jobNumber = jobNumer;
		this.workArea = workArea;
		this.task = task;
	}
	public AssignmentDescriptionKey(String assignmentKey) {

		try {
		Pattern keyPattern = Pattern.compile("^\\d{1,}_\\d{1,}_\\d{1,}");
		Matcher match = keyPattern.matcher(assignmentKey);
		if(!match.matches()) {
			throw new RuntimeException("the format of the assignment key is wrong. it should be jobNumber_workArea_task");
		}

		String[] key = assignmentKey.split(TkConstants.ASSIGNMENT_KEY_DELIMITER);

		this.jobNumber = Long.parseLong(key[0]);
		this.workArea = Long.parseLong(key[1]);
		this.task = Long.parseLong(key[2]);
		}
		catch(NullPointerException npe) {
			LOG.error("The assignment key is null");
		}

	}
	public Long getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}
	public Long getWorkArea() {
		return workArea;
	}
	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}
	public Long getTask() {
		return task;
	}
	public void setTask(Long task) {
		this.task = task;
	}

    @Override
    public String toString() {
        return "jobNumber: " + jobNumber + "; workArea: " + workArea + "; task: " + task;
    }

    public String toAssignmentKeyString() {
        return jobNumber + TkConstants.ASSIGNMENT_KEY_DELIMITER + workArea + TkConstants.ASSIGNMENT_KEY_DELIMITER + task;
    }


    public static String getAssignmentKeyString(Assignment a) {
        return a.getJobNumber() + TkConstants.ASSIGNMENT_KEY_DELIMITER + a.getWorkArea() + TkConstants.ASSIGNMENT_KEY_DELIMITER + a.getTask();
    }
}
