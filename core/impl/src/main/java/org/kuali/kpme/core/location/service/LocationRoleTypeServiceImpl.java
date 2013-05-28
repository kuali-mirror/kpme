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
package org.kuali.kpme.core.location.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;

@SuppressWarnings("deprecation")
public class LocationRoleTypeServiceImpl extends RoleTypeServiceBase {
	
    @Override
	public List<String> getQualifiersForExactMatch() {    
		return Collections.singletonList(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName());
	}
	
	@Override
	public boolean performMatch(Map<String, String> inputAttributes, Map<String, String> storedAttributes) {
		boolean matches = false;
		
		String inputLocation = MapUtils.getString(inputAttributes, KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName());
		String storedLocation = MapUtils.getString(storedAttributes, KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName());
		
		if (storedLocation != null) {
			matches = ObjectUtils.equals(inputLocation, storedLocation) || ObjectUtils.equals(inputLocation, "%");
		}
		
		return matches;
	}

}
