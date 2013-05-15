package org.kuali.kpme.tklm.leave.service.role;

import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.role.service.KPMERoleServiceBase;
import org.kuali.rice.kim.api.role.Role;

public class LMRoleServiceImpl extends KPMERoleServiceBase implements LMRoleService {
	
	@Override
	public String getRoleIdByName(String roleName) {
		return getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_LM.getNamespaceCode(), roleName);
	}
	
	@Override
	public Role getRoleByName(String roleName) {
		return getRoleService().getRoleByNamespaceCodeAndName(KPMENamespace.KPME_LM.getNamespaceCode(), roleName);
	}
}
