package org.kuali.hr.time.clocklog;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class ClockLog extends PersistableBusinessObjectBase {

    /**
     *
     */
    private static final long serialVersionUID = -2499666820628979901L;

    private Long tkClockLogId;
    private String principalId;
    private Long jobNumber;
    private Long workArea;
    private Long task;
    private Long tkWorkAreaId;
    private Long tkTaskId;
    private Timestamp clockTimestamp;
    private String clockTimestampTimezone;
    private String clockAction;
    private String ipAddress;
    private String userPrincipalId;
    private Long hrJobId;
    private Timestamp timestamp;
    
    private String missedPunchDocumentId;

    private Job job;
    private WorkArea workAreaObj;
    private Task taskObj;

    private Person principal;

    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
    	LinkedHashMap<String,Object> toStringMapper = new LinkedHashMap<String,Object>();
    	toStringMapper.put("tkClockLogId", tkClockLogId);
    	toStringMapper.put("tkClockLogId", principalId);
    	toStringMapper.put("tkClockLogId", jobNumber);
    	toStringMapper.put("tkClockLogId", workArea);
    	toStringMapper.put("tkClockLogId", task);
    	toStringMapper.put("tkClockLogId", tkWorkAreaId);
    	toStringMapper.put("tkClockLogId", tkTaskId);
    	toStringMapper.put("tkClockLogId", clockTimestamp);
    	toStringMapper.put("tkClockLogId", clockTimestampTimezone);
    	toStringMapper.put("tkClockLogId", clockAction);
    	toStringMapper.put("tkClockLogId", ipAddress);
    	toStringMapper.put("tkClockLogId", userPrincipalId);
    	toStringMapper.put("tkClockLogId", hrJobId);
    	toStringMapper.put("tkClockLogId", timestamp);
    	toStringMapper.put("tkClockLogId", job);
    	toStringMapper.put("tkClockLogId", workAreaObj);
    	toStringMapper.put("tkClockLogId", taskObj);

    	return toStringMapper;
    }

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
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

    /**
     * TODO: Fix this - may need to return multiple actions, depending on how we want the system to work.
     * @return
     */
    public String getNextValidClockAction() {
	String ret;

	if (this.getClockAction().equals(TkConstants.CLOCK_IN)) {
	    ret = TkConstants.CLOCK_OUT;
	} else if (this.getClockAction().equals(TkConstants.CLOCK_OUT)) {
	    ret = TkConstants.CLOCK_IN;
	} else if (this.getClockAction().equals(TkConstants.LUNCH_IN)) {
	    ret = TkConstants.LUNCH_OUT;
	} else if (this.getClockAction().equals(TkConstants.LUNCH_OUT)) {
	    ret = TkConstants.LUNCH_IN;
	} else {
	    ret = TkConstants.CLOCK_IN;
	}

	return ret;
    }

	public Long getTkClockLogId() {
		return tkClockLogId;
	}

	public void setTkClockLogId(Long tkClockLogId) {
		this.tkClockLogId = tkClockLogId;
	}

	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public Long getTkTaskId() {
		return tkTaskId;
	}

	public void setTkTaskId(Long tkTaskId) {
		this.tkTaskId = tkTaskId;
	}

	public Long getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(Long hrJobId) {
		this.hrJobId = hrJobId;
	}

	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public Task getTaskObj() {
		return taskObj;
	}

	public void setTaskObj(Task taskObj) {
		this.taskObj = taskObj;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public void setTask(Long task) {
		this.task = task;
	}
	public Long getWorkArea() {
		return workArea;
	}
	public Long getTask() {
		return task;
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public String getMissedPunchDocumentId() {
		return missedPunchDocumentId;
	}

	public void setMissedPunchDocumentId(String missedPunchDocumentId) {
		this.missedPunchDocumentId = missedPunchDocumentId;
	}

}
