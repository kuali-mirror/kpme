package org.kuali.hr.time.clock.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkForm;

public class ClockActionForm extends TkForm {

    /**
     *
     */
    private static final long serialVersionUID = -3843074202863670372L;

    private String principalId;
    private List<Assignment> assignments;
    private String currentClockAction;
    private String nextClockAction;
    private String lastClockActionTimestamp;
    private String lastClockActionTimestampFormatted;



    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
	super.reset(mapping, request);
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
	return super.validate(mapping, request);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getCurrentClockAction() {
        return currentClockAction;
    }

    public void setCurrentClockAction(String currentClockAction) {
        this.currentClockAction = currentClockAction;
    }

    public String getLastClockActionTimestampFormatted() {
        return lastClockActionTimestampFormatted;
    }

    public void setLastClockActionTimestampFormatted(String lastClockActionTimestampFormatted) {
        this.lastClockActionTimestampFormatted = lastClockActionTimestampFormatted;
    }

    public String getLastClockActionTimestamp() {
        return lastClockActionTimestamp;
    }

    public void setLastClockActionTimestamp(String lastClockActionTimestamp) {
        this.lastClockActionTimestamp = lastClockActionTimestamp;
    }

	public String getNextClockAction() {
		return nextClockAction;
	}

	public void setNextClockAction(String nextClockAction) {
		this.nextClockAction = nextClockAction;
	}


}
