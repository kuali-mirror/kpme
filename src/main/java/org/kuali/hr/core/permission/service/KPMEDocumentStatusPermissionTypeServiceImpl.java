package org.kuali.hr.core.permission.service;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.kuali.hr.core.permission.KPMEPermissionTemplateAttribute;
import org.kuali.rice.kns.kim.permission.PermissionTypeServiceBase;

@SuppressWarnings("deprecation")
public class KPMEDocumentStatusPermissionTypeServiceImpl extends PermissionTypeServiceBase {
	
	@Override
	public boolean performMatch(Map<String, String> inputAttributes, Map<String, String> storedAttributes) {
		boolean matches = false;
		
		String inputKpmeDocumentStatus = MapUtils.getString(inputAttributes, KPMEPermissionTemplateAttribute.KPME_DOCUMENT_STATUS.getPermissionTemplateAttributeName());
		String storedKpmeDocumentStatus = MapUtils.getString(storedAttributes, KPMEPermissionTemplateAttribute.KPME_DOCUMENT_STATUS.getPermissionTemplateAttributeName());
		
		if (storedKpmeDocumentStatus != null) {
			matches = ObjectUtils.equals(inputKpmeDocumentStatus, storedKpmeDocumentStatus);
		}
		
		return matches;
	}

}