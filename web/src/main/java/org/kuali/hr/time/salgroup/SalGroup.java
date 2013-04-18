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
package org.kuali.hr.time.salgroup;

import java.math.BigDecimal;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.location.impl.campus.CampusBo;

public class SalGroup extends HrBusinessObject {

	private static final long serialVersionUID = 8169672203236887348L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "SalGroup";

	private String hrSalGroupId;
	private String hrSalGroup;
	private String descr;
	private boolean history;

	// fields added to position management
	private String institution;
	private String campus;
	private BigDecimal percentTime;
	private Integer workMonths;
	private String benefitsEligible;
	private String leaveEligible;
	private String leavePlan;

	private CampusBo campusObj;

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
		return getHrSalGroup() + "_" + getInstitution() + "_" + getCampus();
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

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public BigDecimal getPercentTime() {
		return percentTime;
	}

	public void setPercentTime(BigDecimal percentTime) {
		this.percentTime = percentTime;
	}

	public Integer getWorkMonths() {
		return workMonths;
	}

	public void setWorkMonths(Integer workMonths) {
		this.workMonths = workMonths;
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

	public CampusBo getCampusObj() {
		return campusObj;
	}

	public void setCampusObj(CampusBo campusObj) {
		this.campusObj = campusObj;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

}
