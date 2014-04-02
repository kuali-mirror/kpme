package org.kuali.rice.krad.kpme.controller;

import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class KPMEMaintenanceDocumentForm extends MaintenanceDocumentForm {

	private static final long serialVersionUID = 3703432581548963925L;
	
	protected String disapprovalNoteText;

	public String getDisapprovalNoteText() {
		return disapprovalNoteText;
	}

	public void setDisapprovalNoteText(String disapprovalNoteText) {
		this.disapprovalNoteText = disapprovalNoteText;
	}
	
}