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
package org.kuali.hr.tklm.leave.payout;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.core.accrualcategory.AccrualCategory;
import org.kuali.hr.core.earncode.EarnCode;
import org.kuali.hr.core.principal.PrincipalHRAttributes;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LeavePayout extends HrBusinessObject {

	private static final long serialVersionUID = 1L;

	private String lmLeavePayoutId;
	private String principalId;
	private String documentHeaderId;
	private String fromAccrualCategory;
	private String earnCode;
	private String description;
	private BigDecimal payoutAmount = new BigDecimal("0.0");
    private BigDecimal forfeitedAmount = new BigDecimal("0.0");
	private transient Person principal;
	private transient AccrualCategory fromAccrualCategoryObj;
	private transient EarnCode earnCodeObj;
	private transient PrincipalHRAttributes principalHRAttrObj;
    private String leaveCalendarDocumentId;
    private String accrualCategoryRule;
    private String forfeitedLeaveBlockId;
    private String payoutFromLeaveBlockId;
    private String payoutLeaveBlockId;

	private String status;

	public String getEarnCode() {
		return earnCode;
	}
	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}
	public EarnCode getEarnCodeObj() {
		if (earnCodeObj == null) {
            earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(this.earnCode, getEffectiveLocalDate());
        }
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
			principalHRAttrObj = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, this.getEffectiveLocalDate());
		}
		return (principalHRAttrObj != null) ? principalHRAttrObj.getLeavePlan() : "";
	}

	public void setLeavePlan(String leavePlan) {
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
        if (fromAccrualCategoryObj == null) {
            fromAccrualCategoryObj =  TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, getEffectiveLocalDate());
        }
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
	public String getAccrualCategoryRule() {
		return accrualCategoryRule;
	}
	public void setAccrualCategoryRule(String accrualCategoryRule) {
		this.accrualCategoryRule = accrualCategoryRule;
	}
	public String getForfeitedLeaveBlockId() {
		return forfeitedLeaveBlockId;
	}
	public void setForfeitedLeaveBlockId(String forfeitedLeaveBlockId) {
		this.forfeitedLeaveBlockId = forfeitedLeaveBlockId;
	}
	public String getPayoutLeaveBlockId() {
		return payoutLeaveBlockId;
	}
	public void setPayoutLeaveBlockId(String payoutLeaveBlockId) {
		this.payoutLeaveBlockId = payoutLeaveBlockId;
	}
	public String getPayoutFromLeaveBlockId() {
		return payoutFromLeaveBlockId;
	}
	public void setPayoutFromLeaveBlockId(String payoutFromLeaveBlockId) {
		this.payoutFromLeaveBlockId = payoutFromLeaveBlockId;
	}

	public void setLeaveCalendarDocumentId(String leaveCalendarDocumentId) {
		this.leaveCalendarDocumentId = leaveCalendarDocumentId;
	}

    public List<LeaveBlock> getLeaveBlocks() {
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();

		if (getForfeitedLeaveBlockId() != null) {
		    leaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(forfeitedLeaveBlockId));
        }
        if (getPayoutFromLeaveBlockId() != null) {
		    leaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(payoutFromLeaveBlockId));
        }
        if (getPayoutLeaveBlockId() != null) {
		    leaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(payoutLeaveBlockId));
        }
        return leaveBlocks;
    }

    public String getLeaveCalendarDocumentId() {
        return leaveCalendarDocumentId;
    }
	public LeavePayout adjust(BigDecimal payoutAmount) {
		BigDecimal difference = this.payoutAmount.subtract(payoutAmount);
		//technically if there is forfeiture, then the transfer amount has already been maximized
		//via BalanceTransferService::initializeTransfer(...)
		//i.o.w. transfer amount cannot be increased.
		//this method is written with the intention of eventually allowing end user to adjust the transfer
		//amount as many times as they wish before submitting. Currently they cannot.
		if(difference.signum() < 0) {
			//transfer amount is being increased.
			if(forfeitedAmount.compareTo(BigDecimal.ZERO) > 0) {
				//transfer amount has already been maximized.
				if(forfeitedAmount.compareTo(difference.abs()) >= 0)
					// there is enough leave in the forfeited amount to take out the difference.
					forfeitedAmount = forfeitedAmount.subtract(difference.abs());
				else
					// the difference zero's the forfeited amount.
					forfeitedAmount = BigDecimal.ZERO;
			}
			// a forfeited amount equal to zero with an increase in the transfer amount
			// does not produce forfeiture.
			// forfeiture cannot be negative.
		}
		else if (difference.signum() > 0) {
			//transfer amount is being decreased
			forfeitedAmount = forfeitedAmount.add(difference);
		}

		this.payoutAmount = payoutAmount;
		
		return this;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String code) {
		status = code;
	}
	public String getDocumentHeaderId() {
		return documentHeaderId;
	}
	public void setDocumentHeaderId(String documentHeaderId) {
		this.documentHeaderId = documentHeaderId;
	}
}
