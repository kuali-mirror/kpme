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
package org.kuali.kpme.core.bo.earncode.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.location.Location;
import org.kuali.kpme.core.bo.salarygroup.SalaryGroup;

import com.google.common.collect.ImmutableList;

public class EarnCodeSecurity extends HrBusinessObject {

	private static final long serialVersionUID = -4884673156883588639L;
	
	public static final String CACHE_NAME = "KPME/" + "EarnCodeSecurity";
    private static final String[] PRIVATE_CACHES_FOR_FLUSH = {EarnCodeSecurity.CACHE_NAME, EarnCode.CACHE_NAME};
    public static final List<String> CACHE_FLUSH = Collections.unmodifiableList(Arrays.asList(PRIVATE_CACHES_FOR_FLUSH));
	//KPME-2273/1965 Primary Business Keys List. Will be using this from now on instead.	
    public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("dept")
            .add("hrSalGroup")
            .add("earnCode")
            .add("location")
            .build();

	private String hrEarnCodeSecurityId;
	private String dept;
	private String hrSalGroup;
	private String earnCode;
	private boolean employee;
	private boolean approver;
	private String location;
	private String earnCodeType;
	
	private String hrDeptId;
	private String hrSalGroupId;
	private String hrEarnCodeId;
	private String hrLocationId;
	
	private SalaryGroup  salaryGroupObj;
	private Department departmentObj;
	private EarnCode earnCodeObj;
    private Job jobObj;
    private Location locationObj;
    private String history;

	public String getHrEarnCodeSecurityId() {
		return hrEarnCodeSecurityId;
	}

	public void setHrEarnCodeSecurityId(String hrEarnCodeSecurityId) {
		this.hrEarnCodeSecurityId = hrEarnCodeSecurityId;
	}

	public String getEarnCodeType() {
		return earnCodeType;
	}

	public void setEarnCodeType(String earnCodeType) {
		this.earnCodeType = earnCodeType;
	}

	public SalaryGroup getSalaryGroupObj() {
		return salaryGroupObj;
	}

	public void setSalaryGroupObj(SalaryGroup salaryGroupObj) {
		this.salaryGroupObj = salaryGroupObj;
	}

	public Department getDepartmentObj() {
		return departmentObj;
	}

	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
	}
	
	public boolean isEmployee() {
		return employee;
	}

	public void setEmployee(boolean employee) {
		this.employee = employee;
	}

	public boolean isApprover() {
		return approver;
	}

	public void setApprover(boolean approver) {
		this.approver = approver;
	}

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}
	
	public String getDept() {
		return dept;
	}
	
	public void setDept(String dept) {
		this.dept = dept;
	}
	
	public String getHrSalGroup() {
		return hrSalGroup;
	}
	
	public void setHrSalGroup(String hrSalGroup) {
		this.hrSalGroup = hrSalGroup;
	}
	
	public String getEarnCode() {
		return earnCode;
	}
	
	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public Job getJobObj() {
		return jobObj;
	}
	
	public void setJobObj(Job jobObj) {
		this.jobObj = jobObj;
	}
	
	public Location getLocationObj() {
		return locationObj;
	}
	
	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getHrDeptId() {
		return hrDeptId;
	}
	
	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}
	
	public String getHrSalGroupId() {
		return hrSalGroupId;
	}
	
	public void setHrSalGroupId(String hrSalGroupId) {
		this.hrSalGroupId = hrSalGroupId;
	}
	
	public String getHrEarnCodeId() {
		return hrEarnCodeId;
	}
	
	public void setHrEarnCodeId(String hrEarnCodeId) {
		this.hrEarnCodeId = hrEarnCodeId;
	}
	
	public String getHrLocationId() {
		return hrLocationId;
	}
	
	public void setHrLocationId(String hrLocationId) {
		this.hrLocationId = hrLocationId;
	}
	
	@Override
	public String getUniqueKey() {
		return dept + "_" + hrSalGroup + "_" + earnCode;
	}
	
	public String getHistory() {
		return history;
	}
	
	public void setHistory(String history) {
		this.history = history;
	}
	
	@Override
	public String getId() {
		return getHrEarnCodeSecurityId();
	}
	
	@Override
	public void setId(String id) {
		setHrEarnCodeSecurityId(id);
	}

}
