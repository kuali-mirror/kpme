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
package org.kuali.kpme.pm.positionappointment;

import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointmentContract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionAppointment extends HrKeyedBusinessObject implements PositionAppointmentContract {
	
	private static final String GROUP_KEY_CODE = "groupKeyCode";
	private static final String POSITION_APPOINTMENT = "positionAppointment";

	private static final long serialVersionUID = 1L;
	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(POSITION_APPOINTMENT)
            .add(GROUP_KEY_CODE)
            .build();
	
	private String pmPositionAppointmentId;
	private String positionAppointment;
	private String description;
	private boolean history;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(POSITION_APPOINTMENT, this.getPositionAppointment())
			.put(GROUP_KEY_CODE, this.getGroupKeyCode())
			.build();
	}
	
	
	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

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
	
	@Override
	public String getId() {
		return getPmPositionAppointmentId();
	}

	@Override
	public void setId(String pmPositionAppointmentId) {
		setPmPositionAppointmentId(pmPositionAppointmentId);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionAppointment() + "_" + this.getGroupKeyCode();
	}

}
