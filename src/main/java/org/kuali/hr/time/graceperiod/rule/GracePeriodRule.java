/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.graceperiod.rule;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.time.HrBusinessObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;



public class GracePeriodRule extends HrBusinessObject {
    public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "GracePeriodRule";
	private static final long serialVersionUID = 1L;

	private String tkGracePeriodRuleId;
	private BigDecimal hourFactor;
	private String userPrincipalId;
	
	protected LinkedHashMap<String,Object> toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();
		toStringMap.put("effDt", effectiveDate);
		toStringMap.put("hourFactor", hourFactor);
		return toStringMap;
	}

	public boolean isActive() {
	    return active;
	}

	public void setActive(boolean active) {
	    this.active = active;
	}

	public BigDecimal getHourFactor() {
	    return hourFactor;
	}

	public void setHourFactor(BigDecimal hourFactor) {
	    this.hourFactor = hourFactor;
	}


	public String getUserPrincipalId() {
	    return userPrincipalId;
	}


	public void setUserPrincipalId(String userPrincipalId) {
	    this.userPrincipalId = userPrincipalId;
	}


	public Timestamp getTimestamp() {
	    return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
	    this.timestamp = timestamp;
	}


	public String getTkGracePeriodRuleId() {
		return tkGracePeriodRuleId;
	}


	public void setTkGracePeriodRuleId(String tkGracePeriodRuleId) {
		this.tkGracePeriodRuleId = tkGracePeriodRuleId;
	}


	@Override
	public String getUniqueKey() {
		return hourFactor + "";
	}

	@Override
	public String getId() {
		return getTkGracePeriodRuleId();
	}

	@Override
	public void setId(String id) {
		setTkGracePeriodRuleId(id);
	}


}
