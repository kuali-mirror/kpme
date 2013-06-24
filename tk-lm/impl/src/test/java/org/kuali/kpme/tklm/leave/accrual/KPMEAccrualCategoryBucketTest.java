package org.kuali.kpme.tklm.leave.accrual;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SortedMap;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.TKLMUnitTestCase;
import org.kuali.kpme.tklm.leave.accrual.bucket.AccruedLeaveBalance;
import org.kuali.kpme.tklm.leave.accrual.bucket.AvailableLeaveBalance;
import org.kuali.kpme.tklm.leave.accrual.bucket.CarryOverLeaveBalance;
import org.kuali.kpme.tklm.leave.accrual.bucket.FmlaLeaveBalance;
import org.kuali.kpme.tklm.leave.accrual.bucket.KPMEAccrualCategoryBucket;
import org.kuali.kpme.tklm.leave.accrual.bucket.LeaveBalance;
import org.kuali.kpme.tklm.leave.accrual.bucket.PendingLeaveBalance;
import org.kuali.kpme.tklm.leave.accrual.bucket.YearToDateEarnedLeaveBalance;
import org.kuali.kpme.tklm.leave.accrual.bucket.YearToDateUsageLeaveBalance;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;

public class KPMEAccrualCategoryBucketTest extends TKLMUnitTestCase {

	KPMEAccrualCategoryBucket bucket;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		bucket = new KPMEAccrualCategoryBucket();//LmServiceLocator.getKPMEAccrualCategoryBucket();
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
/*	@Test
	public void testInitiate() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		assertNotNull(bucket);
		PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar("admin", DateTime.now().toLocalDate());
		principalCalendar.setLeavePlan("TST");
		bucket.initialize(principalCalendar);
		LinkedHashMap<String, List<LeaveBalance>> balances = bucket.getLeaveBalances();
		assertEquals("Map of balances should house balances for one accrual category, TEX", 1, balances.size());
		List<LeaveBalance> leaveBalances = balances.get("1");
		assertEquals("There should be 7 LeaveBalances in this list", 7, leaveBalances.size());
		for(LeaveBalance balance : leaveBalances) {
			assertTrue("This LeaveBalance should be one of the following instances",
					   balance instanceof AccruedLeaveBalance
					|| balance instanceof PendingLeaveBalance
					|| balance instanceof AvailableLeaveBalance
					|| balance instanceof YearToDateEarnedLeaveBalance
					|| balance instanceof YearToDateUsageLeaveBalance
					|| balance instanceof FmlaLeaveBalance
					|| balance instanceof CarryOverLeaveBalance);
		}
	}*/
	
	@Test
	public void testAddLeaveBlock() {
		assertNull(null);
	}
	
	@Test
	public void testRemoveLeaveBlock() {
		assertNull(null);
	}
	
	@Test
	public void testEditLeaveBlock() {
		assertNull(null);
	}
	
	@Test
	public void testGetLeaveBalance() {
		assertNull(null);
	}
	
	@Test
	@Ignore
	public void testGetLeaveBalanceForAccrualCategory() {
		assertNull(null);
	}
	
	@Test
	public void testCalculateLeaveBalanceForPreviousCalendar() {
		assertNull(null);
	}
	
	@Test
	public void testCalclulateLeaveBalanceForNextCalendar() {
		assertNull(null);
	}

}
