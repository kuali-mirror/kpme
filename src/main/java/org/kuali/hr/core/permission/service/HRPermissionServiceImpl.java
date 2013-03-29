package org.kuali.hr.core.permission.service;

import java.util.HashMap;
import java.util.Map;

import org.kuali.hr.core.KPMENamespace;
import org.kuali.rice.kim.api.permission.PermissionService;

public class HRPermissionServiceImpl implements HRPermissionService {
	
	private PermissionService permissionService;
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return isAuthorized(principalId, permissionName, qualification);
	}
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification) {
		return getPermissionService().isAuthorized(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), permissionName, qualification);
	}
	
	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

}