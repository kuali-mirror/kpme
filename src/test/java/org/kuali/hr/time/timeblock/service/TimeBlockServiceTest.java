package org.kuali.hr.time.timeblock.service;

import java.sql.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class TimeBlockServiceTest extends TkTestCase {
	@Test
	public void testBuildAssignmentStyleClassMap() {
		Date aDate = new Date((new DateTime(2011, 7, 7, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(aDate);
		Map<String, String> aMap = ActionFormUtils.buildAssignmentStyleClassMap(doc.getTimeBlocks());
		assertEquals("Wrong number of classes in style class map", 8, aMap.size());
		assertEquals("Wrong key for class assignment0", "assignment0", aMap.get("1_1234_1"));
		assertEquals("Wrong key for class assignment7", "assignment7", aMap.get("6_1100_5"));
	}

	@Test
	public void testIsTimeBlockEditable() {
		// login as admin
		TKUser user = TkServiceLocator.getUserService().buildTkUser("admin", TKUtils.getCurrentDate());
		TKContext.setUser(user);
		// creator and user are the same person
		TimeBlock tb = new TimeBlock();
		tb.setClockLogCreated(false);
		tb.setJobNumber(new Long(30));
		tb.setUserPrincipalId("admin");
		
		Boolean editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		assertEquals("TimeBlock created by admin should be editable by admin", true, editable);

		// creator and user are different, but user is a system admin
		tb.setUserPrincipalId("fran");
		editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		assertEquals("TimeBlock created by fran should be editable by admin", true, editable);

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
		assertEquals("TimeBlock created by Clock in/out should NOT be editable by fran", false, editable);
		
	}

}
