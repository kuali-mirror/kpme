/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.time.clocklog;

import java.sql.Timestamp;

import javax.persistence.Transient;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class ClockLog extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = -6928657854016622568L;
	
	private String tkClockLogId;
    private String principalId;
    private Long jobNumber;
    private Long workArea;
    private Long task;
    private Timestamp clockTimestamp;
    private String clockTimestampTimezone;
    private String clockAction;
    private String ipAddress;
    private String userPrincipalId;
    private Timestamp timestamp;
    private boolean unapprovedIP = false;
    @Transient
    private String documentId;
    
    private String missedPunchDocumentId;

    private Job job;
    private WorkArea workAreaObj;
    private Task taskObj;

    private Person principal;

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

	public String getTkClockLogId() {
		return tkClockLogId;
	}

	public void setTkClockLogId(String tkClockLogId) {
		this.tkClockLogId = tkClockLogId;
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

	public boolean getUnapprovedIP() {
		return unapprovedIP;
	}

	public void setUnapprovedIP(boolean unapprovedIP) {
		this.unapprovedIP = unapprovedIP;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
}
