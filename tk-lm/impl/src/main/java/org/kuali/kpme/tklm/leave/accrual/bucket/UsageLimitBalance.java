package org.kuali.kpme.tklm.leave.accrual.bucket;

import java.math.BigDecimal;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.KPMEBalanceException;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;

public class UsageLimitBalance extends LeaveBalance {

	private BigDecimal usageLimit;
	
	protected UsageLimitBalance(AccrualCategory accrualCategory, PrincipalHRAttributes principalCalendar) {
		super(accrualCategory, principalCalendar);
		usageLimit = BigDecimal.ZERO;
	}

	@Override
	public void add(LeaveBlock leaveBlock) throws KPMEBalanceException {
		
	}

	@Override
	public void remove(LeaveBlock leaveBlock) throws KPMEBalanceException {

	}

	@Override
	public String getBalanceType() {
		return getBalanceType();
	}

	@Override
	public void adjust(LeaveBlock leaveBlock) throws KPMEBalanceException {

	}

	@Override
	public void clear() {
		usageLimit = new BigDecimal(0);
	}

}
