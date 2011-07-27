package org.kuali.hr.time.timeblock.service;

import java.util.Map;

import org.junit.Test;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.detail.web.TimeDetailAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

public class TimeBlockServiceTest extends TkTestCase {
	@Test
	public void testBuildAssignmentStyleClassMap() {
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(TKUtils.getCurrentDate());
		Map<String, String> aMap = ActionFormUtils.buildAssignmentStyleClassMap(doc);
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
		String editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable("admin");
		assertEquals("TimeBlock created by admin should be editable by admin", "true", editable);

		// creator and user are different, but user is a system admin
		editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable("fran");
		assertEquals("TimeBlock created by fran should be editable by admin", "true", editable);

		// login as fran
		user = TkServiceLocator.getUserService().buildTkUser("fran", TKUtils.getCurrentDate());
		TKContext.setUser(user);
		// creator and user are different, user is not a system admin
		editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable("admin");
		assertEquals("TimeBlock created by admin should NOT be editable by fran", "false", editable);
	}

}
