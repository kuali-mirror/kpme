/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.core.authorization;

import java.util.HashMap;
import java.util.Map;

import org.kuali.hr.core.KPMENamespace;
import org.kuali.hr.core.permission.KPMEPermissionTemplate;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentAuthorizerBase;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;

@SuppressWarnings("deprecation")
public class KPMEMaintenanceDocumentAuthorizerBase extends MaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = -7611928075734193316L;

	@Override
	@SuppressWarnings("rawtypes")
    public boolean canCreate(Class boClass, Person user) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, getDocumentDictionaryService().getMaintenanceDocumentTypeName(boClass));
		return !permissionExistsByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(), 
										   KPMEPermissionTemplate.CREATE_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(), permissionDetails)
				|| getPermissionService().isAuthorizedByTemplate(user.getPrincipalId(), KPMENamespace.KPME_WKFLW.getNamespaceCode(), 
																 KPMEPermissionTemplate.CREATE_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(), 
																 permissionDetails, new HashMap<String, String>());
    }

    @Override
    public boolean canMaintain(Object dataObject, Person user) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, getDocumentDictionaryService().getMaintenanceDocumentTypeName(dataObject.getClass()));
		return !permissionExistsByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(), 
										   KPMEPermissionTemplate.EDIT_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(), permissionDetails)
				|| isAuthorizedByTemplate(dataObject, KPMENamespace.KPME_WKFLW.getNamespaceCode(), 
										  KPMEPermissionTemplate.EDIT_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(), user.getPrincipalId(), 
										  permissionDetails, null);
    }

    @Override
    public boolean canCreateOrMaintain(MaintenanceDocument maintenanceDocument, Person user){
        return canCreate(maintenanceDocument.getDocumentDataObject().getClass(), user) || canMaintain(maintenanceDocument.getDocumentDataObject(), user);
    }

}