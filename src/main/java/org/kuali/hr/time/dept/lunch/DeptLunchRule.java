package org.kuali.hr.time.dept.lunch;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.api.identity.Person;

public class DeptLunchRule extends HrBusinessObject implements DepartmentalRule {

    private static final long serialVersionUID = 1L;

    private String tkDeptLunchRuleId;
    private String dept;
    private Long workArea;
    private String principalId;
    private Long jobNumber;    
    private BigDecimal deductionMins;
    private BigDecimal shiftHours;
    private String userPrincipalId;

    private String tkWorkAreaId;
    private String hrDeptId;
    private String hrJobId;

    WorkArea workAreaObj;
    Department departmentObj;
    private Job job;
	private Person principal;
	
	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

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

    public Long getWorkArea() {
		return workArea;
	}


	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
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

	public String getTkDeptLunchRuleId() {
		return tkDeptLunchRuleId;
	}


	public void setTkDeptLunchRuleId(String tkDeptLunchRuleId) {
		this.tkDeptLunchRuleId = tkDeptLunchRuleId;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public BigDecimal getDeductionMins() {
		return deductionMins;
	}


	public void setDeductionMins(BigDecimal deductionMins) {
		this.deductionMins = deductionMins;
	}


	public BigDecimal getShiftHours() {
		return shiftHours;
	}


	public void setShiftHours(BigDecimal shiftHours) {
		this.shiftHours = shiftHours;
	}

	public String getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(String tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public String getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	public String getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(String hrJobId) {
		this.hrJobId = hrJobId;
	}

	@Override
	public String getUniqueKey() {
		return getDept() + "_" + getWorkArea() != null ? getWorkArea().toString() : "" + "_" + 
				getPrincipalId() + "_" + getJobNumber() != null ? getJobNumber().toString() : "";
	}

	@Override
	public String getId() {
		return getTkDeptLunchRuleId();
	}

	@Override
	public void setId(String id) {
		setTkDeptLunchRuleId(id);
	}
}
