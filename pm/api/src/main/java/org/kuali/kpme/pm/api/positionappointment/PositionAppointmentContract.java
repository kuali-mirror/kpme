/**
 * Copyright 2004-2014 The Kuali Foundation
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

import org.kuali.kpme.core.api.bo.HrKeyedBusinessObjectContract;

/**
 * <p>PositionAppointmentContract interface</p>
 *
 */
public interface PositionAppointmentContract extends HrKeyedBusinessObjectContract {

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
}
