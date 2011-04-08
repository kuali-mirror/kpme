package org.kuali.hr.time.authorization;

import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.DocumentAuthorizerBase;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentAuthorizer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public boolean isAuthorizedByTemplate(BusinessObject businessObject, String namespaceCode, String permissionTemplateName, String principalId, Map<String, String> additionalPermissionDetails, Map<String, String> additionalRoleQualifiers) {
        return true;
    }

    @Override
    public Map<String, String> getCollectionItemRoleQualifications(BusinessObject collectionItemBusinessObject) {
        return null;
    }

    @Override
    public Map<String, String> getCollectionItemPermissionDetails(BusinessObject collectionItemBusinessObject) {
        return null;
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
    public boolean canMaintain(BusinessObject businessObject, Person user) {
        return this.rolesIndicateWriteAccess(businessObject);
    }

    @Override
    /**
     * Called when submit is clicked from maintenance doc
     */
    public boolean canCreateOrMaintain(MaintenanceDocument maintenanceDocument, Person user) {
        return this.rolesIndicateWriteAccess(maintenanceDocument.getNewMaintainableObject().getBusinessObject());
    }

    @Override
    public Set<String> getSecurePotentiallyReadOnlySectionIds() {
        return new HashSet<String>();
    }

    // Methods from DocumentAuthorizer

    @Override
    public Set<String> getDocumentActions(Document document, Person user, Set<String> documentActions) {
        DocumentAuthorizerBase dab = new DocumentAuthorizerBase();
        return dab.getDocumentActions(document, user, documentActions);
    }

    @Override
    /**
     * TODO: What is this used for? It's called often.
     */
    public boolean canInitiate(String documentTypeName, Person user) {
        return this.rolesIndicateGeneralReadAccess();
    }

    @Override
    /**
     * TODO: Does this get called by any of our code?
     * One Reference in KualiDocumentActionBase:366
     */
    public boolean canOpen(Document document, Person user) {
        return false;
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
    public boolean canViewNoteAttachment(Document document, String attachmentTypeCode, Person user) {
        return true;
    }

    @Override
    public boolean canSendAdHocRequests(Document document, String actionRequestCd, Person user) {
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
        return tkuser.getCurrentRoles();
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