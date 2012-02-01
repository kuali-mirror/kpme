package org.kuali.hr.time.clock.location;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.rule.TkRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.bo.Person;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ClockLocationRule extends TkRule implements DepartmentalRule {

	private static final long serialVersionUID = 1L;

	private String tkClockLocationRuleId;

	private Department department;
	private String dept;
	private String hrDeptId;
	
	private Long workArea;
	private String tkWorkAreaId;
	private String principalId;
	private Long jobNumber;
	private String hrJobId;

	private List<ClockLocationRuleIpAddress> ipAddresses = new ArrayList<ClockLocationRuleIpAddress>();
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

	public String getTkClockLocationRuleId() {
		return tkClockLocationRuleId;
	}

	public void setTkClockLocationRuleId(String tkClockLocationRuleId) {
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

	public String getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	public String getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(String tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public String getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(String hrJobId) {
		this.hrJobId = hrJobId;
	}

	@Override
	public String getUniqueKey() {
		String clockLocKey = getDept()+"_"+getPrincipalId()+"_"+
		(getJobNumber()!=null ? getJobNumber().toString(): "") +"_" + 
		(getWorkArea() !=null ? getWorkArea().toString() : "");
		
		return clockLocKey;
	}

	@Override
	public String getId() {
		return getTkClockLocationRuleId();
	}

	@Override
	public void setId(String id) {
		setTkClockLocationRuleId(id);
	}

	public List<ClockLocationRuleIpAddress> getIpAddresses() {
		if(ipAddresses.isEmpty()) {
			TkServiceLocator.getClockLocationRuleService().populateIPAddressesForCLR(this);
		}
		return ipAddresses;
	}

	public void setIpAddresses(List<ClockLocationRuleIpAddress> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}
	// for lookup and inquiry display only
	public String getIpAddressesString() {
		String aString = "";
		for(ClockLocationRuleIpAddress ip : this.getIpAddresses()) {
			aString += ip.getIpAddress() + ", ";
		}
		return aString;
	}


}
