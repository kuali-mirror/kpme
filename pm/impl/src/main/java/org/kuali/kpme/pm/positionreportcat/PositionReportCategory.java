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
package org.kuali.kpme.pm.positionreportcat;

import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategoryContract;
import org.kuali.kpme.pm.positionreporttype.PositionReportTypeBo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionReportCategory extends HrKeyedBusinessObject implements PositionReportCategoryContract {
	
	private static final String POSITION_REPORT_CAT = "positionReportCat";
	private static final String GROUP_KEY_CODE = "groupKeyCode";
	
	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		    .add(POSITION_REPORT_CAT)
		    .add(GROUP_KEY_CODE)
		    .build();

	private static final long serialVersionUID = 1L;
	
	private String pmPositionReportCatId;
	private String positionReportCat;
	private String positionReportType;
	private String description;
	
	private PositionReportTypeBo prtObj;	
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(POSITION_REPORT_CAT, this.getPositionReportCat())
				.put(GROUP_KEY_CODE, this.getGroupKeyCode())
				.build();
	}
	
	@Override
	public String getId() {
		return getPmPositionReportCatId();
	}

	@Override
	public void setId(String id) {
		setPmPositionReportCatId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionReportCat() + "_" + getPositionReportType() + "_" + getGroupKeyCode();
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

	public String getPmPositionReportCatId() {
		return pmPositionReportCatId;
	}

	public void setPmPositionReportCatId(String pmPositionReportCatId) {
		this.pmPositionReportCatId = pmPositionReportCatId;
	}

	public String getPositionReportCat() {
		return positionReportCat;
	}

	public void setPositionReportCat(String positionReportCat) {
		this.positionReportCat = positionReportCat;
	}

	public PositionReportTypeBo getPrtObj() {
		return prtObj;
	}

	public void setPrtObj(PositionReportTypeBo prtObj) {
		this.prtObj = prtObj;
	}

}
