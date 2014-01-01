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

import java.sql.Timestamp;


/**
 * <p>LeaveBlockHistoryContract interface</p>
 *
 */
public interface LeaveBlockHistoryContract extends LeaveBlockContract {

	/**
	 * The primary key of a LeaveBlockHistory entry saved in a database
	 * 
	 * <p>
	 * lmLeaveBlockHistoryId of a LeaveBlockHistory
	 * <p>
	 * 
	 * @return lmLeaveBlockHistoryId for LeaveBlockHistory
	 */
	public String getLmLeaveBlockHistoryId(); 

	/**
	 * The action associated with the LeaveBlockHistory
	 * 
	 * <p>
	 * action of a LeaveBlockHistory
	 * <p>
	 * 
	 * @return action for LeaveBlockHistory
	 */	
	public String getAction();

	/**
	 * TODO: Put a better comment
	 * The principalIdDeleted associated with the LeaveBlockHistory
	 * 
	 * <p>
	 * principalIdDeleted of a LeaveBlockHistory
	 * <p>
	 * 
	 * @return principalIdDeleted for LeaveBlockHistory
	 */
	public String getPrincipalIdDeleted();

	/**
	 * TODO: Put a better comment
	 * The timestampDeleted associated with the LeaveBlockHistory
	 * 
	 * <p>
	 * timestampDeleted of a LeaveBlockHistory
	 * <p>
	 * 
	 * @return timestampDeleted for LeaveBlockHistory
	 */
	public Timestamp getTimestampDeleted();
	
	/**
	 * The assignment title associated with the LeaveBlockHistory
	 * 
	 * <p>
	 * If the leave block associated with the LeaveBlockHistory is not null, it checks its work area.  If the work area is not null,
	 * it gets its description and its task.  If the task is not null and not the default task, it gets the task description.
	 * <p>
	 * 
	 * @return "work area description - task description" for LeaveBlockHistory
	 */
	public String getAssignmentTitle();
}
