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
package org.kuali.kpme.tklm.api.time.timeblock;

import java.sql.Timestamp;
import java.util.List;

import org.kuali.rice.kim.api.identity.Person;


/**
 * <p>TimeBlockHistoryContract interface</p>
 *
 */
public interface TimeBlockHistoryContract extends TimeBlockContract {
	
	/**
	 * The primary key of a TimeBlockHistory entry saved in a database
	 * 
	 * <p>
	 * tkTimeBlockHistoryId of a TimeBlockHistory
	 * <p>
	 * 
	 * @return tkTimeBlockHistoryId for TimeBlockHistory
	 */
	public String getTkTimeBlockHistoryId();
	
	/**
	 * The action history associated with the TimeBlockHistory
	 * 
	 * <p>
	 * actionHistory of a TimeBlockHistory
	 * <p>
	 * 
	 * @return actionHistory for TimeBlockHistory
	 */
	public String getActionHistory();
	
	/**
	 * TODO: Put a better comment
	 * The modifiedByPrincipalId associated with the TimeBlockHistory
	 * 
	 * <p>
	 * modifiedByPrincipalId of a TimeBlockHistory
	 * <p>
	 * 
	 * @return modifiedByPrincipalId for TimeBlockHistory
	 */
	public String getModifiedByPrincipalId();
	
	/**
	 * The timestampModified associated with the TimeBlockHistory
	 * 
	 * <p>
	 * timestampModified of a TimeBlockHistory
	 * <p>
	 * 
	 * @return timestampModified for TimeBlockHistory
	 */
	public Timestamp getTimestampModified();

	/**
	 * The principal associated with the TimeBlockHistory
	 * 
	 * <p>
	 * principal of a TimeBlockHistory
	 * <p>
	 * 
	 * @return principal for TimeBlockHistory
	 */
	public Person getPrincipal();
	
	/**
	 * TODO: What's the difference between this field and principal??
	 * The userPrincipal associated with the TimeBlockHistory
	 * 
	 * <p>
	 * userPrincipal of a TimeBlockHistory
	 * <p>
	 * 
	 * @return userPrincipal for TimeBlockHistory
	 */
	public Person getUserPrincipal();
	
	/**
	 * The list of TimeBlockHistoryDetail objects associated with the TimeBlockHistory
	 * 
	 * <p>
	 * timeBlockHistoryDetails of a TimeBlockHistory
	 * <p>
	 * 
	 * @return timeBlockHistoryDetails for TimeBlockHistory
	 */
	public List<? extends TimeBlockHistoryDetailContract> getTimeBlockHistoryDetails();

	/**
	 * The TimeBlock object associated with the TimeBlockHistory
	 * 
	 * <p>
	 * this.timeBlock of a TimeBlockHistory
	 * <p>
	 * 
	 * @return this.timeBlock for TimeBlockHistory
	 */
	public TimeBlockContract getTimeBlock();

}
