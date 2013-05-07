package org.kuali.kpme.pm.positionappointment;

import org.kuali.kpme.core.bo.institution.Institution;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.rice.location.impl.campus.CampusBo;

public class PositionAppointment extends HrBusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	private String pmPositionAppointmentId;
	private String positionAppointment;
	private String description;
	private String institution;
	private String campus;
	
	private CampusBo campusObj;
	private Institution institutionObj;


	public String getPmPositionAppointmentId() {
		return pmPositionAppointmentId;
	}

	public void setPmPositionAppointmentId(String pmPositionAppointmentId) {
		this.pmPositionAppointmentId = pmPositionAppointmentId;
	}

	public String getPositionAppointment() {
		return positionAppointment;
	}

	public void setPositionAppointment(String positionAppointment) {
		this.positionAppointment = positionAppointment;
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
	@Override
	public String getId() {
		return getPmPositionAppointmentId();
	}

	@Override
	public void setId(String pmPositionAppointmentId) {
		setPmPositionAppointmentId(pmPositionAppointmentId);
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

	@Override
	protected String getUniqueKey() {
		return getPositionAppointment() + "_" + getInstitution() + "_" + getCampus();
	}

}
