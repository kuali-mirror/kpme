package org.kuali.kpme.tklm.leave.accrual.bucket;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.MaximumBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.NegativeBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.UsageLimitException;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;

public class PendingLeaveBalance extends LeaveBalance {

	private List<LeaveBlock> leaveBlocks;

	public PendingLeaveBalance(AccrualCategory accrualCategory, PrincipalHRAttributes principalCalendar) {
		super(accrualCategory, principalCalendar);
		asOfDate = LocalDate.now();
		leaveBlocks = new ArrayList<LeaveBlock>();
	}

	private final static String BALANCE_TYPE = "PENDING_BALANCE";
	
	@Override
	public void add(LeaveBlock leaveBlock) throws UsageLimitException,
			MaximumBalanceException, NegativeBalanceException {
		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), LocalDate.fromDateFields(leaveBlock.getLeaveDate()));
		if(earnCode != null) {
			if(leaveBlock.getLeaveDate().compareTo(asOfDate.toDate()) > 0 && leaveBlock.getLeaveAmount().signum() < 0) {
				
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
		}
	}

	@Override
	public void remove(LeaveBlock leaveBlock) throws UsageLimitException,
			MaximumBalanceException, NegativeBalanceException {
		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), LocalDate.fromDateFields(leaveBlock.getLeaveDate()));
		if(earnCode != null) {
			if(leaveBlock.getLeaveDate().compareTo(asOfDate.toDate()) > 0 && leaveBlock.getLeaveAmount().signum() < 0) {
				
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

	@Override
	public void clear() {
		super.clear();
		leaveBlocks.clear();
	}

}
