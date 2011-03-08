package org.kuali.hr.time.clock.web;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

import java.sql.Timestamp;
import java.util.Map;

public class ClockActionForm extends TimesheetActionForm {

    /**
     *
     */
    private static final long serialVersionUID = -3843074202863670372L;

    private String currentServerTime;
    private String principalId;
    private String currentClockAction;
    private String lastClockAction;
    // do we still need nextClockAction?
    private String nextClockAction;
    private Timestamp lastClockTimestamp;
    private String lastClockHours;
    private ClockLog clockLog;
    private TimeBlock timeBlock;
    private boolean showLunchButton;

    /** This map is used to determine whether or not lunch buttons will render
     * for the selected assignment. The key of this map should be the same key
     * as what is selected in the assignment drop down selection. */
    private Map<String,Boolean> assignmentLunchMap;

    public Map<String, Boolean> getAssignmentLunchMap() {
        return assignmentLunchMap;
    }

    public void setAssignmentLunchMap(Map<String,Boolean> assignmentLunchMap) {
        this.assignmentLunchMap = assignmentLunchMap;
    }

    public String getCurrentServerTime() {
		return currentServerTime;
	}

	public void setCurrentServerTime(String currentServerTime) {
		this.currentServerTime = currentServerTime;
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

	public String getNextClockAction() {
		return nextClockAction;
	}

	public void setNextClockAction(String nextClockAction) {
		this.nextClockAction = nextClockAction;
	}

	public Timestamp getLastClockTimestamp() {
		return lastClockTimestamp;
	}

	public void setLastClockTimestamp(Timestamp lastClockTimestamp) {
		this.lastClockTimestamp = lastClockTimestamp;
	}

	public ClockLog getClockLog() {
		return clockLog;
	}

	public void setClockLog(ClockLog clockLog) {
		this.clockLog = clockLog;
	}

	public String getLastClockHours() {
		return lastClockHours;
	}

	public void setLastClockHours(String lastClockHours) {
		this.lastClockHours = lastClockHours;
	}

	public TimeBlock getTimeBlock() {
		return timeBlock;
	}

	public void setTimeBlock(TimeBlock timeBlock) {
		this.timeBlock = timeBlock;
	}

	public String getLastClockAction() {
		return lastClockAction;
	}

	public void setLastClockAction(String lastClockAction) {
		this.lastClockAction = lastClockAction;
	}

    /**
     * Accounts for presence of Department Lunch Rule and System Lunch Rule.
     *
     * This method is dependent on assignmentLunchMap being populated with
     * proper keys/values.
     *
     * @return true if lunch buttons should be displayed, false otherwise.
     */
    public boolean isShowLunchButton() {
        if (showLunchButton) {
            if (this.assignmentLunchMap != null) {
                Boolean val = this.assignmentLunchMap.get(this.getSelectedAssignment());
                return (showLunchButton && val != null && !val);
            } else {
                return showLunchButton;
            }
        } else {
            return false;
        }
    }

    /**
     * @param showLunchButton true if system lunch rule is set.
     */
    public void setShowLunchButton(boolean showLunchButton) {
        this.showLunchButton = showLunchButton;
    }
}
