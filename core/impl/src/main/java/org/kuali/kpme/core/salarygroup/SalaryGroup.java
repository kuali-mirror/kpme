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
package org.kuali.kpme.core.salarygroup;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.salarygroup.SalaryGroupContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.util.HrConstants;

import com.google.common.collect.ImmutableList;
public class SalaryGroup extends HrBusinessObject implements SalaryGroupContract {

	private static final long serialVersionUID = 8169672203236887348L;

	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "SalaryGroup";
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add("hrSalGroup")
            .build();

	
	private String hrSalGroupId;
	private String hrSalGroup;
	private String descr;
	private boolean history;

	// fields added to position management
	private String institution;
	private String location;
	private BigDecimal percentTime;
	private String benefitsEligible;
	private String leaveEligible;
	private String leavePlan;

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public String getHrSalGroupId() {
		return hrSalGroupId;
	}

	public void setHrSalGroupId(String hrSalGroupId) {
		this.hrSalGroupId = hrSalGroupId;
	}

	public String getHrSalGroup() {
		return hrSalGroup;
	}

	public void setHrSalGroup(String hrSalGroup) {
		this.hrSalGroup = hrSalGroup;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String getUniqueKey() {
		return getHrSalGroup() + "_" + getInstitution() + "_" + getLocation();
	}

	@Override
	public String getId() {
		return getHrSalGroupId();
	}

	@Override
	public void setId(String id) {
		setHrSalGroupId(id);
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public BigDecimal getPercentTime() {
		return percentTime;
	}

	public void setPercentTime(BigDecimal percentTime) {
		this.percentTime = percentTime;
	}

	public String getBenefitsEligible() {
		return benefitsEligible;
	}

	public void setBenefitsEligible(String benefitsEligible) {
		this.benefitsEligible = benefitsEligible;
	}

	public String getLeaveEligible() {
		return leaveEligible;
	}

	public void setLeaveEligible(String leaveEligible) {
		this.leaveEligible = leaveEligible;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
