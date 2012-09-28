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
package org.kuali.hr.time.timeblock.service;

import java.sql.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
@Ignore
public class TimeBlockServiceTest extends KPMETestCase {
	@Test
	public void testBuildAssignmentStyleClassMap() {
		Date aDate = new Date((new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(aDate);
		Map<String, String> aMap = ActionFormUtils.buildAssignmentStyleClassMap(doc.getTimeBlocks());
		Assert.assertEquals("Wrong number of classes in style class map", 8, aMap.size());
		Assert.assertEquals("Wrong key for class assignment0", "assignment0", aMap.get("1_1234_1"));
		Assert.assertEquals("Wrong key for class assignment7", "assignment7", aMap.get("6_1100_5"));
	}

	@Test
	public void testIsTimeBlockEditable() {
		// creator and user are the same person
		TimeBlock tb = new TimeBlock();
		tb.setClockLogCreated(false);
		tb.setJobNumber(new Long(30));
		tb.setUserPrincipalId("admin");
		
		Boolean editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		Assert.assertEquals("TimeBlock created by admin should be editable by admin", true, editable);

		// creator and user are different, but user is a system admin
		tb.setUserPrincipalId("fran");
		editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		Assert.assertEquals("TimeBlock created by fran should be editable by admin", true, editable);

		// login as fran
//		user = TkServiceLocator.getUserService().buildTkUser("fran", TKUtils.getCurrentDate());
//		TKContext.setUser(user);
//		// creator and user are different, user is not a system admin
//		tb.setUserPrincipalId("admin");
//		editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
//		assertEquals("TimeBlock created by admin should NOT be editable by fran", false, editable);
		
		tb.setUserPrincipalId("fran");
		tb.setClockLogCreated(true);
		editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		Assert.assertEquals("TimeBlock created by Clock in/out should NOT be editable by fran", false, editable);
		
	}

}
