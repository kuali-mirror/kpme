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
package org.kuali.kpme.tklm.time.clocklog;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLogContract;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import com.google.common.collect.ImmutableList;

public class ClockLogBo extends PersistableBusinessObjectBase implements ClockLogContract {

	private static final long serialVersionUID = -6928657854016622568L;
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		            .add("task")
                    .add("groupKeyCode")
		            .add("principalId")
		            .add("workArea")
		            .add("jobNumber")
		            .build();

    private String tkClockLogId;
	private String documentId;
    private String groupKeyCode;
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

    private boolean clockedByMissedPunch;

    private transient HrGroupKey groupKey;
    private transient Job job;
    private transient WorkAreaBo workAreaObj;
    private transient TaskBo taskObj;

    private transient Person principal;
    
	public String getTkClockLogId() {
		return tkClockLogId;
	}

	public void setTkClockLogId(String tkClockLogId) {
		this.tkClockLogId = tkClockLogId;
	}
	
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
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
    
    public DateTime getClockDateTime() {
    	return clockTimestamp != null? new DateTime(clockTimestamp.getTime()) : null;
    }
    
    public void setClockDateTime(DateTime clockDateTime) {
    	clockTimestamp = clockDateTime != null ? new Timestamp(clockDateTime.getMillis()) : null;
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

    @Override
    public DateTime getCreateTime() {
        return getTimestamp() == null ? null : new DateTime(getTimestamp().getTime());
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getClockTimestampTimezone() {
        return clockTimestampTimezone;
    }

    public String getShortClockTimezone() {
        if (StringUtils.isEmpty(clockTimestampTimezone)) {
            return StringUtils.EMPTY;
        }
        return DateTimeZone.forID(clockTimestampTimezone).getShortName(getTimestamp().getTime());
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

    public String getAssignmentDescriptionKey() {
        return new AssignmentDescriptionKey(getGroupKeyCode(), getJobNumber(), getWorkArea(), getTask()).toAssignmentKeyString();
    }

    public String getDept() {
        if (job == null) {
            setJob(HrServiceLocator.getJobService().getJob(getPrincipalId(), getJobNumber(), getClockDateTime().toLocalDate()));
        }
        return job == null ? null : job.getDept();
    }

    @Override
    public String getGroupKeyCode() {
        return groupKeyCode;
    }

    public void setGroupKeyCode(String groupKeyCode) {
        this.groupKeyCode = groupKeyCode;
    }

    @Override
    public HrGroupKey getGroupKey() {
        if (groupKey == null) {
            setGroupKey(HrServiceLocator.getHrGroupKeyService().getHrGroupKey(getGroupKeyCode(), getClockDateTime().toLocalDate()));
        }
        return groupKey;
    }

    public void setGroupKey(HrGroupKey groupKey) {
        this.groupKey = groupKey;
    }

    public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public WorkAreaBo getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkAreaBo workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public TaskBo getTaskObj() {
		return taskObj;
	}

	public void setTaskObj(TaskBo taskObj) {
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

	public boolean isClockedByMissedPunch() {
		return clockedByMissedPunch;
	}

	public void setClockedByMissedPunch(boolean clockedByMissedPunch) {
		this.clockedByMissedPunch = clockedByMissedPunch;
	}

	public boolean isUnapprovedIP() {
		return unapprovedIP;
	}

	public void setUnapprovedIP(boolean unapprovedIP) {
		this.unapprovedIP = unapprovedIP;
	}

    public static ClockLogBo from(ClockLog im) {
        if (im == null) {
            return null;
        }
        ClockLogBo cl = new ClockLogBo();
        cl.setTkClockLogId(im.getTkClockLogId());
        cl.setDocumentId(im.getDocumentId());
        cl.setPrincipalId(im.getPrincipalId());
        cl.setJobNumber(im.getJobNumber());
        cl.setWorkArea(im.getWorkArea());
        cl.setTask(im.getTask());
        cl.setClockTimestamp(im.getClockDateTime() == null ? null : new Timestamp(im.getClockDateTime().getMillis()));
        cl.setClockTimestampTimezone(im.getClockTimestampTimezone());
        cl.setClockAction(im.getClockAction());
        cl.setIpAddress(im.getIpAddress());
        cl.setUserPrincipalId(im.getUserPrincipalId());
        cl.setTimestamp(im.getCreateTime() == null ? null : new Timestamp(im.getCreateTime().getMillis()));
        cl.setUnapprovedIP(im.isUnapprovedIP());
        cl.setClockedByMissedPunch(im.isClockedByMissedPunch());

        cl.setJob(im.getJob());
        cl.setGroupKeyCode(im.getGroupKeyCode());
        cl.setGroupKey(im.getGroupKey());

        cl.setVersionNumber(im.getVersionNumber());
        cl.setObjectId(im.getObjectId());

        return cl;
    }

    public static ClockLog to(ClockLogBo bo) {
        if (bo == null) {
            return null;
        }

        return ClockLog.Builder.create(bo).build();
    }
}