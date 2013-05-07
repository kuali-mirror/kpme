package org.kuali.kpme.pm.pstncontracttype;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.institution.Institution;
import org.kuali.rice.location.impl.campus.CampusBo;

import com.google.common.collect.ImmutableList;

public class PstnContractType extends HrBusinessObject {
	//KPME-2273/1965 Primary Business Keys List.	
		public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
			    .add("name")
			    .build();

		private static final long serialVersionUID = 1L;
		
		private String pmCntrctTypeId;		
		private String name;
		private String description;
		private String institution;
		private String campus;
		
		private CampusBo campusObj;
		private Institution institutionObj;

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getUniqueKey() {
		return getName() + "_" + getInstitution() + "_" + getCampus();
	}

	public String getPmCntrctTypeId() {
		return pmCntrctTypeId;
	}

	public void setPmCntrctTypeId(String pmCntrctTypeId) {
		this.pmCntrctTypeId = pmCntrctTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	

}
