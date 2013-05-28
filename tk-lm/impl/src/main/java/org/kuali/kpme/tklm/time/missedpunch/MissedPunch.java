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
package org.kuali.kpme.tklm.time.missedpunch;

import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.task.Task;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class MissedPunch extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = 4494739150619504989L;
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("hh:mm aa");
	
	private String tkMissedPunchId;
	private String principalId;
	private String timesheetDocumentId;
	private Long jobNumber;
	private Long workArea;
	private Long task;
	private Date actionDateTime;
	private String clockAction;
	private String tkClockLogId;
	private Timestamp timestamp;
	
	private transient Job jobObj;
	private transient WorkArea workAreaObj;
	private transient Task taskObj;
	
	private transient boolean isAssignmentReadOnly;

	public String getTkMissedPunchId() {
		return tkMissedPunchId;
	}

	public void setTkMissedPunchId(String tkMissedPunchId) {
		this.tkMissedPunchId = tkMissedPunchId;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getTimesheetDocumentId() {
		return timesheetDocumentId;
	}

	public void setTimesheetDocumentId(String timesheetDocumentId) {
		this.timesheetDocumentId = timesheetDocumentId;
	}
	
	public String getAssignmentKey() {
		return TKUtils.formatAssignmentKey(getJobNumber(), getWorkArea(), getTask());
	}
	
	public void setAssignmentKey(String assignmentKey) {
		AssignmentDescriptionKey assignmentDescriptionKey = AssignmentDescriptionKey.get(assignmentKey);
		
		setJobNumber(assignmentDescriptionKey.getJobNumber());
		setWorkArea(assignmentDescriptionKey.getWorkArea());
		setTask(assignmentDescriptionKey.getTask());
	}
	
	public String getAssignmentValue() {
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);
		LocalDate asOfDate = timesheetDocument != null ? timesheetDocument.getAsOfDate() : null;
		return TKUtils.getAssignmentString(getPrincipalId(), getJobNumber(), getWorkArea(), getTask(), asOfDate);
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
	
    public DateTime getActionFullDateTime() {
    	return actionDateTime != null ? new DateTime(actionDateTime) : null;
    }
    
    public void setActionFullDateTime(DateTime actionFullDateTime) {
    	this.actionDateTime = actionFullDateTime != null ? actionFullDateTime.toDate() : null;
    }

	public Date getActionDateTime() {
		return actionDateTime;
	}

	public void setActionDateTime(Date actionDateTime) {
		this.actionDateTime = actionDateTime;
	}
	
    public Date getActionDate() {
    	return actionDateTime != null ? LocalDate.fromDateFields(actionDateTime).toDate() : null;
    }
    
    public void setActionDate(Date actionDate) {
    	LocalDate localDate = actionDate != null ? LocalDate.fromDateFields(actionDate) : null;
    	LocalTime localTime = actionDateTime != null ? LocalTime.fromDateFields(actionDateTime) : LocalTime.MIDNIGHT;
    	actionDateTime = localDate != null ? localDate.toDateTime(localTime).toDate() : null;
    }
    
    public String getActionTime() {
    	return actionDateTime != null ? FORMATTER.print(LocalTime.fromDateFields(actionDateTime)) : null;
    }
    
    public void setActionTime(String actionTime) {
    	LocalDate localDate = actionDateTime != null ? LocalDate.fromDateFields(actionDateTime) : LocalDate.now();
    	LocalTime localTime = actionTime != null ? FORMATTER.parseLocalTime(actionTime) : null;
    	actionDateTime = localTime != null ? localTime.toDateTime(localDate.toDateTimeAtStartOfDay()).toDate() : null;
    }

	public String getClockAction() {
		return clockAction;
	}

	public void setClockAction(String clockAction) {
		this.clockAction = clockAction;
	}

	public String getTkClockLogId() {
		return tkClockLogId;
	}

	public void setTkClockLogId(String tkClockLogId) {
		this.tkClockLogId = tkClockLogId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Job getJobObj() {
		return jobObj;
	}

	public void setJobObj(Job jobObj) {
		this.jobObj = jobObj;
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
	
	public boolean isAssignmentReadOnly() {
		return isAssignmentReadOnly;
	}
	
	public void setAssignmentReadOnly(boolean isAssignmentReadOnly) {
		this.isAssignmentReadOnly = isAssignmentReadOnly;
	}

}
