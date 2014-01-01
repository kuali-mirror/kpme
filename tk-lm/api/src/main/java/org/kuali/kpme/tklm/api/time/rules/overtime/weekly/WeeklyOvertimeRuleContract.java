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
package org.kuali.kpme.tklm.api.time.rules.overtime.weekly;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupContract;
import org.kuali.kpme.tklm.api.time.rules.TkRuleContract;

/**
 * <p>WeeklyOvertimeRuleContract interface</p>
 *
 */
public interface WeeklyOvertimeRuleContract extends TkRuleContract {
	
	/**
	 * The primary key of a WeeklyOvertimeRule entry saved in a database
	 * 
	 * <p>
	 * tkWeeklyOvertimeRuleId of a WeeklyOvertimeRule
	 * <p>
	 * 
	 * @return tkWeeklyOvertimeRuleId for WeeklyOvertimeRule
	 */
	public String getTkWeeklyOvertimeRuleId();
	
	/**
	 * The EarnGroup name defined to represent the earn codes which count towards the calculation of overtime
	 * 
	 * <p>
	 * maxHoursEarnGroup of a WeeklyOvertimeRule
	 * <p>
	 * 
	 * @return maxHoursEarnGroup for WeeklyOvertimeRule
	 */
	public String getMaxHoursEarnGroup();	

	/**
	 * The EarnGroup name defined to represent the earn codes to be converted to overtime
	 * 
	 * <p>
	 * If there is only one step in overtime calculation, this will be the same earn group as max hours. 
	 * If there are multiple steps, this will be an earn group which is a subset of the max hours earn group
	 * <p>
	 * 
	 * @return convertFromEarnGroup for WeeklyOvertimeRule
	 */
	public String getConvertFromEarnGroup();
	
	/**
	 * The default EarnCode name which other earnings will be converted to
	 * 
	 * <p>
	 * Only earn codes that are designated as an Overtime Earn Code can be used.  This earn code is used for overtime hours 
	 * unless the Work Area for the Assignment has a designated Default Overtime Earn Code.
	 * <p>
	 * 
	 * @return convertToEarnCode for WeeklyOvertimeRule
	 */
	public String getConvertToEarnCode();	

	/**
	 * The definition of multiple steps
	 * 
	 * <p>
	 * This enables the definition of multiple steps in the hours conversion for overtime
	 * <p>
	 * 
	 * @return step for WeeklyOvertimeRule
	 */
	public BigDecimal getStep();	

	/**
	 * The maximum hours in an FLSA period for overtime calculation
	 * 
	 * <p>
	 * maxHours of a WeeklyOvertimeRule
	 * <p>
	 * 
	 * @return maxHours for WeeklyOvertimeRule
	 */
	public BigDecimal getMaxHours();	

	/**
	 * The userPrincipalId associated with the WeeklyOvertimeRule
	 * 
	 * <p>
	 * userPrincipalId of a WeeklyOvertimeRule
	 * <p>
	 * 
	 * @return userPrincipalId for WeeklyOvertimeRule
	 */
	public String getUserPrincipalId();	
	
	/**
	 * 	Indicates this EarnCode may be used for overtime
	 * 
	 * <p>
	 * ovtEarnCode falg of a WeeklyOvertimeRule
	 * <p>
	 * 
	 * @return Y if used for over time, N if not
	 */	
	public Boolean getOvtEarnCode();	

	/**
	 * The EarnCodeGroup object defined to represent the earn codes which count towards the calculation of overtime
	 * 
	 * <p>
	 * maxHoursEarnGroupObj of a WeeklyOvertimeRule
	 * <p>
	 * 
	 * @return maxHoursEarnGroupObj for WeeklyOvertimeRule
	 */
	public EarnCodeGroupContract getMaxHoursEarnGroupObj();	

	/**
	 * The EarnCodeGroup object defined to represent the earn codes to be converted to overtime
	 * 
	 * <p>
	 * convertFromEarnGroupObj of a WeeklyOvertimeRule
	 * <p>
	 * 
	 * @return convertFromEarnGroupObj for WeeklyOvertimeRule
	 */
	public EarnCodeGroupContract getConvertFromEarnGroupObj();	

	/**
	 * The default EarnCode object which other earnings will be converted to
	 * 
	 * <p>
	 * convertToEarnCodeObj of a WeeklyOvertimeRule
	 * <p>
	 * 
	 * @return convertToEarnCodeObj for WeeklyOvertimeRule
	 */
	public EarnCodeContract getConvertToEarnCodeObj();	

	/**
	 * The WeeklyOvertimeRuleGroup id associated with the WeeklyOvertimeRule
	 * 
	 * <p>
	 * tkWeeklyOvertimeRuleGroupId of a WeeklyOvertimeRule
	 * <p>
	 * 
	 * @return tkWeeklyOvertimeRuleGroupId for WeeklyOvertimeRule
	 */
	public Long getTkWeeklyOvertimeRuleGroupId();

}
