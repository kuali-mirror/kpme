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
package org.kuali.hr.time.authorization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kns.document.authorization.DocumentAuthorizer;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentAuthorizer;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class SystemAdminAuthorizer implements MaintenanceDocumentAuthorizer, DocumentAuthorizer {
	
	public boolean isSystemAdmin(){
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isSystemAdmin();
	}
	
	public boolean isGlobalViewOnly(){
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isGlobalViewOnly();
	}

	@Override
	public boolean canInitiate(String documentTypeName, Person user) {
		return isSystemAdmin() || isGlobalViewOnly();
	}

	@Override
	public boolean canOpen(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canReceiveAdHoc(Document document, Person user,
			String actionRequestCode) {
		return isSystemAdmin();
	}

	@Override
	public boolean canAddNoteAttachment(Document document,
			String attachmentTypeCode, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canDeleteNoteAttachment(Document document,
			String attachmentTypeCode, String createdBySelfOnly, Person user) {
		return isSystemAdmin();
	}
	
	@Override
	public boolean canViewNoteAttachment(Document document,
			String attachmentTypeCode, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canViewNoteAttachment(Document document, 
			String attachmentTypeCode, String authorUniversalIdentifier, Person user) {
		return isSystemAdmin();
	}
	
	@Override
	public boolean canSendAdHocRequests(Document document,
			String actionRequestCd, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean isAuthorized(BusinessObject businessObject,
			String namespaceCode, String permissionName, String principalId) {
		return isSystemAdmin();
	}

	@Override
	public boolean isAuthorizedByTemplate(BusinessObject businessObject,
			String namespaceCode, String permissionTemplateName,
			String principalId) {
		return isSystemAdmin();
	}

	@Override
	public boolean isAuthorized(BusinessObject businessObject,
			String namespaceCode, String permissionName, String principalId,
			Map<String, String> additionalPermissionDetails,
			Map<String, String> additionalRoleQualifiers) {
		return isSystemAdmin();
	}

	@Override
	public boolean isAuthorizedByTemplate(Object dataObject,
			String namespaceCode, String permissionTemplateName,
			String principalId,
			Map<String, String> additionalPermissionDetails,
			Map<String, String> additionalRoleQualifiers) {
		return isSystemAdmin();
	}

	@Override
	public Map<String, String> getCollectionItemRoleQualifications(
			BusinessObject collectionItemBusinessObject) {
		return new HashMap<String,String>();
	}

	@Override
	public Map<String, String> getCollectionItemPermissionDetails(
			BusinessObject collectionItemBusinessObject) {
		return new HashMap<String,String>();
	}

	@Override
	public Set<String> getSecurePotentiallyHiddenSectionIds() {
		return new HashSet<String>();
	}

	@Override
	public boolean canCreate(Class boClass, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canMaintain(Object dataObject, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canCreateOrMaintain(MaintenanceDocument maintenanceDocument, 
			Person user) {
		return isSystemAdmin();
	}

	@Override
	public Set<String> getSecurePotentiallyReadOnlySectionIds() {
		return new HashSet<String>();
	}

	@Override
	public boolean canEdit(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canAnnotate(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canReload(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canClose(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canSave(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canRoute(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canCancel(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canCopy(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canPerformRouteReport(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canBlanketApprove(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canApprove(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canDisapprove(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canSendNoteFyi(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canEditDocumentOverview(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canFyi(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canAcknowledge(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canSendAnyTypeAdHocRequests(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canTakeRequestedAction(Document document,
			String actionRequestCode, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean canRecall(Document document, Person user) {
		return isSystemAdmin();
	}

	@Override
	public boolean isAuthorized(Object dataObject, String namespaceCode,
			String permissionName, String principalId) {
		return isSystemAdmin();
	}

	@Override
	public boolean isAuthorizedByTemplate(Object dataObject,
			String namespaceCode, String permissionTemplateName,
			String principalId) {
		return isSystemAdmin();
	}

	@Override
	public boolean isAuthorized(Object dataObject, String namespaceCode,
			String permissionName, String principalId,
			Map<String, String> additionalPermissionDetails,
			Map<String, String> additionalRoleQualifiers) {
		return isSystemAdmin();
	}

	/**
	 * Copied from org.kuali.rice.kns.document.authorization.DocumentAuthorizerBase
	 */
	@Override
	public Set<String> getDocumentActions(Document document, Person user, Set<String> documentActions) {
        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_EDIT) && !canEdit(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_EDIT);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_COPY) && !canCopy(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_COPY);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_CLOSE) && !canClose(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_CLOSE);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_RELOAD) && !canReload(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_RELOAD);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_BLANKET_APPROVE) && !canBlanketApprove(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_BLANKET_APPROVE);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_CANCEL) && !canCancel(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_CANCEL);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_RECALL) && !canRecall(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_RECALL);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_SAVE) && !canSave(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_SAVE);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_ROUTE) && !canRoute(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_ROUTE);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_ACKNOWLEDGE) && !canAcknowledge(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_ACKNOWLEDGE);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_FYI) && !canFyi(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_FYI);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_APPROVE) && !canApprove(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_APPROVE);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_DISAPPROVE) && !canDisapprove(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_DISAPPROVE);
        }

        if (!canSendAnyTypeAdHocRequests(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_ADD_ADHOC_REQUESTS);
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_SEND_ADHOC_REQUESTS);
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_SEND_NOTE_FYI);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_SEND_NOTE_FYI) && !canSendNoteFyi(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_SEND_NOTE_FYI);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_ANNOTATE) && !canAnnotate(document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_ANNOTATE);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_CAN_EDIT_DOCUMENT_OVERVIEW) && !canEditDocumentOverview(
                document, user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_CAN_EDIT_DOCUMENT_OVERVIEW);
        }

        if (documentActions.contains(KRADConstants.KUALI_ACTION_PERFORM_ROUTE_REPORT) && !canPerformRouteReport(document,
                user)) {
            documentActions.remove(KRADConstants.KUALI_ACTION_PERFORM_ROUTE_REPORT);
        }

        return documentActions;
	}

}