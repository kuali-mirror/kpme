/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.leaveblock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveStatusHistory;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class LeaveStatusHistoryServiceImplTest extends KPMETestCase{

	private LeaveStatusHistoryService leaveStatusHistoryService;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		leaveStatusHistoryService = TkServiceLocator.getLeaveStatusHistoryService();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetLeaveStatusByLmLeaveBlockId() {
		LeaveStatusHistory leaveStatusHistory = leaveStatusHistoryService.getLeaveStatusHistoryByLmLeaveBlockIdAndRequestStatus("1000", null);
		assertNotNull("Leave Status Object not found", leaveStatusHistory);
	}

}
