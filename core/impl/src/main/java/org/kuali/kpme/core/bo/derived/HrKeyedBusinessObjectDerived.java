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
package org.kuali.kpme.core.bo.derived;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.bo.derived.HrKeyedBusinessObjectDerivedContract;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.institution.InstitutionBo;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.service.HrServiceLocator;

public abstract class HrKeyedBusinessObjectDerived extends HrBusinessObjectDerived implements HrKeyedBusinessObjectDerivedContract {

	private static final long serialVersionUID = -4676316817255855400L;
	
	protected String groupKeyCode;
	protected transient HrGroupKeyBo groupKey;
	
	@Override
	public String getGroupKeyCode() {
		return groupKeyCode;
	}

	public void setGroupKeyCode(String groupKeyCode) {
		this.groupKeyCode = groupKeyCode;
	}

	@Override
	public HrGroupKeyBo getGroupKey() {
		
		LocalDate asOfDate = LocalDate.now();
		if(this.getEffectiveLocalDateOfOwner() != null) {
			asOfDate = this.getEffectiveLocalDateOfOwner();
		}
		
		if (groupKey == null) {
			groupKey = HrGroupKeyBo.from(HrServiceLocator.getHrGroupKeyService().getHrGroupKey(getGroupKeyCode(), asOfDate));
		}
		
		return groupKey;
	}

	public void setGroupKey(HrGroupKeyBo groupKey) {
		this.groupKey = groupKey;
	}
	
	
	/**
	 * @return the locationObj
	 */
	public LocationBo getLocationObj() {
		HrGroupKeyBo grpKey = getGroupKey(); 
		if(grpKey != null) {
			return grpKey.getLocation();
		}
		return null;		
	}

	
	/**
	 * @return the institutionObj
	 */
	public InstitutionBo getInstitutionObj() {
		HrGroupKeyBo grpKey = getGroupKey(); 
		if(grpKey != null) {
			return grpKey.getInstitution();
		}
		return null;
	}
	
	/**
	 * @return the institutionCode
	 */
	public String getInstitution() {
		HrGroupKeyBo grpKey = getGroupKey(); 
		if(grpKey != null) {
			return grpKey.getInstitutionCode();
		}
		return null;
	}


	/**
	 * @return the locationId
	 */
	public String getLocation() {
		
		HrGroupKeyBo grpKey = getGroupKey(); 
		if(grpKey != null) {
			return grpKey.getLocationId();
		}
		return null;
	}

}
