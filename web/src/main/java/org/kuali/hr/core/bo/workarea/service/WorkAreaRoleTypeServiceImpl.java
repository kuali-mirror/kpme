package org.kuali.hr.core.bo.workarea.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;

@SuppressWarnings("deprecation")
public class WorkAreaRoleTypeServiceImpl extends RoleTypeServiceBase {
	
    @Override
	public List<String> getQualifiersForExactMatch() {    
		return Collections.singletonList(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName());
	}
	
	@Override
	public boolean performMatch(Map<String, String> inputAttributes, Map<String, String> storedAttributes) {
		boolean matches = false;
		
		Long inputWorkArea = MapUtils.getLong(inputAttributes, KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName());
		Long storedWorkArea = MapUtils.getLong(storedAttributes, KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName());
		
		if (storedWorkArea != null) {
			matches = ObjectUtils.equals(inputWorkArea, storedWorkArea) || ObjectUtils.equals(inputWorkArea, -1);
		}
		
		return matches;
	}

}