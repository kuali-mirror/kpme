package org.kuali.kpme.edo.permission;

public enum EDOPermissionTemplate {

    ASSIGN_ROLE ("Assign Role"),
    INITIATE_DOCUMENT ("Initiate Document"),
    GRANT_PERMISSION ("Grant Permission"),
    USE_SCREEN ("Use Screen"),
    VIEW_VOTE_RECORD ("View Vote Record"),
    EDIT_VOTE_RECORD ("Edit Vote Record"),
    EDIT_DOSSIER ("Edit Dossier"),
    ASSIGN_DELEGATE ("Assign Delegate"),
    ASSIGN_GUEST ("Assign Guest"),
    UPLOAD_REVIEW_LETTER ("Upload Review Letter"),
    UPLOAD_EXTERNAL_LETTER ("Upload External Letter"),
    LOGIN ("Login"),
    CANCEL_DOCUMENT ("Cancel Document"),
    RECALL_DOCUMENT ("Recall Document"),
    SUPER_USER_APPROVE_DOCUMENT ("Super User Approve Document"),
    SUPER_USER_DISAPPROVE_DOCUMENT ("Super User Disapprove Document");

    private String permissionTemplateName;

    private EDOPermissionTemplate(String permissionTemplateName) {
        this.permissionTemplateName = permissionTemplateName;
    }

    public String getPermissionTemplateName() {
        return permissionTemplateName;
    }

    public void setPermissionTemplateName(String permissionTemplateName) {
        this.permissionTemplateName = permissionTemplateName;
    }

}
