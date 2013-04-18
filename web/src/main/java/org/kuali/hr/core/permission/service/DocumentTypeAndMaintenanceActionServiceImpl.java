package org.kuali.hr.core.permission.service;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kns.kim.permission.PermissionTypeServiceBase;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class DocumentTypeAndMaintenanceActionServiceImpl extends PermissionTypeServiceBase {

    @Override
    protected boolean performMatch(Map<String, String> inputMap, Map<String, String> storedMap) {
    	String requestedDocumentType = MapUtils.getString(inputMap, KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME);
		String permissionDocumentType = MapUtils.getString(storedMap, KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME);
		
		if (requestedDocumentType == null || permissionDocumentType == null) {
			return false;
		}
		
		if (!requestedDocumentType.equals(permissionDocumentType)) {
			return false;
		}
		
		String existingRecordsOnly = MapUtils.getString(storedMap, KimConstants.AttributeConstants.EXISTING_RECORDS_ONLY);
		
		if (existingRecordsOnly == null) {
			return true;
		}
		
		if (!Boolean.parseBoolean(existingRecordsOnly)) {
			return StringUtils.equals(inputMap.get(KRADConstants.MAINTENANCE_ACTN), KRADConstants.MAINTENANCE_NEW_ACTION);
		} else {
			return StringUtils.equals(inputMap.get(KRADConstants.MAINTENANCE_ACTN), KRADConstants.MAINTENANCE_EDIT_ACTION);
		}
	}
    
}