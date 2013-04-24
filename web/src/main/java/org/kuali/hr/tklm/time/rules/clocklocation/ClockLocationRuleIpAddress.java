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
package org.kuali.hr.tklm.time.rules.clocklocation;

import org.kuali.hr.core.HrBusinessObject;

public class ClockLocationRuleIpAddress extends HrBusinessObject {

	private static final long serialVersionUID = 1L;
	private String tkClockLocationRuleIpId;
	private String tkClockLocationRuleId;
	private String ipAddress;
	
	@Override
	public String getId() {
		return this.getTkClockLocationRuleIpId();
	}

	@Override
	public void setId(String id) {
		this.setTkClockLocationRuleIpId(id);
	}
	@Override
	public String getUniqueKey() {
		String ipAddressKey = getTkClockLocationRuleIpId().toString()
			+"_"+ getTkClockLocationRuleId().toString() + "_" + getIpAddress();
		return ipAddressKey;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getTkClockLocationRuleIpId() {
		return tkClockLocationRuleIpId;
	}

	public void setTkClockLocationRuleIpId(String tkClockLocationRuleIpId) {
		this.tkClockLocationRuleIpId = tkClockLocationRuleIpId;
	}

	public String getTkClockLocationRuleId() {
		return tkClockLocationRuleId;
	}

	public void setTkClockLocationRuleId(String tkClockLocationRuleId) {
		this.tkClockLocationRuleId = tkClockLocationRuleId;
	}

}
