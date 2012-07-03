package org.kuali.hr.time.authorization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.DocumentAuthorizerBase;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentAuthorizer;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.document.Document;

public class SystemAdminAuthorizer implements MaintenanceDocumentAuthorizer {
	public boolean isSystemAdmin(){
		return TKContext.getUser().getCurrentRoles().isSystemAdmin();
	}
	
	public boolean isGlobalViewOnly(){
		return TKContext.getUser().getCurrentRoles().isGlobalViewOnly();
	}

//	@Override
//	public Set<String> getDocumentActions(Document document, Person user,
//			Set<String> documentActions) {
//        DocumentAuthorizerBase dab = new DocumentAuthorizerBase();
//        return dab.getDocumentActions(document, user, documentActions);
//	}

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

//	@Override
//	public boolean canViewNoteAttachment(Document document,
//			String attachmentTypeCode, Person user) {
//		return isSystemAdmin();
//	}

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

//	@Override
//	public boolean isAuthorizedByTemplate(BusinessObject businessObject,
//			String namespaceCode, String permissionTemplateName,
//			String principalId,
//			Map<String, String> additionalPermissionDetails,
//			Map<String, String> additionalRoleQualifiers) {
//		return isSystemAdmin();
//	}

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

//	@Override
//	public boolean canMaintain(BusinessObject businessObject, Person user) {
//		return isSystemAdmin();
//	}
//
//	@Override
//	public boolean canCreateOrMaintain(MaintenanceDocument maintenanceDocument,
//			Person user) {
//		return isSystemAdmin();
//	}

	@Override
	public Set<String> getSecurePotentiallyReadOnlySectionIds() {
		return new HashSet<String>();
	}

	@Override
	public boolean isAuthorizedByTemplate(Object dataObject,
			String namespaceCode, String permissionTemplateName,
			String principalId,
			Map<String, String> additionalPermissionDetails,
			Map<String, String> additionalRoleQualifiers) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMaintain(Object dataObject, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canCreateOrMaintain(
			org.kuali.rice.krad.maintenance.MaintenanceDocument maintenanceDocument,
			Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canEdit(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canAnnotate(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canReload(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canClose(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSave(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRoute(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canCancel(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canCopy(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPerformRouteReport(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBlanketApprove(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canApprove(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDisapprove(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSendNoteFyi(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canEditDocumentOverview(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canFyi(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canAcknowledge(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canViewNoteAttachment(Document document,
			String attachmentTypeCode, String authorUniversalIdentifier,
			Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSendAnyTypeAdHocRequests(Document document, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canTakeRequestedAction(Document document,
			String actionRequestCode, Person user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAuthorized(Object dataObject, String namespaceCode,
			String permissionName, String principalId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAuthorizedByTemplate(Object dataObject,
			String namespaceCode, String permissionTemplateName,
			String principalId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAuthorized(Object dataObject, String namespaceCode,
			String permissionName, String principalId,
			Map<String, String> additionalPermissionDetails,
			Map<String, String> additionalRoleQualifiers) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRecall(Document arg0, Person arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}