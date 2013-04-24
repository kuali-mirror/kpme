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
package org.kuali.hr.tklm.time.rules.clocklocation.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.tklm.time.rules.clocklocation.ClockLocationRule;

public interface ClockLocationDao {
	/**
	 * Get Clock location rule based on passed in criteria
	 * @param dept
	 * @param workArea
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
	public List<ClockLocationRule> getClockLocationRule(String dept, Long workArea, String principalId, 
													Long jobNumber, LocalDate asOfDate);
	/**
	 * Get list of clock location rules based on criteria
	 * @param dept
	 * @param workArea
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
	public List<ClockLocationRule> getNewerVersionClockLocationRule(String dept, Long workArea, String principalId, 
			Long jobNumber, LocalDate asOfDate);
	/**
	 * Get Clock Location Rule based on id
	 * @param tkClockLocationRuleId
	 * @return
	 */
	public ClockLocationRule getClockLocationRule(String tkClockLocationRuleId);
	/**
	 * populate ip addresses for given ClockLocationRule
	 * @param ClockLocationRule
	 * @return
	 */
	public void populateIPAddressesForCLR(ClockLocationRule clr);

    List<ClockLocationRule> getClockLocationRules(LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber, String dept, String workArea, String active, String showHistory);
}
