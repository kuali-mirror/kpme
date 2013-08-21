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
package org.kuali.kpme.tklm.api.time.missedpunch;

import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.rice.krad.bo.PersistableBusinessObject;


/**
 * <p>MissedPunchContract interface</p>
 *
 */
public interface MissedPunchContract extends PersistableBusinessObject {
	
	/**
	 * The primary key of a MissedPunch entry saved in a database
	 * 
	 * <p>
	 * tkMissedPunchId of a MissedPunch
	 * <p>
	 * 
	 * @return tkMissedPunchId for MissedPunch
	 */
	public String getTkMissedPunchId();

	/**
	 * The principalId associated with the MissedPunch
	 * 
	 * <p>
	 * principalId of a MissedPunch
	 * <p>
	 * 
	 * @return principalId for MissedPunch
	 */
	public String getPrincipalId();
	
	/**
	 * The id of the employee's timesheet document that the missed punch was recorded on
	 * 
	 * <p>
	 * timesheetDocumentId of a MissedPunch
	 * <p>
	 * 
	 * @return timesheetDocumentId for MissedPunch
	 */
	public String getTimesheetDocumentId();
	
	/**
	 * The assignment key associated with the MissedPunch
	 * 
	 * <p>
	 * The assignment key the missed punch was for
	 * <p>
	 * 
	 * @return assignment key for MissedPunch
	 */
	public String getAssignmentKey();
	
	/**
	 * The assignment value associated with the MissedPunch
	 * 
	 * <p>
	 * The assignment value the missed punch was for
	 * <p>
	 * 
	 * @return assignment value for MissedPunch
	 */
	public String getAssignmentValue();
	
	/**
	 * The jobNumber associated with the MissedPunch
	 * 
	 * <p>
	 * jobNumber of a MissedPunch
	 * <p>
	 * 
	 * @return jobNumber for MissedPunch
	 */
	public Long getJobNumber();
	
	/**
	 * The workArea associated with the MissedPunch
	 * 
	 * <p>
	 * workArea of a MissedPunch
	 * <p>
	 * 
	 * @return workArea for MissedPunch
	 */
	public Long getWorkArea();
	
	/**
	 * The task associated with the MissedPunch
	 * 
	 * <p>
	 * task of a MissedPunch
	 * <p>
	 * 
	 * @return task for MissedPunch
	 */
	public Long getTask();

	/**
	 * The actionDateTime (Datetime) associated with the MissedPunch
	 * 
	 * <p>
	 * actionDateTime of a MissedPunch
	 * <p>
	 * 
	 * @return new DateTime(actionDateTime) for MissedPunch
	 */
    public DateTime getActionFullDateTime();
    
    /**
	 * The actionDateTime (Date) associated with the MissedPunch
	 * 
	 * <p>
	 * actionDateTime of a MissedPunch
	 * <p>
	 * 
	 * @return actionDateTime for MissedPunch
	 */
	public Date getActionDateTime();
	
	/**
	 * The action date (Date) associated with the MissedPunch
	 * 
	 * <p>
	 * actionDateTime.toDate() of a MissedPunch
	 * <p>
	 * 
	 * @return actionDateTime.toDate() for MissedPunch
	 */
    public Date getActionDate() ;
   
    /**
	 * The action time (String) associated with the MissedPunch
	 * 
	 * <p>
	 * LocalTime.fromDateFields(actionDateTime) of a MissedPunch
	 * <p>
	 * 
	 * @return LocalTime.fromDateFields(actionDateTime) for MissedPunch
	 */
    public String getActionTime();
   
    /**
	 * The clockAction associated with the MissedPunch
	 * 
	 * <p>
	 * clock in or clock out of the missed punch
	 * <p>
	 * 
	 * @return clockAction for MissedPunch
	 */
	public String getClockAction();

	/**
	 * The tkClockLogId associated with the MissedPunch
	 * 
	 * <p>
	 * tkClockLogId of a MissedPunch
	 * <p>
	 * 
	 * @return tkClockLogId for MissedPunch
	 */
	public String getTkClockLogId();
	
	/**
	 * The timestamp associated with the MissedPunch
	 * 
	 * <p>
	 * timestamp of a MissedPunch
	 * <p>
	 * 
	 * @return timestamp for MissedPunch
	 */
	public Timestamp getTimestamp();

	/**
	 * The principal name associated with the MissedPunch
	 * 
	 * <p>
	 * principalName of a MissedPunch
	 * <p>
	 * 
	 * @return principalName for MissedPunch
	 */
	public String getPrincipalName();
	
	/**
	 * The personName associated with the MissedPunch
	 * 
	 * <p>
	 * personName of a MissedPunch
	 * <p>
	 * 
	 * @return personName for MissedPunch
	 */
	public String getPersonName();

	/**
	 * The Job object associated with the MissedPunch
	 * 
	 * <p>
	 * jobObj of a MissedPunch
	 * <p>
	 * 
	 * @return jobObj for MissedPunch
	 */
	public JobContract getJobObj();
	
	/**
	 * The WorkArea object associated with the MissedPunch
	 * 
	 * <p>
	 * workAreaObj of a MissedPunch
	 * <p>
	 * 
	 * @return workAreaObj for MissedPunch
	 */
	public WorkAreaContract getWorkAreaObj();
	
	/**
	 * The Task object associated with the MissedPunch
	 * 
	 * <p>
	 * taskObj of a MissedPunch
	 * <p>
	 * 
	 * @return taskObj for MissedPunch
	 */
	public TaskContract getTaskObj();
	
	/**
	 * The isAssignmentReadOnly flag of the MissedPunch
	 * 
	 * <p>
	 * isAssignmentReadOnly flag of a MissedPunch
	 * <p>
	 * 
	 * @return Y if it's assignment read only, N if not
	 */
	public boolean isAssignmentReadOnly();

}
