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
package org.kuali.kpme.core.bo.earncode.group.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroup;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;

public interface EarnCodeGroupService {
	/**
	 * Fetch earn group for a particular date
	 * @param earnCodeGroup
	 * @param asOfDate
	 * @return
	 */
	public EarnCodeGroup getEarnCodeGroup(String earnCodeGroup, LocalDate asOfDate);
	/**
	 * Fetch earn group for an earn code as of a particular date
	 * @param earnCode
	 * @param asOfDate
	 * @return
	 */
	public EarnCodeGroup getEarnCodeGroupForEarnCode(String earnCode, LocalDate asOfDate);
	/**
	 * Fetch Set of earn codes for earn group
	 * @param earnGroup
	 * @param asOfDate
	 * @return
	 */
	public Set<String> getEarnCodeListForEarnCodeGroup(String earnCodeGroup, LocalDate asOfDate);
	/**
	 * Used to get earn group that this earn code belongs on in context to the summary
	 * CAUTION this is used only for the timesheet summary
	 */
	public EarnCodeGroup getEarnCodeGroupSummaryForEarnCode(String earnCode, LocalDate asOfDate);
	
	public EarnCodeGroup getEarnCodeGroup(String hrEarnCodeGroupId);
	
	/**
	 * Returns list of warning text from earn group that is used by time blocks of the timesheetDocument
	 * @param earnCodeMap A mapping of earn codes to the begin dates of the time blocks for which the earn code is in use.
	 * @return
	 */
	public List<String> warningTextFromEarnCodeGroupsOfDocument(Map<String,List<LocalDate>> earnCodeMap);
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
    public int getNewerEarnCodeGroupCount(String earnGroup, LocalDate effdt);
    
    List<EarnCode> getEarnCodeGroups(String earnCodeGroup, String descr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist);
}
