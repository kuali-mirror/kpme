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
package org.kuali.kpme.tklm.time.missedpunch.authorization;


import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.rice.kew.api.document.Document;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.framework.document.security.DocumentSecurityAttribute;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

import java.util.HashMap;
import java.util.Map;

public class MissedPunchSecurityFilterAttribute implements DocumentSecurityAttribute {
   
	private static final long serialVersionUID = 2553961332039285881L;

	@Override
    public boolean isAuthorizedForDocument(String principalId, Document document) {
        try {
            MissedPunchDocument mpd = (MissedPunchDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(document.getDocumentId());
            String groupKey = mpd.getGroupKeyCode();
            Long workArea = mpd.getWorkArea();
            String department = mpd.getDepartment();
            String location = mpd.getGroupKey() != null ? mpd.getGroupKey().getLocationId() : HrServiceLocator.getHrGroupKeyService().getHrGroupKey(mpd.getGroupKeyCode(), mpd.getMissedPunch().getActionLocalDate()).getLocationId();


            Map<String, String> roleQualification = new HashMap<String, String>();
            roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, principalId);
            roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
            roleQualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), workArea.toString());
            roleQualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKey);
            roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
            
            Map<String, String> permissionDetails = new HashMap<String, String>();
            permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, KRADServiceLocatorWeb.getDocumentDictionaryService().getDocumentTypeByClass(MissedPunchDocument.class));

            return (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                     KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
                   || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(principalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                      KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), permissionDetails, roleQualification));
        } catch (WorkflowException e) {
            return false;
        }
    }
}
