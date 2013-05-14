package org.kuali.kpme.pm.pstnrptgrpsubcat;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.rice.location.impl.campus.CampusBo;

public class PositionReportGroupSubCategory extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	
	private String pmPstnRptGrpSubCatId;
	private String pstnRptGrpSubCat;
	private String positionReportGroup;
	private String positionReportSubCat;
	private String description;
	private String institution;
	private String campus;

	private CampusBo campusObj;	
	
	@Override
	public String getId() {
		return this.getPmPstnRptGrpSubCatId();
	}

	@Override
	public void setId(String id) {
		setPmPstnRptGrpSubCatId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPstnRptGrpSubCat() + "_" + getPositionReportGroup() + "_" + getPositionReportSubCat();
	}

	public String getPmPstnRptGrpSubCatId() {
		return pmPstnRptGrpSubCatId;
	}

	public void setPmPstnRptGrpSubCatId(String pmPstnRptGrpSubCatId) {
		this.pmPstnRptGrpSubCatId = pmPstnRptGrpSubCatId;
	}

	public String getPstnRptGrpSubCat() {
		return pstnRptGrpSubCat;
	}

	public void setPstnRptGrpSubCat(String pstnRptGrpSubCat) {
		this.pstnRptGrpSubCat = pstnRptGrpSubCat;
	}

	public String getPositionReportGroup() {
		return positionReportGroup;
	}

	public void setPositionReportGroup(String positionReportGroup) {
		this.positionReportGroup = positionReportGroup;
	}

	public String getPositionReportSubCat() {
		return positionReportSubCat;
	}

	public void setPositionReportSubCat(String positionReportSubCat) {
		this.positionReportSubCat = positionReportSubCat;
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

}
