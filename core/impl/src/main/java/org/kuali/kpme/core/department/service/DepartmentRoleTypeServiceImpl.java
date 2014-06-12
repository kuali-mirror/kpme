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
package org.kuali.kpme.core.department.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;

@SuppressWarnings("deprecation")
public class DepartmentRoleTypeServiceImpl extends RoleTypeServiceBase {
	
    @Override
	public List<String> getQualifiersForExactMatch() {
    	List<String> qualifiers = new ArrayList<String>();
    	qualifiers.add(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
    	qualifiers.add(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName());
		return qualifiers;
	}
	
	@Override
	public boolean performMatch(Map<String, String> inputAttributes, Map<String, String> storedAttributes) {
		boolean matches = false;
		String inputDepartment = MapUtils.getString(inputAttributes, KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
		String storedDepartment = MapUtils.getString(storedAttributes, KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
		
		String inputGroupKeyCode = MapUtils.getString(inputAttributes, KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName());
		String storedGroupKeyCode = MapUtils.getString(storedAttributes, KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName());
		
		matches = (ObjectUtils.equals(inputDepartment, storedDepartment) || ObjectUtils.equals(inputDepartment, "%"))
                    && (ObjectUtils.equals(inputGroupKeyCode, storedGroupKeyCode) || ObjectUtils.equals(inputGroupKeyCode, "%"));

		return matches;
	}

}
