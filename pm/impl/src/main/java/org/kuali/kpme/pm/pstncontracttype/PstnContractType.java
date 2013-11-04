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
package org.kuali.kpme.pm.pstncontracttype;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.pm.api.pstncontracttype.PstnContractTypeContract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PstnContractType extends HrBusinessObject implements PstnContractTypeContract {
	
	private static final String NAME = "name";

	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		    .add(NAME)
		    .build();

	private static final long serialVersionUID = 1L;
	
	private String pmCntrctTypeId;		
	private String name;
	private String description;
	private String institution;
	private String location;
		
	private Location locationObj;
	private Institution institutionObj;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(NAME, this.getName())
			.build();
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getUniqueKey() {
		return getName() + "_" + getInstitution() + "_" + getLocation();
	}

	public String getPmCntrctTypeId() {
		return pmCntrctTypeId;
	}

	public void setPmCntrctTypeId(String pmCntrctTypeId) {
		this.pmCntrctTypeId = pmCntrctTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Institution getInstitutionObj() {
		return institutionObj;
	}

	public void setInstitutionObj(Institution institutionObj) {
		this.institutionObj = institutionObj;
	}
	
	

}
