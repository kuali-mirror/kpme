package org.kuali.kpme.pm.classification;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.kuali.rice.location.impl.campus.CampusBo;
import org.kuali.kpme.core.bo.HrBusinessObject;

public class Classification extends HrBusinessObject {

	private static final long serialVersionUID = 1L;

	private String pmPositionClassId;
	private String positionClass;
	private String classificationTitle;
	private String institution;
	private String campus;
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
	
	private CampusBo campusObj;
	
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

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
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

	public CampusBo getCampusObj() {
		return campusObj;
	}

	public void setCampusObj(CampusBo campusObj) {
		this.campusObj = campusObj;
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
