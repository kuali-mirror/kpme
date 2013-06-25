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

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SortedMap;

import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.KPMEBalanceException;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;

public interface KPMEAccrualCategoryBucketContract {

	public void initialize(PrincipalHRAttributes principalCalendar) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public LeaveBalance getLeaveBalance(AccrualCategory accrualCategory, String balanceType);
	
	public List<LeaveBalance> getLeaveBalancesForAccrualCategory(AccrualCategory accrualCategory);
	
	public void calculateLeaveBalanceForPreviousCalendar();
	
	public void calculateLeaveBalanceForNextCalendar();
	
	public void calculateLeaveBalanceForCalendar(CalendarEntry calendarEntry) throws KPMEBalanceException;
	
	public LeaveBlock withdrawal(AccrualCategory accrualCategory, BigDecimal amount);
	
	public void editLeaveBlock(LeaveBlock leaveBlock) throws KPMEBalanceException;
	
	public void addLeaveBlock(LeaveBlock leaveBlock) throws KPMEBalanceException;
	
	public void removeLeaveBlock(LeaveBlock leaveBlock) throws KPMEBalanceException;
	
	public LinkedHashMap<String, List<LeaveBalance>> getLeaveBalances();
	
	public void setLeaveBalances(LinkedHashMap<String, List<LeaveBalance>> balances);
	
	public CalendarEntry getLeaveCalendarDocument();
	
	public boolean isInitialized();

	public void setCalendarDocument(CalendarEntry calendarDocument);
}
