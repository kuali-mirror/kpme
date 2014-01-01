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
package org.kuali.kpme.tklm.api.time.rules.clocklocation;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;


/**
 * <p>ClockLocationRuleIpAddressContract interface</p>
 *
 */
public interface ClockLocationRuleIpAddressContract extends HrBusinessObjectContract {
	
	/**
	 * The ipAddress associated with the ClockLocationRuleIpAddress
	 * 
	 * <p>
	 * ipAddress of a ClockLocationRuleIpAddress
	 * <p>
	 * 
	 * @return ipAddress for ClockLocationRuleIpAddress
	 */
	public String getIpAddress();

	/**
	 * The primary key of a ClockLocationRuleIpAddress entry saved in a database
	 * 
	 * <p>
	 * tkClockLocationRuleIpId of a ClockLocationRuleIpAddress
	 * <p>
	 * 
	 * @return tkClockLocationRuleIpId for ClockLocationRuleIpAddress
	 */
	public String getTkClockLocationRuleIpId();
	
	/**
	 * The tkClockLocationRuleId associated with the ClockLocationRuleIpAddress
	 * 
	 * <p>
	 * tkClockLocationRuleId of a ClockLocationRuleIpAddress
	 * <p>
	 * 
	 * @return tkClockLocationRuleId for ClockLocationRuleIpAddress
	 */
	public String getTkClockLocationRuleId();

}
