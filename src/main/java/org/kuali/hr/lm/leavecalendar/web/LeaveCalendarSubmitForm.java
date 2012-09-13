package org.kuali.hr.lm.leavecalendar.web;


import org.kuali.hr.time.base.web.TkCommonCalendarForm;

public class LeaveCalendarSubmitForm extends TkCommonCalendarForm {
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
