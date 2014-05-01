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
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointment;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointmentContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionAppointmentBo extends HrKeyedBusinessObject implements PositionAppointmentContract {

	static class KeyFields {
		private static final String GROUP_KEY_CODE = "groupKeyCode";
		private static final String POSITION_APPOINTMENT = "positionAppointment";
	}

	/*
	 * convert bo to immutable
	 *
	 * Can be used with ModelObjectUtils:
	 *
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfPositionAppointmentBo, PositionAppointmentBo.toImmutable);
	 */
	public static final ModelObjectUtils.Transformer<PositionAppointmentBo, PositionAppointment> toImmutable =
			new ModelObjectUtils.Transformer<PositionAppointmentBo, PositionAppointment>() {
		public PositionAppointment transform(PositionAppointmentBo input) {
			return PositionAppointmentBo.to(input);
		};
	};

	/*
	 * convert immutable to bo
	 * 
	 * Can be used with ModelObjectUtils:
	 * 
	 * org.kuali.rice.core.api.mo.ModelObjectUtils.transform(listOfPositionAppointment, PositionAppointmentBo.toBo);
	 */
	public static final ModelObjectUtils.Transformer<PositionAppointment, PositionAppointmentBo> toBo =
			new ModelObjectUtils.Transformer<PositionAppointment, PositionAppointmentBo>() {
		public PositionAppointmentBo transform(PositionAppointment input) {
			return PositionAppointmentBo.from(input);
		};
	};

	private static final long serialVersionUID = 1L;

	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(KeyFields.POSITION_APPOINTMENT)
			.add(KeyFields.GROUP_KEY_CODE)
			.build();

	private String pmPositionAppointmentId;
	private String positionAppointment;
	private String description;
	private boolean history;

	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.POSITION_APPOINTMENT, this.getPositionAppointment())
				.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
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

	public static PositionAppointmentBo from(PositionAppointment im) {
		if (im == null) {
			return null;
		}
		PositionAppointmentBo pa = new PositionAppointmentBo();
		pa.setPmPositionAppointmentId(im.getPmPositionAppointmentId());
		pa.setPositionAppointment(im.getPositionAppointment());
		pa.setDescription(im.getDescription());
		pa.setGroupKeyCode(im.getGroupKeyCode());        
		pa.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
		
		// finally copy over the common fields into pa from im
		copyCommonFields(pa, im);

		return pa;
	} 

	public static PositionAppointment to(PositionAppointmentBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionAppointment.Builder.create(bo).build();
	}
}
