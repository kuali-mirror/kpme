package org.kuali.hr.core.role;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;

@SuppressWarnings("deprecation")
public class KPMEDefaultRoleTypeServiceImpl extends RoleTypeServiceBase {
	
	@Override
	public boolean performMatch(Map<String, String> inputAttributes, Map<String, String> storedAttributes) {
		return MapUtils.isEmpty(inputAttributes) && MapUtils.isEmpty(storedAttributes);
	}

}