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
package org.kuali.hr.lm.leavepayout;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LeavePayout extends HrBusinessObject {

	private static final long serialVersionUID = 1L;

	private String lmLeavePayoutId;
	private String principalId;
	private String leavePlan;
	private String fromAccrualCategory;
	private String earnCode;
	private String description;
	private BigDecimal payoutAmount = new BigDecimal("0.0");
    private BigDecimal forfeitedAmount = new BigDecimal("0.0");
	private Person principal;
	private AccrualCategory fromAccrualCategoryObj;
	private EarnCode earnCodeObj;
	private PrincipalHRAttributes principalHRAttrObj;
    private String leaveCalendarDocumentId;

    private String forfeitedLeaveBlockId;
    private String accruedLeaveBlockId;
    private String debitedLeaveBlockId;

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
			principalHRAttrObj = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, this.getEffectiveDate());
		}
		return (principalHRAttrObj != null) ? principalHRAttrObj.getLeavePlan() : "";
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}
	public String getFromAccrualCategory() {
		return fromAccrualCategory;
	}
	public void setFromAccrualCategory(String accrualCategory) {
		this.fromAccrualCategory = accrualCategory;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPayoutAmount() {
		return payoutAmount;
	}
	public void setPayoutAmount(BigDecimal amount) {
		this.payoutAmount = amount;
	}
    public BigDecimal getForfeitedAmount() {
        return forfeitedAmount;
    }
    public void setForfeitedAmount(BigDecimal amount) {
        this.forfeitedAmount = amount;
    }
	public AccrualCategory getFromAccrualCategoryObj() {
		return fromAccrualCategoryObj;
	}
	public void setFromAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.fromAccrualCategoryObj = accrualCategoryObj;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLmLeavePayoutId() {
		return lmLeavePayoutId;
	}
	public void setLmLeavePayoutId(String id) {
		this.lmLeavePayoutId = id;
	}
	
	@Override
	protected String getUniqueKey() {
		return getLmLeavePayoutId();
	}
	
	@Override
	public String getId() {
		return getLmLeavePayoutId();
	}

	@Override
	public void setId(String id) {
		setLmLeavePayoutId(id);
	}

	public PrincipalHRAttributes getPrincipalHRAttrObj() {
		return principalHRAttrObj;
	}
	public void setPrincipalHRAttrObj(PrincipalHRAttributes hrObj) {
		this.principalHRAttrObj = hrObj;
	}

    public List<LeaveBlock> getLeaveBlocks() {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();

        leaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(forfeitedLeaveBlockId));
        leaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(accruedLeaveBlockId));
        leaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(debitedLeaveBlockId));

        return leaveBlocks;
    }

    public String getLeaveCalendarDocumentId() {
        return leaveCalendarDocumentId;
    }

}
