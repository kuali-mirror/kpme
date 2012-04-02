package org.kuali.hr.lm.leaveblock.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;

public class LeaveBlockHistoryServiceImplTest extends TkTestCase {

	private LeaveBlockHistoryService leaveBlockHistoryService;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		leaveBlockHistoryService = TkServiceLocator
				.getLeaveBlockHistoryService();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetLeaveBlockHistoryByLmLeaveBlockId() {
		List<LeaveBlockHistory> leaveBlockHistories = leaveBlockHistoryService.getLeaveBlockHistoryByLmLeaveBlockId("1000");
		assertNotNull("Leave Block histories  not found ", leaveBlockHistories);
	}
	
	@Test
	public void testGetLeaveBlockHistories() {
		List<LeaveBlockHistory> leaveBlockHistories = leaveBlockHistoryService.getLeaveBlockHistories("admin", null);
		assertNotNull("Leave Block histories  not found ", leaveBlockHistories);
	}
	
	@Test
	public void testGetLeaveBlockHistoriesForLeaveDisplay() {
		Calendar currCal = Calendar.getInstance();
		currCal.set(2012, 0, 1);
		Date beginDate = TKUtils.getTimelessDate(currCal.getTime());
		currCal.set(2012, 11, 31);
		Date endDate = TKUtils.getTimelessDate(currCal.getTime());
        List<LeaveBlockHistory> leaveBlockHistories= leaveBlockHistoryService.getLeaveBlockHistoriesForLeaveDisplay("admin", beginDate, endDate, Boolean.TRUE);
        assertNotNull("Leave Block histories for leavedisplay  not found ", leaveBlockHistories);
	}
	
	

}
