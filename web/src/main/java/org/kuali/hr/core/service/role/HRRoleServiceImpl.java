package org.kuali.hr.core.service.role;

import org.kuali.hr.core.KPMENamespace;
import org.kuali.hr.core.role.service.KPMERoleServiceBase;
import org.kuali.rice.kim.api.role.Role;

public class HRRoleServiceImpl extends KPMERoleServiceBase implements HRRoleService {
	
	@Override
	public String getRoleIdByName(String roleName) {
		return getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), roleName);
	}
	
	@Override
	public Role getRoleByName(String roleName) {
		return getRoleService().getRoleByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), roleName);
	}

}