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
package org.kuali.hr.tklm.leave.adjustment;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.bo.HrBusinessObject;
import org.kuali.hr.core.bo.accrualcategory.AccrualCategory;
import org.kuali.hr.core.bo.earncode.EarnCode;
import org.kuali.hr.core.bo.principal.PrincipalHRAttributes;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class LeaveAdjustment extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	private String lmLeaveAdjustmentId;
	private String principalId;
	private String leavePlan;
	private String accrualCategory;
	private String earnCode;
	private String description;
	private BigDecimal adjustmentAmount = new BigDecimal("0.0");
    private transient Person principal;
	private transient AccrualCategory accrualCategoryObj;
	private transient EarnCode earnCodeObj;
	private transient PrincipalHRAttributes principalHRAttrObj;
	
	public String getEarnCode() {
		return earnCode;
	}
	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}
	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}
	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
    public Person getPrincipal() {
        return principal;
    }
    public void setPrincipal(Person principal) {
        this.principal = principal;
    }
    public String getName() {
		if (principal == null) {
        principal = KimApiServiceLocator.getPersonService().getPerson(this.principalId);
		}
		return (principal != null) ? principal.getName() : "";
	}
	
	public String getLeavePlan() {
		if (!StringUtils.isEmpty(this.principalId)) {
			principalHRAttrObj = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, this.getEffectiveLocalDate());
		}
		return (principalHRAttrObj != null) ? principalHRAttrObj.getLeavePlan() : "";
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}
	public String getAccrualCategory() {
		return accrualCategory;
	}
	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	public AccrualCategory getAccrualCategoryObj() {
		return accrualCategoryObj;
	}
	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLmLeaveAdjustmentId() {
		return lmLeaveAdjustmentId;
	}
	public void setLmLeaveAdjustmentId(String lmLeaveAdjustmentId) {
		this.lmLeaveAdjustmentId = lmLeaveAdjustmentId;
	}
	
	@Override
	protected String getUniqueKey() {
		return getLmLeaveAdjustmentId();
	}
	
	@Override
	public String getId() {
		return getLmLeaveAdjustmentId();
	}

	@Override
	public void setId(String id) {
		setLmLeaveAdjustmentId(id);
	}

	public PrincipalHRAttributes getPrincipalHRAttrObj() {
		return principalHRAttrObj;
	}
	public void setPrincipalHRAttrObj(PrincipalHRAttributes principalHRAttrObj) {
		this.principalHRAttrObj = principalHRAttrObj;
	}
}
