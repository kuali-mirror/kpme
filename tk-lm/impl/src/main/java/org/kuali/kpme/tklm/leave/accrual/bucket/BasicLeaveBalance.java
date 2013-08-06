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
package org.kuali.kpme.tklm.leave.accrual.bucket;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.tklm.api.leave.accrual.bucket.BasicLeaveBalanceContract;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.MaximumBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.NegativeBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.UsageLimitException;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;

public class BasicLeaveBalance extends LeaveBalance implements BasicLeaveBalanceContract {

	private List<LeaveBlock> leaveBlocks;

	public BasicLeaveBalance(AccrualCategory accrualCategory, PrincipalHRAttributes principalCalendar) {
		super(accrualCategory, principalCalendar);
		leaveBlocks = new ArrayList<LeaveBlock>();
	}

	private final static String BALANCE_TYPE = "BASIC_BALANCE";
	
	@Override
	public void add(LeaveBlock leaveBlock) throws UsageLimitException,
			MaximumBalanceException, NegativeBalanceException {
		add(leaveBlock.getLeaveAmount());
		
		leaveBlocks.add(leaveBlock);
	}

	@Override
	public void remove(LeaveBlock leaveBlock) throws UsageLimitException,
			MaximumBalanceException, NegativeBalanceException {
		remove(leaveBlock.getLeaveAmount());
		
		leaveBlocks.remove(leaveBlock);
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
		leaveBlocks.clear();
		super.clear();
	}

}
