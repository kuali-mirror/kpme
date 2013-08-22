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
package org.kuali.kpme.pm.pstnrptgrpsubcat;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.pm.api.pstnrptgrpsubcat.PositionReportGroupSubCategoryContract;

public class PositionReportGroupSubCategory extends HrBusinessObject implements PositionReportGroupSubCategoryContract {
	private static final long serialVersionUID = 1L;
	
	private String pmPstnRptGrpSubCatId;
	private String pstnRptGrpSubCat;
	private String positionReportGroup;
	private String positionReportSubCat;
	private String description;
	private String institution;
	private String location;

	private Location locationObj;	
	
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

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

}
