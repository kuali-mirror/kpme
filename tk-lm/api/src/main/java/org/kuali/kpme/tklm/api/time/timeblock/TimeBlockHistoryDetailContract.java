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
package org.kuali.kpme.tklm.api.time.timeblock;

import java.util.Date;

import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetailContract;
import org.kuali.rice.kim.api.identity.Person;


/**
 * <p>TimeBlockHistoryDetailContract interface</p>
 *
 */
public interface TimeBlockHistoryDetailContract extends TimeHourDetailContract {
	
	/**
	 * The primary key of a TimeBlockHistoryDetail entry saved in a database
	 * 
	 * <p>
	 * tkTimeBlockHistoryDetailId of a TimeBlockHistoryDetail
	 * <p>
	 * 
	 * @return tkTimeBlockHistoryDetailId for TimeBlockHistoryDetail
	 */
	public String getTkTimeBlockHistoryDetailId();

	/**
	 * The id of the TimeBlockHistory object associated with the TimeBlockHistoryDetail
	 * 
	 * <p>
	 * tkTimeBlockHistoryId of a TimeBlockHistoryDetail
	 * <p>
	 * 
	 * @return tkTimeBlockHistoryId for TimeBlockHistoryDetail
	 */
	public String getTkTimeBlockHistoryId();
	
	/**
	 * The TimeBlockHistory object associated with the TimeBlockHistoryDetail
	 * 
	 * <p>
	 * timeBlockHistory of a TimeBlockHistoryDetail
	 * <p>
	 * 
	 * @return timeBlockHistory for TimeBlockHistoryDetail
	 */
	public TimeBlockHistoryContract getTimeBlockHistory();
	
	/**
	 * The principal associated with the TimeBlockHistoryDetail
	 * 
	 * <p>
	 * principal of a TimeBlockHistoryDetail
	 * <p>
	 * 
	 * @return principal for TimeBlockHistoryDetail
	 */
	public Person getPrincipal();

	/**
	 * TODO: What is the difference between principal and userprincipal??
	 * The userPrincipal associated with the TimeBlockHistoryDetail
	 * 
	 * <p>
	 * userPrincipal of a TimeBlockHistoryDetail
	 * <p>
	 * 
	 * @return userPrincipal for TimeBlockHistoryDetail
	 */
	public Person getUserPrincipal();

	/**
	 * The begin date of the TimeBlockHistory object associated with the TimeBlockHistoryDetail
	 * 
	 * <p>
	 * timeBlockHistory.getBeginDate() of a TimeBlockHistoryDetail
	 * <p>
	 * 
	 * @return timeBlockHistory.getBeginDate() for TimeBlockHistoryDetail
	 */
	public Date getBeginDate();

}
