package org.kuali.hr.time.admin.web;

import org.kuali.hr.time.base.web.TkForm;

public class DeleteTimesheetForm extends TkForm {

	private static final long serialVersionUID = 3121271193055358638L;
	
	private String deleteDocumentId;

	public String getDeleteDocumentId() {
		return deleteDocumentId;
	}

	public void setDeleteDocumentId(String deleteDocumentId) {
		this.deleteDocumentId = deleteDocumentId;
	}

}