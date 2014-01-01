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
package org.kuali.kpme.tklm.time.rules.clocklocation;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.tklm.api.time.rules.clocklocation.ClockLocationRuleIpAddressContract;

import com.google.common.collect.ImmutableMap;

public class ClockLocationRuleIpAddress extends HrBusinessObject implements ClockLocationRuleIpAddressContract {

	private static final long serialVersionUID = 1L;
	private String tkClockLocationRuleIpId;
	private String tkClockLocationRuleId;
	private String ipAddress;
	
	// TODO returning an empty map for the time-being, until the BK is finalized
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.build();
	}
	
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
