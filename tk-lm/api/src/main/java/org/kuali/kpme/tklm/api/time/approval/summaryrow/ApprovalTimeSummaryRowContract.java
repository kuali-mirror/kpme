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
package org.kuali.kpme.tklm.api.time.approval.summaryrow;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timesummary.TimeSummaryContract;
import org.kuali.rice.kew.api.note.Note;



/**
 * <p>ApprovalTimeSummaryRowContract interface</p>
 *
 */
public interface ApprovalTimeSummaryRowContract {
    
	/**
   	 * The principalId associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * principalId of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return principalId for ApprovalTimeSummaryRow
   	 */
	public String getApprovalStatusMessage() ;

	/**
   	 * The map of assignment descriptions associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * A String (AssignmentDescriptionKey) to Description mapping for all assignments on this summary row 
   	 * <p>
   	 * 
   	 * @return assignmentDescriptions for ApprovalTimeSummaryRow
   	 */
	public Map<String, String> getAssignmentDescriptions();
   
	/**
   	 * The map of approver hours associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * A Map (Assignment key) of Mapped totals (pay label mapping)
   	 * <p>
   	 * 
   	 * @return approverHoursByAssignment for ApprovalTimeSummaryRow
   	 */
    public Map<String, Map<String, BigDecimal>> getApproverHoursByAssignment();

    /**
   	 * The map of other hours associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * A Map (Assignment key) of Mapped totals (pay label mapping) 
   	 * <p>
   	 * 
   	 * @return otherHoursByAssignment for ApprovalTimeSummaryRow
   	 */
    public Map<String, Map<String, BigDecimal>> getOtherHoursByAssignment();
   
    /**
	 * The name associated with the ApprovalTimeSummaryRow
	 * 
	 * <p>
	 * name of an ApprovalTimeSummaryRow
	 * <p>
	 * 
	 * @return name for ApprovalTimeSummaryRow
	 */
    public String getName();
    
    /**
	 * The list of TimeBlock objects associated with the ApprovalTimeSummaryRow
	 * 
	 * <p>
	 * lstTimeBlocks of an ApprovalTimeSummaryRow
	 * <p>
	 * 
	 * @return lstTimeBlocks for ApprovalTimeSummaryRow
	 */
    public List<? extends TimeBlockContract> getLstTimeBlocks();
	
    /**
	 * The approvalStatus associated with the ApprovalTimeSummaryRow
	 * 
	 * <p>
	 * approvalStatus of an ApprovalTimeSummaryRow
	 * <p>
	 * 
	 * @return approvalStatus for ApprovalTimeSummaryRow
	 */
	public String getApprovalStatus();
	
	/**
	 * The documentId associated with the ApprovalTimeSummaryRow
	 * 
	 * <p>
	 * documentId of an ApprovalTimeSummaryRow
	 * <p>
	 * 
	 * @return documentId for ApprovalTimeSummaryRow
	 */
	public String getDocumentId();
	
	/**
   	 * The hoursToPayLabelMap associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * hoursToPayLabelMap of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return hoursToPayLabelMap for ApprovalTimeSummaryRow
   	 */
	public Map<String,BigDecimal> getHoursToPayLabelMap();
    
	/**
   	 * The hoursToFlsaPayLabelMap associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * hoursToFlsaPayLabelMap of an ApprovalTimeSummaryRow 
   	 * <p>
   	 * 
   	 * @return hoursToFlsaPayLabelMap for ApprovalTimeSummaryRow
   	 */
    public Map<String,BigDecimal> getHoursToFlsaPayLabelMap();
	
    /**
	 * The clockStatusMessage associated with the ApprovalTimeSummaryRow
	 * 
	 * <p>
	 * clockStatusMessage of an ApprovalTimeSummaryRow
	 * <p>
	 * 
	 * @return clockStatusMessage for ApprovalTimeSummaryRow
	 */
	public String getClockStatusMessage();

	/**
	 * The payCalendarGroup associated with the ApprovalTimeSummaryRow
	 * 
	 * <p>
	 * payCalendarGroup of an ApprovalTimeSummaryRow
	 * <p>
	 * 
	 * @return payCalendarGroup for ApprovalTimeSummaryRow
	 */
    public String getPayCalendarGroup();
   
    /**
	 * Indicates if this record is initiated or not
	 * 
	 * <p>
	 * Is this record initiated?
	 * <p>
	 * 
	 * @return true if initiated, false if not
	 */
    public boolean isRoutable();

    /**
	 * Indicates if this record is read to be approved.
	 * 
	 * <p>
	 * Is this record ready to be approved?
	 * <p>
	 * 
	 * @return true if a valid TK_APPROVER / TK_PROCESSOR can approve, false otherwise.
	 */
    public boolean isApprovable();

    /**
   	 * The URL parameters associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * Helper method to grab the URL parameters for setting target mode for a
     * user/documentID timesheet.
   	 * <p>
   	 * 
   	 * @return parameter portion of a URL, usable to initiate target mode.
   	 */
    public String getTimesheetUserTargetURLParams();

    /**
   	 * The list of Note objects associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * notes of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return notes for ApprovalTimeSummaryRow
   	 */
    public List<Note> getNotes();

    /**
   	 * The list of warning strings associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * warnings of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return warnings for ApprovalTimeSummaryRow
   	 */
    public List<String> getWarnings();

    /**
   	 * The set of work area strings associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * workAreas of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return workAreas for ApprovalTimeSummaryRow
   	 */
    public Set<String> getWorkAreas();
   
    /**
   	 * The principalId associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * principalId of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return principalId for ApprovalTimeSummaryRow
   	 */
    public String getPrincipalId();

    /**
     * TODO: Put a better comment
	 * The clockedInOverThreshold flag of the ApprovalTimeSummaryRow
	 * 
	 * <p>
	 * clockedInOverThreshold flag of an ApprovalTimeSummaryRow
	 * <p>
	 * 
	 * @return Y if, N if not
	 */
	public Boolean getClockedInOverThreshold();
	
	/**
	 * TODO: Put a better comment
   	 * The selected associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * selected of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return selected for ApprovalTimeSummaryRow
   	 */
	public String getSelected();

	/**
   	 * The TimeSummary object associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * timeSummary of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return timeSummary for ApprovalTimeSummaryRow
   	 */
    public TimeSummaryContract getTimeSummary();

    /**
   	 * The periodTotal associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * periodTotal of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return periodTotal for ApprovalTimeSummaryRow
   	 */
    public BigDecimal getPeriodTotal();
    
    /**
   	 * The outputString associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * outputString of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return outputString for ApprovalTimeSummaryRow
   	 */
	public String getOutputString();
	
	/**
   	 * The color associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * color of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return color for ApprovalTimeSummaryRow
   	 */
	public String getColor();

	/**
   	 * The map of week totals associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * weekTotalMap of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return weekTotalMap for ApprovalTimeSummaryRow
   	 */
	public Map<String, BigDecimal> getWeekTotalMap();
	
	/**
   	 * The map of role names associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * roleNames of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return roleNames for ApprovalTimeSummaryRow
   	 */
    public Map<String, String> getRoleNames();
    
    /**
   	 * The role name of principalId associated with the ApprovalTimeSummaryRow
   	 * 
   	 * <p>
   	 * role name of principalId of an ApprovalTimeSummaryRow
   	 * <p>
   	 * 
   	 * @return getRoleNames().get(HrContext.getPrincipalId() for ApprovalTimeSummaryRow
   	 */
    public String getRoleName();

}
