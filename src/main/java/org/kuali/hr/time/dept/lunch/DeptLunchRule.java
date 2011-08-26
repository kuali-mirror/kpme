package org.kuali.hr.time.dept.lunch;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.bo.Person;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class DeptLunchRule extends HrBusinessObject implements DepartmentalRule {

    private static final long serialVersionUID = 1L;

    private Long tkDeptLunchRuleId;
    private String dept;
    private Long workArea;
    private String principalId;
    private Long jobNumber;    
    private BigDecimal deductionMins;
    private BigDecimal shiftHours;
    private String userPrincipalId;

    private Long tkWorkAreaId;
    private Long hrDeptId;
    private Long hrJobId;

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


	@SuppressWarnings({ "rawtypes" })
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

	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public Long getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(Long hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	public Long getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(Long hrJobId) {
		this.hrJobId = hrJobId;
	}

	@Override
	protected String getUniqueKey() {
		return getDept() + "_" + getWorkArea() != null ? getWorkArea().toString() : "" + "_" + 
				getPrincipalId() + "_" + getJobNumber() != null ? getJobNumber().toString() : "";
	}

	@Override
	public Long getId() {
		return getTkDeptLunchRuleId();
	}

	@Override
	public void setId(Long id) {
		setTkDeptLunchRuleId(id);
	}
}
