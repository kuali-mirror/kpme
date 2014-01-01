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
package org.kuali.kpme.tklm.api.time.service.mobile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * <p>ClockEntryInfoContract interface</p>
 *
 */
public interface ClockEntryInfoContract {
	
	/**
	 * The map of assignment key to assignment description associated with the ClockEntryInfo
	 * 
	 * <p>
	 * assignKeyToAssignmentDescriptions of a ClockEntryInfo
	 * <p>
	 * 
	 * @return assignKeyToAssignmentDescriptions for ClockEntryInfo
	 */
	public Map<String, String> getAssignKeyToAssignmentDescriptions();
	
	/**
	 * The lastClockLogDescription associated with the ClockEntryInfo
	 * 
	 * <p>
	 * lastClockLogDescription of a ClockEntryInfo
	 * <p>
	 * 
	 * @return lastClockLogDescription for ClockEntryInfo
	 */
	public String getLastClockLogDescription();
	
	/**
	 * TODO: Make suret this comment is right
	 * The list of clock actions associated with the ClockEntryInfo
	 * 
	 * <p>
	 * clockActions of a ClockEntryInfo
	 * <p>
	 * 
	 * @return clockActions for ClockEntryInfo
	 */
	public List<String> getClockActions();
	
	/**
	 * The current time associated with the ClockEntryInfo
	 * 
	 * <p>
	 * currentTime of a ClockEntryInfo
	 * <p>
	 * 
	 * @return currentTime for ClockEntryInfo
	 */
	public Timestamp getCurrentTime();

}
