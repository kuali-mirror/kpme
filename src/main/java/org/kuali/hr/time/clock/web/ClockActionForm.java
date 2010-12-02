package org.kuali.hr.time.clock.web;

import java.sql.Timestamp;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

public class ClockActionForm extends TimesheetActionForm {

    /**
     *
     */
    private static final long serialVersionUID = -3843074202863670372L;

    private String principalId;
    private String currentClockAction;
    private String lastClockAction;
    // do we still need nextClockAction?
    private String nextClockAction;
    private Timestamp lastClockTimestamp;
    private String lastClockHours;
    private ClockLog clockLog;
    private TimeBlock timeBlock;

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


}
