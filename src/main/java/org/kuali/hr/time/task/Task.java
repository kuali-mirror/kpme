package org.kuali.hr.time.task;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;

public class Task extends HrBusinessObject {
    public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "Task";
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String tkTaskId;
    private Long task;
    private Long workArea;
    private String tkWorkAreaId;
    private String description;
    private String userPrincipalId;
    private String administrativeDescription;
	private WorkArea workAreaObj;
	private String workAreaDescription;

    public String getDescription()
    {
    return description;
    }

    public void setDescription(String desc)
    {
    	this.description = desc;
    }

    public String getUserPrincipalId(){
    	return userPrincipalId;
    }

    public void setUserPrincipalId(String pid)
    {
    	this.userPrincipalId = pid;
    }

    public String getAdministrativeDescription(){
    	return administrativeDescription;
    }

    public void setAdministrativeDescription(String addesc)
    {
    	this.administrativeDescription = addesc;
    }

	public String getTkTaskId() {
		return tkTaskId;
	}

	public void setTkTaskId(String tkTaskId) {
		this.tkTaskId = tkTaskId;
	}

	public Long getTask() {
		return task;
	}

	public void setTask(Long task) {
		this.task = task;
	}

	public Long getWorkArea() {
		if (this.getTkWorkAreaId() != null &&  workArea == null) {
        	WorkArea wa = TkServiceLocator.getWorkAreaService().getWorkArea(this.getTkWorkAreaId());
        	this.setWorkArea((wa != null) ? wa.getWorkArea() : null);
		}
		
		return workArea;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public String getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(String tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

	public String getWorkAreaDescription() { 
		if (this.getTkWorkAreaId() != null && StringUtils.isEmpty(workAreaDescription)) {
        	WorkArea wa = TkServiceLocator.getWorkAreaService().getWorkArea(this.getTkWorkAreaId());
        	this.setWorkAreaDescription((wa != null) ? wa.getDescription() : "");
		}
		return workAreaDescription;
	}
	
	public void setWorkAreaDescription(String workAreaDescription) {
		this.workAreaDescription = workAreaDescription;
	}

	@Override
	public String getUniqueKey() {
		return workArea + "_" + task;
	}

	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	@Override
	public String getId() {
		return getTkTaskId();
	}

	@Override
	public void setId(String id) {
		setTkTaskId(id);
	}

}
