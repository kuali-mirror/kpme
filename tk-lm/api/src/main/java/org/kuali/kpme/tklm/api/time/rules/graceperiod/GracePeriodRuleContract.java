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
package org.kuali.kpme.tklm.api.time.rules.graceperiod;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;


/**
 * <p>GracePeriodRuleContract interface</p>
 *
 */
public interface GracePeriodRuleContract extends HrBusinessObjectContract {
	
	/**
	 * The number of minutes that define the Grace Period
	 * 
	 * <p>
	 * hourFactor of a GracePeriodRule
	 * <p>
	 * 
	 * @return hourFactor for GracePeriodRule
	 */
	public BigDecimal getHourFactor();

	/**
	 * TODO: Put a better comment
	 * The user principal id associated with the GracePeriodRule
	 * 
	 * <p>
	 * userPrincipalId of a GracePeriodRule
	 * <p>
	 * 
	 * @return userPrincipalId for GracePeriodRule
	 */
	public String getUserPrincipalId();
	
	/**
	 * The primary key of a GracePeriodRule entry saved in a database
	 * 
	 * <p>
	 * tkGracePeriodRuleId of a GracePeriodRule
	 * <p>
	 * 
	 * @return tkGracePeriodRuleId for GracePeriodRule
	 */
	public String getTkGracePeriodRuleId();

	/**
	 * TODO: Put a better comment
	 * The history flag of the GracePeriodRule
	 * 
	 * <p>
	 * history flag of a GracePeriodRule
	 * <p>
	 * 
	 * @return history for GracePeriodRule
	 */
	public boolean isHistory();

}
