package org.kuali.kpme.pm.positionresponsibility;

import java.math.BigDecimal;

import org.kuali.kpme.core.bo.HrBusinessObject;

public class PositionResponsibilty extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1631206606795253956L;
	
	private String positionResponsibilityId;
	private String institution;
	private String campus;
	private String positionResponsibility;
	private BigDecimal percentage;
	
	public String getPositionResponsibilityId() {
		return positionResponsibilityId;
	}

	public void setPositionResponsibilityId(String positionResponsibilityId) {
		this.positionResponsibilityId = positionResponsibilityId;
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

	public String getPositionResponsibility() {
		return positionResponsibility;
	}

	public void setPositionResponsibility(String positionResponsibility) {
		this.positionResponsibility = positionResponsibility;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	@Override
	public String getId() {
		return this.getPositionResponsibilityId();
	}
	@Override
	public void setId(String id) {
		this.setPositionResponsibilityId(id);
		
	}

	@Override
	protected String getUniqueKey() {
		return this.getInstitution() + "_" + this.getCampus();
	}

}
