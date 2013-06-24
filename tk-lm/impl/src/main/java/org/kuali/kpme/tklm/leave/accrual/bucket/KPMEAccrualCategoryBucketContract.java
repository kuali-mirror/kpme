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
