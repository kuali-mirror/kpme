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

/**
 * <p>EarnCodeSectionContract interface</p>
 *
 */
public interface EarnCodeSectionContract {
	
	/**
	 * The EarnCode name associated with the EarnCodeSection
	 * 
	 * <p>
	 * earnCode for an EarnCodeSection
	 * <p>
	 * 
	 * @return earnCode for EarnCodeSection
	 */
	public String getEarnCode();
	
	/**
	 * The description name associated with the EarnCodeSection
	 * 
	 * <p>
	 * description for an EarnCodeSection
	 * <p>
	 * 
	 * @return description for EarnCodeSection
	 */
	public String getDescription();

	/**
	 * The list of AssignmentRow objects associated with the EarnCodeSection
	 * 
	 * <p>
	 * assignmentsRows for an EarnCodeSection
	 * <p>
	 * 
	 * @return assignmentsRows for EarnCodeSection
	 */
	public List<? extends AssignmentRowContract> getAssignmentsRows();
	
	/**
	 * The list of totals associated with the EarnCodeSection
	 * 
	 * <p>
	 * totals for an EarnCodeSection
	 * <p>
	 * 
	 * @return totals for EarnCodeSection
	 */
	public List<BigDecimal> getTotals();
	
	/**
	 * The map of AssignKey to AssignmentRow object associated with the EarnCodeSection
	 * 
	 * <p>
	 * assignKeyToAssignmentRowMap for an EarnCodeSection
	 * <p>
	 * 
	 * @return assignKeyToAssignmentRowMap for EarnCodeSection
	 */
	public Map<String, ? extends AssignmentRowContract> getAssignKeyToAssignmentRowMap();
	
	/**
	 * The EarnGroupSection object associated with the EarnCodeSection
	 * 
	 * <p>
	 * earnGroupSection for an EarnCodeSection
	 * <p>
	 * 
	 * @return earnGroupSection for EarnCodeSection
	 */
	public EarnGroupSectionContract getEarnGroupSection();
	
	/**
	 * TODO: Put a better comment
	 * The isAmountEarnCode flag of the EarnCodeSection
	 * 
	 * <p>
	 * isAmountEarnCode flag of an EarnCodeSection
	 * <p>
	 * 
	 * @return isAmountEarnCode for EarnCodeSection
	 */
	public Boolean getIsAmountEarnCode();

}
