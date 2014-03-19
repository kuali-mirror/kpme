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
package org.kuali.kpme.tklm.api.leave.accrual.bucket;

import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>KPMEAccrualCategoryBucketContract interface</p>
 *
 */
public interface KPMEAccrualCategoryBucketContract {

	/**
	 * The LeaveBalance object the KPMEAccrualCategoryBucketContract is associated with
	 * 
	 * <p>
	 * </p>
	 * 
	 * @param AccrualCategory
	 * @param String
	 * @return LeaveBalance object based on the parameters
	 */
	public LeaveBalanceContract getLeaveBalance(AccrualCategoryContract accrualCategory, String balanceType);
	
	/**
	 * The list of LeaveBalance objects the KPMEAccrualCategoryBucketContract is associated with
	 * 
	 * <p>
	 * </p>
	 * 
	 * @param AccrualCategory
	 * @return leaveBalances.get(accrualCategory.getAccrualCategory())
	 */
	public List<? extends LeaveBalanceContract> getLeaveBalancesForAccrualCategory(AccrualCategoryContract accrualCategory);

	/**
	 * The map of leave balance the KPMEAccrualCategoryBucketContract is associated with
	 * 
	 * <p>
	 * leaveBalances of a KPMEAccrualCategoryBucketContract
	 * </p>
	 * 
	 * @return leaveBalances for KPMEAccrualCategoryBucketContract
	 */
	public LinkedHashMap<String, ? extends List<? extends LeaveBalanceContract>> getLeaveBalances();
	
	/**
	 * The CalendarEntry object the KPMEAccrualCategoryBucketContract is associated with
	 * 
	 * <p>
	 * viewingCalendarEntry of a KPMEAccrualCategoryBucketContract
	 * </p>
	 * 
	 * @return viewingCalendarEntry for KPMEAccrualCategoryBucketContract
	 */
	public CalendarEntry getLeaveCalendarDocument();

	/**
	 * The baseBalanceList the KPMEAccrualCategoryBucketContract is associated with
	 * 
	 * <p>
	 * baseBalanceList of a KPMEAccrualCategoryBucketContract
	 * </p>
	 * 
	 * @return baseBalanceList for KPMEAccrualCategoryBucketContract
	 */
	public List<? extends Class<? extends LeaveBalanceContract>> getBaseBalanceList();

	/**
	 * The isInitialized flag the KPMEAccrualCategoryBucketContract is assoacited with
	 * 
	 * <p>
	 * isInitialized of a KPMEAccrualCategoryBucketContract
	 * </p>
	 * 
	 * @return isInitialized for KPMEAccrualCategoryBucketContract
	 */
	public boolean isInitialized();
	
	// all the other methods in case they need to be here...
	/*
	public void initialize(PrincipalHRAttributesContract principalCalendar) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;	
	public void calculateLeaveBalanceForPreviousCalendar();
	public void calculateLeaveBalanceForNextCalendar();
	public void calculateLeaveBalanceForCalendar(CalendarEntry calendarEntry) throws KPMEBalanceException;
	public LeaveBlock withdrawal(AccrualCategory accrualCategory, BigDecimal amount);
	public void editLeaveBlock(LeaveBlock leaveBlock) throws KPMEBalanceException;
	public void addLeaveBlock(LeaveBlock leaveBlock) throws KPMEBalanceException;
	public void removeLeaveBlock(LeaveBlock leaveBlock) throws KPMEBalanceException;	
	public void setLeaveBalances(LinkedHashMap<String, List<LeaveBalance>> balances);
	public void setCalendarDocument(CalendarEntry calendarDocument); 
	*/
}
