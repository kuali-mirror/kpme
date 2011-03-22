package org.kuali.hr.time.clock.web;

import java.sql.Timestamp;
import java.util.Map;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

public class ClockActionForm extends TimesheetActionForm {

    /**
     *
     */
    private static final long serialVersionUID = -3843074202863670372L;

    private String currentServerTime;
    private String currentClockAction;
    private String lastClockAction;
    // do we still need nextClockAction?
    private String nextClockAction;
    private Timestamp lastClockTimestamp;
    private String lastClockHours;
    private ClockLog clockLog;
    private TimeBlock timeBlock;
    private boolean showLunchButton;
    private boolean showDistributeButton;
	private String outputString;

    /** This map is used to determine whether or not lunch buttons will render
     * for the selected assignment. The key of this map should be the same key
     * as what is selected in the assignment drop down selection. */
    private Map<String,Boolean> assignmentLunchMap;

    public String getOutputString() {
		return outputString;
	}

	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}
    
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
    
    /**
     *
     * This method is dependent on hrsDistributionF flag of TimeCollectionRule
     *
     * @return true if Distribute TimeBlock button should be displayed, false otherwise.
     */
    public boolean isShowDistributeButton() {
    	String assignmentKey = this.getSelectedAssignment();
    	if(assignmentKey != null) {
	    	TimesheetDocument timesheetDocument = this.getTimesheetDocument();
	    	if(timesheetDocument != null) {
		    	Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(timesheetDocument, assignmentKey);
		    	if(assignment != null) {
			    	TimeCollectionRule rule = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), assignment.getEffectiveDate());
			    	if(rule != null) {
			    		setShowDistrubuteButton(rule.isHrsDistributionF());
			    	}
		    	}
	    	}
	    	
    	}
    	return showDistributeButton;
    }

    public void setShowDistrubuteButton(boolean showDistrubuteButton) {
        this.showDistributeButton = showDistrubuteButton;
    }
    
	public void setSelectedAssignment(String selectedAssignment) {
		super.setSelectedAssignment(selectedAssignment);
		this.isShowDistributeButton();
	}
    
    
    
}
