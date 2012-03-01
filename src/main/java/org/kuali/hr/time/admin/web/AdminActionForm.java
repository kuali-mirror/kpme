package org.kuali.hr.time.admin.web;

import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.util.TKUser;

public class AdminActionForm extends TkForm {

    private static final long serialVersionUID = -4827349215638585861L;

    private String backdoorPrincipalName;
    private String changeTargetPrincipalId;
    private String deleteDocumentId;
    private String documentId;
    private String targetUrl;
    private String returnUrl;
    private boolean canChangePrincipal;


    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getBackdoorPrincipalName() {
        return backdoorPrincipalName;
    }

    public void setBackdoorPrincipalName(String backdoorPrincipalName) {
        this.backdoorPrincipalName = backdoorPrincipalName;
    }

	public String getChangeTargetPrincipalId() {
		return changeTargetPrincipalId;
	}

	public void setChangeTargetPrincipalId(String changeTargetPrincipalId) {
		this.changeTargetPrincipalId = changeTargetPrincipalId;
	}

	public String getDeleteDocumentId() {
		return deleteDocumentId;
	}

	public void setDeleteDocumentId(String deleteDocumentId) {
		this.deleteDocumentId = deleteDocumentId;
	}

    public boolean isCanChangePrincipal() {
        TKUser user = getUser();
        return user.isSystemAdmin()
               || user.isGlobalViewOnly()
               || user.isDepartmentAdmin()
               || user.isDepartmentViewOnly()
               || user.isLocationAdmin()
               || user.isReviewer();



    }


}
