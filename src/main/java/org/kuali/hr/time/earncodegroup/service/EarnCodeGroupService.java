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
package org.kuali.hr.time.earncodegroup.service;

import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public interface EarnCodeGroupService {
	/**
	 * Fetch earn group for a particular date
	 * @param earnCodeGroup
	 * @param asOfDate
	 * @return
	 */
	public EarnCodeGroup getEarnCodeGroup(String earnCodeGroup, Date asOfDate);
	/**
	 * Fetch earn group for an earn code as of a particular date
	 * @param earnCode
	 * @param asOfDate
	 * @return
	 */
	public EarnCodeGroup getEarnCodeGroupForEarnCode(String earnCode, Date asOfDate);
	/**
	 * Fetch Set of earn codes for earn group
	 * @param earnGroup
	 * @param asOfDate
	 * @return
	 */
	public Set<String> getEarnCodeListForEarnCodeGroup(String earnCodeGroup, Date asOfDate);
	/**
	 * Used to get earn group that this earn code belongs on in context to the summary
	 * CAUTION this is used only for the timesheet summary
	 */
	public EarnCodeGroup getEarnCodeGroupSummaryForEarnCode(String earnCode, Date asOfDate);
	
	public EarnCodeGroup getEarnCodeGroup(String hrEarnCodeGroupId);
	
	/**
	 * Returns list of warning text from earn group that is used by time blocks of the timesheetDocument
	 * @param timesheetDocument
	 * @return
	 */
	public List<String> warningTextFromEarnCodeGroupsOfDocument(TimesheetDocument timesheetDocument);
    /**
     * get the count of earn groups by given earnGroup
     * @param earnGroup
     * @return int
     */
    public int getEarnCodeGroupCount(String earnGroup);
    /**
     * get the count of newer version of earn groups by given earnGroup and date
     * @param earnGroup
     * @param effdt
     * @return int
     */
    public int getNewerEarnCodeGroupCount(String earnGroup, Date effdt);
    
    List<EarnCode> getEarnCodeGroups(String earnCodeGroup, String descr, Date fromEffdt, Date toEffdt, String active, String showHist);
}
