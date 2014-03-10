/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.earncode.security;

import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurityContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.salarygroup.SalaryGroupBo;
import org.kuali.kpme.core.util.HrConstants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EarnCodeSecurity extends HrBusinessObject implements EarnCodeSecurityContract {

	private static final String LOCATION = "location";
	private static final String EARN_CODE = "earnCode";
	private static final String HR_SAL_GROUP = "hrSalGroup";
	private static final String DEPT = "dept";

	private static final long serialVersionUID = -4884673156883588639L;
	
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "EarnCodeSecurity";
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(EarnCodeSecurity.CACHE_NAME)
            .add(EarnCodeBo.CACHE_NAME)
            .add(CalendarBlockPermissions.CACHE_NAME)
            .build();
	//KPME-2273/1965 Primary Business Keys List. Will be using this from now on instead.	
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(DEPT)
            .add(HR_SAL_GROUP)
            .add(EARN_CODE)
            .add(LOCATION)
            .build();

	private String hrEarnCodeSecurityId;
	private String dept;
	private String hrSalGroup;
	private String earnCode;
	private boolean employee;
	private boolean approver;
	private boolean payrollProcessor;  // KPME-2532
	private String location;
	private String earnCodeType;
	
	private SalaryGroupBo salaryGroupObj;
	private DepartmentBo departmentObj;
	private EarnCodeBo earnCodeObj;
    private JobBo jobObj;
    private LocationBo locationObj;

    
    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(DEPT, this.getDept())
			.put(HR_SAL_GROUP, this.getHrSalGroup())
			.put(EARN_CODE, this.getEarnCode())
			.put(LOCATION, this.getLocation())
			.build();
	}
    
    
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

	public SalaryGroupBo getSalaryGroupObj() {
		return salaryGroupObj;
	}

	public void setSalaryGroupObj(SalaryGroupBo salaryGroupObj) {
		this.salaryGroupObj = salaryGroupObj;
	}

	public DepartmentBo getDepartmentObj() {
		return departmentObj;
	}

	public void setDepartmentObj(DepartmentBo departmentObj) {
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

	public boolean isPayrollProcessor() {
		return payrollProcessor;
	}

	public void setPayrollProcessor(boolean payrollProcessor) {
		this.payrollProcessor = payrollProcessor;
	}

	public EarnCodeBo getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCodeBo earnCodeObj) {
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

	public JobBo getJobObj() {
		return jobObj;
	}
	
	public void setJobObj(JobBo jobObj) {
		this.jobObj = jobObj;
	}
	
	public LocationBo getLocationObj() {
		return locationObj;
	}
	
	public void setLocationObj(LocationBo locationObj) {
		this.locationObj = locationObj;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String getUniqueKey() {
		return dept + "_" + hrSalGroup + "_" + earnCode;
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
