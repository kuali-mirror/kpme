/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.rules.clocklocation;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.core.authorization.DepartmentalRule;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.tklm.api.time.rules.clocklocation.ClockLocationRuleContract;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.time.rules.TkRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kim.api.identity.Person;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class ClockLocationRule extends TkRule implements DepartmentalRule, ClockLocationRuleContract {

	private static final String PRINCIPAL_ID = "principalId";

	private static final String JOB_NUMBER = "jobNumber";

	private static final String WORK_AREA = "workArea";

	private static final String DEPT = "dept";

	private static final long serialVersionUID = 959554402289679184L;

	public static final String CACHE_NAME = TkConstants.CacheNamespace.NAMESPACE_PREFIX + "ClockLocationRule";
	//KPME-2273/1965 Primary Business Keys List. 
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(DEPT)
            .add(WORK_AREA)
            .add(JOB_NUMBER)
            .add(PRINCIPAL_ID)
            .build();


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
	private transient Person principal;

	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(DEPT, this.getDept())
			.put(WORK_AREA, this.getWorkArea())
			.put(JOB_NUMBER, this.getJobNumber())
			.put(PRINCIPAL_ID, this.getPrincipalId())
			.build();
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
