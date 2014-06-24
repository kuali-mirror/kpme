package org.kuali.kpme.tklm.time.missedpunch.authorization;


import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
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

            return (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                     KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
                   || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(principalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                      KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification));
        } catch (WorkflowException e) {
            return false;
        }
        //(!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
        //        KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
        //        || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(principalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
        //        KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification))
    }
}
