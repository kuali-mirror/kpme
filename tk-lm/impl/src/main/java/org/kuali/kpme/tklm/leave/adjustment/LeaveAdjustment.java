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
package org.kuali.kpme.tklm.leave.adjustment;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.accrualcategory.AccrualCategoryBo;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.principal.PrincipalHRAttributesBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.api.leave.adjustment.LeaveAdjustmentContract;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class LeaveAdjustment extends HrBusinessObject implements LeaveAdjustmentContract {
	private static final String EARN_CODE = "earnCode";
	private static final String ACCRUAL_CATEGORY = "accrualCategory";
	private static final String PRINCIPAL_ID = "principalId";
	
	private static final long serialVersionUID = 1L;
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(PRINCIPAL_ID)
            .add(ACCRUAL_CATEGORY)
            .add(EARN_CODE)
            .build();	
	private String lmLeaveAdjustmentId;
	private String principalId;
	private String leavePlan;
	private String accrualCategory;
	private String earnCode;
	private String description;
	private BigDecimal adjustmentAmount = new BigDecimal("0.0");
    private transient Person principal;
	private transient AccrualCategoryBo accrualCategoryObj;
	private transient EarnCodeBo earnCodeObj;
	private transient PrincipalHRAttributesBo principalHRAttrObj;
	
	

    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(PRINCIPAL_ID, this.getPrincipalId())
			.put(ACCRUAL_CATEGORY, this.getAccrualCategory())
			.put(EARN_CODE, this.getEarnCode())
			.build();
	}
    
	
	public String getEarnCode() {
		return earnCode;
	}
	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}
	public EarnCodeBo getEarnCodeObj() {
		return earnCodeObj;
	}
	public void setEarnCodeObj(EarnCodeBo earnCodeObj) {
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
			principalHRAttrObj = PrincipalHRAttributesBo.from(HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, this.getEffectiveLocalDate()));
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
	public AccrualCategoryBo getAccrualCategoryObj() {
		return accrualCategoryObj;
	}
	public void setAccrualCategoryObj(AccrualCategoryBo accrualCategoryObj) {
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

	public PrincipalHRAttributesBo getPrincipalHRAttrObj() {
		return principalHRAttrObj;
	}
	public void setPrincipalHRAttrObj(PrincipalHRAttributesBo principalHRAttrObj) {
		this.principalHRAttrObj = principalHRAttrObj;
	}
}
