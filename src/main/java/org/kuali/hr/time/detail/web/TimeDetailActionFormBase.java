package org.kuali.hr.time.detail.web;

import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

public class TimeDetailActionFormBase extends TimesheetActionForm {

    private String outputString;
    private String warningJason;


    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    public String getWarningJason() {
        return warningJason;
    }

    public void setWarningJason(String warningJason) {
        this.warningJason = warningJason;
    }
}
