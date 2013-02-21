package org.kuali.hr.pm.institution;

import org.joda.time.DateTime;

import org.kuali.hr.time.HrBusinessObject;

public class Institution extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pmInstitutionId;
	private String insitutionCode;
	private String description;
	private boolean active;
	
	public String getInsitutionCode() {
		return insitutionCode;
	}
	
	public void setInsitutionCode(String insitutionCode) {
		this.insitutionCode = insitutionCode;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String getId() {
		return pmInstitutionId;
	}

	@Override
	public void setId(String id) {
		setPmInstitutionId(id);
	}

	@Override
	protected String getUniqueKey() {
		return pmInstitutionId;
	}

	public String getPmInstitutionId() {
		return pmInstitutionId;
	}

	public void setPmInstitutionId(String pmInstitutionId) {
		this.pmInstitutionId = pmInstitutionId;
	}	
}
