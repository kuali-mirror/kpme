package org.kuali.hr.time.dept.lunch;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class DeptLunchRule extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 1L;

    private Long tkDeptLunchRuleId;
    private String dept;
    private Long workArea;
    private String principalId;         // like principal id
    private Long jobNumber;    // like job number
    private Date effectiveDate;
    private boolean active;
    private boolean requiredClockFl;
    private BigDecimal maxMins;
    private String userPrincipalId;

    private Timestamp timestamp;

    WorkArea workAreaObj;
    Department departmentObj;   
    private Job job;

	public Job getJob() {
		return job;
	}


	public void setJob(Job job) {
		this.job = job;
	}


	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}


	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}


	public Department getDepartmentObj() {
		return departmentObj;
	}


	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
	}


	@SuppressWarnings("unchecked")
	@Override
    protected LinkedHashMap toStringMapper() {
	// TODO Auto-generated method stub
	LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();
		toStringMap.put("principalId", principalId);
		return toStringMap;
    }

    public Long getWorkArea() {
		return workArea;
	}


	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
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


    public boolean isRequiredClockFl() {
		return requiredClockFl;
	}


	public void setRequiredClockFl(boolean requiredClockFl) {
		this.requiredClockFl = requiredClockFl;
	}


	public BigDecimal getMaxMins() {
        return maxMins;
    }


    public void setMaxMins(BigDecimal maxMins) {
        this.maxMins = maxMins;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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


	public String getUserPrincipalId() {
        return userPrincipalId;
    }


    public void setUserPrincipalId(String userPrincipalId) {
        this.userPrincipalId = userPrincipalId;
    }

	public Long getTkDeptLunchRuleId() {
		return tkDeptLunchRuleId;
	}


	public void setTkDeptLunchRuleId(Long tkDeptLunchRuleId) {
		this.tkDeptLunchRuleId = tkDeptLunchRuleId;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}
}
