/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.clock.location;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.rule.TkRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.api.identity.Person;

public class ClockLocationRule extends TkRule implements DepartmentalRule {

	private static final long serialVersionUID = 959554402289679184L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "ClockLocationRule";

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
	private Boolean history;

	private WorkArea workAreaObj;
	private Job job;
	private Person principal;

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
