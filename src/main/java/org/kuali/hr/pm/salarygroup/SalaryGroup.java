package org.kuali.hr.pm.salarygroup;

import java.math.BigDecimal;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.location.impl.campus.CampusBo;

public class SalaryGroup  extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	
	private String pmSalaryGroupId;
	private String salaryGroup;
	private String description;
	private String institution;
	private String campus;
	private BigDecimal percentTime;
	private Integer payMonths;
	private Integer workMonths;
	private String benefitsEligible;
	private String leaveEligible;	

	CampusBo campusObj;
	@Override
	public String getId() {
		return getPmSalaryGroupId();
	}

	@Override
	public void setId(String id) {
		setPmSalaryGroupId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getSalaryGroup() + "_" + getInstitution() + "_" + getCampus();
	}

	public String getPmSalaryGroupId() {
		return pmSalaryGroupId;
	}

	public void setPmSalaryGroupId(String pmSalaryGroupId) {
		this.pmSalaryGroupId = pmSalaryGroupId;
	}

	public String getSalaryGroup() {
		return salaryGroup;
	}

	public void setSalaryGroup(String salaryGroup) {
		this.salaryGroup = salaryGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public CampusBo getCampusObj() {
		return campusObj;
	}

	public void setCampusObj(CampusBo campusObj) {
		this.campusObj = campusObj;
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

	public Integer getPayMonths() {
		return payMonths;
	}

	public void setPayMonths(Integer payMonths) {
		this.payMonths = payMonths;
	}

	public Integer getWorkMonths() {
		return workMonths;
	}

	public void setWorkMonths(Integer workMonths) {
		this.workMonths = workMonths;
	}

	public BigDecimal getPercentTime() {
		return percentTime;
	}

	public void setPercentTime(BigDecimal percentTime) {
		this.percentTime = percentTime;
	}

}
