/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.pm.positionappointment;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.location.Location;

public class PositionAppointment extends HrBusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	private String pmPositionAppointmentId;
	private String positionAppointment;
	private String description;
	private String institution;
	private String location;
	
	private Location locationObj;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
	 * @return the locationObj
	 */
	public Location getLocationObj() {
		return locationObj;
	}

	/**
	 * @param locationObj the locationObj to set
	 */
	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
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
		return getPositionAppointment() + "_" + getInstitution() + "_" + getLocation();
	}

}
