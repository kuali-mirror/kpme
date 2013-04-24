package org.kuali.hr.pm.positionreportgroup;

import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.pm.institution.Institution;
import org.kuali.rice.location.impl.campus.CampusBo;

public class PositionReportGroup extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	
	private String pmPositionReportGroupId;
	private String positionReportGroup;
	private String description;
	private String institution;
	private String campus;
	
	private CampusBo campusObj;
	private Institution institutionObj;

	@Override
	public String getId() {
		return this.getPmPositionReportGroupId();
	}

	@Override
	public void setId(String id) {
		setPmPositionReportGroupId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionReportGroup() + "_" + getInstitution() + "_" + getCampus();
	}

	public String getPmPositionReportGroupId() {
		return pmPositionReportGroupId;
	}

	public void setPmPositionReportGroupId(String pmPositionReportGroupId) {
		this.pmPositionReportGroupId = pmPositionReportGroupId;
	}

	public String getPositionReportGroup() {
		return positionReportGroup;
	}

	public void setPositionReportGroup(String positionReportGroup) {
		this.positionReportGroup = positionReportGroup;
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
