package org.kuali.hr.time.admin.web;

import org.kuali.hr.time.base.web.TkForm;

public class AdminActionForm extends TkForm {

    private static final long serialVersionUID = -4827349215638585861L;

    private String backdoorPrincipalName;
    private String changeTargetPrincipalName;
    private String deleteDocumentId;

    public String getBackdoorPrincipalName() {
        return backdoorPrincipalName;
    }

    public void setBackdoorPrincipalName(String backdoorPrincipalName) {
        this.backdoorPrincipalName = backdoorPrincipalName;
    }

	public String getChangeTargetPrincipalName() {
		return changeTargetPrincipalName;
	}

	public void setChangeTargetPrincipalName(String changeTargetPrincipalName) {
		this.changeTargetPrincipalName = changeTargetPrincipalName;
	}

	public String getDeleteDocumentId() {
		return deleteDocumentId;
	}

	public void setDeleteDocumentId(String deleteDocumentId) {
		this.deleteDocumentId = deleteDocumentId;
	}

}
