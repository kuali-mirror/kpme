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
package org.kuali.hr.tklm.leave.balancetransfer.web;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.hr.core.accrualcategory.AccrualCategory;
import org.kuali.hr.tklm.leave.LMConstants;
import org.kuali.hr.tklm.leave.balancetransfer.BalanceTransfer;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.util.ActionFormUtilMap;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class BalanceTransferForm extends KualiTransactionalDocumentFormBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BalanceTransfer balanceTransfer;
	private String balanceTransferId;
	private String accrualCategoryRule;
	private String principalId;
	private String toAccrualCategory;
	private String fromAccrualCategory;
	private BigDecimal transferAmount;
	private BigDecimal forfeitedAmount;
	private Date effectiveDate;
	private String leaveCalendarDocumentId;
	private String type;
	private boolean onLeaveApproval;
	private boolean timesheet;
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getToAccrualCategory() {
		return toAccrualCategory;
	}

	public void setToAccrualCategory(String toAccrualCategory) {
		this.toAccrualCategory = toAccrualCategory;
	}

	public String getFromAccrualCategory() {
		return fromAccrualCategory;
	}

	public void setFromAccrualCategory(String fromAccrualCategory) {
		this.fromAccrualCategory = fromAccrualCategory;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getForfeitedAmount() {
		return forfeitedAmount;
	}

	public void setForfeitedAmount(BigDecimal forfeitedAmount) {
		this.forfeitedAmount = forfeitedAmount;
	}
	
	public String getBalanceTransferId() {
		return balanceTransferId;
	}

	public void setBalanceTransferId(String balanceTransferId) {
		this.balanceTransferId = balanceTransferId;
	}
	
	public String getAccrualCategoryRule() {
		return accrualCategoryRule;
	}

	public void setAccrualCategoryRule(String accrualCategoryRule) {
		this.accrualCategoryRule = accrualCategoryRule;
	}

	public AccrualCategory getCreditedAccrualCategory() {
		return TkServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory, LocalDate.fromDateFields(effectiveDate));
	}

	public AccrualCategory getDebitedAccrualCategory() {
		return TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, LocalDate.fromDateFields(effectiveDate));
	}
	
    @Override
    public String getBackLocation() {
        return "LeaveCalendarSubmit.do?methodToCall=approveLeaveCalendar";
    }

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
        ((ActionFormUtilMap) getActionFormUtilMap()).setCacheValueFinderResults(false);

        if (this.getMethodToCall() == null || StringUtils.isEmpty(this.getMethodToCall())) {
            setMethodToCall(WebUtils.parseMethodToCall(this, request));
        }
    }	
	
	@Override
	public void addRequiredNonEditableProperties() {
		super.addRequiredNonEditableProperties();
	}

	public BalanceTransfer getBalanceTransfer() {
		return balanceTransfer;
	}

	public void setBalanceTransfer(BalanceTransfer balanceTransfer) {
		this.balanceTransfer = balanceTransfer;
	}

	public String getLeaveCalendarDocumentId() {
		return leaveCalendarDocumentId;
	}

	public void setLeaveCalendarDocumentId(String leaveCalendarDocId) {
		this.leaveCalendarDocumentId = leaveCalendarDocId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isOnLeaveApproval() {
		if(StringUtils.equals(type, LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE))
			return true;
		else
			return false;
		//Will return false if MAX_BAL_ACTION_FREQ is YEAR_END, but for purposes of the leave calendar, YEAR_END is not
		//transferable.
	}

	public void setOnLeaveApproval(boolean onLeaveApproval) {
		this.onLeaveApproval = onLeaveApproval;
	}

	public void isTimesheet(boolean b) {
		timesheet = true;
	}

	public boolean isSstoTransfer() {
		if(this.getBalanceTransfer() != null) {
			if(StringUtils.isNotEmpty(this.getBalanceTransfer().getSstoId()))
				return true;
			else
				return false;
		}
		else
			return false;
	}

}
