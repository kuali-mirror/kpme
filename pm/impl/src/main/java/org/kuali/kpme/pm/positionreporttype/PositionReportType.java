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
package org.kuali.kpme.pm.positionreporttype;

import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.institution.InstitutionBo;
import org.kuali.kpme.pm.api.positionreporttype.PositionReportTypeContract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionReportType extends HrKeyedBusinessObject implements PositionReportTypeContract {
	private static final String POSITION_REPORT_TYPE = "positionReportType";

	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		     .add(POSITION_REPORT_TYPE)
		     .build();

	private static final long serialVersionUID = 1L;
	
	private String pmPositionReportTypeId;
	private String positionReportType;
	private String description;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(POSITION_REPORT_TYPE, this.getPositionReportType())
				.build();
	}
	
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
		return getPositionReportType() + "_" + getGroupKeyCode();
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

	public String getPmPositionReportTypeId() {
		return pmPositionReportTypeId;
	}

	public void setPmPositionReportTypeId(String pmPositionReportTypeId) {
		this.pmPositionReportTypeId = pmPositionReportTypeId;
	}
}
