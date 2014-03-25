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
package org.kuali.kpme.tklm.api.time.clocklog;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;


/**
 * <p>ClockLogContract interface</p>
 *
 */
public interface ClockLogContract extends KpmeDataTransferObject {
	
	/**
	 * The primary key of a ClockLog entry saved in a database
	 * 
	 * <p>
	 * tkClockLogId of a ClockLog
	 * <p>
	 * 
	 * @return tkClockLogId for ClockLog
	 */
	public String getTkClockLogId();

	/**
	 * The documentId associated with the ClockLog
	 * 
	 * <p>
	 * documentId of a ClockLog
	 * <p>
	 * 
	 * @return documentId for ClockLog
	 */
	public String getDocumentId();

	/**
	 * The principalId associated with the ClockLog
	 * 
	 * <p>
	 * principalId of a ClockLog
	 * <p>
	 * 
	 * @return principalId for ClockLog
	 */
	public String getPrincipalId();
   
	/**
	 * The jobNumber associated with the ClockLog
	 * 
	 * <p>
	 * jobNumber of a ClockLog
	 * <p>
	 * 
	 * @return jobNumber for ClockLog
	 */
    public Long getJobNumber();

    /**
     * The dept associated with the ClockLog's job record
     *
     * <p>
     * dept of a ClockLog
     * <p>
     *
     * @return jobNumber for ClockLog
     */
    public String getDept();


    /**
	 * The clockTimestamp associated with the ClockLog
	 * 
	 * <p>
	 * clockTimestamp of a ClockLog
	 * <p>
	 * 
	 * @return new DateTime(clockTimestamp) for ClockLog
	 */
    public DateTime getClockDateTime();
    
    /**
	 * The clockAction associated with the ClockLog
	 * 
	 * <p>
	 * clockAction of a ClockLog
	 * <p>
	 * 
	 * @return clockAction for ClockLog
	 */
    public String getClockAction();
   
    /**
	 * The ipAddress associated with the ClockLog
	 * 
	 * <p>
	 * ipAddress of a ClockLog
	 * <p>
	 * 
	 * @return ipAddress for ClockLog
	 */
    public String getIpAddress();
   
    /**
	 * The userPrincipalId associated with the ClockLog
	 * 
	 * <p>
	 * userPrincipalId of a ClockLog
	 * <p>
	 * 
	 * @return userPrincipalId for ClockLog
	 */
    public String getUserPrincipalId();
   
    /**
	 * The creation date associated with the ClockLog
	 * 
	 * <p>
	 * timestamp of a ClockLog
	 * <p>
	 * 
	 * @return timestamp for ClockLog
	 */
    public DateTime getCreateTime();

    /**
	 * The clockTimestampTimezone associated with the ClockLog
	 * 
	 * <p>
	 * clockTimestampTimezone of a ClockLog
	 * <p>
	 * 
	 * @return clockTimestampTimezone for ClockLog
	 */
    public String getClockTimestampTimezone();
    
    /**
	 * The next valid clock action associated with the ClockLog
	 * 
	 * <p>
	 * If clockAction is CI (clock in), the next action is CO (clock out).
	 * If clockAction is CO, the next action is CI.
	 * If clockAction is LI (lunch in), the next action is LO (lunch out).
	 * If clockAction is LO, the next action is LI.
	 * <p>
	 * 
	 * @return next valid clock action for ClockLog
	 */
    public String getNextValidClockAction();
	
    /**
	 * The Job object associated with the ClockLog
	 * 
	 * <p>
	 * job of a ClockLog
	 * <p>
	 * 
	 * @return job for ClockLog
	 */
	public JobContract getJob();

	/**
	 * The WorkArea object associated with the ClockLog
	 * 
	 * <p>
	 * workAreaObj of a ClockLog
	 * <p>
	 * 
	 * @return workAreaObj for ClockLog
	 */
	//public WorkAreaContract getWorkAreaObj();
	
	/**
	 * The Task object associated with the ClockLog
	 * 
	 * <p>
	 * taskObj of a ClockLog
	 * <p>
	 * 
	 * @return taskObj for ClockLog
	 */
	//public TaskContract getTaskObj();

	/**
	 * The workArea associated with the ClockLog
	 * 
	 * <p>
	 * workArea of a ClockLog
	 * <p>
	 * 
	 * @return workArea for ClockLog
	 */
	public Long getWorkArea() ;
	
	/**
	 * The task associated with the ClockLog
	 * 
	 * <p>
	 * task of a ClockLog
	 * <p>
	 * 
	 * @return task for ClockLog
	 */
	public Long getTask();

	/**
	 * The clockedByMissedPunch flag of the MissedPunch
	 * 
	 * <p>
	 * clockedByMissedPunch flag of a MissedPunch
	 * <p>
	 * 
	 * @return Y if clocked by missed punch, N if not
	 */
	public boolean isClockedByMissedPunch();

	/**
	 * The unapprovedIP flag of the ClockLog
	 * 
	 * <p>
	 * unapprovedIP flag of a ClockLog
	 * <p>
	 * 
	 * @return Y if it's an approved ip, N if not
	 */
	public boolean isUnapprovedIP();

    /**
     * The string representation of the Assignment of the ClockLog
     *
     * @return assignmentDesriptionKey
     */
    public String getAssignmentDescriptionKey();

}
