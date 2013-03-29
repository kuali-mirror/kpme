package org.kuali.hr.core.permission.service;

import java.util.Map;

public interface HRPermissionService {

	boolean isAuthorized(String principalId, String permissionName);
	
	boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification);

}