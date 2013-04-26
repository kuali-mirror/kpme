package org.kuali.kpme.pm.positiontype;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.institution.Institution;
import org.kuali.rice.location.impl.campus.CampusBo;

public class PositionType extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	
	private String pmPositionTypeId;
	private String positionType;
	private String description;
	private String institution;
	private String campus;
	
	private CampusBo campusObj;
	private Institution institutionObj;

	@Override
	public String getId() {
		return this.getPmPositionTypeId();
	}

	@Override
	public void setId(String id) {
		setPmPositionTypeId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionType() + "_" + getInstitution() + "_" + getCampus();
	}

	public String getPmPositionTypeId() {
		return pmPositionTypeId;
	}

	public void setPmPositionTypeId(String pmPositionTypeId) {
		this.pmPositionTypeId = pmPositionTypeId;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String PositionType) {
		this.positionType = PositionType;
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

	public Institution getInstitutionObj() {
		return institutionObj;
	}

	public void setInstitutionObj(Institution institutionObj) {
		this.institutionObj = institutionObj;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}