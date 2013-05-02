package org.kuali.kpme.tklm.time.service.role;

import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.role.service.KPMERoleServiceBase;
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

	@Override
	public Role getTkRoleByName(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role getLmRoleByName(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role getPmRoleByName(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

}