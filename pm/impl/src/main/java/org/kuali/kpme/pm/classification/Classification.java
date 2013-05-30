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
package org.kuali.kpme.pm.classification;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.pm.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.classification.flag.ClassificationFlag;

import com.google.common.collect.ImmutableList;

public class Classification extends HrBusinessObject {

	private static final long serialVersionUID = 1L;
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
											    .add("positionClass")
											    .add("classificationTitle")
											    .add("institution")
											    .add("location")
											    .build();
	private String pmPositionClassId;
	private String positionClass;
	private String classificationTitle;
	private String institution;
	private String location;
	// salary group fields
	private String salaryGroup;
	private BigDecimal percentTime;
	private String benefitsEligible;
	private String leaveEligible;
	private String leavePlan;
	private String positionReportGroup;
	private String positionType;
	private String poolEligible;
	private String tenureEligible;
	private String externalReference;
	
	private List<ClassificationQualification> qualificationList = new LinkedList<ClassificationQualification>(); 
	private List<ClassificationDuty> dutyList = new LinkedList<ClassificationDuty>(); 
	private List<ClassificationFlag> flagList = new LinkedList<ClassificationFlag>(); 
	
	// list of position flags, need to add flag maint section to Position maint doc
	
	private Location locationObj;
	
	@Override
	public String getId() {
		return this.getPmPositionClassId();
	}

	@Override
	public void setId(String id) {
		this.setPmPositionClassId(id);
	}

	@Override
	protected String getUniqueKey() {
		return this.getPositionClass();
	}

	public String getPositionClass() {
		return positionClass;
	}

	public void setPositionClass(String positionClass) {
		this.positionClass = positionClass;
	}

	public String getClassificationTitle() {
		return classificationTitle;
	}

	public void setClassificationTitle(String classificationTitle) {
		this.classificationTitle = classificationTitle;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSalaryGroup() {
		return salaryGroup;
	}

	public void setSalaryGroup(String salaryGroup) {
		this.salaryGroup = salaryGroup;
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

	public String getPositionReportGroup() {
		return positionReportGroup;
	}

	public void setPositionReportGroup(String positionReportGroup) {
		this.positionReportGroup = positionReportGroup;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getPoolEligible() {
		return poolEligible;
	}

	public void setPoolEligible(String poolEligible) {
		this.poolEligible = poolEligible;
	}

	public String getTenureEligible() {
		return tenureEligible;
	}

	public void setTenureEligible(String tenureEligible) {
		this.tenureEligible = tenureEligible;
	}

	public String getExternalReference() {
		return externalReference;
	}

	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}

	public List<ClassificationQualification> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(
			List<ClassificationQualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public String getPmPositionClassId() {
		return pmPositionClassId;
	}

	public void setPmPositionClassId(String pmPositionClassId) {
		this.pmPositionClassId = pmPositionClassId;
	}

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

	public List<ClassificationDuty> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<ClassificationDuty> dutyList) {
		this.dutyList = dutyList;
	}

	public List<ClassificationFlag> getFlagList() {
		return flagList;
	}

	public void setFlagList(List<ClassificationFlag> flagList) {
		this.flagList = flagList;
	}

}
