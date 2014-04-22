/**
 * Copyright 2004-2014 The Kuali Foundation
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

import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.principal.PrincipalHRAttributesBo;
import org.kuali.kpme.tklm.api.leave.accrual.bucket.KPMEBalanceException;
import org.kuali.kpme.tklm.api.leave.accrual.bucket.UsageLimitBalanceContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;

import java.math.BigDecimal;

public class UsageLimitBalance extends LeaveBalance implements UsageLimitBalanceContract {

	private BigDecimal usageLimit;
	
	protected UsageLimitBalance(AccrualCategory accrualCategory, PrincipalHRAttributesBo principalCalendar) {
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
