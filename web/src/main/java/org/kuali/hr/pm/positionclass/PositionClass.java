package org.kuali.hr.pm.positionclass;

import java.math.BigDecimal;

import org.kuali.hr.time.HrBusinessObject;

public class PositionClass extends HrBusinessObject {

	private static final long serialVersionUID = 1L;

	private String pmPositionClassId;
	private String positionClass;
	private String classificationTitle;
	private String institution;
	private String campus;
	private String salaryGroup;
	private BigDecimal percentTime;
	private Integer workMonths;
	private String benefitsEligible;
	private String leaveEligible;
	private String leavePlan;
	private String positionReportGroup;
	private String positionType;
	private String poolEligible;
	private String tenureEligible;
	// list of duties
	// list of position qualifier types
	// list of position flags
	private String externalReference;
		
	
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

	public String getPmPositionClassId() {
		return pmPositionClassId;
	}

	public void setPmPositionClassId(String pmPositionClassId) {
		this.pmPositionClassId = pmPositionClassId;
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

}
