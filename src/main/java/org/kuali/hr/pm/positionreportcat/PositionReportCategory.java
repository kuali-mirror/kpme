package org.kuali.hr.pm.positionreportcat;

import org.kuali.hr.pm.institution.Institution;
import org.kuali.hr.pm.positionreporttype.PositionReportType;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.location.impl.campus.CampusBo;

public class PositionReportCategory extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	
	String pmPositionReportCatId;
	String positionReportCat;
	String positionReportType;
	String description;
	String institution;
	String campus;
	
	CampusBo campusObj;
	Institution institutionObj;
	PositionReportType prtObj;
	
	
	@Override
	public String getId() {
		return getPmPositionReportCatId();
	}

	@Override
	public void setId(String id) {
		setPmPositionReportCatId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionReportCat() + "_" + getPositionReportType();
	}

	public String getPositionReportType() {
		return positionReportType;
	}

	public void setPositionReportType(String positionReportType) {
		this.positionReportType = positionReportType;
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
	
	public Institution getInstitutionObj() {
		return institutionObj;
	}

	public void setInstitutionObj(Institution institutionObj) {
		this.institutionObj = institutionObj;
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

	public String getPmPositionReportCatId() {
		return pmPositionReportCatId;
	}

	public void setPmPositionReportCatId(String pmPositionReportCatId) {
		this.pmPositionReportCatId = pmPositionReportCatId;
	}

	public String getPositionReportCat() {
		return positionReportCat;
	}

	public void setPositionReportCat(String positionReportCat) {
		this.positionReportCat = positionReportCat;
	}

	public PositionReportType getPrtObj() {
		return prtObj;
	}

	public void setPrtObj(PositionReportType prtObj) {
		this.prtObj = prtObj;
	}

}
