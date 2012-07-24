package org.kuali.hr.time.authorization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentAuthorizer;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;

/**
 * Base class for the implementation of Authorization in KPME Time and Attendance.
 *
 * Role Security Grid Documentation:
 * https://wiki.kuali.org/display/KPME/Role+Security+Grid
 */
public abstract class TkMaintenanceDocumentAuthorizerBase implements MaintenanceDocumentAuthorizer {

    // Methods from BusinessObjectAuthorizer

    @Override
    public boolean isAuthorized(BusinessObject businessObject, String namespaceCode, String permissionName, String principalId) {
        return true;
    }

    @Override
    public boolean isAuthorizedByTemplate(BusinessObject businessObject, String namespaceCode, String permissionTemplateName, String principalId) {
        return true;
    }

    @Override
    public boolean isAuthorized(BusinessObject businessObject, String namespaceCode, String permissionName, String principalId, Map<String, String> additionalPermissionDetails, Map<String, String> additionalRoleQualifiers) {
        return true;
    }
    
    @Override
    public boolean isAuthorizedByTemplate(Object dataObject, String namespaceCode, String permissionTemplateName, String principalId, Map<String, String> additionalPermissionDetails, Map<String, String> additionalRoleQualifiers) {
    	return true;
    }

    @Override
    public Map<String, String> getCollectionItemRoleQualifications(BusinessObject collectionItemBusinessObject) {
        return new HashMap<String,String>();
    }

    @Override
    public Map<String, String> getCollectionItemPermissionDetails(BusinessObject collectionItemBusinessObject) {
        return new HashMap<String,String>();
    }

    // Methods from MaintenanceDocumentAuthorizer

    @Override
    public boolean canCreate(Class boClass, Person user) {
        return this.rolesIndicateGeneralWriteAccess();
    }

    @Override
    /**
     * In lookup, called for each Business object if the user can edit or not.
     */
    public boolean canMaintain(Object dataObject, Person user) {
        return this.rolesIndicateWriteAccess((BusinessObject) dataObject);
    }

    @Override
    /**
     * Called when submit is clicked from maintenance doc
     */
    public boolean canCreateOrMaintain(MaintenanceDocument maintenanceDocument, Person user){
        return this.rolesIndicateWriteAccess((BusinessObject) maintenanceDocument.getNewMaintainableObject().getDataObject());
    }

    @Override
    public Set<String> getSecurePotentiallyReadOnlySectionIds() {
        return new HashSet<String>();
    }

    // Methods from DocumentAuthorizer

    @Override
    /**
     * TODO: What is this used for? It's called often.
     */
    public boolean canInitiate(String documentTypeName, Person user) {
        return this.rolesIndicateGeneralReadAccess();
    }

    @Override
    /**
     * 
     * One Reference in KualiDocumentActionBase:366
     */
    public boolean canOpen(Document document, Person user) {
    	return this.rolesIndicateGeneralReadAccess();
    }
    
    @Override
    public boolean canEdit(Document document, Person user) {
    	return this.rolesIndicateGeneralWriteAccess();
    }
    
    @Override
    public boolean canAnnotate(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canReload(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canClose(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canSave(Document document, Person user) {
    	return this.rolesIndicateGeneralWriteAccess();
    }

    @Override
    public boolean canRoute(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canCancel(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canCopy(Document document, Person user) {
    	return this.rolesIndicateGeneralWriteAccess();
    }

    @Override
    public boolean canPerformRouteReport(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canBlanketApprove(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canApprove(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canDisapprove(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canSendNoteFyi(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canEditDocumentOverview(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canFyi(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canAcknowledge(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canReceiveAdHoc(Document document, Person user, String actionRequestCode) {
        return true;
    }

    @Override
    public boolean canAddNoteAttachment(Document document, String attachmentTypeCode, Person user) {
        return true;
    }

    @Override
    public boolean canDeleteNoteAttachment(Document document, String attachmentTypeCode, String createdBySelfOnly, Person user) {
        return true;
    }

    @Override
    public boolean canViewNoteAttachment(Document document, String attachmentTypeCode, String authorUniversalIdentifier, Person user) {
        return true;
    }

    @Override
    public boolean canSendAdHocRequests(Document document, String actionRequestCd, Person user) {
        return true;
    }
    
    @Override
    public boolean canSendAnyTypeAdHocRequests(Document document, Person user) {
    	return true;
    }

    @Override
    public boolean canTakeRequestedAction(Document document, String actionRequestCode, Person user) {
    	return true;
    }
    
    @Override
    public boolean canRecall(Document document, Person user) {
    	return true;
    }
    
    // Methods from DataObjectAuthorizer
    
    @Override
    public boolean isAuthorized(Object dataObject, String namespaceCode, String permissionName, String principalId) {
    	return true;
    }

    @Override
    public boolean isAuthorizedByTemplate(Object dataObject, String namespaceCode, String permissionTemplateName, String principalId) {
    	return true;
    }

    @Override
    public boolean isAuthorized(Object dataObject, String namespaceCode, String permissionName, String principalId,
            Map<String, String> additionalPermissionDetails, Map<String, String> additionalRoleQualifiers) {
    	return true;
    }


    // Methods from InquiryOrMaintenanceDocumentAuthorizer

    @Override
    /**
     * Can't return null.
     */
    public Set<String> getSecurePotentiallyHiddenSectionIds() {
        return new HashSet<String>();
    }

    // Override this if necessary:

    /**
     * Returns the UserRoles object for the CURRENT user. This will take into
     * account target/backdoor user status. Subclasses can override this if
     * necessary.
     *
     * @return The UserRoles object for the current user.
     */
    public UserRoles getRoles() {
        TKUser tkuser = TKContext.getUser();
        return tkuser.getCurrentPersonRoles();
    }

    // Subclasses will implement these methods

    /**
     * Method to indicate whether or not the Context current user can read
     * objects if the current maintenance type.
     *
     * @return true if readable, false otherwise.
     */
    abstract public boolean rolesIndicateGeneralReadAccess();

    /**
     * Method to indicate whether or not the Context current user can create/edit
     * objects if the current maintenance type.
     *
     * @return true if create/editable, false otherwise.
     */
    abstract public boolean rolesIndicateGeneralWriteAccess();

    /**
     * Indicates whether or not the current Context user has create/edit rights
     * to the provided BusinessObject.
     *
     * @param bo The BusinessObject under investigation.
     *
     * @return true if editable, false otherwise.
     */
    abstract public boolean rolesIndicateWriteAccess(BusinessObject bo);

    /**
     * Indicates whether or not the current Context user has view rights to the
     * provided BusinessObject.
     *
     * @param bo The BusinessObject under investigation.
     *
     * @return true if editable, false otherwise.
     */
    abstract public boolean rolesIndicateReadAccess(BusinessObject bo);
}