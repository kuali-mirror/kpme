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
package org.kuali.kpme.pm.positionreportcat;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.pm.positionreporttype.PositionReportType;

import com.google.common.collect.ImmutableList;

public class PositionReportCategory extends HrBusinessObject {
	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
		    .add("positionReportCat")
		    .build();

	private static final long serialVersionUID = 1L;
	
	private String pmPositionReportCatId;
	private String positionReportCat;
	private String positionReportType;
	private String description;
	private String institution;
	private String location;
	
	private Location locationObj;
	private Institution institutionObj;
	private PositionReportType prtObj;	
	
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
		return getPositionReportCat() + "_" + getPositionReportType() + "_" + getInstitution() + "_" + getLocation();
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

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
	public Institution getInstitutionObj() {
		return institutionObj;
	}

	public void setInstitutionObj(Institution institutionObj) {
		this.institutionObj = institutionObj;
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

	public PositionReportType getPrtObj() {
		return prtObj;
	}

	public void setPrtObj(PositionReportType prtObj) {
		this.prtObj = prtObj;
	}

}
