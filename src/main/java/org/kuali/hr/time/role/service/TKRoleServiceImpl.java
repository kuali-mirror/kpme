package org.kuali.hr.time.role.service;

import org.kuali.hr.core.KPMENamespace;
import org.kuali.hr.core.role.service.KPMERoleServiceBase;
import org.kuali.rice.kim.api.role.Role;

public class TKRoleServiceImpl extends KPMERoleServiceBase implements TKRoleService {
	
	@Override
	public String getRoleIdByName(String roleName) {
		return getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_TK.getNamespaceCode(), roleName);
	}
	
	@Override
	public Role getRoleByName(String roleName) {
		return getRoleService().getRoleByNamespaceCodeAndName(KPMENamespace.KPME_TK.getNamespaceCode(), roleName);
	}

}