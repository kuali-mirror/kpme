package org.kuali.kpme.pm.positionreporttype;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.institution.Institution;
import org.kuali.rice.location.impl.campus.CampusBo;

import com.google.common.collect.ImmutableList;

public class PositionReportType extends HrBusinessObject {
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
		     .add("positionReportType")
		     .build();

	private static final long serialVersionUID = 1L;
	
	private String pmPositionReportTypeId;
	private String positionReportType;
	private String description;
	private String institution;
	private String campus;
	
	private CampusBo campusObj;
	private Institution institutionObj;	
	
	@Override
	public String getId() {
		return getPmPositionReportTypeId();
	}

	@Override
	public void setId(String id) {
		setPmPositionReportTypeId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionReportType() + "_" + getInstitution() + "_" + getCampus();
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

	public String getPmPositionReportTypeId() {
		return pmPositionReportTypeId;
	}

	public void setPmPositionReportTypeId(String pmPositionReportTypeId) {
		this.pmPositionReportTypeId = pmPositionReportTypeId;
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

}
