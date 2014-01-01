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
package org.kuali.kpme.tklm.api.time.timesummary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;



/**
 * <p>EarnGroupSectionContract interface</p>
 *
 */
public interface EarnGroupSectionContract {

	/**
	 * The EarnGroup name associated with the EarnGroupSection
	 * 
	 * <p>
	 * earnGroup for an EarnGroupSection
	 * <p>
	 * 
	 * @return earnGroup for EarnGroupSection
	 */
	public String getEarnGroup(); 
	
	/**
	 * The map of totals associated with the EarnGroupSection
	 * 
	 * <p>
	 * totals for an EarnGroupSection
	 * <p>
	 * 
	 * @return totals for EarnGroupSection
	 */
	public Map<Integer, BigDecimal> getTotals();
	
	/**
	 * The map of EarnCode to EarnCodeSection object associated with the EarnGroupSection
	 * 
	 * <p>
	 * earnCodeToEarnCodeSectionMap for an EarnGroupSection
	 * <p>
	 * 
	 * @return earnCodeToEarnCodeSectionMap for EarnGroupSection
	 */
	public Map<String, ? extends EarnCodeSectionContract> getEarnCodeToEarnCodeSectionMap();
	
	/**
	 * The list of EarnCodeSection objects associated with the EarnGroupSection
	 * 
	 * <p>
	 * earnCodeSections for an EarnGroupSection
	 * <p>
	 * 
	 * @return earnCodeSections for EarnGroupSection
	 */
	public List<? extends EarnCodeSectionContract> getEarnCodeSections();

}
