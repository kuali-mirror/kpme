package org.kuali.hr.time.clock.location;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class ClockLocationRule extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = 1L;

	private Long clockLocationRuleId;

	private Department department;
	private String deptId;

	private Long workArea;
	private String principalId;
	private BigInteger jobNumber;

	private Date effectiveDate;
	private boolean active;
	private String ipAddress;
	private String userPrincipalId;
	private Timestamp timestamp;

	private WorkArea workAreaObj;
	private Job job;

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

	public BigInteger getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(BigInteger jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Long getClockLocationRuleId() {
	    return clockLocationRuleId;
	}

	public void setClockLocationRuleId(Long clockLocationRuleId) {
	    this.clockLocationRuleId = clockLocationRuleId;
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

	public String getDeptId() {
	    return deptId;
	}

	public void setDeptId(String deptId) {
	    this.deptId = deptId;
	}

	public Job getJob() {
	    return job;
	}

	public void setJob(Job job) {
	    this.job = job;
	}


}
