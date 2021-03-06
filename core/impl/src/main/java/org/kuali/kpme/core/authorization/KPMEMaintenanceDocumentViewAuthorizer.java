/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.authorization;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentAuthorizerBase;

import java.util.HashMap;
import java.util.Map;

public class KPMEMaintenanceDocumentViewAuthorizer extends MaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = -7611928075734193316L;

    @SuppressWarnings("rawtypes")
	@Override
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
        Map<String, String> roleQualifications = getRoleQualification(dataObject, user.getPrincipalId());
		permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, getDocumentDictionaryService().getMaintenanceDocumentTypeName(dataObject.getClass()));
		return !permissionExistsByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(), 
										   KPMEPermissionTemplate.EDIT_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(), permissionDetails)
				|| isAuthorizedByTemplate(dataObject, KPMENamespace.KPME_WKFLW.getNamespaceCode(), 
										  KPMEPermissionTemplate.EDIT_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(), user.getPrincipalId(), 
										  permissionDetails, roleQualifications);
    }


    @Override
    public boolean canCreateOrMaintain(MaintenanceDocument maintenanceDocument, Person user){
        return canCreate(maintenanceDocument.getDocumentDataObject().getClass(), user) || canMaintain(maintenanceDocument.getDocumentDataObject(), user);
    }

    protected String cleanAttributeValue(String value) {
        return StringUtils.equals("%", value) ? StringUtils.EMPTY : value;
    }
     
    @Override
    protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
    	super.addRoleQualification(dataObject, attributes);		
		
    	// put in the wildcards for various possible qualifiers 
    	attributes.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "%");
    	attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
		attributes.put(KPMERoleMemberAttribute.INSTITUION.getRoleMemberAttributeName(), "%");
		attributes.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), "%");
		
		// if keyed object then replace institution and location qualifiers
		if (dataObject instanceof HrKeyedBusinessObject && dataObject != null) {
			// get location, institution and grp key code from the object's group key and replace them in the qualification attributes
			HrGroupKeyBo groupKey = ((HrKeyedBusinessObject) dataObject).getGroupKey();
			if(groupKey != null) {
				attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), groupKey.getLocationId());
				attributes.put(KPMERoleMemberAttribute.INSTITUION.getRoleMemberAttributeName(), groupKey.getInstitutionCode());
				attributes.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKey.getGroupKeyCode());
			}
		}
    }

}