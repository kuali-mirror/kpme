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
package org.kuali.kpme.tklm.time.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMEUnitTestCase;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.utils.TkTestUtils;

public class ActionFormUtilsTest extends KPMEUnitTestCase {
	
	@Test
	public void testBuildAssignmentStyleClassMap() {
		DateTime aDate = new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(aDate, "admin");
		Map<String, String> aMap = ActionFormUtils.buildAssignmentStyleClassMap(doc.getTimeBlocks());
		Assert.assertEquals("Wrong number of classes in style class map", 1, aMap.size());
		Assert.assertEquals("Wrong key for class assignment0", "assignment0", aMap.get("1_1234_1"));
	}

	@Test
	public void testGetUnitOfTimeForEarnCode() throws Exception {
		// earn code with an existing Accrual category
		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCodeById("5000");
		String unitOfTime = ActionFormUtils.getUnitOfTimeForEarnCode(earnCode);
		Assert.assertTrue("Unit of Time should be 'H', not " + unitOfTime, unitOfTime.equals("H"));
		// earn code without an existing accrual category
		earnCode = HrServiceLocator.getEarnCodeService().getEarnCodeById("5002");
		unitOfTime = ActionFormUtils.getUnitOfTimeForEarnCode(earnCode);
		Assert.assertTrue("Unit of Time should be 'H', not " + unitOfTime, unitOfTime.equals("H"));
		
	}
	
	@Test
	public void testGetLeaveBlocksJson() {
		List<LeaveBlock> lbList = new ArrayList<LeaveBlock>();
		LeaveBlock lb = new LeaveBlock();
		lb.setAssignmentTitle("testAssignment");
		lb.setAssignmentKey("0-123-0");
		lb.setEarnCode("EarnCode");
		lb.setLmLeaveBlockId("1111");
		lb.setLeaveAmount(new BigDecimal(3));
		lb.setLeaveLocalDate(new DateTime(2012, 2, 20, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).toLocalDate());
		lb.setAccrualGenerated(false);
		lbList.add(lb);
		
		String jsonString = ActionFormUtils.getLeaveBlocksJson(lbList);
		String expectedString = "[{\"title\":\"\",\"assignment\":\"0-123-0\",\"earnCode\":\"EarnCode\",\"lmLeaveBlockId\":\"1111\",\"leaveAmount\":\"3\",\"leaveDate\":\"02\\/20\\/2012\",\"id\":\"1111\",\"canTransfer\":false,\"startDate\":\"02\\/20\\/2012\",\"endDate\":\"02\\/20\\/2012\"}]";
		Assert.assertTrue("Leave Block Json should include assignment", jsonString.equals(expectedString));
		
		
	}
	

}
