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
package org.kuali.kpme.tklm.api.leave.accrual.bucket;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;

/**
 * <p>LeaveBalanceContract interface</p>
 *
 */
public interface LeaveBalanceContract {
	
	/** 
	 * The balance type associated with the LeaveBalance
	 * 
	 * <p>
	 * This is an abstract method that gets overridden in sub classes
	 * <p>
	 * 
	 * @return 
	 */	
	public abstract String getBalanceType();
	
	/**
	 * The AccuralCategory object associated with the LeaveBalance
	 * 
	 * <p>
	 * balance of a LeaveBalance
	 * <p>
	 * 
	 * @return balance for LeaveBalance
	 */	
	public BigDecimal getBalance();

	/**
	 * The AccuralCategory object associated with the LeaveBalance
	 * 
	 * <p>
	 * accrualCategory of a LeaveBalance
	 * <p>
	 * 
	 * @return accrualCategory for LeaveBalance
	 */	
	public AccrualCategoryContract getAccrualCategory();
}
