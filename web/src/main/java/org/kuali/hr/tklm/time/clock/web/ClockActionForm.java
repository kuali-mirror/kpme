/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.tklm.time.clock.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.kuali.hr.core.HrConstants;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.time.clocklog.ClockLog;
import org.kuali.hr.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.timesheet.web.TimesheetActionForm;

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
    private Date lastClockTimeWithZone;

    private String lastClockHours;
    private ClockLog clockLog;
    private TimeBlock timeBlock;
    private boolean showLunchButton;
    private boolean showDistributeButton;
    private boolean showMissedPunchButton;
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
	
    public String getTargetUserTimezone() {
        return TkServiceLocator.getTimezoneService().getUserTimezone(TKContext.getTargetPrincipalId());
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

    public String getCurrentServerTime() {
		return currentServerTime;
	}

	public void setCurrentServerTime(String currentServerTime) {
		this.currentServerTime = currentServerTime;
	}
	
	public String getUserSystemOffsetServerTime(){
		DateTime dt = new DateTime(LocalDate.now().toDate().getTime()+getUserTimezoneOffset());
		return String.valueOf(dt.getMillis());
	}
	
	public Long getUserTimezoneOffset(){
		DateTimeZone dtz = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
		return TkServiceLocator.getTimezoneService().getTimezoneOffsetFromServerTime(dtz);
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

	public TimeBlock getCurrentTimeBlock() {
		if(currentTimeBlock == null && this.getEditTimeBlockId() != null) {
			this.setCurrentTimeBlock(TkServiceLocator.getTimeBlockService().getTimeBlock(this.getEditTimeBlockId()));
		}
		return currentTimeBlock;
	}

	public void setCurrentTimeBlock(TimeBlock currentTimeBlock) {
		this.currentTimeBlock = currentTimeBlock;
	}

    public String getCurrentAssignmentDescription() {
    	if(currentAssignmentDescription == null && this.getCurrentTimeBlock() != null) {
    		Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(this.getTimesheetDocument(), this.getCurrentTimeBlock().getAssignmentKey());
    		if(assignment != null) {
    			this.setCurrentAssignmentDescription(assignment.getAssignmentDescription());
    		}
    	}
		return currentAssignmentDescription;
	}

	public void setCurrentAssignmentDescription(String currentAssignmentDescription) {
		this.currentAssignmentDescription = currentAssignmentDescription;
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
		 String pId = this.getPrincipalId();
		 if(pId != null) {
			 TimesheetDocument td = this.getTimesheetDocument();
			 if(td != null && !td.getDocumentHeader().getDocumentStatus().equals(HrConstants.ROUTE_STATUS.FINAL)) {
				 List<TimeBlock> tbList = new ArrayList<TimeBlock>();
				 if(td != null) {
					 for(TimeBlock tbTemp : td.getTimeBlocks()) {
						 if(tbTemp.getClockLogCreated()) {
							 tbList.add(tbTemp);
						 }
					 }
				 }
				 List<Assignment> assignmentList = HrServiceLocator.getAssignmentService().getAssignments(pId, null);
				 List<String> aList = new ArrayList<String>();
				 Map<String, List<TimeBlock>> tbMap = new HashMap<String, List<TimeBlock>>();
				 Map<String, String> map2 = new HashMap<String, String>();
				 LinkedHashMap<String, String> desMap = new LinkedHashMap<String, String>();  // for populating assignment dropdown list when click Edit button

				 for(Assignment assignment : assignmentList) {
					TimeCollectionRule rule = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), assignment.getEffectiveLocalDate());
					if(rule != null && rule.isHrsDistributionF() && rule.isClockUserFl()) {
						aList.add(assignment.getAssignmentDescription()+ "=" + assignment.getTkAssignmentId().toString());
						desMap.put(assignment.getTkAssignmentId().toString(), assignment.getAssignmentDescription());

						for(TimeBlock tb: tbList){
							if(assignment.getWorkArea().equals(tb.getWorkArea())) {
								List<TimeBlock> tempList = tbMap.get(assignment.getAssignmentDescription());
								if(tempList == null) {
									tempList = new ArrayList<TimeBlock>();
								}
								tempList.add(tb);
								Collections.sort(tempList);
								tbMap.put(assignment.getAssignmentDescription(), tempList);
								map2.put(assignment.getAssignmentDescription(),assignment.getTkAssignmentId().toString() );
							}
						}
					}
				 }

				 this.setTimeBlocksMap(tbMap);
				 this.setDesList(desMap);

				 List<String> list1= new ArrayList<String>();
				 for(String aString : tbMap.keySet()) {
					 list1.add(aString);
				 }

				 //remove duplicate
				 HashSet h = new HashSet(aList);
				 aList.clear();
				 aList.addAll(h);
				 Collections.sort(aList);
				 this.setAssignDescriptionsList(list1);
				 this.setDistributeAssignList(aList);
			 }
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

	public boolean isShowMissedPunchButton() {
		return showMissedPunchButton;
	}

	public void setShowMissedPunchButton(boolean showMissedPunchButton) {
		this.showMissedPunchButton = showMissedPunchButton;
	}
	
}
