package org.kuali.hr.core.department.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;

@SuppressWarnings("deprecation")
public class DepartmentRoleTypeServiceImpl extends RoleTypeServiceBase {
	
    @Override
	public List<String> getQualifiersForExactMatch() {    
		return Collections.singletonList(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
	}
	
	@Override
	public boolean performMatch(Map<String, String> inputAttributes, Map<String, String> storedAttributes) {
		boolean matches = false;
		
		String inputDepartment = MapUtils.getString(inputAttributes, KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
		String storedDepartment = MapUtils.getString(storedAttributes, KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
		
		if (storedDepartment != null) {
			matches = ObjectUtils.equals(inputDepartment, storedDepartment) || ObjectUtils.equals(inputDepartment, "%");
		}
		
		return matches;
	}

}
