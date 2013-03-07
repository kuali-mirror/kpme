package org.kuali.hr.pm.positionreportsubcat;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.pm.institution.Institution;
import org.kuali.hr.pm.positionreportcat.PositionReportCategory;
import org.kuali.hr.pm.positionreporttype.PositionReportType;
import org.kuali.hr.pm.service.base.PmServiceLocator;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.location.impl.campus.CampusBo;

public class PositionReportSubCategory extends HrBusinessObject {
	
	private static final long serialVersionUID = 1L;
	String pmPositionReportSubCatId;
	String positionReportSubCat;
	String positionReportCat;
	String positionReportType;
	String description;
	String institution;
	String campus;
	
	CampusBo campusObj;
	Institution institutionObj;
	PositionReportCategory prcObj;
	
	@Override
	public String getId() {
		return getPmPositionReportSubCatId();
	}

	@Override
	public void setId(String id) {
		this.setPmPositionReportSubCatId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionReportSubCat() +this.getPositionReportCat() + "_" + this.getPositionReportType();
		
	}

	public String getPmPositionReportSubCatId() {
		return pmPositionReportSubCatId;
	}

	public void setPmPositionReportSubCatId(String pmPositionReportSubCatId) {
		this.pmPositionReportSubCatId = pmPositionReportSubCatId;
	}

	public String getPositionReportSubCat() {
		return positionReportSubCat;
	}

	public void setPositionReportSubCat(String positionReportSubCat) {
		this.positionReportSubCat = positionReportSubCat;
	}

	public String getPositionReportCat() {
		return positionReportCat;
	}

	public void setPositionReportCat(String positionReportCat) {
		this.positionReportCat = positionReportCat;
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

	public PositionReportCategory getPrcObj() {
		return prcObj;
	}

	public void setPrcObj(PositionReportCategory prcObj) {
		this.prcObj = prcObj;
	}

}
