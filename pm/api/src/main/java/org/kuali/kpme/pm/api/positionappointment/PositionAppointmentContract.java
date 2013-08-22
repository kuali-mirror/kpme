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
package org.kuali.kpme.pm.api.positionappointment;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.institution.InstitutionContract;

/**
 * <p>PositionAppointmentContract interface</p>
 *
 */
public interface PositionAppointmentContract extends HrBusinessObjectContract {

    /**
     * The primary key for a PositionAppointment entry saved in the database
     *
     * <p>
     * pmPositionAppointmentId for the PositionAppointment.
     * <p>
     *
     * @return pmPositionAppointmentId for PositionAppointment
     */
	public String getPmPositionAppointmentId();

    /**
     * The short text name of the appointment type
     *
     * <p>
     * positionAppointment for the PositionAppointment.
     * <p>
     *
     * @return positionAppointment for PositionAppointment
     */
	public String getPositionAppointment();

    /**
     * The longer text description of the appointment type
     *
     * <p>
     * description for the PositionAppointment.
     * <p>
     *
     * @return description for PositionAppointment
     */
	public String getDescription();

    /**
     * The institution associated with the PositionAppointment
     *
     * <p>
     * institution for the PositionAppointment.
     * <p>
     *
     * @return institution for PositionAppointment
     */
	public String getInstitution();

    /**
     * The location associated with the PositionAppointment
     *
     * <p>
     * location for the PositionAppointment.
     * <p>
     *
     * @return location for PositionAppointment
     */
	public String getLocation();

    /**
     * The Location object associated with the PositionAppointment
     *
     * <p>
     * locationObj object for the PositionAppointment.
     * <p>
     *
     * @return locationObj for PositionAppointment
     */
	public LocationContract getLocationObj();

    /**
     * The Institution object associated with the PositionAppointment
     *
     * <p>
     * institutionObj object for the PositionAppointment.
     * <p>
     *
     * @return institutionObj for PositionAppointment
     */
	public InstitutionContract getInstitutionObj();

}
