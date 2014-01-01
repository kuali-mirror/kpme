/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.time.clock.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.timesheet.web.TimesheetActionForm;

public class ClockActionForm extends TimesheetActionForm {

    private static final long serialVersionUID = -3843074202863670372L;

    private String currentClockAction;
    private String lastClockAction;
    // do we still need nextClockAction?
    private String nextClockAction;
    private Date lastClockTimestamp;
    private Date lastClockTimeWithZone;

    private String lastClockHours;
    private ClockLog clockLog;
    private TimeBlock timeBlock;
    private boolean clockButtonEnabled;
	private boolean showClockButton;
    private boolean showLunchButton;
    private boolean showDistributeButton;
    private Map<String, List<TimeBlock>> timeBlocksMap;
    private List<String> assignDescriptionsList;

    private List<String> distributeAssignList;
    private LinkedHashMap<String, String> desList;

    private String editTimeBlockId;
    private TimeBlock currentTimeBlock;
    private String currentAssignmentDescription;
    private String currentAssignmentKey;
    private String tbId;
    private String tsDocId;

    private String newAssignDesCol;
    private String newBDCol;
    private String newBTCol;
    private String newEDCol;
    private String newETCol;
    private String newHrsCol;
	private String errorMessage;


 // this is for the ajax call
	private String outputString;
	
	// for lunch deduction rule
	private BigDecimal lunchDeductionAmt = BigDecimal.ZERO;
	
    public String getTargetUserTimezone() {
        return HrServiceLocator.getTimezoneService().getUserTimezone(HrContext.getTargetPrincipalId());
    }

    public Date getLastClockTimeWithZone() {
        return lastClockTimeWithZone;
    }

    public void setLastClockTimeWithZone(Date lastClockTimeWithZone) {
        this.lastClockTimeWithZone = lastClockTimeWithZone;
    }

    public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOutputString() {
		return outputString;
	}

	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}
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

    public long getCurrentServerTime() {
		return DateTime.now(TKUtils.getSystemDateTimeZone()).getMillis();
	}
	
	public long getCurrentUserUTCOffset() {
		return Long.valueOf(DateTime.now(HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback()).toString("Z")) / 100;
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

	public Date getLastClockTimestamp() {
		return lastClockTimestamp;
	}

	public void setLastClockTimestamp(Date lastClockTimestamp) {
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

	public TimeBlock getCurrentTimeBlock() {
		if(currentTimeBlock == null && this.getEditTimeBlockId() != null) {
			this.setCurrentTimeBlock(TkServiceLocator.getTimeBlockService().getTimeBlock(this.getEditTimeBlockId()));
			if(currentTimeBlock != null) {
				for(TimeHourDetail thd : currentTimeBlock.getTimeHourDetails()) {
					if(thd.getEarnCode().equalsIgnoreCase(HrConstants.LUNCH_EARN_CODE)) {
						this.setLunchDeductionAmt(thd.getHours());
					}
				}
			}
		}
		return currentTimeBlock;
	}

	public void setCurrentTimeBlock(TimeBlock currentTimeBlock) {
		this.currentTimeBlock = currentTimeBlock;
	}

    public String getCurrentAssignmentDescription() {
    	if(currentAssignmentDescription == null && this.getCurrentTimeBlock() != null) {
    		AssignmentDescriptionKey adk = AssignmentDescriptionKey.get(this.getCurrentTimeBlock().getAssignmentKey());
    		Assignment assignment = this.getTimesheetDocument().getAssignment(adk);
    		if(assignment != null) {
    			this.setCurrentAssignmentDescription(assignment.getAssignmentDescription());
    		}
    	}
		return currentAssignmentDescription;
	}

	public void setCurrentAssignmentDescription(String currentAssignmentDescription) {
		this.currentAssignmentDescription = currentAssignmentDescription;
	}

	public boolean isClockButtonEnabled() {
		return clockButtonEnabled;
	}

	public void setClockButtonEnabled(boolean clockButtonEnabled) {
		this.clockButtonEnabled = clockButtonEnabled;
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
    	return showDistributeButton;
    }

    public void setShowDistrubuteButton(boolean showDistrubuteButton) {
        this.showDistributeButton = showDistrubuteButton;
    }

	public void setSelectedAssignment(String selectedAssignment) {
		super.setSelectedAssignment(selectedAssignment);
		this.isShowDistributeButton();
	}

	public List<String> getAssignDescriptionsList() {
		if(assignDescriptionsList == null && this.getTimeBlocksMap() != null) {
			List<String> list1= new ArrayList<String>();
			 for(String aString : getTimeBlocksMap().keySet()) {
				 list1.add(aString);
			 }
			 this.setAssignDescriptionsList(list1);
		}
		return assignDescriptionsList;
	}

	public void setAssignDescriptionsList(List<String> assignDescriptionsList) {
		this.assignDescriptionsList = assignDescriptionsList;
	}

	public String getEditTimeBlockId() {
		return editTimeBlockId;
	}

	public void setEditTimeBlockId(String editTimeBlockId) {
		this.editTimeBlockId = editTimeBlockId;
	}

	public void findTimeBlocksToDistribute() {
		if (getTimesheetDocument() != null && !HrConstants.ROUTE_STATUS.FINAL.equals(getTimesheetDocument().getDocumentHeader().getDocumentStatus())) {

			Map<String, List<TimeBlock>> timeBlocksMap = new HashMap<String, List<TimeBlock>>();
			for (TimeBlock timeBlock : getTimesheetDocument().getTimeBlocks()) {
				if (timeBlock.getClockLogCreated()) {
					AssignmentContract assignment = HrServiceLocator.getAssignmentService().getAssignmentForTargetPrincipal(AssignmentDescriptionKey.get(timeBlock.getAssignmentKey()), LocalDate.fromDateFields(timeBlock.getBeginDate()));
					if (assignment != null) {
						WorkAreaContract aWorkArea = HrServiceLocator.getWorkAreaService().getWorkAreaWithoutRoles(assignment.getWorkArea(), LocalDate.fromDateFields(timeBlock.getBeginDate()));
						if(assignment.getJob() != null) {
							TimeCollectionRule rule = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), assignment.getJob().getHrPayType(), LocalDate.fromDateFields(timeBlock.getBeginDate()));
							if (rule != null && aWorkArea != null && aWorkArea.isHrsDistributionF() && rule.isClockUserFl()) {
								List<TimeBlock> timeBlockList = timeBlocksMap.get(assignment.getAssignmentDescription());
								if (timeBlockList == null) {
									timeBlockList = new ArrayList<TimeBlock>();
								}
								timeBlockList.add(timeBlock);
								Collections.sort(timeBlockList);
								timeBlocksMap.put(assignment.getAssignmentDescription(), timeBlockList);	
							}
						}
					}
				}
			}
			
			setTimeBlocksMap(timeBlocksMap);
			setAssignDescriptionsList(new ArrayList<String>(timeBlocksMap.keySet()));
		}
	}
	
	public void populateAssignmentsForSelectedTimeBlock(TimeBlock tb) {
		if(tb == null) {
			setDesList(new LinkedHashMap<String, String>());
			setDistributeAssignList(new ArrayList<String>());
			return;
		}
		if (getTimesheetDocument() != null && !HrConstants.ROUTE_STATUS.FINAL.equals(getTimesheetDocument().getDocumentHeader().getDocumentStatus())) {
	
			LinkedHashMap<String, String> desList = new LinkedHashMap<String, String>();
			List<String> distributeAssignList = new ArrayList<String>();
			for (Assignment assignment : getTimesheetDocument().getAssignments()) {
				WorkAreaContract aWorkArea = HrServiceLocator.getWorkAreaService().getWorkAreaWithoutRoles(assignment.getWorkArea(), LocalDate.fromDateFields(tb.getBeginDate()));
				if(assignment.getJob() != null) {
					TimeCollectionRule rule = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), assignment.getJob().getHrPayType(), LocalDate.fromDateFields(tb.getBeginDate()));
					if (rule != null && aWorkArea != null && aWorkArea.isHrsDistributionF() && rule.isClockUserFl()) {
						desList.put(assignment.getTkAssignmentId().toString(), assignment.getAssignmentDescription());
						distributeAssignList.add(assignment.getAssignmentDescription()+ "=" + assignment.getTkAssignmentId().toString());
					}
				}
			}
			setDesList(desList);
			setDistributeAssignList(distributeAssignList);
		}
	}
					
	public String getCurrentAssignmentKey() {
		return currentAssignmentKey;
	}

	public void setCurrentAssignmentKey(String currentAssignmentKey) {
		this.currentAssignmentKey = currentAssignmentKey;
	}

	public String getTbId() {
		return tbId;
	}

	public void setTbId(String tbId) {
		this.tbId = tbId;
	}
	public String getNewAssignDesCol() {
		return newAssignDesCol;
	}

	public void setNewAssignDesCol(String newAssignDesCol) {
		this.newAssignDesCol = newAssignDesCol;
	}

	public String getNewBDCol() {
		return newBDCol;
	}

	public void setNewBDCol(String newBDCol) {
		this.newBDCol = newBDCol;
	}

	public String getNewBTCol() {
		return newBTCol;
	}

	public void setNewBTCol(String newBTCol) {
		this.newBTCol = newBTCol;
	}

	public String getNewEDCol() {
		return newEDCol;
	}

	public void setNewEDCol(String newEDCol) {
		this.newEDCol = newEDCol;
	}

	public String getNewETCol() {
		return newETCol;
	}

	public void setNewETCol(String newETCol) {
		this.newETCol = newETCol;
	}

	public String getNewHrsCol() {
		return newHrsCol;
	}

	public void setNewHrsCol(String newHrsCol) {
		this.newHrsCol = newHrsCol;
	}

	public Map<String, List<TimeBlock>> getTimeBlocksMap() {
		if(timeBlocksMap == null ) {
			this.findTimeBlocksToDistribute();
		}
		return timeBlocksMap;
	}

	public void setTimeBlocksMap(Map<String, List<TimeBlock>> timeBlocksMap) {
		this.timeBlocksMap = timeBlocksMap;
	}

	public List<String> getDistributeAssignList() {
		return distributeAssignList;
	}

	public void setDistributeAssignList(List<String> distributeAssignList) {
		this.distributeAssignList = distributeAssignList;
	}

	public LinkedHashMap<String, String> getDesList() {
		return desList;
	}

	public void setDesList(LinkedHashMap<String, String> desList) {
		this.desList = desList;
	}

	public String getTsDocId() {
		return tsDocId;
	}

	public void setTsDocId(String tsDocId) {
		this.tsDocId = tsDocId;
	}

	public BigDecimal getLunchDeductionAmt() {
		return lunchDeductionAmt;
	}

	public void setLunchDeductionAmt(BigDecimal lunchDeductionAmt) {
		this.lunchDeductionAmt = lunchDeductionAmt;
	}

	public void setShowClockButton(boolean showClockButton) {
		this.showClockButton = showClockButton;
	}
	
	public boolean getShowClockButton() {
		return showClockButton;
	}
	
}
