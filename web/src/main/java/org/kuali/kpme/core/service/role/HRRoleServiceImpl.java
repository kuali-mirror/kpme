package org.kuali.kpme.core.service.role;

import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.role.service.KPMERoleServiceBase;
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

	@Override
	public String getTkRoleIdByName(String roleName) {
		return getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_TK.getNamespaceCode(), roleName);

	}

	@Override
	public String getLmRoleIdByName(String roleName) {
		return getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_LM.getNamespaceCode(), roleName);

	}

	@Override
	public String getPmRoleIdByName(String roleName) {
		throw new RuntimeException("PM namespace has not been defined");
	}

}