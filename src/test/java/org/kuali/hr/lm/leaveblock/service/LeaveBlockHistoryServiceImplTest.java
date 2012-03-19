package org.kuali.hr.lm.leaveblock.service;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;

public class LeaveBlockHistoryServiceImplTest extends TkTestCase {

	private LeaveBlockHistoryService leaveBlockHistoryService;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		leaveBlockHistoryService = TkServiceLocator
				.getLeaveBlockHisotryService();
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
	
	

}
