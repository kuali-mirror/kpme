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
package org.kuali.kpme.pm.position.authorization;

import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentViewAuthorizer;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.PositionContract;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mlemons on 6/23/14.
 */
public class PositionDocumentAuthorizer extends KPMEMaintenanceDocumentViewAuthorizer {

    private static final long serialVersionUID = 1362536674228377102L;

    /*
    @Override
    public boolean canEdit(Document document, Person user) {
        return super.canEdit(document, user) || canApprove(document, user);
    }
*/
    public boolean canView(Object dataObject, Person user)
    {
        Map<String, String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, getDocumentDictionaryService().getMaintenanceDocumentTypeName(dataObject.getClass()));

        Map<String, String> q = getRoleQualification(dataObject, user.getPrincipalId());

        if (isAuthorizedByTemplate(dataObject, KPMENamespace.KPME_WKFLW.getNamespaceCode(), KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(),
                user.getPrincipalId(), permissionDetails, q ))
        {
            return true;
        }

        return false;
    }

    public boolean canCopy (Object dataObject, Person user)
    {
        Map<String, String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, getDocumentDictionaryService().getMaintenanceDocumentTypeName(dataObject.getClass()));


        Map<String, String> q = getRoleQualification(dataObject, user.getPrincipalId());

        if (isAuthorizedByTemplate(dataObject, KPMENamespace.KPME_WKFLW.getNamespaceCode(), KPMEPermissionTemplate.COPY_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(),
                user.getPrincipalId(), permissionDetails, q))
        {
            return true;
        }

        return false;
    }

    public boolean canCopy(Document document, Person user) {
        if (document instanceof org.kuali.rice.krad.maintenance.MaintenanceDocumentBase)
        {
            Object dataObject = ((MaintenanceDocumentBase) document).getDocumentDataObject();

            if (dataObject != null)
            {
                if (!canCopy(dataObject, user))
                {
                    return false;
                }
            }
        }

        return canMaintain(document, user);
    }

    @Override
    protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
        super.addRoleQualification(dataObject, attributes);

        if ( (dataObject instanceof PositionBo) || (dataObject instanceof PositionContract) || (dataObject instanceof Position)) {
            PositionBo positionObj = (PositionBo) dataObject;

            if (positionObj != null) {
                Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(positionObj.getPrimaryDepartment(), positionObj.getGroupKeyCode(), positionObj.getEffectiveLocalDate());

                if (departmentObj != null) {
                    attributes.put(KPMERoleMemberAttribute.INSTITUION.getRoleMemberAttributeName(), departmentObj.getGroupKey().getInstitutionCode());

                    attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), departmentObj.getDept());

                    attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), departmentObj.getGroupKey().getLocationId());

                    attributes.put(KPMERoleMemberAttribute.ORGANIZATION.getRoleMemberAttributeName(), departmentObj.getOrg());
                }
                attributes.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), positionObj.getGroupKeyCode());
            }
        }
    }

    @Override
    public boolean canMaintain(Object dataObject, Person user) {

        Map<String, String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, getDocumentDictionaryService().getMaintenanceDocumentTypeName(dataObject.getClass()));


        if (isAuthorizedByTemplate(dataObject, KPMENamespace.KPME_WKFLW.getNamespaceCode(), KPMEPermissionTemplate.EDIT_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(),
                user.getPrincipalId(), permissionDetails, getRoleQualification(dataObject, user.getPrincipalId())))
        {
            return true;
        }

        return false;
        //return super.canMaintain(dataObject, user);
    }
}
