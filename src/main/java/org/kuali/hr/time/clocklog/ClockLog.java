package org.kuali.hr.time.clocklog;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class ClockLog extends PersistableBusinessObjectBase {

    /**
     *
     */
    private static final long serialVersionUID = -2499666820628979901L;

    private Long clockLogId = null;
    private String principalId;
    private Long jobNumber;
    private Long workAreaId;
    private Long taskId;
    private Timestamp clockTimestamp;
    private String clockTimestampTimezone;
    private String clockAction;
    private String ipAddress;
    private String userPrincipalId;
    private Timestamp timestamp;

    private WorkArea workArea;
    private Task task;

    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
	// TODO Auto-generated method stub
	return null;
    }

    public WorkArea getWorkArea() {
		return workArea;
	}

	public void setWorkArea(WorkArea workArea) {
		this.workArea = workArea;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public Long getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(Long jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Long getWorkAreaId() {
        return workAreaId;
    }

    public void setWorkAreaId(Long workAreaId) {
        this.workAreaId = workAreaId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Timestamp getClockTimestamp() {
        return clockTimestamp;
    }

    public void setClockTimestamp(Timestamp clockTimestamp) {
        this.clockTimestamp = clockTimestamp;
    }

    public String getClockAction() {
        return clockAction;
    }

    public void setClockAction(String clockAction) {
        this.clockAction = clockAction;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserPrincipalId() {
        return userPrincipalId;
    }

    public void setUserPrincipalId(String userPrincipalId) {
        this.userPrincipalId = userPrincipalId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getClockTimestampTimezone() {
        return clockTimestampTimezone;
    }

    public void setClockTimestampTimezone(String clockTimestampTimezone) {
        this.clockTimestampTimezone = clockTimestampTimezone;
    }

    public Long getClockLogId() {
        return clockLogId;
    }

    public void setClockLogId(Long clockLogId) {
        this.clockLogId = clockLogId;
    }

    /**
     * TODO: Fix this - may need to return multiple actions, depending on how we want the system to work.
     * @return
     */
    public String getNextValidClockAction() {
	String ret = "";

	if (this.getClockAction().equals(TkConstants.CLOCK_IN)) {
	    ret = TkConstants.CLOCK_OUT;
	} else if (this.getClockAction().equals(TkConstants.CLOCK_OUT)) {
	    ret = TkConstants.CLOCK_IN;
	} else if (this.getClockAction().equals(TkConstants.LUNCH_IN)) {
	    ret = TkConstants.LUNCH_OUT;
	} else if (this.getClockAction().equals(TkConstants.LUNCH_OUT)) {
	    ret = TkConstants.LUNCH_IN;
	} else if (this.getClockAction().equals(TkConstants.BREAK_IN)) {
	    ret = TkConstants.BREAK_OUT;
	} else if (this.getClockAction().equals(TkConstants.BREAK_OUT)) {
	    ret = TkConstants.BREAK_IN;
	} else {
	    ret = TkConstants.CLOCK_IN;
	}

	return ret;
    }

}
