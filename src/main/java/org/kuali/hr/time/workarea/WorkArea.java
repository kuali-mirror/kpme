package org.kuali.hr.time.workarea;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.task.Task;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class WorkArea extends PersistableBusinessObjectBase {
    
    private static final long serialVersionUID = 1L;
    
    private Long workAreaId;
    private Date effectiveDate;
    private boolean active = false;
    private String description;
    private String deptId;
    private String overtimePreference;
    private String adminDescr;
    private String userPrincipalId;
    private Timestamp timestamp;
    
    private transient List<TkRoleAssign> roleAssignments = new ArrayList<TkRoleAssign>();
    private List<Task> tasks = new ArrayList<Task>();
    

    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
	LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();
	toStringMap.put("workAreaId", workAreaId);
	return toStringMap;
    }


    public Long getWorkAreaId() {
        return workAreaId;
    }


    public void setWorkAreaId(Long workAreaId) {
        this.workAreaId = workAreaId;
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


    public String getOvertimePreference() {
        return overtimePreference;
    }


    public void setOvertimePreference(String overtimePreference) {
        this.overtimePreference = overtimePreference;
    }


    public String getAdminDescr() {
        return adminDescr;
    }


    public void setAdminDescr(String adminDescr) {
        this.adminDescr = adminDescr;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getUserPrincipalId() {
        return userPrincipalId;
    }


    public void setUserPrincipalId(String userPrincipalId) {
        this.userPrincipalId = userPrincipalId;
    }  

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getDeptId() {
        return deptId;
    }


    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }


	public List<TkRoleAssign> getRoleAssignments() {
		return roleAssignments;
	}


	public void setRoleAssignments(List<TkRoleAssign> roleAssignments) {
		this.roleAssignments = roleAssignments;
	}


	public List<Task> getTasks() {
	    return tasks;
	}


	public void setTasks(List<Task> tasks) {
	    this.tasks = tasks;
	}
}
