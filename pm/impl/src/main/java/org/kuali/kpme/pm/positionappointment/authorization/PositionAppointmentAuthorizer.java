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
package org.kuali.kpme.pm.positionappointment.authorization;


import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentViewAuthorizer;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointment;
import org.kuali.kpme.pm.positionappointment.PositionAppointmentBo;
import org.kuali.kpme.core.api.institution.Institution;
import org.kuali.kpme.pm.positionappointment.web.PositionAppointmentMaintainableImpl;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;


@SuppressWarnings("deprecation")
public class PositionAppointmentAuthorizer extends KPMEMaintenanceDocumentViewAuthorizer {

	private static final long serialVersionUID = 7842865077640195329L;
	
	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

        if (dataObject instanceof PositionAppointmentBo)
        {
            attributes.put(KPMERoleMemberAttribute.INSTITUION.getRoleMemberAttributeName(), ((PositionAppointmentBo)dataObject).getInstitution() );

            attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(),  ((PositionAppointmentBo)dataObject).getLocation());

            attributes.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), ((PositionAppointmentBo)dataObject).getGroupKeyCode() );
        }
	}

    @Override
    public boolean canMaintain(Object dataObject, Person user) {
        return super.canMaintain(dataObject, user);
    }

    @Override
    public boolean canCopy(Document document, Person user) {

        Object dbo = (((MaintenanceDocument)document).getDocumentDataObject());
        if ( dbo instanceof PositionAppointmentBo)
        {
            return canMaintain((PositionAppointmentBo)dbo, user);
        }

        return false;
    }

    public boolean canView(Object dataObject, Person user)
    {
        Map<String, String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, getDocumentDictionaryService().getMaintenanceDocumentTypeName(dataObject.getClass()));


        Map<String, String> roleQualifiers = getRoleQualification(dataObject, user.getPrincipalId());
        if (KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(user.getPrincipalId(), KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), permissionDetails, roleQualifiers)) {
            return true;
        }

        if (KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(user.getPrincipalId(), KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                KPMEPermissionTemplate.EDIT_KPME_MAINTENANCE_DOCUMENT.getPermissionTemplateName(), permissionDetails, roleQualifiers)) {
            return true;
        }

        return false;
    }

}
