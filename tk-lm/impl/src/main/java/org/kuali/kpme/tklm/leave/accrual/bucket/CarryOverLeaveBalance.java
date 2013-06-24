package org.kuali.kpme.tklm.leave.accrual.bucket;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.MaxCarryoverException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.MaximumBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.NegativeBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.UsageLimitException;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;

public class CarryOverLeaveBalance extends LeaveBalance {

	private List<LeaveBlock> leaveBlocks;

	public CarryOverLeaveBalance(AccrualCategory accrualCategory, PrincipalHRAttributes principalCalendar) {
		super(accrualCategory, principalCalendar);
		leaveBlocks = new ArrayList<LeaveBlock>();
	}

	private static final String BALANCE_TYPE = "CARRYOVER_BALANCE";
	
	@Override
	public void add(LeaveBlock leaveBlock) throws MaxCarryoverException {
		
		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), LocalDate.fromDateFields(leaveBlock.getLeaveDate()));
		
		DateTime rolloverDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalCalendar.getLeavePlan(), asOfDate);
		
		if(earnCode != null) {
			if(leaveBlock.getLeaveDate().compareTo(rolloverDate.toDate()) < 0) {
	
				if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)){
		
					add(leaveBlock.getLeaveAmount());
					
					leaveBlocks.add(leaveBlock);
	
				}
				else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.ADJUSTMENT)) {
					//does not validate against balances
					add(leaveBlock.getLeaveAmount());
					
					leaveBlocks.add(leaveBlock);
				}
				else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.NONE)) {
					//no balance validations, does not affect balances
					leaveBlocks.add(leaveBlock);
				}
			}
			//otherwise, the leave block is within the same calendar year as asOfDate
		}
	}

	@Override
	public void remove(LeaveBlock leaveBlock) throws MaxCarryoverException {
		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), LocalDate.fromDateFields(leaveBlock.getLeaveDate()));
		
		DateTime rolloverDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalCalendar.getLeavePlan(), asOfDate);
		
		if(earnCode != null) {
			if(leaveBlock.getLeaveDate().compareTo(rolloverDate.toDate()) < 0) {
				
				if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)){
		
					remove(leaveBlock.getLeaveAmount());
					
					leaveBlocks.remove(leaveBlock);
				}
				else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.ADJUSTMENT)) {
					//does not validate against balances
					remove(leaveBlock.getLeaveAmount());
					
					leaveBlocks.remove(leaveBlock);
				}
				else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.NONE)) {
					//no balance validations, does not affect balances
					leaveBlocks.remove(leaveBlock);
				}
			}
		}
	}

	@Override
	public String getBalanceType() {
		return BALANCE_TYPE;
	}

	@Override
	public void adjust(LeaveBlock leaveBlock) throws UsageLimitException,
			MaximumBalanceException, NegativeBalanceException {
		// TODO Auto-generated method stub
		
	}

	public void clear() {
		leaveBlocks.clear();
		super.clear();
	}

}
