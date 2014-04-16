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
package org.kuali.kpme.tklm.time.missedpunch;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.util.KpmeUtils;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.kpme.tklm.api.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.api.time.missedpunch.MissedPunchContract;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import java.sql.Timestamp;
import java.util.Date;

public class MissedPunchBo extends PersistableBusinessObjectBase implements MissedPunchContract {

	private static final long serialVersionUID = 4494739150619504989L;
    public static final ModelObjectUtils.Transformer<MissedPunchBo, MissedPunch> toMissedPunch =
            new ModelObjectUtils.Transformer<MissedPunchBo, MissedPunch>() {
                public MissedPunch transform(MissedPunchBo input) {
                    return MissedPunchBo.to(input);
                };
            };
    public static final ModelObjectUtils.Transformer<MissedPunch, MissedPunchBo> toMissedPunchBo =
            new ModelObjectUtils.Transformer<MissedPunch, MissedPunchBo>() {
                public MissedPunchBo transform(MissedPunch input) {
                    return MissedPunchBo.from(input);
                };
            };
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
    private String groupKeyCode;

    private transient HrGroupKeyBo groupKey;
	private transient String principalName;
    private transient String personName;
	private transient JobBo jobObj;
	private transient WorkAreaBo workAreaObj;
    private transient DepartmentBo departmentObj;
	private transient TaskBo taskObj;
    private transient LocalDate localDate;
    private transient LocalTime localTime;
    private transient Person person;
	
	private transient boolean isAssignmentReadOnly;
    private transient String note;
    private transient String missedPunchDocId;
    private transient String missedPunchDocStatus;

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
		return KpmeUtils.formatAssignmentKey(getGroupKeyCode(), getJobNumber(), getWorkArea(), getTask());
	}
	
	public void setAssignmentKey(String assignmentKey) {
		AssignmentDescriptionKey assignmentDescriptionKey = AssignmentDescriptionKey.get(assignmentKey);

        setGroupKeyCode(assignmentDescriptionKey.getGroupKeyCode());
		setJobNumber(assignmentDescriptionKey.getJobNumber());
		setWorkArea(assignmentDescriptionKey.getWorkArea());
		setTask(assignmentDescriptionKey.getTask());
	}
	
	public String getAssignmentValue() {
		return HrServiceLocator.getAssignmentService().getAssignmentDescription(getGroupKeyCode(), getPrincipalId(), getJobNumber(), getWorkArea(), getTask(), getActionFullDateTime().toLocalDate());
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

    @Override
    public String getGroupKeyCode() {
        return groupKeyCode;
    }

    public void setGroupKeyCode(String groupKeyCode) {
        this.groupKeyCode = groupKeyCode;
    }

    @Override
    public HrGroupKeyBo getGroupKey() {
        if (groupKey == null) {
            groupKey = HrGroupKeyBo.from(HrServiceLocator.getHrGroupKeyService().getHrGroupKey(getGroupKeyCode(), getActionFullDateTime().toLocalDate()));
        }
        return groupKey;
    }

    public void setGroupKey(HrGroupKeyBo groupKey) {
        this.groupKey = groupKey;
    }
	
    public DateTime getActionFullDateTime() {
    	return actionDateTime != null ? new DateTime(actionDateTime) : null;
    }

    public LocalDate getActionLocalDate() {
        return actionDateTime != null ? new LocalDate(actionDateTime) : null;
    }
    
    public void setActionFullDateTime(DateTime actionFullDateTime) {
    	this.actionDateTime = actionFullDateTime != null ? actionFullDateTime.toDate() : null;
    }

	public Date getActionDateTime() {
		return new Date(actionDateTime.getTime());
	}

    public Timestamp getActionDateTimestamp() {
        return new Timestamp(actionDateTime.getTime());
    }

	public void setActionDateTime(Date actionDateTime) {
		this.actionDateTime = new Date(actionDateTime.getTime());
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
		return timestamp == null ?  null : new Timestamp(timestamp.getTime());
	}

    public DateTime getCreateTime() {
        return timestamp == null ? null : new DateTime(timestamp.getTime());
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

	public JobBo getJobObj() {
		return jobObj;
	}

	public void setJobObj(JobBo jobObj) {
		this.jobObj = jobObj;
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

    public DepartmentBo getDepartmentObj() {
        return departmentObj;
    }

    public void setDepartmentOjb(DepartmentBo departmentObj) {
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

	public String getMissedPunchDocId() {
		if(StringUtils.isBlank(missedPunchDocId)) {
			MissedPunchDocument aDoc = TkServiceLocator.getMissedPunchDocumentService().getMissedPunchDocumentByMissedPunchId(this.getTkMissedPunchId());
			if(aDoc != null) {
				this.setMissedPunchDocId(aDoc.getDocumentNumber());
			}
		}
		
		return missedPunchDocId;
	}

	public void setMissedPunchDocId(String missedPunchDocId) {
		this.missedPunchDocId = missedPunchDocId;
	}

	public String getMissedPunchDocStatus() {
		if(StringUtils.isBlank(missedPunchDocStatus)) {
            String docId = getMissedPunchDocId();
			if(StringUtils.isNotEmpty(docId)) {
				DocumentStatus aStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(docId);
				if(aStatus != null) {
					this.setMissedPunchDocStatus(aStatus.getLabel());
				}
			}
		}
		
		return missedPunchDocStatus;
	}

	public void setMissedPunchDocStatus(String missedPunchDocStatus) {
		this.missedPunchDocStatus = missedPunchDocStatus;
	}

    public static MissedPunchBo from(MissedPunch im) {
        if (im == null) {
            return null;
        }
        MissedPunchBo mp = new MissedPunchBo();


        mp.setTkMissedPunchId(im.getTkMissedPunchId());
        mp.setPrincipalId(im.getPrincipalId());
        mp.setTimesheetDocumentId(im.getTimesheetDocumentId());
        mp.setJobNumber(im.getJobNumber());
        mp.setWorkArea(im.getWorkArea());
        mp.setTask(im.getTask());
        mp.setActionDateTime(im.getActionFullDateTime() == null ? null : im.getActionFullDateTime().toDate());
        mp.setClockAction(im.getClockAction());
        mp.setTkClockLogId(im.getTkClockLogId());

        mp.setPrincipalName(im.getPrincipalName());
        mp.setPersonName(im.getPersonName());

        mp.setGroupKeyCode(im.getGroupKeyCode());
        mp.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
        mp.setAssignmentReadOnly(im.isAssignmentReadOnly());
        mp.setMissedPunchDocId(im.getMissedPunchDocId());
        mp.setMissedPunchDocStatus(im.getMissedPunchDocStatus());

        mp.setTimestamp(im.getCreateTime() == null ? null : new Timestamp(im.getCreateTime().getMillis()));
        mp.setVersionNumber(im.getVersionNumber());
        mp.setObjectId(im.getObjectId());

        return mp;
    }

    public static MissedPunch to(MissedPunchBo bo) {
        if (bo == null) {
            return null;
        }

        return MissedPunch.Builder.create(bo).build();
    }
}