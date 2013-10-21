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

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.Task;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.tklm.api.time.missedpunch.MissedPunchContract;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class MissedPunch extends PersistableBusinessObjectBase implements MissedPunchContract {

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
	
	private transient String principalName;
    private transient String personName;
	private transient Job jobObj;
	private transient WorkArea workAreaObj;
    private transient Department departmentObj;
	private transient Task taskObj;
    private transient LocalDate localDate;
    private transient LocalTime localTime;
    private transient Person person;
	
	private transient boolean isAssignmentReadOnly;
    private transient String note;

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
	
    public Date getRelativeEffectiveDate() {
        return getActionDate();
    }

    public void setRelativeEffectiveDate(Date relativeEffectiveDate) {
        //do nothing
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

    public Timestamp getActionDateTimestamp() {
        return new Timestamp(actionDateTime.getTime());
    }

	public void setActionDateTime(Date actionDateTime) {
		this.actionDateTime = actionDateTime;
	}
	
    public Date getActionDate() {
    	return actionDateTime != null ? LocalDate.fromDateFields(actionDateTime).toDate() : (getLocalDate() != null ? getLocalDate().toDate() : null);
    }
    
    public void setActionDate(Date actionDate) {
    	setLocalDate(actionDate != null ? LocalDate.fromDateFields(actionDate) : null);
    	//LocalTime localTime = actionDateTime != null ? LocalTime.fromDateFields(actionDateTime) : LocalTime.MIDNIGHT;
        if (localDate != null
                && localTime != null) {
    	    actionDateTime = localDate.toDateTime(localTime).toDate();
        }
    }
    
    public String getActionTime() {
    	return actionDateTime != null ? FORMATTER.print(LocalTime.fromDateFields(actionDateTime)) : getLocalTimeString();
    }
    
    public void setActionTime(String actionTime) {
        if (StringUtils.isNotBlank(actionTime)) {
            setLocalTime(actionTime != null ? FORMATTER.parseLocalTime(actionTime) : null);
            if (localDate != null
                    && localTime != null) {
                actionDateTime = localTime.toDateTime(localDate.toDateTimeAtStartOfDay()).toDate();
            }
        }
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

	public String getPrincipalName() {
		if (StringUtils.isBlank(principalName) && StringUtils.isNotBlank(principalId)) {
			Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(principalId);
            principalName = principal != null ? principal.getPrincipalName() : null;
		}
		
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getPersonName() {
		if (StringUtils.isBlank(personName) && StringUtils.isNotBlank(principalId)) {
			EntityNamePrincipalName entityNamePrincipalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
			return entityNamePrincipalName.getDefaultName().getCompositeName();
		}
		
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    protected String getLocalTimeString() {
        if (getLocalTime() != null) {
            return FORMATTER.print(getLocalTime());
        } else {
            return StringUtils.EMPTY;
        }
    }

    public String getDepartment() {
        if (getAssignmentKey() != null) {
            Assignment a = HrServiceLocator.getAssignmentService().getAssignment(getPrincipalId(), AssignmentDescriptionKey.get(getAssignmentKey()), getActionFullDateTime().toLocalDate());
            return a != null ? (a.getJob() != null ? a.getJob().getDept() : null) : null;
        }
        return null;
    }

    public Department getDepartmentObj() {
        return departmentObj;
    }

    public void setDepartmentOjb(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}