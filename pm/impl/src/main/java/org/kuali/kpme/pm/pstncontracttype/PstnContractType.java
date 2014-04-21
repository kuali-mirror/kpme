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
package org.kuali.kpme.pm.pstncontracttype;

import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.pm.api.pstncontracttype.PstnContractTypeContract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PstnContractType extends HrKeyedBusinessObject implements PstnContractTypeContract {
	
	static class KeyFields {
		final static String GROUP_KEY_CODE = "groupKeyCode";
		private static final String NAME = "name";
	}
	
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		    .add(KeyFields.NAME)
		    .add(KeyFields.GROUP_KEY_CODE)
		    .build();

	private static final long serialVersionUID = 1L;
	
	private String pmCntrctTypeId;		
	private String name;
	private String description;
		
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(KeyFields.NAME, this.getName())
			.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
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

}
