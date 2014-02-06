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
package org.kuali.kpme.core.job;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.core.paygrade.PayGrade;
import org.kuali.kpme.core.paytype.PayType;
import org.kuali.kpme.core.position.PositionBase;
import org.kuali.kpme.core.salarygroup.SalaryGroup;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class Job extends HrBusinessObject implements JobContract {

	private static final String JOB_NUMBER = "jobNumber";
	private static final String PRINCIPAL_ID = "principalId";
	private static final long serialVersionUID = 1369595897637935064L;	
	//KPME-2273/1965 Primary Business Keys List. Will be using this from now on instead.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
	            .add(PRINCIPAL_ID)
	            .add(JOB_NUMBER)
	            .build();
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "Job";
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(Job.CACHE_NAME)
            .add(Assignment.CACHE_NAME)
            .add(CalendarBlockPermissions.CACHE_NAME)
            .build();
	
	private String location;
	private String hrPayType;
	private String payGrade;
	private BigDecimal standardHours;
	private String hrJobId;
	private String principalId;
	private String firstName;
	private String lastName;
	private String principalName;
	private Long jobNumber;
	private String dept;
	private String hrSalGroup;
	private Boolean primaryIndicator;
	private KualiDecimal compRate = new KualiDecimal(0);
	private String positionNumber;
	
	private boolean eligibleForLeave;
	
	private transient Person principal;
	private transient Department deptObj;
	private PayType payTypeObj;
	private transient Location locationObj;
    private transient PayGrade payGradeObj;
    private transient SalaryGroup salaryGroupObj;
    private transient PositionBase positionObj;
    
    private BigDecimal fte = new BigDecimal(0); //kpme1465, chen
    private String flsaStatus;
    
    
    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(PRINCIPAL_ID, this.getPrincipalId())
				.put(JOB_NUMBER, this.getJobNumber())
				.build();
	}
    
    
	public String getFlsaStatus() {
		return flsaStatus;
	}

	public void setFlsaStatus(String flsaStatus) {
		this.flsaStatus = flsaStatus;
	}

	public BigDecimal getFte() {
		if ( this.standardHours != null ) {
			return this.standardHours.divide(new BigDecimal(40)).setScale(2, RoundingMode.HALF_EVEN);
		} else {
			return fte;
		}
	}

	public void setFte() {
		if ( this.standardHours != null ) {
			this.fte = this.standardHours.divide(new BigDecimal(40)).setScale(2, RoundingMode.HALF_EVEN);
		} else {
			this.fte = new BigDecimal(0).setScale(2);
		}
	}
	
	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public BigDecimal getStandardHours() {
		return standardHours;
	}

	public void setStandardHours(BigDecimal standardHours) {
		this.standardHours = standardHours;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		if (principal == null) {
            principal = KimApiServiceLocator.getPersonService().getPerson(this.principalId);
	    }
	    return (principal != null) ? principal.getName() : "";
	}

	public String getPrincipalName() {
		if(principalName == null && !this.getPrincipalId().isEmpty()) {
			EntityNamePrincipalName aPerson = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(getPrincipalId());
			if (aPerson != null && aPerson.getDefaultName() != null) {
                setPrincipalName(aPerson.getDefaultName().getCompositeName());
            }
		}
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public Long getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
	
	public String getHrPayType() {
		return hrPayType;
	}

	public void setHrPayType(String hrPayType) {
		this.hrPayType = hrPayType;
	}

	public String getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(String hrJobId) {
		this.hrJobId = hrJobId;
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


	public KualiDecimal getCompRate() {
		return compRate;
	}


	public void setCompRate(KualiDecimal compRate) {
        this.compRate = compRate;
	}

	public Department getDeptObj() {
		if(deptObj == null) {
			this.setDeptObj((Department)HrServiceLocator.getDepartmentService().getDepartment(dept, getEffectiveLocalDate()));
		}
		return deptObj;
	}


	public void setDeptObj(Department deptObj) {
		this.deptObj = deptObj;
	}


	public PayType getPayTypeObj() {
		return payTypeObj;
	}


	public void setPayTypeObj(PayType payTypeObj) {
		this.payTypeObj = payTypeObj;
	}


	public Person getPrincipal() {
		return principal;
	}


	public void setPrincipal(Person principal) {
		this.principal = principal;
	}


	public void setPrimaryIndicator(Boolean primaryIndicator) {
		this.primaryIndicator = primaryIndicator;
	}


	public Boolean getPrimaryIndicator() {
		return primaryIndicator;
	}

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

	public PayGrade getPayGradeObj() {
		return payGradeObj;
	}

	public void setPayGradeObj(PayGrade payGradeObj) {
		this.payGradeObj = payGradeObj;
	}

	public SalaryGroup getSalaryGroupObj() {
		return salaryGroupObj;
	}

	public void setSalaryGroupObj(SalaryGroup salaryGroupObj) {
		this.salaryGroupObj = salaryGroupObj;
	}

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionObj(PositionBase positionObj) {
		this.positionObj = positionObj;
	}

	public PositionBase getPositionObj() {
		return positionObj;
	}

	@Override
	public String getUniqueKey() {
		return getPrincipalId() + "_" + getJobNumber();
	}

	@Override
	public String getId() {
		return getHrJobId();
	}

	@Override
	public void setId(String id) {
		setHrJobId(id);
	}
	public boolean isEligibleForLeave() {
		return eligibleForLeave;
	}

	public void setEligibleForLeave(boolean eligibleForLeave) {
		this.eligibleForLeave = eligibleForLeave;
	}
	
}
