package org.kuali.hr.time.timeblock.service;

import java.util.Map;

import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;

public class TimeBlockServiceTest extends TkTestCase {
	@Test
	public void testBuildAssignmentStyleClassMap() {
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(TKUtils.getCurrentDate());
		Map<String, String> aMap = TkServiceLocator.getTimeBlockService().buildAssignmentStyleClassMap(doc);
		assertEquals("Wrong number of classes in style class map", 8, aMap.size());
		assertEquals("Wrong key for class assignment0", "assignment0", aMap.get("1_1234_1"));
		assertEquals("Wrong key for class assignment7", "assignment7", aMap.get("6_1100_5"));
	}

}
