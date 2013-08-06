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
package org.kuali.kpme.tklm.api.time.timesummary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.tklm.api.leave.summary.LeaveSummaryRowContract;



/**
 * <p>TimeSummaryContract interface</p>
 *
 */
public interface TimeSummaryContract {

	/**
	 * The summaryHeader associated with the TimeSummaryContract
	 * 
	 * <p>
	 * summaryHeader for TimeSummaryContract
	 * <p>
	 * 
	 * @return summaryHeader for TimeSummaryContract
	 */
	public List<String> getSummaryHeader();
	
	/**
	 * The list of EarnGroupSection objects associated with the TimeSummaryContract
	 * 
	 * <p>
	 * sections for TimeSummaryContract
	 * <p>
	 * 
	 * @return sections for TimeSummaryContract
	 */
	public List<? extends EarnGroupSectionContract> getSections();
	
	/**
	 * The list of worked hours associated with the TimeSummaryContract
	 * 
	 * <p>
	 * workedHours for TimeSummaryContract
	 * <p>
	 * 
	 * @return workedHours for TimeSummaryContract
	 */
	public List<BigDecimal> getWorkedHours();
	
	/**
	 * The list of LeaveSummaryRow objects associated with the TimeSummaryContract
	 * 
	 * <p>
	 * maxedLeaveRows for TimeSummaryContract
	 * <p>
	 * 
	 * @return maxedLeaveRows for TimeSummaryContract
	 */
	public List<? extends LeaveSummaryRowContract> getMaxedLeaveRows();
	
	/**
	 * TODO: Put a better comment
	 * The weekTotalMap associated with the TimeSummaryContract
	 * 
	 * <p>
	 * weekTotalMap for TimeSummaryContract
	 * <p>
	 * 
	 * @return weekTotalMap for TimeSummaryContract
	 */
	public Map<String, BigDecimal> getWeekTotalMap();
	
	/**
	 * TODO: Put a better comment
	 * The flsaWeekTotalMap associated with the TimeSummaryContract
	 * 
	 * <p>
	 * flsaWeekTotalMap for TimeSummaryContract
	 * <p>
	 * 
	 * @return flsaWeekTotalMap for TimeSummaryContract
	 */
	public Map<String, BigDecimal> getFlsaWeekTotalMap();

}
