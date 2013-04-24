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
package org.kuali.hr.core.job;

import java.math.BigDecimal;

import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.core.department.Department;
import org.kuali.hr.core.location.Location;
import org.kuali.hr.core.paygrade.PayGrade;
import org.kuali.hr.core.paytype.PayType;
import org.kuali.hr.core.position.Position;
import org.kuali.hr.core.salgroup.SalGroup;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class Job extends HrBusinessObject {

	private static final long serialVersionUID = 1369595897637935064L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "Job";
	
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
	private Boolean history;
	private BigDecimal compRate = new BigDecimal(0);
	private String positionNumber;
	
	private String hrDeptId;
	private String hrPayTypeId;
	private boolean eligibleForLeave;
	
	private transient Person principal;
	private transient Department deptObj;
	private PayType payTypeObj;
	private transient Location locationObj;
    private transient PayGrade payGradeObj;
    private transient SalGroup salGroupObj;
    private transient Position positionObj;
    
    private BigDecimal fte = new BigDecimal(0); //kpme1465, chen
    private String flsaStatus;
    
	public String getFlsaStatus() {
		return flsaStatus;
	}

	public void setFlsaStatus(String flsaStatus) {
		this.flsaStatus = flsaStatus;
	}

	public BigDecimal getFte() {
		if ( this.standardHours != null ) {
			return this.standardHours.divide(new BigDecimal(40)).setScale(2);
		} else {
			return fte;
		}
	}

	public void setFte() {
		if ( this.standardHours != null ) {
			this.fte = this.standardHours.divide(new BigDecimal(40)).setScale(2);
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

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
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


	public BigDecimal getCompRate() {
		return compRate;
	}


	public void setCompRate(BigDecimal compRate) {
		if(compRate != null){
			this.compRate = compRate.setScale(TkConstants.BIG_DECIMAL_SCALE);
		} else {
			this.compRate = compRate;
		}
	}

	public Department getDeptObj() {
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

	public SalGroup getSalGroupObj() {
		return salGroupObj;
	}

	public void setSalGroupObj(SalGroup salGroupObj) {
		this.salGroupObj = salGroupObj;
	}

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionObj(Position positionObj) {
		this.positionObj = positionObj;
	}

	public Position getPositionObj() {
		return positionObj;
	}

	public String getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	public String getHrPayTypeId() {
		return hrPayTypeId;
	}

	public void setHrPayTypeId(String hrPayTypeId) {
		this.hrPayTypeId = hrPayTypeId;
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
