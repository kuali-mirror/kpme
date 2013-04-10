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
package org.kuali.hr.lm.leavepayout.web;

import java.math.BigDecimal;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavepayout.LeavePayout;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.util.ActionFormUtilMap;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class LeavePayoutForm extends KualiTransactionalDocumentFormBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LeavePayout leavePayout;
	private String leavePayoutId;
	private String accrualCategoryRule;
	private String principalId;
	private String earnCode;
	private String fromAccrualCategory;
	private BigDecimal payoutAmount;
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

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public String getFromAccrualCategory() {
		return fromAccrualCategory;
	}

	public void setFromAccrualCategory(String fromAccrualCategory) {
		this.fromAccrualCategory = fromAccrualCategory;
	}

	public BigDecimal getPayoutAmount() {
		return payoutAmount;
	}

	public void setPayoutAmount(BigDecimal payoutAmount) {
		this.payoutAmount = payoutAmount;
	}

	public BigDecimal getForfeitedAmount() {
		return forfeitedAmount;
	}

	public void setForfeitedAmount(BigDecimal forfeitedAmount) {
		this.forfeitedAmount = forfeitedAmount;
	}
	
	public String getLeavePayoutId() {
		return leavePayoutId;
	}

	public void setLeavePayoutId(String leavePayoutId) {
		this.leavePayoutId = leavePayoutId;
	}
	
	public String getAccrualCategoryRule() {
		return accrualCategoryRule;
	}

	public void setAccrualCategoryRule(String accrualCategoryRule) {
		this.accrualCategoryRule = accrualCategoryRule;
	}

	public EarnCode getPayoutEarnCodeObj() {
		return TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, LocalDate.fromDateFields(effectiveDate));
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

	public LeavePayout getLeavePayout() {
		return leavePayout;
	}

	public void setLeavePayout(LeavePayout leavePayout) {
		this.leavePayout = leavePayout;
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


}
