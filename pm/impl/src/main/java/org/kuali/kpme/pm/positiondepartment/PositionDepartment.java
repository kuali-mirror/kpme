package org.kuali.kpme.pm.positiondepartment;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.institution.Institution;
import org.kuali.kpme.pm.positiondepartmentaffiliation.PositionDepartmentAffiliation;
import org.kuali.rice.location.impl.campus.CampusBo;

import com.google.common.collect.ImmutableList;
public class PositionDepartment extends HrBusinessObject {
	
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
		    .add("department")
		    .build();
	
	private static final long serialVersionUID = 1L;
	
	private String pmPositionDeptId;
	private String institution;
	private String campus;
	private String department;
	private String positionDeptAffl;
		
	private CampusBo campusObj;
	private Institution institutionObj;
	private Department departmentObj;
	private PositionDepartmentAffiliation positionDeptAfflObj;

	@Override
	public String getId() {
		return this.getPmPositionDeptId();
	}

	@Override
	public void setId(String id) {
		setPmPositionDeptId(id);
	}

	@Override
	protected String getUniqueKey() {
		return  getInstitution() + "_" + getCampus() + "_" + getDepartment() + "_" + getPositionDeptAffl()	;
	}

	/**
	 * @return the positionDeptAffl
	 */
	public String getPositionDeptAffl() {
		return positionDeptAffl;
	}

	/**
	 * @param positionDeptAffl the positionDeptAffl to set
	 */
	public void setPositionDeptAffl(String positionDeptAffl) {
		this.positionDeptAffl = positionDeptAffl;
	}

	/**
	 * @return the positionDeptAfflObj
	 */
	public PositionDepartmentAffiliation getPositionDeptAfflObj() {
		return positionDeptAfflObj;
	}

	/**
	 * @param positionDeptAfflObj the positionDeptAfflObj to set
	 */
	public void setPositionDeptAfflObj(
			PositionDepartmentAffiliation positionDeptAfflObj) {
		this.positionDeptAfflObj = positionDeptAfflObj;
	}

	/**
	 * @return the pmPositionDeptId
	 */
	public String getPmPositionDeptId() {
		return pmPositionDeptId;
	}

	/**
	 * @param pmPositionDeptId the pmPositionDeptId to set
	 */
	public void setPmPositionDeptId(String pmPositionDeptId) {
		this.pmPositionDeptId = pmPositionDeptId;
	}

	/**
	 * @return the institution
	 */
	public String getInstitution() {
		return institution;
	}

	/**
	 * @param institution the institution to set
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * @return the campus
	 */
	public String getCampus() {
		return campus;
	}

	/**
	 * @param campus the campus to set
	 */
	public void setCampus(String campus) {
		this.campus = campus;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the campusObj
	 */
	public CampusBo getCampusObj() {
		return campusObj;
	}

	/**
	 * @param campusObj the campusObj to set
	 */
	public void setCampusObj(CampusBo campusObj) {
		this.campusObj = campusObj;
	}

	/**
	 * @return the institutionObj
	 */
	public Institution getInstitutionObj() {
		return institutionObj;
	}

	/**
	 * @param institutionObj the institutionObj to set
	 */
	public void setInstitutionObj(Institution institutionObj) {
		this.institutionObj = institutionObj;
	}

	/**
	 * @return the departmentObj
	 */
	public Department getDepartmentObj() {
		return departmentObj;
	}

	/**
	 * @param departmentObj the departmentObj to set
	 */
	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
	}

	

}