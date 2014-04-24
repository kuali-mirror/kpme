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

import java.sql.Timestamp;

import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurityContract;
import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.salarygroup.SalaryGroupBo;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EarnCodeSecurityBo extends HrBusinessObject implements EarnCodeSecurityContract {

	private static final String LOCATION = "location";
	private static final String EARN_CODE = "earnCode";
	private static final String HR_SAL_GROUP = "hrSalGroup";
	private static final String DEPT = "dept";

	private static final long serialVersionUID = -4884673156883588639L;
	
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "EarnCodeSecurity";
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(EarnCodeSecurityBo.CACHE_NAME)
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
    
    
    /*
     * convert bo to immutable
     *
     * Can be used with ModelObjectUtils:
     *
     * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfEarnCodeSecurityBo, EarnCodeSecurityBo.toImmutable);
     */
    public static final ModelObjectUtils.Transformer<EarnCodeSecurityBo, EarnCodeSecurity> toImmutable =
    		new ModelObjectUtils.Transformer<EarnCodeSecurityBo, EarnCodeSecurity>() {
    	public EarnCodeSecurity transform(EarnCodeSecurityBo input) {
    		return EarnCodeSecurityBo.to(input);
    	};
    };
    /*
     * convert immutable to bo
     *
     * Can be used with ModelObjectUtils:
     *
     * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfEarnCodeSecurity, EarnCodeSecurityBo.toBo);
     */
    public static final ModelObjectUtils.Transformer<EarnCodeSecurity, EarnCodeSecurityBo> toBo =
    		new ModelObjectUtils.Transformer<EarnCodeSecurity, EarnCodeSecurityBo>() {
    	public EarnCodeSecurityBo transform(EarnCodeSecurity input) {
    		return EarnCodeSecurityBo.from(input);
    	};
    };

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
	
	public static EarnCodeSecurityBo from(EarnCodeSecurity im) {
	    if (im == null) {
	        return null;
	    }
	    EarnCodeSecurityBo ecs = new EarnCodeSecurityBo();
	    ecs.setHrEarnCodeSecurityId(im.getHrEarnCodeSecurityId());
	    ecs.setDept(im.getDept());
	    ecs.setHrSalGroup(im.getHrSalGroup());
	    ecs.setEarnCode(im.getEarnCode());
	    ecs.setEmployee(im.isEmployee());
		ecs.setApprover(im.isApprover());
		ecs.setPayrollProcessor(im.isPayrollProcessor());
		ecs.setLocation(im.getLocation());
		ecs.setEarnCodeType(im.getEarnCodeType());
		
		ecs.setSalaryGroupObj(im.getSalaryGroupObj() == null ? null : SalaryGroupBo.from(im.getSalaryGroupObj()));
		ecs.setDepartmentObj(im.getDepartmentObj() == null ? null : DepartmentBo.from(im.getDepartmentObj()));
		ecs.setEarnCodeObj(im.getEarnCodeObj() == null ? null : EarnCodeBo.from(im.getEarnCodeObj()));
		ecs.setJobObj(im.getJobObj() == null ? null : JobBo.from(im.getJobObj()));
	    ecs.setLocationObj(im.getLocationObj() == null ? null : LocationBo.from(im.getLocationObj()));
	    
	    copyCommonFields(ecs, im);
	 
	    return ecs;
	}

	public static void copyCommonFields(HrBusinessObject dest, KpmeEffectiveDataTransferObject src) {
	    dest.setEffectiveDate(src.getEffectiveLocalDate() == null ? null : src.getEffectiveLocalDate().toDate());
	    dest.setActive(src.isActive());
	    if (src.getCreateTime() != null) {
	        dest.setTimestamp(new Timestamp(src.getCreateTime().getMillis()));
	    }
	    dest.setUserPrincipalId(src.getUserPrincipalId());
	    dest.setVersionNumber(src.getVersionNumber());
	    dest.setObjectId(src.getObjectId());
	}
	
	public static EarnCodeSecurity to(EarnCodeSecurityBo bo) {
	    if (bo == null) {
	        return null;
	    }
	    return EarnCodeSecurity.Builder.create(bo).build();
	}
	
}
