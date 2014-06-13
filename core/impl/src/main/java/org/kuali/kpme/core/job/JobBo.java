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
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.position.PositionBase;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.paygrade.PayGradeBo;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.kpme.core.position.PositionBaseBo;
import org.kuali.kpme.core.salarygroup.SalaryGroupBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class JobBo extends HrKeyedBusinessObject implements JobContract {
	static class KeyFields {
		private static final String JOB_NUMBER = "jobNumber";
		private static final String PRINCIPAL_ID = "principalId";
		//private static final String GROUP_KEY_CODE = "groupKeyCode";
	}
	private static final long serialVersionUID = 1369595897637935064L;	
	//KPME-2273/1965 Primary Business Keys List. Will be using this from now on instead.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
	            .add(KeyFields.PRINCIPAL_ID)
	            .add(KeyFields.JOB_NUMBER)
	            //.add(KeyFields.GROUP_KEY_CODE)
	            .build();
	
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "Job";
	
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(JobBo.CACHE_NAME)
            .add(AssignmentBo.CACHE_NAME)
            .add(CalendarBlockPermissions.CACHE_NAME)
            .add(RoleMember.Cache.NAME)
            .build();
	
	//private String location;
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
    private BigDecimal fte = new BigDecimal(0); //kpme1465, chen
    private String flsaStatus;
	
	private boolean eligibleForLeave;
	
	private transient Person principal;
	private transient DepartmentBo deptObj;
	private PayTypeBo payTypeObj;
	//private transient LocationBo locationObj;
    private transient PayGradeBo payGradeObj;
    private transient SalaryGroupBo salaryGroupObj;
    private transient PositionBaseBo positionObj;
    
    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.PRINCIPAL_ID, this.getPrincipalId())
				.put(KeyFields.JOB_NUMBER, this.getJobNumber())
				//.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
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

	public DepartmentBo getDeptObj() {
		if(deptObj == null) {
			this.setDeptObj(DepartmentBo.from(HrServiceLocator.getDepartmentService().getDepartment(dept, groupKeyCode, getEffectiveLocalDate())));
		}
		return deptObj;
	}


	public void setDeptObj(DepartmentBo deptObj) {
		this.deptObj = deptObj;
	}


	public PayTypeBo getPayTypeObj() {
		return payTypeObj;
	}


	public void setPayTypeObj(PayTypeBo payTypeObj) {
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
	public Boolean isPrimaryJob() {
		return primaryIndicator;
	}

	/*
	public LocationBo getLocationObj() {
        if (locationObj == null) {
            this.setLocationObj(LocationBo.from(HrServiceLocator.getLocationService().getLocation(location,getEffectiveLocalDate())));
        }
		return locationObj;
	}

	public void setLocationObj(LocationBo locationObj) {
		this.locationObj = locationObj;
	}*/

	public PayGradeBo getPayGradeObj() {
		return payGradeObj;
	}

	public void setPayGradeObj(PayGradeBo payGradeObj) {
		this.payGradeObj = payGradeObj;
	}

	public SalaryGroupBo getSalaryGroupObj() {
		return salaryGroupObj;
	}

	public void setSalaryGroupObj(SalaryGroupBo salaryGroupObj) {
		this.salaryGroupObj = salaryGroupObj;
	}

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionObj(PositionBaseBo positionObj) {
		this.positionObj = positionObj;
	}

	public PositionBaseBo getPositionObj() {
        if (positionObj == null) {
            this.setPositionObj(PositionBaseBo.from((PositionBase)HrServiceLocator.getPositionService().getPosition(positionNumber, getEffectiveLocalDate())));
        }
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

    public static JobBo from(Job im) {
        if (im == null) {
            return null;
        }
        JobBo job = new JobBo();

        //job.setLocation(im.getLocation());
        job.setHrPayType(im.getHrPayType());
        job.setPayGrade(im.getPayGrade());
        job.setStandardHours(im.getStandardHours());
        job.setHrJobId(im.getHrJobId());
        job.setPrincipalId(im.getPrincipalId());
        
        job.setGroupKeyCode(im.getGroupKeyCode());
        job.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
        
        job.setFirstName(im.getFirstName());
        job.setLastName(im.getLastName());
        job.setPrincipalName(im.getPrincipalName());
        job.setJobNumber(im.getJobNumber());
        job.setDept(im.getDept());
        job.setHrSalGroup(im.getHrSalGroup());
        job.setPrimaryIndicator(im.isPrimaryJob());
        job.setCompRate(im.getCompRate());
        job.setPositionNumber(im.getPositionNumber());
        job.setFte();
        job.setFlsaStatus(im.getFlsaStatus());
        job.setPayGrade(im.getPayGrade());
        
        job.setEligibleForLeave(im.isEligibleForLeave());
        if (im.getPayTypeObj() != null) {
            job.setPayTypeObj(PayTypeBo.from(im.getPayTypeObj()));
        } else {
            if (StringUtils.isNotEmpty(job.getHrPayType())) {
                job.setPayTypeObj(PayTypeBo.from(HrServiceLocator.getPayTypeService().getPayType(im.getHrPayType(), im.getEffectiveLocalDate())));
            }
        }


        job.setEffectiveDate(im.getEffectiveLocalDate() == null ? null : im.getEffectiveLocalDate().toDate());
        job.setActive(im.isActive());
        if (im.getCreateTime() != null) {
            job.setTimestamp(new Timestamp(im.getCreateTime().getMillis()));
        }
        job.setUserPrincipalId(im.getUserPrincipalId());
        job.setVersionNumber(im.getVersionNumber());
        job.setObjectId(im.getObjectId());

        return job;
    }

    public static Job to(JobBo bo) {
        if (bo == null) {
            return null;
        }

        return Job.Builder.create(bo).build();
    }
}
