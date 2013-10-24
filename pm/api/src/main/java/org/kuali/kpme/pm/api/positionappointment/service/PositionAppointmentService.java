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
package org.kuali.kpme.pm.api.positionappointment.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointmentContract;

public interface PositionAppointmentService {

	/**
	 * retrieve the PositionAppointment with given id
	 * 
	 * @param pmPositionAppointmentId
	 * @return
	 */
	public PositionAppointmentContract getPositionAppointmentById(String pmPositionAppointmentId);

	/**
	 * Get list of PositionAppointment with given group, description, institution, location and
	 * effective date wild card allowed
	 * 
	 * @param positionAppointment
	 * @param description
	 * @param institution
	 * @param location
	 * @param fromEffdt
	 * @param toEffdt
	 * @param active
	 * @param showHistory
	 * @return
	 */
	public List<? extends PositionAppointmentContract> getPositionAppointmentList(String positionAppointment, String description, String institution, String location, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
}
