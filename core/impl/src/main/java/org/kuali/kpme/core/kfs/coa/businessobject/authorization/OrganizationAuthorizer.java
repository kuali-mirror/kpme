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
package org.kuali.kpme.core.kfs.coa.businessobject.authorization;

import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentViewAuthorizer;
import org.kuali.kpme.core.kfs.coa.businessobject.Organization;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mlemons on 7/10/14.
 */
public class OrganizationAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

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
        Map<String, String> permissionDetails = new HashMap <String, String>();
        Map<String, String> roleQualifications = getRoleQualification(dataObject, user.getPrincipalId());
        permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, getDocumentDictionaryService().getMaintenanceDocumentTypeName(dataObject.getClass()));
        return !permissionExistsByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                KPMEPermissionTemplate.EDIT_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(), permissionDetails)
                || isAuthorizedByTemplate(dataObject, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                KPMEPermissionTemplate.EDIT_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(), user.getPrincipalId(),
                permissionDetails, roleQualifications);
    }

}
