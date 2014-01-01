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
package org.kuali.kpme.tklm.api.leave.block;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.block.CalendarBlockContract;


/**
 * <p>LeaveBlockContract interface</p>
 *
 */
public interface LeaveBlockContract extends CalendarBlockContract {

	/**
	 * The AccrualCategoryRuleId associated with the LeaveBlock
	 * 
	 * <p>
	 * It gets AccrualCategoryRule based on the AccrualCategory and PrincipalHRAttributes.
	 * <p>
	 * 
	 * @return lmAccrualCategoryRuleId if AccrualCategoryRule is not null
	 */
	public String getAccrualCategoryRuleId();
	
	/**
	 * The AccrualCategory name associated with the LeaveBlock
	 * 
	 * <p>
	 * It gets AccrualCategory based on the principal id and leave local date.
	 * <p>
	 * 
	 * @return accrualCategory for LeaveBlock
	 */	
	public String getAccrualCategory();

	/**
	 * The accrualGenerated flag to indicate if the accrual is generated or not
	 * 
	 * <p>
	 * accrualGenerated of a LeaveBlock
	 * <p>
	 * 
	 * @return Y if accrual is generated, N if not
	 */	
	public Boolean getAccrualGenerated();

	/**
	 * TODO: Put a better comment
	 * The submit flag of the LeaveBlock
	 * 
	 * <p>
	 * submit of a LeaveBlock
	 * <p>
	 * 
	 * @return Y if submitted, N if not
	 */	
	public boolean isSubmit();

	/**
	 * TODO: What is this used for?  Different from lmLeaveBlockId??
	 * The blockId associated with the LeaveBlock
	 * 
	 * <p>
	 * blockId of a LeaveBlock
	 * <p>
	 * 
	 * @return blockId for LeaveBlock
	 */	
	public Long getBlockId();

	/**
	 * The description associated with the LeaveBlock
	 * 
	 * <p>
	 * description of a LeaveBlock
	 * <p>
	 * 
	 * @return description for LeaveBlock
	 */	
	public String getDescription();

	/**
	 * The leave date (Date) associated with the LeaveBlock
	 * 
	 * <p>
	 * leaveDate of a LeaveBlock
	 * <p>
	 * 
	 * @return leaveDate for LeaveBlock
	 */	
	public Date getLeaveDate();
	
	/**
	 * The leave date (LocalDate) associated with the LeaveBlock
	 * 
	 * <p>
	 * leaveDate of a LeaveBlock
	 * <p>
	 * 
	 * @return leaveDate in LocalDate for LeaveBlock
	 */	
	public LocalDate getLeaveLocalDate();

	/**
	 * The scheduleTimeOffId associated with the LeaveBlock
	 * 
	 * <p>
	 * scheduleTimeOffId of a LeaveBlock
	 * <p>
	 * 
	 * @return scheduleTimeOffId for LeaveBlock
	 */	
	public String getScheduleTimeOffId();
	
	/**
	 * The requestStatus associated with the LeaveBlock
	 * 
	 * <p>
	 * requestStatus of a LeaveBlock
	 * <p>
	 * 
	 * @return requestStatus for LeaveBlock
	 */	
	public String getRequestStatus();

	/**
	 * The value of requestStatus associated with the LeaveBlock
	 * 
	 * <p>
	 * value of requestStatus of a LeaveBlock
	 * <p>
	 * 
	 * @return value for requestStatus in REQUEST_STATUS_STRINGS map if found, "usage" if not found 
	 */	
    public String getRequestStatusString();

    /**
	 * The list of LeaveBlockHistory objects associated with the LeaveBlock
	 * 
	 * <p>
	 * leaveBlockHistories of a LeaveBlock
	 * <p>
	 * 
	 * @return leaveBlockHistories for LeaveBlock
	 */	
	public List<? extends LeaveBlockHistoryContract> getLeaveBlockHistories();

	/**
	 * The reason associated with the LeaveBlock
	 * 
	 * <p>
	 * reason of a LeaveBlock
	 * <p>
	 * 
	 * @return reason for LeaveBlock
	 */	
	public String getReason();

	/**
	 * The assignment title associated with the LeaveBlock
	 * 
	 * <p>
	 * If the work area associated with the LeaveBlock is not null, it gets its description and its task.  
	 * If the task is not null and not the default task, it gets the task description.
	 * <p>
	 * 
	 * @return "work area description - task description" for LeaveBlock
	 */
	public String getAssignmentTitle();

	/**
	 * The calendar id associated with the LeaveBlock
	 * 
	 * <p>
	 * calendarId of a LeaveBlock
	 * <p>
	 * 
	 * @return calendarId for LeaveBlock
	 */	
	public String getCalendarId();

	/**
	 * The description of the earn code associated with the LeaveBlock
	 * 
	 * <p>
	 * earnCodeObj.getDescription() of a LeaveBlock
	 * <p>
	 * 
	 * @return earnCodeObj.getDescription() for LeaveBlock
	 */	
	public String getEarnCodeDescription();

	/**
	 * The type of leave block associated with the LeaveBlock
	 * 
	 * <p>
	 * leaveBlockType of a LeaveBlock
	 * <p>
	 * 
	 * @return leaveBlockType for LeaveBlock
	 */	
	public String getLeaveBlockType();

	/**
	 * Indicates if the principal has a permission to edit a leave block
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return Y if the principal has a permission to edit a leave block, N if not
	 */
    public boolean isEditable();

    /**
	 * Indicates if the principal has a permission to delete the leave block
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return Y if the principal has a permission to delete the leave block, N if not
	 */
    public boolean isDeletable();
    
    /**
	 * The assignment key associated with the LeaveBlock
	 * 
	 * <p>
	 * The key is consisted of job number, work area number, and task number
	 * <p>
	 * 
	 * @return assignmentKey for LeaveBlock
	 */	
    public String getAssignmentKey();
   
    /**
	 * The document status associated with the LeaveBlock
	 * 
	 * <p>
	 * documentStatus of a LeaveBlock
	 * <p>
	 * 
	 * @return documentStatus for LeaveBlock
	 */	
	public String getDocumentStatus();

	/**
	 * The document id of the leave request associated with the LeaveBlock
	 * 
	 * <p>
	 * leaveRequestDocumentId of a LeaveBlock
	 * <p>
	 * 
	 * @return leaveRequestDocumentId for LeaveBlock
	 */	
    public String getLeaveRequestDocumentId();
   
    /**
     * TODO: Put a better comment
	 * The planning description associated with the LeaveBlock
	 * 
	 * <p>
	 * planningDescription of a LeaveBlock
	 * <p>
	 * 
	 * @return planningDescription for LeaveBlock
	 */	
    public String getPlanningDescription();

    /**
     * TODO: Put a better comment
	 * The transactional document id associated with the LeaveBlock
	 * 
	 * <p>
	 * transactionalDocId of a LeaveBlock
	 * <p>
	 * 
	 * @return transactionalDocId for LeaveBlock
	 */	
	public String getTransactionalDocId();

	/**
	 * The begin timestamp (DateTime) associated with the LeaveBlock
	 * 
	 * <p>
	 * beginTimestamp of a LeaveBlock
	 * <p>
	 * 
	 * @return beginTimestamp for LeaveBlock
	 */	
	public DateTime getBeginDateTime();
	
	/**
	 * The end timestamp (DateTime) associated with the LeaveBlock
	 * 
	 * <p>
	 * endTimestamp of a LeaveBlock
	 * <p>
	 * 
	 * @return endTimestamp for LeaveBlock
	 */	
	public DateTime getEndDateTime();
	
	/**
	 * The primary key of a LeaveBlock entry saved in a database
	 * 
	 * <p>
	 * lmLeaveBlockId of a LeaveBlock
	 * <p>
	 * 
	 * @return lmLeaveBlockId for LeaveBlock
	 */
	public String getLmLeaveBlockId();

	/**
	 * The leave amount associated with the LeaveBlock
	 * 
	 * <p>
	 * leaveAmount of a LeaveBlock
	 * <p>
	 * 
	 * @return leaveAmount for LeaveBlock
	 */	
	public BigDecimal getLeaveAmount();

	/**
	 * The workArea number associated with the LeaveBlock
	 * 
	 * <p>
	 * workArea of a LeaveBlock
	 * <p>
	 * 
	 * @return workArea for LeaveBlock
	 */	
	public Long getWorkArea();

	/**
	 * The job number associated with the LeaveBlock
	 * 
	 * <p>
	 * jobNumber of a LeaveBlock
	 * <p>
	 * 
	 * @return jobNumber for LeaveBlock
	 */	
	public Long getJobNumber();

	/**
	 * The task number associated with the LeaveBlock
	 * 
	 * <p>
	 * task of a LeaveBlock
	 * <p>
	 * 
	 * @return task for LeaveBlock
	 */	
	public Long getTask();

	/**
	 * The EarnCode name associated with the LeaveBlock
	 * 
	 * <p>
	 * earnCode of a LeaveBlock
	 * <p>
	 * 
	 * @return earnCode for LeaveBlock
	 */	
	public String getEarnCode();

}
