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

import java.util.Map;

import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;

public class LocationAdminAuthorizer extends SystemAdminAuthorizer {
	
	public boolean isLocationAdmin(){
		return TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isLocationAdmin();
	}
	
	@Override
	public boolean canInitiate(String documentTypeName, Person user) {
		return super.canInitiate(documentTypeName, user) || isLocationAdmin();
	}

	@Override
	public boolean canOpen(Document document, Person user) {
		return super.canOpen(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canReceiveAdHoc(Document document, Person user,
			String actionRequestCode) {
		return super.canReceiveAdHoc(document, user, actionRequestCode) || isLocationAdmin();
	}

	@Override
	public boolean canAddNoteAttachment(Document document,
			String attachmentTypeCode, Person user) {
		return super.canAddNoteAttachment(document, attachmentTypeCode, user) || isLocationAdmin();
	}

	@Override
	public boolean canDeleteNoteAttachment(Document document,
			String attachmentTypeCode, String createdBySelfOnly, Person user) {
		return super.canDeleteNoteAttachment(document, attachmentTypeCode, createdBySelfOnly, user) || isLocationAdmin();
	}
	
	@Override
	public boolean canViewNoteAttachment(Document document,
			String attachmentTypeCode, Person user) {
		return super.canViewNoteAttachment(document, attachmentTypeCode, user) || isLocationAdmin();
	}

	@Override
	public boolean canViewNoteAttachment(Document document, 
			String attachmentTypeCode, String authorUniversalIdentifier, Person user) {
		return super.canViewNoteAttachment(document, attachmentTypeCode, authorUniversalIdentifier, user) || isLocationAdmin();
	}
	
	@Override
	public boolean canSendAdHocRequests(Document document,
			String actionRequestCd, Person user) {
		return super.canSendAdHocRequests(document, actionRequestCd, user) || isLocationAdmin();
	}

	@Override
	public boolean isAuthorized(BusinessObject businessObject,
			String namespaceCode, String permissionName, String principalId) {
		return super.isAuthorized(businessObject, namespaceCode, permissionName, principalId) || isLocationAdmin();
	}

	@Override
	public boolean isAuthorizedByTemplate(BusinessObject businessObject,
			String namespaceCode, String permissionTemplateName,
			String principalId) {
		return super.isAuthorizedByTemplate(businessObject, namespaceCode, permissionTemplateName, principalId) || isLocationAdmin();
	}

	@Override
	public boolean isAuthorized(BusinessObject businessObject,
			String namespaceCode, String permissionName, String principalId,
			Map<String, String> additionalPermissionDetails,
			Map<String, String> additionalRoleQualifiers) {
		return super.isAuthorized(businessObject, namespaceCode, permissionName, principalId, additionalPermissionDetails, additionalRoleQualifiers) || isLocationAdmin();
	}

	@Override
	public boolean isAuthorizedByTemplate(Object dataObject,
			String namespaceCode, String permissionTemplateName,
			String principalId,
			Map<String, String> additionalPermissionDetails,
			Map<String, String> additionalRoleQualifiers) {
		return super.isAuthorizedByTemplate(dataObject, namespaceCode, permissionTemplateName, principalId) || isLocationAdmin();
	}

	@Override
	public boolean canCreate(Class boClass, Person user) {
		return super.canCreate(boClass, user) || isLocationAdmin();
	}

	@Override
	public boolean canMaintain(Object dataObject, Person user) {
		return super.canMaintain(dataObject, user) || isLocationAdmin();
	}

	@Override
	public boolean canCreateOrMaintain(MaintenanceDocument maintenanceDocument, 
			Person user) {
		return super.canCreateOrMaintain(maintenanceDocument, user) || isLocationAdmin();
	}

	@Override
	public boolean canEdit(Document document, Person user) {
		return super.canEdit(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canAnnotate(Document document, Person user) {
		return super.canAnnotate(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canReload(Document document, Person user) {
		return super.canReload(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canClose(Document document, Person user) {
		return super.canClose(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canSave(Document document, Person user) {
		return super.canSave(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canRoute(Document document, Person user) {
		return super.canRoute(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canCancel(Document document, Person user) {
		return super.canCancel(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canCopy(Document document, Person user) {
		return super.canCopy(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canPerformRouteReport(Document document, Person user) {
		return super.canCopy(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canBlanketApprove(Document document, Person user) {
		return super.canBlanketApprove(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canApprove(Document document, Person user) {
		return super.canApprove(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canDisapprove(Document document, Person user) {
		return super.canDisapprove(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canSendNoteFyi(Document document, Person user) {
		return super.canSendNoteFyi(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canEditDocumentOverview(Document document, Person user) {
		return super.canEditDocumentOverview(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canFyi(Document document, Person user) {
		return super.canFyi(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canAcknowledge(Document document, Person user) {
		return super.canAcknowledge(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canSendAnyTypeAdHocRequests(Document document, Person user) {
		return super.canSendAnyTypeAdHocRequests(document, user) || isLocationAdmin();
	}

	@Override
	public boolean canTakeRequestedAction(Document document,
			String actionRequestCode, Person user) {
		return super.canTakeRequestedAction(document, actionRequestCode, user) || isLocationAdmin();
	}

	@Override
	public boolean canRecall(Document document, Person user) {
		return super.canRecall(document, user) || isLocationAdmin();
	}

	@Override
	public boolean isAuthorized(Object dataObject, String namespaceCode,
			String permissionName, String principalId) {
		return super.isAuthorized(dataObject, namespaceCode, permissionName, principalId) || isLocationAdmin();
	}

	@Override
	public boolean isAuthorizedByTemplate(Object dataObject,
			String namespaceCode, String permissionTemplateName,
			String principalId) {
		return super.isAuthorizedByTemplate(dataObject, namespaceCode, permissionTemplateName, principalId) || isLocationAdmin();
	}

	@Override
	public boolean isAuthorized(Object dataObject, String namespaceCode,
			String permissionTemplateName, String principalId,
			Map<String, String> additionalPermissionDetails,
			Map<String, String> additionalRoleQualifiers) {
		return super.isAuthorizedByTemplate(dataObject, namespaceCode, permissionTemplateName, principalId, additionalPermissionDetails, additionalRoleQualifiers) || isLocationAdmin();
	}

}
