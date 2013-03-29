package org.kuali.hr.location.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
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
