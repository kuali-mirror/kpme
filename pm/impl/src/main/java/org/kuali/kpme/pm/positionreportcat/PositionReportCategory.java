package org.kuali.kpme.pm.positionreportcat;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.institution.Institution;
import org.kuali.kpme.pm.positionreporttype.PositionReportType;
import org.kuali.rice.location.impl.campus.CampusBo;

import com.google.common.collect.ImmutableList;

public class PositionReportCategory extends HrBusinessObject {
	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
		    .add("positionReportCat")
		    .build();

	private static final long serialVersionUID = 1L;
	
	private String pmPositionReportCatId;
	private String positionReportCat;
	private String positionReportType;
	private String description;
	private String institution;
	private String campus;
	
	private CampusBo campusObj;
	private Institution institutionObj;
	private PositionReportType prtObj;	
	
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
		return getPositionReportCat() + "_" + getPositionReportType() + "_" + getInstitution() + "_" + getCampus();
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
