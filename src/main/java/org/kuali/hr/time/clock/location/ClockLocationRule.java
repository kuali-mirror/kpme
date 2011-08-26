package org.kuali.hr.time.clock.location;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.rule.TkRule;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.bo.Person;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class ClockLocationRule extends TkRule implements DepartmentalRule {

	private static final long serialVersionUID = 1L;

	private Long tkClockLocationRuleId;

	private Department department;
	private String dept;
	private Long hrDeptId;
	
	private Long workArea;
	private Long tkWorkAreaId;
	private String principalId;
	private Long jobNumber;
	private Long hrJobId;

	private String ipAddress;
	private String userPrincipalId;
	private Timestamp timestamp;
	private Boolean history;

	private WorkArea workAreaObj;
	private Job job;
	private Person principal;

	@Override
	protected LinkedHashMap<String,Object> toStringMapper() {
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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	public Long getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}
	public Department getDepartment() {
	    return department;
	}

	public void setDepartment(Department department) {
	    this.department = department;
	}

	public WorkArea getWorkAreaObj() {
	    return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
	    this.workAreaObj = workAreaObj;
	}

	public Job getJob() {
	    return job;
	}

	public void setJob(Job job) {
	    this.job = job;
	}

	public Long getTkClockLocationRuleId() {
		return tkClockLocationRuleId;
	}

	public void setTkClockLocationRuleId(Long tkClockLocationRuleId) {
		this.tkClockLocationRuleId = tkClockLocationRuleId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	public Boolean getHistory() {
		return history;
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public Long getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(Long hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public Long getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(Long hrJobId) {
		this.hrJobId = hrJobId;
	}

	@Override
	protected String getUniqueKey() {
		String clockLocKey = getDept()+"_"+getIpAddress()+"_"+getPrincipalId()+"_"+
		(getJobNumber()!=null ? getJobNumber().toString(): "") +"_" + 
		(getWorkArea() !=null ? getWorkArea().toString() : "");
		
		return clockLocKey;
	}

	@Override
	public Long getId() {
		return getTkClockLocationRuleId();
	}

	@Override
	public void setId(Long id) {
		setTkClockLocationRuleId(id);
	}


}
