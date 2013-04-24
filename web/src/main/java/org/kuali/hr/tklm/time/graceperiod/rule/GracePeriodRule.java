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
package org.kuali.hr.tklm.time.graceperiod.rule;

import java.math.BigDecimal;

import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.core.KPMEConstants;

public class GracePeriodRule extends HrBusinessObject {

	private static final long serialVersionUID = 2756221187837436165L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "GracePeriodRule";

	private String tkGracePeriodRuleId;
	private BigDecimal hourFactor;
	private String userPrincipalId;

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
