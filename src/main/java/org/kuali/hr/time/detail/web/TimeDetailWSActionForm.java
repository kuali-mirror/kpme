package org.kuali.hr.time.detail.web;

public class TimeDetailWSActionForm extends TimeDetailActionFormBase {

    private Boolean isTimeBlockReadOnly = Boolean.FALSE;

    public Boolean getTimeBlockReadOnly() {
        return isTimeBlockReadOnly;
    }

    public void setTimeBlockReadOnly(Boolean timeBlockReadOnly) {
        isTimeBlockReadOnly = timeBlockReadOnly;
    }
}


