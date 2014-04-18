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
package org.kuali.kpme.pm.pstnrptgrpsubcat;

import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.pm.api.pstnrptgrpsubcat.PositionReportGroupSubCategoryContract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PositionReportGroupSubCategory extends HrKeyedBusinessObject implements PositionReportGroupSubCategoryContract {
	
	static class KeyFields {
		private static final String POSITION_REPORT_GROUP = "positionReportGroup";
		private static final String POSITION_REPORT_SUB_CAT = "positionReportSubCat";
		private static final String GROUP_KEY_CODE = "groupKeyCode";
	}

	private static final long serialVersionUID = 1L;
	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		     .add(KeyFields.POSITION_REPORT_SUB_CAT)
		     .add(KeyFields.POSITION_REPORT_GROUP)
		     .add(KeyFields.GROUP_KEY_CODE)
		     .build();
	
	private String pmPstnRptGrpSubCatId;
	private String pstnRptGrpSubCat;
	private String positionReportGroup;
	private String positionReportSubCat;
	private String description;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.POSITION_REPORT_SUB_CAT, this.getPositionReportSubCat())
				.put(KeyFields.POSITION_REPORT_GROUP, this.getPositionReportGroup())
				.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
				.build();
	}
	
	@Override
	public String getId() {
		return this.getPmPstnRptGrpSubCatId();
	}

	@Override
	public void setId(String id) {
		setPmPstnRptGrpSubCatId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPstnRptGrpSubCat() + "_" + getPositionReportGroup() + "_" + getPositionReportSubCat();
	}

	public String getPmPstnRptGrpSubCatId() {
		return pmPstnRptGrpSubCatId;
	}

	public void setPmPstnRptGrpSubCatId(String pmPstnRptGrpSubCatId) {
		this.pmPstnRptGrpSubCatId = pmPstnRptGrpSubCatId;
	}

	public String getPstnRptGrpSubCat() {
		return pstnRptGrpSubCat;
	}

	public void setPstnRptGrpSubCat(String pstnRptGrpSubCat) {
		this.pstnRptGrpSubCat = pstnRptGrpSubCat;
	}

	public String getPositionReportGroup() {
		return positionReportGroup;
	}

	public void setPositionReportGroup(String positionReportGroup) {
		this.positionReportGroup = positionReportGroup;
	}

	public String getPositionReportSubCat() {
		return positionReportSubCat;
	}

	public void setPositionReportSubCat(String positionReportSubCat) {
		this.positionReportSubCat = positionReportSubCat;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}