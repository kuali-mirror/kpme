package org.kuali.hr.time.workarea;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.task.Task;

public class WorkArea extends HrBusinessObject implements DepartmentalRule {

    private static final long serialVersionUID = 1L;

    private String tkWorkAreaId;
    private Long workArea;
    private String description;
    private String dept;
    private Long task;
    private String adminDescr;
    private String userPrincipalId;
    private String defaultOvertimeEarnCode;
    private String overtimeEditRole;
    private Boolean ovtEarnCode;
    
	private boolean history;
    
    private String hrDeptId;

    private List<TkRole> roles = new ArrayList<TkRole>();
    private List<TkRole> inactiveRoles = new ArrayList<TkRole>();
    private List<Task> tasks = new ArrayList<Task>();

    private Department department;
    private Task taskObj;
    private EarnCode defaultOvertimeEarnCodeObj;

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


	public List<Task> getTasks() {
	    return tasks;
	}


	public void setTasks(List<Task> tasks) {
	    this.tasks = tasks;
	}


	public String getTkWorkAreaId() {
		return tkWorkAreaId;
	}


	public void setTkWorkAreaId(String tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}


	public Long getWorkArea() {
		return workArea;
	}


	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public List<TkRole> getInactiveRoles() {
		return inactiveRoles;
	}


	public void setInactiveRoles(List<TkRole> inactiveRoles) {
		this.inactiveRoles = inactiveRoles;
	}


	public List<TkRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TkRole> roles) {
		this.roles = roles;
	}

	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
	}


	public Task getTaskObj() {
		return taskObj;
	}


	public void setTaskObj(Task taskObj) {
		this.taskObj = taskObj;
	}


	public Long getTask() {
		return task;
	}


	public void setTask(Long task) {
		this.task = task;
	}

    public String getDefaultOvertimeEarnCode() {
        return defaultOvertimeEarnCode;
    }

    public void setDefaultOvertimeEarnCode(String defaultOvertimeEarnCode) {
        this.defaultOvertimeEarnCode = defaultOvertimeEarnCode;
    }

    public EarnCode getDefaultOvertimeEarnCodeObj() {
        return defaultOvertimeEarnCodeObj;
    }

    public void setDefaultOvertimeEarnCodeObj(EarnCode defaultOvertimeEarnCodeObj) {
        this.defaultOvertimeEarnCodeObj = defaultOvertimeEarnCodeObj;
    }

    public String getOvertimeEditRole() {
        return overtimeEditRole;
    }

    public void setOvertimeEditRole(String overtimeEditRole) {
        this.overtimeEditRole = overtimeEditRole;
    }

	public Boolean getOvtEarnCode() {
		return ovtEarnCode;
	}


	public void setOvtEarnCode(Boolean ovtEarnCode) {
		this.ovtEarnCode = ovtEarnCode;
	}


	public String getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}


	@Override
	public String getUniqueKey() {
		return workArea != null ? workArea.toString() : "" +"_"+dept;
	}


	@Override
	public String getId() {
		return getTkWorkAreaId();
	}


	@Override
	public void setId(String id) {
		setTkWorkAreaId(id);
	}


	public boolean isHistory() {
		return history;
	}


	public void setHistory(boolean history) {
		this.history = history;
	}
    
}
