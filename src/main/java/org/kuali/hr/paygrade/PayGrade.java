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
package org.kuali.hr.paygrade;

import java.sql.Date;
import java.sql.Timestamp;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.salgroup.SalGroup;

public class PayGrade extends HrBusinessObject {
    public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "PayGrade";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hrPayGradeId;
	private String payGrade;
	private String description;
	private String userPrincipalId;
	private String salGroup;
	private SalGroup salGroupObj;

	public String getHrPayGradeId() {
		return hrPayGradeId;
	}

	public void setHrPayGradeId(String hrPayGradeId) {
		this.hrPayGradeId = hrPayGradeId;
	}

	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	@Override
	public String getUniqueKey() {
		return payGrade;
	}
	
	@Override
	public String getId() {
		return getHrPayGradeId();
	}

	@Override
	public void setId(String id) {
		setHrPayGradeId(id);
	}
	
	public String getSalGroup() {
		return salGroup;
	}

	public void setSalGroup(String salGroup) {
		this.salGroup = salGroup;
	}

	public SalGroup getSalGroupObj() {
		return salGroupObj;
	}

	public void setSalGroupObj(SalGroup salGroupObj) {
		this.salGroupObj = salGroupObj;
	}

}
