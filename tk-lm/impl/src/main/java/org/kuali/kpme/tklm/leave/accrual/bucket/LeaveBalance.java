package org.kuali.kpme.tklm.leave.accrual.bucket;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.KPMEBalanceException;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;

public abstract class LeaveBalance {

	protected AccrualCategory accrualCategory;
	protected PrincipalHRAttributes principalCalendar;
	protected LocalDate asOfDate;
	protected LocalDate calendarPeriodBeginDate;
	protected LocalDate calendarPeriodEndDate;
	protected BigDecimal balance;
	
	protected LeaveBalance(AccrualCategory accrualCategory, PrincipalHRAttributes principalCalendar) {
		this.accrualCategory = accrualCategory;
		this.principalCalendar = principalCalendar;
		asOfDate = LocalDate.now();
		balance = new BigDecimal(0);
	}
	
	protected void add(BigDecimal amount) {
		balance = balance.add(amount);
	}
	
	protected void remove(BigDecimal amount) {
		balance = balance.subtract(amount);
	}
	
	/**
	 * A hook for adding leave blocks via leave calendar action
	 * 
	 * @param leaveBlock The leave block to be added to the bucket
	 * @throws KPMEBalanceException
	 */
	public abstract void add(LeaveBlock leaveBlock) throws KPMEBalanceException;
	
	/**
	 * A hook for removing leave blocks via leave calendar action
	 * 
	 * @param leaveBlock
	 * @throws KPMEBalanceException
	 */
	public abstract void remove(LeaveBlock leaveBlock) throws KPMEBalanceException;
	
	/**
	 * A hook for editing leave blocks via leave calendar action
	 * 
	 * @param leaveBlock
	 * @throws KPMEBalanceException
	 */
	public abstract void adjust(LeaveBlock leaveBlock) throws KPMEBalanceException;
	
	public void clear() {
		balance = new BigDecimal(0);
	}
	
	public abstract String getBalanceType();
	
	public BigDecimal getBalance() { return balance; }

	protected AccrualCategoryRule getAccrualCategoryRuleForDate(LocalDate leaveLocalDate) {
		return HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, leaveLocalDate, principalCalendar.getServiceLocalDate());
	}

	protected EmployeeOverride getEmployeeOverride(LeaveBlock leaveBlock, String overrideType) {
		EmployeeOverride eo = null;
		eo = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalCalendar.getPrincipalId(),
				principalCalendar.getLeavePlan(), accrualCategory.getAccrualCategory(), overrideType, leaveBlock.getLeaveLocalDate());
		return eo;
	}

	public AccrualCategory getAccrualCategory() {
		return accrualCategory;
	}
	
}
