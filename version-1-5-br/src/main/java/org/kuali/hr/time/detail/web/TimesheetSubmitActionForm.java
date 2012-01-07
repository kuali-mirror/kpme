package org.kuali.hr.time.detail.web;

import org.kuali.hr.time.base.web.TkForm;

public class TimesheetSubmitActionForm extends TkForm {


    private String action;
    private String documentId;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
