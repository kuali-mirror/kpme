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
package org.kuali.hr.lm.balancetransfer.web;

import java.math.BigDecimal;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.util.ActionFormUtilMap;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;

public class BalanceTransferForm extends KualiDocumentFormBase {

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
		return TkServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory, effectiveDate);
	}

	public AccrualCategory getDebitedAccrualCategory() {
		return TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
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
		// TODO Auto-generated method stub
		System.out.println("*******************************************");
		System.out.println("* Adding required non editable properties *");
		System.out.println("*******************************************");
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

}
