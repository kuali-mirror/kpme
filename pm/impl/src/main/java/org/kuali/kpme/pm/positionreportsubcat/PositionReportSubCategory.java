package org.kuali.kpme.pm.positionreportsubcat;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.institution.Institution;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;
import org.kuali.rice.location.impl.campus.CampusBo;

import com.google.common.collect.ImmutableList;

public class PositionReportSubCategory extends HrBusinessObject {
	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
		     .add("positionReportSubCat")
		     .build();

	private static final long serialVersionUID = 1L;
	private String pmPositionReportSubCatId;
	private String positionReportSubCat;
	private String positionReportCat;
	private String positionReportType;
	private String description;
	private String institution;
	private String campus;
	
	private CampusBo campusObj;
	private Institution institutionObj;
	private PositionReportCategory prcObj;
	
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
